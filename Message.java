package SMATP3;


public class Message {
	private Agent emetteur; // Agent
	private Agent recepteur; // Agent
	static String performatif = "Request";
	static String action = "move";
	private Position positionDepart;
	private Position positionArrive;
	
	public Message(Agent emetteur, Agent recepteur) {
		
	}
}
