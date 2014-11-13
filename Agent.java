package SMATP3;

public class Agent implements Runnable {
	private static int LAST_AGENT_ID = 0;

	private final int agentId;
	private final Grid grid;
	private final PostOffice postOffice;
	private final Position aimPosition;

	private Position position;
	private Snapshot snapshot;
	
	private ThinkingStragegy strategy;
	
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
	}

	public void sendMessage(int recipient) {
//TODO: Spécifier le type de message. On pourra le construire via un builder...
		Message m = new Message(this.agentId, recipient);
		postOffice.sendMessage(m);
	}
	
	private void handleMessage(Message message) {
		//TODO: handle messages
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

	@Override
	public void run() {
		// Tant que le puzzle n'est pas reconstitue => tant qu'on est pas content : une fois content, on bouge plus ! xD
		while (!this.isHappy()) {
			this.perceiveEnvironment();
			this.strategy.reflexionAction(this.snapshot, this);
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
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public void setStrategy(ThinkingStragegy strategy) {
		this.strategy = strategy;
	}
	
	public String getSymbol() {
		return Agent.getSymbol(this.agentId);
	}
	
	public static String getSymbol(int agentId) {
		char code = (char) ((agentId % 26) + 65);
		Character c = new Character(code);
		return "" + c;
	}
	
	private void talk(String stringToSay) {
		if (this.verbose) {
			System.out.println("agent " + this.agentId + " : " + stringToSay);
		}
	}
}
