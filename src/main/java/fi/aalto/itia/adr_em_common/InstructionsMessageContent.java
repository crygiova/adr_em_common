package fi.aalto.itia.adr_em_common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

public class InstructionsMessageContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5879250292700574071L;
	// TODO think about the content in here!
	private Double underNominalDecrease;// in W
	private Double underNominalFrequency;// in Hz
	private Double aboveNominalIncrease;// in W
	private Double aboveNominalFrequency;// in sed
	private final Timestamp timeOfMessage;
	private final String consumerReceiver;

	// Empty
	public InstructionsMessageContent(String sender) {
		super();
		this.underNominalDecrease = 0d;
		this.underNominalFrequency = 0d;
		this.underNominalFrequency = 0d;
		this.aboveNominalFrequency = 0d;
		this.consumerReceiver = sender;
		// NOW
		this.timeOfMessage = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param underNominalDecrease
	 * @param underNominalFrequency
	 * @param aboveNominalIncrease
	 * @param aboveNominalFrequency
	 * @param consumerReceiver
	 */
	public InstructionsMessageContent(Double underNominalDecrease,
			Double underNominalFrequency, Double aboveNominalIncrease,
			Double aboveNominalFrequency, String consumerReceiver) {
		super();
		this.underNominalDecrease = underNominalDecrease;
		this.underNominalFrequency = underNominalFrequency;
		this.aboveNominalIncrease = aboveNominalIncrease;
		this.aboveNominalFrequency = aboveNominalFrequency;
		this.consumerReceiver = consumerReceiver;
		this.timeOfMessage = new Timestamp(System.currentTimeMillis());
	}

	public Double getUnderNominalDecrease() {
		return underNominalDecrease;
	}

	public void setUnderNominalDecrease(Double underNominalDecrease) {
		this.underNominalDecrease = underNominalDecrease;
	}

	public Double getUnderNominalFrequency() {
		return underNominalFrequency;
	}

	public void setUnderNominalFrequency(Double underNominalFrequency) {
		this.underNominalFrequency = underNominalFrequency;
	}

	public Double getAboveNominalIncrease() {
		return aboveNominalIncrease;
	}

	public void setAboveNominalIncrease(Double aboveNominalIncrease) {
		this.aboveNominalIncrease = aboveNominalIncrease;
	}

	public Double getAboveNominalFrequency() {
		return aboveNominalFrequency;
	}

	public void setAboveNominalFrequency(Double aboveNominalFrequency) {
		this.aboveNominalFrequency = aboveNominalFrequency;
	}

	public Timestamp getTimeOfMessage() {
		return timeOfMessage;
	}

	public String getConsumerReceiver() {
		return consumerReceiver;
	}

	public static Comparator<InstructionsMessageContent> DescSortByUnderFrequency = new Comparator<InstructionsMessageContent>() {
		// order time increase first and then possible increase if the time is
		// the same
		public int compare(InstructionsMessageContent o1,
				InstructionsMessageContent o2) {
			if (o1.getUnderNominalFrequency() > o2.getUnderNominalFrequency())
				return -1;
			if (o1.getUnderNominalFrequency() < o2.getUnderNominalFrequency())
				return 1;
			return 0;
		}
	};

	public static Comparator<InstructionsMessageContent> AscSortByUnderFrequency = new Comparator<InstructionsMessageContent>() {
		// order time increase first and then possible increase if the time is
		// the same
		public int compare(InstructionsMessageContent o1,
				InstructionsMessageContent o2) {
			return 0 - DescSortByUnderFrequency.compare(o1, o2);
		}
	};

	public static Comparator<InstructionsMessageContent> DescSortByAboveFrequency = new Comparator<InstructionsMessageContent>() {
		// order time increase first and then possible increase if the time is
		// the same
		public int compare(InstructionsMessageContent o1,
				InstructionsMessageContent o2) {
			if (o1.getAboveNominalFrequency() > o2.getAboveNominalFrequency())
				return -1;
			if (o1.getAboveNominalFrequency() < o2.getAboveNominalFrequency())
				return 1;
			return 0;
		}
	};

	public static Comparator<InstructionsMessageContent> AscSortByAboveFrequency = new Comparator<InstructionsMessageContent>() {
		// order time increase first and then possible increase if the time is
		// the same
		public int compare(InstructionsMessageContent o1,
				InstructionsMessageContent o2) {
			return 0 - DescSortByAboveFrequency.compare(o1, o2);
		}
	};

	@Override
	public String toString() {
		return "InstructionsMessageContent [underNominalDecrease="
				+ underNominalDecrease + ", underNominalFrequency="
				+ underNominalFrequency + ", aboveNominalIncrease="
				+ aboveNominalIncrease + ", aboveNominalFrequency="
				+ aboveNominalFrequency + ", timeOfMessage=" + timeOfMessage
				+ ", consumerReceiver=" + consumerReceiver + "]";
	}

}
