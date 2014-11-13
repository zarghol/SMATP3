package SMATP3.messages;

public class ResponseMessage extends Message {
	public ResponseMessage(Action action, int emitterId, int recipientId) {
		super("Response", action, emitterId, recipientId);
	}
}
