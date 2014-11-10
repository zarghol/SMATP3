package SMATP3;

public class Agent implements Runnable {
	private static int LAST_AGENT_ID = 0;

	private int agentId;
	private Position position;
	private Position aimPosition;
	private PerceivedEnvironment currentEnvironment;
	private PostOffice postOffice;

	public Agent(PerceivedEnvironment currentEnvironment, PostOffice postOffice, Position aimPosition) {
		this.agentId = LAST_AGENT_ID++;
		this.currentEnvironment = currentEnvironment;
		this.postOffice = postOffice;
		this.aimPosition = aimPosition;
	}

	public void sendMessage(Agent recipient) {
		Message m = new Message(this, recipient);
		postOffice.sendMessage(m);
	}

	public void perceiveEnvironment() {
		this.currentEnvironment = PerceivedEnvironment.getPerceptionFromAgent(this);
	}

	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitue => tant qu'on est pas content : une fois content, on bouge plus ! xD
		while (!this.isHappy()) {
			this.perceiveEnvironment();
			if (this.currentEnvironment.getMessagesToRead().size() > 0) {
				// TODO
			} else {
				Direction toFollow = Direction.directionDifferential(position, aimPosition);
				Position newPosition = toFollow.newPosition(position);
				if (this.currentEnvironment.getGridVision().isPositionOccupied(newPosition)) {
					this.sendMessage(this.currentEnvironment.getGridVision().getAgent(newPosition));
				} else {
					Grid.getInstance().moveAgent(this.position, newPosition);
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

	public void setAimPosition(Position aimPosition) {
		this.aimPosition = aimPosition;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
