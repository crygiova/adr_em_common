package fi.aalto.itia.adr_em_common;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
public abstract class SimulationElementWithDelays implements Serializable, Runnable {

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
    // two routing key for the broadcast and the point to point
    protected static final String broadcastRouting = "BROADCAST";
    // myRouting is same as the queue name
    protected String myRouting;

    protected Channel dRChannel = null;
    // Count down to communicate that the current R0Abstract has finished it s
    // own tasks,
    protected CountDownLatch endOfSimulationTasks;
    protected boolean keepGoing;

    // Delays var and const
    private boolean commWithDelays = ADR_EM_Common.MSG_DELAYS;
    private Random rand = new Random();
    // constant delay in ms
    private static final int CONSTANT_DELAY = ADR_EM_Common.MSG_DELAY_CONSTANT;
    // variable delay in seconds
    private static final int VAR_DELAY = ADR_EM_Common.MSG_DELAY_VARIABLE;

    public SimulationElementWithDelays(String inputQueueName) {
	super();
	this.simulationToken = new SynchronousQueue<Integer>();
	this.messageQueue = new LinkedBlockingQueue<SimulationMessage>();
	this.inputQueueName = inputQueueName;
	// XXX New
	this.myRouting = this.inputQueueName;
	// XXX
	this.keepGoing = true;
	try {
	    dRChannel = createMqChannel();
	    // XXX New
	    Map<String, Object> args = new HashMap<String, Object>();
	    args.put("x-delayed-type", "direct");
	    dRChannel.exchangeDeclare(EXCHANGE_NAME, "x-delayed-message", true, false, args);
	    // XXX
	    // dRChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	    dRChannel.queueDeclare(inputQueueName, false, false, false, null);
	    // XXX New
	    // point-to-poit
	    dRChannel.queueBind(this.inputQueueName, EXCHANGE_NAME, this.myRouting);
	    // broadcast
	    dRChannel.queueBind(this.inputQueueName, EXCHANGE_NAME, broadcastRouting);
	    // XXX

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
		    AMQP.BasicProperties properties, byte[] body) throws IOException {
		SimulationMessage sm = null;
		try {
		    sm = (SimulationMessage) SimulationMessage.deserialize(body);
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

    // true if the msgQueue is empty
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

	byte[] body;
	try {
	    Map<String, Object> headers = new HashMap<String, Object>();
	    headers.put("x-delay", this.getMsgDelay());
	    // headers.put("x-delay", counter * DELAY_MAX * DELAY_SEC);
	    AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder()
		    .headers(headers);
	    props.correlationId(corrId).replyTo(this.inputQueueName).build();
	    body = SimulationMessage.serialize(msg);
	    // queue receiver as the routing key
	    dRChannel.basicPublish(EXCHANGE_NAME, queueReceiver, props.build(), body);
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    // Send a msg to a specified SimulationElement
    /**
     * @param peer
     * @param msg
     * @throws IOException
     */
    public void sendMessage(SimulationMessage msg, boolean forceDelay) {
	String queueReceiver = msg.getReceiver();
	String corrId = java.util.UUID.randomUUID().toString();

	byte[] body;
	try {

	    int delay;
	    if (forceDelay) {
		delay = this.getMsgDelay();
	    } else {
		delay = 0;
	    }
	    Map<String, Object> headers = new HashMap<String, Object>();
	    headers.put("x-delay", delay);
	    // headers.put("x-delay", counter * DELAY_MAX * DELAY_SEC);
	    AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder()
		    .headers(headers);
	    props.correlationId(corrId).replyTo(this.inputQueueName).build();
	    body = SimulationMessage.serialize(msg);
	    // queue receiver as the routing key
	    dRChannel.basicPublish(EXCHANGE_NAME, queueReceiver, props.build(), body);
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    public void publishMessage(SimulationMessage msg) {
	String corrId = java.util.UUID.randomUUID().toString();

	byte[] body;
	try {
	    Map<String, Object> headers = new HashMap<String, Object>();
	    headers.put("x-delay", this.getMsgDelay());
	    // headers.put("x-delay", counter * DELAY_MAX * DELAY_SEC);
	    AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder()
		    .headers(headers);
	    props.correlationId(corrId).replyTo(this.inputQueueName).build();
	    body = SimulationMessage.serialize(msg);
	    dRChannel.basicPublish(EXCHANGE_NAME, broadcastRouting, props.build(), body);
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    // get the delay for the message
    private int getMsgDelay() {
	if (commWithDelays)
	    return CONSTANT_DELAY + rand.nextInt(VAR_DELAY);
	// No delay return
	return 0;
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
}
