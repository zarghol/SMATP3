package SMATP3;

public class MessageBuilder {
	public static Message createMessage(boolean askToMove, int emitter, int recipient) {
		Message m = null;
		
		if (askToMove) {
			m = new Message(emitter, recipient, "Request", "move");
		} else {
			m = new Message(emitter, recipient);
		}
		
		return m;
	}
}
