package SMATP3;

public class Message {
//TODO: On va avoir besoin de de différents types de messages => héritage 
	// Tried : useless
	
	// TODO: use Enumerations
	private String performative;
	private String action;

	private final int emitterId;
	private final int recipientId;

	public Message(int emitterId, int recipientId) {
		this.emitterId = emitterId;
		this.recipientId = recipientId;
	}
	
	public Message(int emitterId, int recipientId, String performative, String actionType) {
		this(emitterId, recipientId);

		this.action = actionType;
		this.performative = performative;
	}

	public int getEmitterId() {
		return emitterId;
	}

	public int getRecipientId() {
		return recipientId;
	}

	public String getPerformative() {
		return performative;
	}
	
	public String getAction() {
		return action;
	}
}
