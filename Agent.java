package SMATP3;

public class Agent implements Runnable {
	private static int LAST_AGENT_ID = 0;

	private final int agentId;
	private final Grid grid;
	private final PostOffice postOffice;
	private final Position aimPosition;

	private Position position;
	private Grid snapshot;

	public Agent(Grid grid, PostOffice postOffice, Position aimPosition, Position startPosition) {
		this.agentId = LAST_AGENT_ID++;
		this.grid = grid;
		this.postOffice = postOffice;
		this.postOffice.addAgent(this);
		this.aimPosition = aimPosition;

		this.position = startPosition;
		this.snapshot = null;
	}

	public void sendMessage(Agent recipient) {
		Message m = new Message(this, recipient);
		postOffice.sendMessage(m);
	}

	public void perceiveEnvironment() {
		this.snapshot = (Grid) this.grid.clone();
	}

	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitue => tant qu'on est pas content : une fois content, on bouge plus ! xD
		while (!this.isHappy()) {
			this.perceiveEnvironment();
			if (this.postOffice.getNextMessage(this) != null) {
				// TODO: handle messages
			} else {
				Direction toFollow = Direction.directionDifferential(position, aimPosition);
				Position newPosition = position.move(toFollow);
				if (this.snapshot.isPositionOccupied(newPosition)) {
					this.sendMessage(this.snapshot.getAgent(newPosition));
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
