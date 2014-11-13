package SMATP3.messages;

/*
TODO: On va avoir besoin de de différents types de messages => héritage
Tried : useless
À discuter en effet. Ca dépend du protocole de communication et de la variété des messages...
*/

public abstract class Message {
	// TODO: use Enumerations
	private final String performative;
	private final Action action;
	private final int emitterId;
	private final int recipientId;

	public Message(String performative, Action action, int emitterId, int recipientId) {
		this.performative = performative;
		this.action = action;
		this.emitterId = emitterId;
		this.recipientId = recipientId;
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
	
	public Action getAction() {
		return action;
	}
}
