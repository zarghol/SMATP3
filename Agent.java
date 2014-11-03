package SMATP3;

import java.util.ArrayList;


public class Agent extends Thread {
	ArrayList<Agent> agents;
	Grille puzzle;
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
		
		// // traiter messages
		// // verifier
		// // raisonne
		// // // si case vers son chemin est libre
		// // // // in se déplace
		// // // sinon
		// // // // on envoit un message
		// // effectuer les actions
	}

	Position getPositionBut() {
		return positionBut;
	}

	void setPositionBut(Position positionBut) {
		this.positionBut = positionBut;
	}

	Position getPosition() {
		return position;
	}

	void setPosition(Position position) {
		this.position = position;
	}
}
