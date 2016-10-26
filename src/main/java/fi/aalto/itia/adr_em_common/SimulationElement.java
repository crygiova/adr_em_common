package fi.aalto.itia.adr_em_common;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @author giovanc1
 *
 */
public abstract class SimulationElement implements Serializable, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 55L;
	// TODO make it client server, means that the AG can have a list of clients
	// which
	// Queue to take the token from and release it from the Simulator
	protected SynchronousQueue<Integer> simulationToken;
	// Message queue
	protected LinkedBlockingQueue<SimulationMessage> messageQueue;
	// Input queue
	protected String inputQueueName;
	private Connection connection;
	protected static final String EXCHANGE_NAME = "Ag_Publisher";
	protected Channel dRChannel = null;
	// Count down to communicate that the current R0Abstract has finished it s
	// own tasks,
	protected CountDownLatch endOfSimulationTasks;
	protected boolean keepGoing;
	
	public SimulationElement(String inputQueueName) {
		super();
		this.simulationToken = new SynchronousQueue<Integer>();
		this.messageQueue = new LinkedBlockingQueue<SimulationMessage>();
		this.inputQueueName = inputQueueName;
		this.keepGoing = true;
		try {
			dRChannel = createMqChannel();
			dRChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			dRChannel.queueDeclare(inputQueueName, false, false, false, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean isKeepGoing() {
		return keepGoing;
	}

	public void setKeepGoing(boolean keepGoing) {
		this.keepGoing = keepGoing;
	}

	public String getInputQueueName() {
		return inputQueueName;
	}

	// TODO change this one when there is a message coming in for each actor
	public void startConsumingMq() {
		Consumer consumer = new DefaultConsumer(dRChannel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				SimulationMessage sm = null;
				try {
					sm = (SimulationMessage) SimulationMessage
							.deserialize(body);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (sm != null) {
					System.out.println(sm.toString());
					addMessage(sm);
				}
			}
		};
		try {
			dRChannel.basicConsume(inputQueueName, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is used to schedule the daily tasks of a simulation element
	 */
	public abstract void scheduleTasks();

	/**
	 * Used to execute the tasks for the current hour
	 */
	public abstract void executeTasks();

	/**
	 * This method is used to elaborate the messages received in input fr om
	 * other Simulation elements
	 */
	public abstract void elaborateIncomingMessages();

	// Add a msg to this SimulationElement
	/**
	 * @param msg
	 */
	public void addMessage(SimulationMessage msg) {
		this.messageQueue.add(msg);
	}

	/**
	 * @param timeout
	 * @return
	 */
	public SimulationMessage pollMessageMs(long timeout) {
		try {
			return this.messageQueue.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	//true if the msgQueue is empty
	public boolean isEmptyMessageQueue() {
		return messageQueue.isEmpty();
	}

	// WAits for the next message till it does not arrive
	public SimulationMessage waitForMessage() {
		try {
			return this.messageQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Send a msg to a specified SimulationElement
	/**
	 * @param peer
	 * @param msg
	 * @throws IOException
	 */
	public void sendMessage(SimulationMessage msg) {
		String queueReceiver = msg.getReceiver();
		String corrId = java.util.UUID.randomUUID().toString();
		BasicProperties props = new BasicProperties.Builder()
				.correlationId(corrId).replyTo(this.inputQueueName).build();
		byte[] body;
		try {
			body = SimulationMessage.serialize(msg);
			dRChannel.basicPublish("", queueReceiver, props, body);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void publishMessage(SimulationMessage msg) {
		String corrId = java.util.UUID.randomUUID().toString();

		BasicProperties props = new BasicProperties.Builder()
				.correlationId(corrId).replyTo(this.inputQueueName).build();
		byte[] body;
		try {
			body = SimulationMessage.serialize(msg);
			dRChannel.basicPublish(EXCHANGE_NAME, "", props, body);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Channel createMqChannel() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		Channel channel = null;
		try {
			channel = connection.createChannel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel;
	}

	public void closeConnection() {
		try {
			dRChannel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
