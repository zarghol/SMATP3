package SMATP3;

public class Agent implements Runnable {
	private static int LAST_AGENT_ID = 0;

	private int agentId;
	private Position position;
	private Position positionBut;
	private PerceivedEnvironment currentEnvironment;
	private BoiteAuxLettres mailbox;

	public Agent(PerceivedEnvironment currentEnvironment, BoiteAuxLettres mailbox, Position positionBut) {
		this.agentId = LAST_AGENT_ID++;
		this.currentEnvironment = currentEnvironment;
		this.mailbox = mailbox;
		this.positionBut = positionBut;
	}

	public void envoyerMessage(Agent toAgent) {
		Message m = new Message(this, toAgent);
		mailbox.envoyerMessage(m);
	}

	public void percevoirEnvironnement() {
		this.currentEnvironment = PerceivedEnvironment.getPerceptionFromAgent(this);
	}

	public void modifierEnvironnement() {
		// TODO utilisation ? actuellement on fait un moveAgent dans le run
	}

	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitue => tant qu'on est pas content : une fois content, on bouge plus ! xD
		while (!this.estHeureux()) {
			this.percevoirEnvironnement();
			if (this.currentEnvironment.getMessagesToRead().size() > 0) {
				// TODO
			} else {
				Direction toFollow = Direction.directionDifferential(position, positionBut);
				Position newPosition = toFollow.newPosition(position);
				if (this.currentEnvironment.getGridVision().isPositionOccupied(newPosition)) {
					this.envoyerMessage(this.currentEnvironment.getGridVision().getAgent(newPosition));
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

	public boolean estHeureux() {
		return this.position.equals(this.positionBut);
	}

	public Position getPositionBut() {
		return positionBut;
	}

	public void setPositionBut(Position positionBut) {
		this.positionBut = positionBut;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
