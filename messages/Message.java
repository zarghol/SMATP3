package SMATP3.messages;

public abstract class Message {
	private final String performative;
	private final Action action;
	private final int emitterId;
	private final int recipientId;
//	private Position startPosition;
//	private Position aimPosition;

	public Message(String performative, Action action, int emitterId, int recipientId) {
		this.performative = performative;
		this.action = action;
		this.emitterId = emitterId;
		this.recipientId = recipientId;
//		this.startPosition = emitterId.getPosition();
//		this.aimPosition = recipientId.getPosition();
	}

	public int getEmitterId() {
		return emitterId;
	}

	public int getRecipientId() {
		return recipientId;
	}

//	public Position getStartPosition() {
//		return startPosition;
//	}

//	public Position getAimPosition() {
//		return aimPosition;
//	}
}
