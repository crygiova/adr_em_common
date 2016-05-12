package fi.aalto.itia.adr_em_common;

public class SimulationMessageFactory {

	
	//TODO instead of empty probably would be better to put the first UpdateMessageContent
	public static SimulationMessage getRegisterMessage(String sender,
			String receiver) {
		return new SimulationMessage(sender, receiver,
				ADR_EM_Common.REG_HEADER, ADR_EM_Common.EMPTY);
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
	
}
