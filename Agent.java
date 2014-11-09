package SMATP3;

import java.util.List;



public class Agent extends Thread {
	private int agentId;
	private Position position;
	private Position positionBut;
	
	private PerceivedEnvironment currentEnvironment;
	
	
	public void sendMessage(Agent toAgent) {
		// envoyer message
		Message m = new Message(this, toAgent);
		MailBox.getInstance().postMessage(m);
	}
	
	public void percevoirEnvironnement() {
		this.currentEnvironment = PerceivedEnvironment.getPerceptionFromAgent(this);
	}
	
	public void modifierEnvironnement() {
		
	}
	
	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitue
        while(!this.isHappy()) {
        	this.percevoirEnvironnement();
        	if (this.currentEnvironment.getMessagesToRead().size() > 0) {
        		
        	} else {
        		Direction toFollow = Direction.directionDifferential(position, positionBut);
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
	
	public int getAgentId() {
		return this.agentId;
	}
	
	public boolean isHappy() {
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
