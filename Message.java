package SMATP3;

public class Message {
//TODO: On va avoir besoin de de différents types de messages => héritage
	private static String performative = "Request";
	private static String action = "move";

	private final int emitterId;
	private final int recipientId;
//	private Position startPosition;
//	private Position aimPosition;

	public Message(int emitterId, int recipientId) {
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
