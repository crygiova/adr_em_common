package fi.aalto.itia.adr_em_common;

import java.util.Random;

import org.apache.commons.math3.ml.clustering.DoublePoint;

public class SimulationMessageFactory {

    // TODO instead of empty probably would be better to put the first
    // UpdateMessageContent
    public static SimulationMessage getEmptyRegisterMessage(String sender, String receiver) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.REG_HEADER,
		ADR_EM_Common.EMPTY);
    }

    public static SimulationMessage getEmptyAggToStatsMessage(String sender, String receiver) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.AGG_TO_STATS_HEADER,
		ADR_EM_Common.EMPTY);
    }

    public static SimulationMessage getRegisterMessage(String sender, String receiver,
	    UpdateMessageContent umc) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.REG_HEADER, umc);
    }

    public static SimulationMessage getRegisterDeny(String sender, String receiver) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.DENY_REG_HEADER,
		ADR_EM_Common.EMPTY);
    }

    public static SimulationMessage getRegisterAccept(String sender, String receiver) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.ACCEPT_REG_HEADER,
		ADR_EM_Common.EMPTY);
    }

    public static SimulationMessage getUpdateMessage(String sender, String receiver,
	    UpdateMessageContent content) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.STATUS_UPDATE_HEADER, content);
    }

    public static SimulationMessage getInstructionMessage(String sender, String receiver,
	    InstructionsMessageContent content) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.INSTRUCTIONS_HEADER, content);
    }

    public static SimulationMessage getStatsToAggUpdateMessage(String sender, String receiver,
	    StatsToAggUpdateContent content) {
	return new SimulationMessage(sender, receiver, ADR_EM_Common.STATS_TO_AGG_HEADER, content);
    }

    public static UpdateMessageContent getSemiRandomUpdateMessage(String sender) {
	Random rand = new Random();
	double consumption = 10d + 90 * rand.nextDouble();
	return new UpdateMessageContent(consumption, consumption, 60 * 15 + 15 * rand.nextDouble(),
		60 * 15 + 15 * rand.nextDouble(), consumption, 60 * 15 + 15 * rand.nextDouble(),
		60 * 15 + 15 * rand.nextDouble(), sender, new AgingADRConsumer(sender,
			rand.nextInt()));

    }

    public static UpdateMessageContent getSemiRandomUpdateMessage(String sender, boolean on) {
	Random rand = new Random();
	int minute = 60;
	int time = 10;
	double consumption = 70d;
	if (on) {
	    return new UpdateMessageContent(consumption, consumption, minute * time + minute * time
		    * rand.nextDouble(), minute * time + minute * time * rand.nextDouble(), 0d, 0d,
		    0d, sender, new AgingADRConsumer(sender, rand.nextInt()));
	} else {
	    return new UpdateMessageContent(0d, 0d, 0d, 0d, consumption, minute * time + minute
		    * time * rand.nextDouble(), minute * time + minute * time * rand.nextDouble(),
		    sender, new AgingADRConsumer(sender, rand.nextInt()));
	}
    }

    public static UpdateMessageContent getUpdateMessageContent(Double currentConsumption,
	    Double possibleCut, Double reactionTimeCut, Double timeCut, Double possibleIncrease,
	    Double reactionTimeIncrease, Double timeIncrease, String consumerSender,
	    AgingADRConsumer aging) {
	return new UpdateMessageContent(currentConsumption, possibleCut, reactionTimeCut, timeCut,
		possibleIncrease, reactionTimeIncrease, timeIncrease, consumerSender, aging);
    }

    public static InstructionsMessageContent getInstructionsMessageContent(
	    Double aboveNominalIncrease, Double underNominalDecrease, Double aboveNominalFrequency,
	    Double underNominalFrequency, String consumerReceiver) {
	return new InstructionsMessageContent(underNominalDecrease, underNominalFrequency,
		aboveNominalIncrease, aboveNominalFrequency, consumerReceiver);
    }
}
