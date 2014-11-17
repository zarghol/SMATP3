package SMATP3.model;

import SMATP3.model.messages.Message;
import SMATP3.model.strategies.ThinkingStrategy;
import SMATP3.utils.Position;

//TODO: Gros boulot sur la synchronisation Agent-Grid de la position de l'agent...
public class Agent implements Runnable {
	public static int NO_AGENT = -1;
	private static int LAST_AGENT_ID = 0;

	private final Object lockRunning = new Object();
	private final Object lockVerbose = new Object();
	private final Object lockLatency = new Object();
	private final int agentId;
	private final Grid grid;
	private final PostOffice postOffice;
	private final Position aimPosition;

	private Position position;
	private Snapshot snapshot;
	private ThinkingStrategy strategy;
	private long latency = 500;
	private boolean verbose = false;
	private boolean running = false;

	public Agent(Grid grid, PostOffice postOffice, Position aimPosition, Position startPosition) {
		this.agentId = LAST_AGENT_ID++;
		this.grid = grid;
		this.postOffice = postOffice;
		this.postOffice.addAgent(this);
		this.aimPosition = aimPosition;
		this.position = startPosition;
		this.snapshot = null;
		this.strategy = null;
	}

	@Override
	public void run() {
		boolean goOn = true;
		long waitingTime;
		while (goOn) {
			this.perceiveEnvironment();
			this.talk("strategy : " + this.strategy.getName());
			this.strategy.reflexionAction(this);

			
			synchronized (lockLatency) {
				waitingTime = latency;
			}

			synchronized (lockRunning) {
				goOn = running;
			}
			goOn = goOn && !this.grid.isSolved();

			try {
				Thread.sleep(waitingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
				goOn = false;
			}
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

	public void sendMessage(Message message) {
		StringBuilder sb = new StringBuilder();
		for (int id : message.getRecipientIds()) {
			sb.append(" ").append(id);
		}
		this.talk("sending mail to following agents :" + sb.toString());
		postOffice.sendMessage(message);
	}

	public boolean handleMessages() {
		Message message = this.postOffice.getNextMessage(this);

		if (message != null) {
			this.talk("handling mail from Agent " + message.getEmitterId());
			this.strategy.handleMessage(message, this);
			return true;
		}
		return false;
	}

	private void perceiveEnvironment() {
		this.talk("getting snapshot");
		this.snapshot = this.grid.getSnapshot();
		this.talk("snapshot received");
	}

	public void move(Position toPosition) {
		if (this.grid.moveAgent(this.position, toPosition)) {
			this.talk("moving from " + this.position + " to " + toPosition);
			this.position = new Position(toPosition);
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

	public void setLatency(int latency) {
		synchronized (lockLatency) {
			this.latency = latency;
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
}
