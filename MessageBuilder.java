package SMATP3;

import SMATP3.messages.Message;
import SMATP3.messages.MoveRequestMessage;

public class MessageBuilder {
	public static Message createMessage(boolean askToMove, int emitter, int recipient) {
		Message m = null;
		
		if (askToMove) {
			m = new MoveRequestMessage(emitter, recipient);
		}/* else {
			m = null;
		}*/
		
		return m;
	}
}
