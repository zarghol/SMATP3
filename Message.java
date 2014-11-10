package SMATP3;

public class Message {
	private Agent emitter; // Agent
	private Agent recipient; // Agent
	private static String performative = "Request";
	private static String action = "move";
	private Position startPosition;
	private Position aimPosition;

	public Message(Agent emitter, Agent recipient) {
		this.emitter = emitter;
		this.recipient = recipient;
		this.startPosition = emitter.getPosition();
		this.aimPosition = recipient.getPosition();
	}

	public Agent getEmitter() {
		return emitter;
	}

	public Agent getRecipient() {
		return recipient;
	}

	public Position getStartPosition() {
		return startPosition;
	}

	public Position getAimPosition() {
		return aimPosition;
	}
}
