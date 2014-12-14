package SMATP3.model;

import SMATP3.model.messages.ConversationStatus;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;
import SMATP3.model.strategies.ThinkingStrategy;
import SMATP3.utils.Position;

public class Agent implements Runnable {
	public static int NO_AGENT = -1;
	private static int LAST_AGENT_ID = 0;

	private final Object lockRunning = new Object();
	private final Object lockVerbose = new Object();
	private final Object lockLatency = new Object();
	private final int agentId;
	private final Grid grid;
	private final PostOffice postOffice;
	// TODO: revoir : sert à savoir si on est en attente de message. Si oui, on bouge pas, on attends les réponses
	private int numberOfMessagesWaited;
	private final Position aimPosition;

	private Position position;
	private Snapshot snapshot;
	private ThinkingStrategy strategy;
	private long latency = 500;
	private boolean verbose = false;
	private boolean running = false;

	private int nbMove;
	
	public Agent(Grid grid, PostOffice postOffice, Position aimPosition, Position startPosition) {
		this.agentId = LAST_AGENT_ID++;
		this.grid = grid;
		this.postOffice = postOffice;
		this.postOffice.addAgent(this);
		this.numberOfMessagesWaited = 0;
		this.aimPosition = aimPosition;
		this.position = startPosition;
		this.snapshot = null;
		this.strategy = null;
		
		this.nbMove = 0;
	}

	@Override
	public void run() {
		boolean goOn = true;
		long waitingTime;
		while (goOn) {
			this.perceiveEnvironment();
			this.strategy.reflexionAction(this);

			synchronized (lockLatency) {
				waitingTime = latency;
			}

			synchronized (lockRunning) {
				goOn = running;
			}

			if(this.grid.isSolved()) {
				this.grid.setDirty();
				this.grid.notifyObservers(Grid.NotificationCode.PUZZLE_SOLVED);
				goOn = false;
			}

			try {
				Thread.sleep(waitingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
				goOn = false;
			}
		}
		synchronized (lockRunning) {
			running = false;
		}
	}

	public void start() {
		synchronized (lockRunning) {
			if (running) {
				return;
			}
			running = true;
		}
		new Thread(this).start();
	}

	public void stop() {
		synchronized (lockRunning) {
			running = false;
		}
	}

	public Message getNewMessage() {
		return new Message(this);
	}

	public void sendMessage(Message message, ConversationStatus status) {
		StringBuilder sb = new StringBuilder();
		for (int id : message.getRecipientIds()) {
			sb.append(" ").append(id);
		}
		this.talk("sending mail to following agents :" + sb.toString());
		// Si c'est une requete, on attend une réponse
		this.handleConversationStatus(status);

		postOffice.sendMessage(message);
	}

	private void handleConversationStatus(ConversationStatus status) {
		switch (status) {
		case BEGAN:
			this.numberOfMessagesWaited++;
			break;
		
		case ENDED:
			this.numberOfMessagesWaited--;
			break;
			
		default:
			break;
		}
	}
	
	
	/**
	 * gère les messages
	 * @return true si on attend encore des réponses, false sinon
	 */
	public boolean handleMessages() {
		Message message = this.postOffice.getNextMessage(this);
		// Tant qu'on a des messages a lire
		while (message != null) {
			this.talk("handling mail from Agent " + message.getEmitterId());
			
			this.handleConversationStatus(this.strategy.handleMessage(message, this));
			
			message = this.postOffice.getNextMessage(this);		
		}
		this.talk("number of messages : " + this.numberOfMessagesWaited);
		return this.numberOfMessagesWaited > 0;
	}

	private void perceiveEnvironment() {
		this.talk("getting snapshot");
		this.snapshot = this.grid.getSnapshot();
	}

	public void move(Position toPosition) {
		if (this.grid.moveAgent(this.position, toPosition)) {
			this.talk("moving from " + this.position + " to " + toPosition);
			this.position = new Position(toPosition);
			this.nbMove++;
		}
	}
	
	private void talk(String stringToSay) {
		boolean v;
		synchronized (lockVerbose) {
			v = verbose;
		}

		if (v) {
			System.out.println("agent " + this.agentId + " : " + stringToSay);
		}
	}
	
	// GETTERS / SETTERS
	
	public double getUtility() {
		double finalUtility = (double) this.nbMove / (double)this.grid.getGlobalMoves();
		if (this.grid.isSolved()) {
			return finalUtility;
		} else if (this.isHappy()){
			return finalUtility - 0.05 * finalUtility;
		} else {
			return finalUtility - 0.1 * finalUtility; // ou 0 ?
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
	
	public int getNbMove() {
		return this.nbMove;
	}

	public void setVerbose(boolean verbose) {
		synchronized (lockVerbose) {
			this.verbose = verbose;
		}
	}

	public Snapshot getSnapshot() {
		return this.snapshot;
	}

	public void setStrategy(ThinkingStrategy strategy) {
		this.strategy = strategy;
	}

	public void setLatency(long latency) {
		synchronized (lockLatency) {
			this.latency = latency;
		}
	}
}
