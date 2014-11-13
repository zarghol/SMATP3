package SMATP3;

import SMATP3.messages.Message;
import SMATP3.messages.MoveRequestMessage;

public class Agent implements Runnable {
	private static int LAST_AGENT_ID = 0;

	private final int agentId;
	private final Grid grid;
	private final PostOffice postOffice;
	private final Position aimPosition;

	private Position position;
	private Snapshot snapshot;

	public Agent(Grid grid, PostOffice postOffice, Position aimPosition, Position startPosition) {
		this.agentId = LAST_AGENT_ID++;
		this.grid = grid;
		this.postOffice = postOffice;
		this.postOffice.addAgent(this);
		this.aimPosition = aimPosition;

		this.position = startPosition;
		this.snapshot = null;
	}

	public void sendMessage(Message message) {
//TODO: Spécifier le type de message. On pourra le construire via un builder...


		postOffice.sendMessage(message);
	}

	public void perceiveEnvironment() {
		this.snapshot = new Snapshot(this.grid);
	}

	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitue => tant qu'on est pas content : une fois content, on bouge plus ! xD
		while (!this.isHappy()) {
			this.perceiveEnvironment();
			if (this.postOffice.getNextMessage(this) != null) {
				// TODO: gérer les messages
			} else {
				Direction toFollow = Direction.directionDifferential(position, aimPosition);
				Position newPosition = position.move(toFollow);
				if (this.snapshot.isPositionOccupied(newPosition)) {
					Message message = new MoveRequestMessage(this
							.setRecipientId(recipient);
					this.sendMessage(this.snapshot.getAgentId(newPosition));
				} else {
					this.grid.moveAgent(this.position, newPosition);
				}
			}
			// traiter messages
			// verifier
			// raisonne
			// // si case vers son chemin est libre
			// // // in se déplace
			// // sinon
			// // // on envoit un message
			// effectuer les actions
		}
	}

	public int getId() {
		return this.agentId;
	}

	public boolean isHappy() {
		return this.position.equals(this.aimPosition);
	}

	public Position getAimPosition() {
		return aimPosition;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
