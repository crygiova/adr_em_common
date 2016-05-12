package fi.aalto.itia.adr_em_common;

import java.io.Serializable;
import java.sql.Timestamp;

public class UpdateMessageContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8089216898281578784L;

	private Double currentConsumption;
	private Double possibleCut;
	private Double timeCut;
	private Double possibleIncrease;
	private Double timeIncrease;
	private final Timestamp timeOfMessage;

	/**
	 * @param currentConsumption
	 * @param possibleCut
	 * @param timeCut
	 * @param possibleIncrease
	 * @param timeIncrease
	 */
	public UpdateMessageContent(Double currentConsumption, Double possibleCut,
			Double timeCut, Double possibleIncrease, Double timeIncrease) {
		super();
		this.currentConsumption = currentConsumption;
		this.possibleCut = possibleCut;
		this.timeCut = timeCut;
		this.possibleIncrease = possibleIncrease;
		this.timeIncrease = timeIncrease;
		//NOW
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
			Timestamp timeOfMessage) {
		super();
		this.currentConsumption = currentConsumption;
		this.possibleCut = possibleCut;
		this.timeCut = timeCut;
		this.possibleIncrease = possibleIncrease;
		this.timeIncrease = timeIncrease;
		this.timeOfMessage = timeOfMessage;
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

}
