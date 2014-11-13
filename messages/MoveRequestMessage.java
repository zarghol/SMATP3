package SMATP3.messages;

public class MoveRequestMessage extends RequestMessage {
	public MoveRequestMessage(int emitterId, int recipientId) {
		super(Action.MOVE, emitterId, recipientId);
	}
}
