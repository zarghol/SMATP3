package SMATP3.messages;

public class RequestMessage extends Message {
	public RequestMessage(Action action, int emitterId, int recipientId) {
		super("Request", action, emitterId, recipientId);
	}
}
