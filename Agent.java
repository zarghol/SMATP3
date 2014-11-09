package SMATP3;

import java.util.ArrayList;


public class Agent extends Thread {
	ArrayList<Agent> agents;
	Grille puzzle;
    MailBox mailbox;
	private Position position;
	private Position positionBut;
	
	
	public void communiquer() {
		// envoyer message
	}
	
	public void percevoirEnvironnement() {
		
	}
	
	public void modifierEnvironnement() {
		
	}
	
	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitue
        while(!this.isHappy()) {
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
