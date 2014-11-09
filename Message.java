package SMATP3;


public class Message {
	private Agent emetteur; // Agent
	private Agent recepteur; // Agent
	private static String performatif = "Request";
	private static String action = "move";
	private Position positionDepart;
	private Position positionArrive;
	
	public Message(Agent emetteur, Agent recepteur) {
		this.emetteur = emetteur;
		this.recepteur = recepteur;
		this.positionDepart = emetteur.getPosition();
		this.positionArrive = recepteur.getPosition();
	}
	
	public Agent getEmetteur() {
		return emetteur;
	}

	public Agent getRecepteur() {
		return recepteur;
	}

	public Position getPositionDepart() {
		return positionDepart;
	}

	public Position getPositionArrive() {
		return positionArrive;
	}
}
