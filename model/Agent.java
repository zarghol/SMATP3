package SMATP3.model;

import SMATP3.model.messages.Message;
import SMATP3.model.strategies.ThinkingStrategy;

public class Agent implements Runnable {
	public static int NO_AGENT = -1;
	private static int LAST_AGENT_ID = 0;

	private final int agentId;
	private final Grid grid;
	private final PostOffice postOffice;
	private final Position aimPosition;

	private Position position;
	private Snapshot snapshot;
	
	private ThinkingStrategy strategy;
	
	private boolean verbose;

	public Agent(Grid grid, PostOffice postOffice, Position aimPosition, Position startPosition) {
		this.agentId = LAST_AGENT_ID++;
		this.grid = grid;
		this.postOffice = postOffice;
		this.postOffice.addAgent(this);
		this.aimPosition = aimPosition;
		this.verbose = false;

		this.position = startPosition;
		this.snapshot = null;
		this.strategy = null;
	}

	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitué => tant qu'on n'est pas content : une fois content, on bouge plus ! xD
		while (!this.isHappy()) {
			this.perceiveEnvironment();
			this.talk("strategy : " + this.strategy.getName());
			this.strategy.reflexionAction(this);
		}
	}

	public Message getNewMessage() {
		return new Message(this);
	}

	public void sendMessage(Message message) {
		StringBuilder sb = new StringBuilder();
		for(int id : message.getRecipientIds()) {
			sb.append(" ").append(id);
		}
		this.talk("sending mail to following agents :" + sb.toString());
		postOffice.sendMessage(message);
	}
	
	private void handleMessage(Message message) {
		this.talk("handling mail from Agent " + message.getEmitterId());
		this.strategy.handleMessage(message, this);
	}
	
	public boolean handlePostOffice() {
		Message m = this.postOffice.getNextMessage(this);
		
		if (m != null) {
			this.handleMessage(m);
			return true;
		}
		return false;
	}

	public void perceiveEnvironment() {
		this.talk("getting snapshot");
		this.snapshot = new Snapshot(this.grid);
		this.talk("snapshot received");
	}

	public void move(Position toPosition) {
		this.talk("moving Agent " + this.agentId + " from " + this.position + " to " + toPosition);
		this.grid.moveAgent(this.position, toPosition);
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
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public Snapshot getSnapshot() {
		return this.snapshot;
	}
	
	public void setStrategy(ThinkingStrategy strategy) {
		this.strategy = strategy;
	}
	
	public String getSymbol() {
		return Agent.getSymbol(this.agentId);
	}
	
	public static String getSymbol(int agentId) {
		char code = (char) ((agentId % 26) + 65);
		return Character.toString(code);
	}
	
	private void talk(String stringToSay) {
		if (this.verbose) {
			System.out.println("agent " + this.agentId + " : " + stringToSay);
		}
	}
}
