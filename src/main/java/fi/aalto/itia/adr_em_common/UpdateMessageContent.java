package fi.aalto.itia.adr_em_common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

public class UpdateMessageContent implements Serializable, Comparable<UpdateMessageContent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8089216898281578784L;

	private Double currentConsumption;// in W
	private Double possibleCut;// in W
	private Double timeCut;// in sec
	private Double possibleIncrease;// in W
	private Double timeIncrease;// in sed
	private final Timestamp timeOfMessage;
	private final String consumerSender;

	// Empty
	public UpdateMessageContent(String sender) {
		super();
		this.currentConsumption = 0d;
		this.possibleCut = 0d;
		this.timeCut = 0d;
		this.possibleIncrease = 0d;
		this.timeIncrease = 0d;
		this.consumerSender = sender;
		// NOW
		this.timeOfMessage = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param currentConsumption
	 * @param possibleCut
	 * @param timeCut
	 * @param possibleIncrease
	 * @param timeIncrease
	 */
	public UpdateMessageContent(Double currentConsumption, Double possibleCut,
			Double timeCut, Double possibleIncrease, Double timeIncrease,
			String sender) {
		super();
		this.currentConsumption = currentConsumption;
		this.possibleCut = possibleCut;
		this.timeCut = timeCut;
		this.possibleIncrease = possibleIncrease;
		this.timeIncrease = timeIncrease;
		this.consumerSender = sender;
		// NOW
		this.timeOfMessage = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param currentConsumption
	 * @param possibleCut
	 * @param timeCut
	 * @param possibleIncrease
	 * @param timeIncrease
	 * @param timeOfMessage
	 */
	public UpdateMessageContent(Double currentConsumption, Double possibleCut,
			Double timeCut, Double possibleIncrease, Double timeIncrease,
			Timestamp timeOfMessage, String sender) {
		super();
		this.currentConsumption = currentConsumption;
		this.possibleCut = possibleCut;
		this.timeCut = timeCut;
		this.possibleIncrease = possibleIncrease;
		this.timeIncrease = timeIncrease;
		this.timeOfMessage = timeOfMessage;
		this.consumerSender = sender;
	}

	public Double getCurrentConsumption() {
		return currentConsumption;
	}

	public void setCurrentConsumption(Double currentConsumption) {
		this.currentConsumption = currentConsumption;
	}

	public Double getPossibleCut() {
		return possibleCut;
	}

	public void setPossibleCut(Double possibleCut) {
		this.possibleCut = possibleCut;
	}

	public Double getTimeCut() {
		return timeCut;
	}

	public void setTimeCut(Double timeCut) {
		this.timeCut = timeCut;
	}

	public Double getPossibleIncrease() {
		return possibleIncrease;
	}

	public void setPossibleIncrease(Double possibleIncrease) {
		this.possibleIncrease = possibleIncrease;
	}

	public Double getTimeIncrease() {
		return timeIncrease;
	}

	public void setTimeIncrease(Double timeIncrease) {
		this.timeIncrease = timeIncrease;
	}

	public Timestamp getTimeOfMessage() {
		return timeOfMessage;
	}

	public String getConsumerSender() {
		return consumerSender;
	}

	public int compareTo(UpdateMessageContent o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** Comparators **/

	public static Comparator<UpdateMessageContent> SortByTimeCutComparator = new Comparator<UpdateMessageContent>() {
		// if same time cut => goes to power cut
		public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
			if (o1.getTimeCut() > o2.getTimeCut())
				return 1;
			if (o1.getTimeCut() < o2.getTimeCut())
				return -1;
			if (o1.getPossibleCut() > o2.getPossibleCut())
				return 1;
			if (o1.getPossibleCut() < o2.getPossibleCut())
				return -1;
			return 0;
		}
	};

	public static Comparator<UpdateMessageContent> SortByTimeIncreaseComparator = new Comparator<UpdateMessageContent>() {
		// order time increase first and then possible increase if the time is
		// the same
		public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
			if (o1.getTimeIncrease() > o2.getTimeIncrease())
				return 1;
			if (o1.getTimeIncrease() < o2.getTimeIncrease())
				return -1;
			if (o1.getPossibleIncrease() > o2.getPossibleIncrease())
				return 1;
			if (o1.getPossibleIncrease() < o2.getPossibleIncrease())
				return -1;
			return 0;
		}
	};

	public static Comparator<UpdateMessageContent> SortByConsumerSenderComparator = new Comparator<UpdateMessageContent>() {
		// compare only by consumer's name
		public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
			return o1.getConsumerSender().compareTo(o2.getConsumerSender());
		}
	};

	@Override
	public String toString() {
		return "UpdateMessageContent [currentConsumption=" + currentConsumption
				+ ", possibleCut=" + possibleCut + ", timeCut=" + timeCut
				+ ", possibleIncrease=" + possibleIncrease + ", timeIncrease="
				+ timeIncrease + ", timeOfMessage=" + timeOfMessage
				+ ", consumerSender=" + consumerSender + "]";
	}
}
