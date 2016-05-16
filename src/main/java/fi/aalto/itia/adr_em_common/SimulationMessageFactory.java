package fi.aalto.itia.adr_em_common;

import java.util.Random;

public class SimulationMessageFactory {

	// TODO instead of empty probably would be better to put the first
	// UpdateMessageContent
	public static SimulationMessage getEmptyRegisterMessage(String sender,
			String receiver) {
		return new SimulationMessage(sender, receiver,
				ADR_EM_Common.REG_HEADER, ADR_EM_Common.EMPTY);
	}

	public static SimulationMessage getRegisterMessage(String sender,
			String receiver, UpdateMessageContent umc) {
		return new SimulationMessage(sender, receiver,
				ADR_EM_Common.REG_HEADER, umc);
	}

	public static SimulationMessage getRegisterDeny(String sender,
			String receiver) {
		return new SimulationMessage(sender, receiver,
				ADR_EM_Common.DENY_REG_HEADER, ADR_EM_Common.EMPTY);
	}

	public static SimulationMessage getRegisterAccept(String sender,
			String receiver) {
		return new SimulationMessage(sender, receiver,
				ADR_EM_Common.ACCEPT_REG_HEADER, ADR_EM_Common.EMPTY);
	}

	public static SimulationMessage getUpdateMessage(String sender,
			String receiver, UpdateMessageContent content) {
		return new SimulationMessage(sender, receiver,
				ADR_EM_Common.STATUS_UPDATE_HEADER, content);
	}

	public static UpdateMessageContent generateUpdateMessage(
			Double currentConsumption, Double possibleCut, Double timeCut,
			Double possibleIncrease, Double timeIncrease, String sender) {
		return new UpdateMessageContent(currentConsumption, possibleCut,
				timeCut, possibleIncrease, timeIncrease, sender);
	}
	
	public static UpdateMessageContent generateRandomUpdateMessage( String sender) {
		Random rand = new Random();
		return new UpdateMessageContent(10 + 90 * rand.nextDouble(), 10 * rand.nextDouble(),
				60* 15 + 15 * rand.nextDouble(), 10 * rand.nextDouble(), 60* 15 + 15 * rand.nextDouble(), sender);
	}
}
