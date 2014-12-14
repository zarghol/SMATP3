package SMATP3.model;

import SMATP3.model.strategies.Strategy;
import SMATP3.utils.IObservable;
import SMATP3.utils.Observable;
import SMATP3.utils.Observer;
import SMATP3.utils.Position;

import java.util.*;

public class Grid extends Snapshot implements IObservable {

	public final static int DEFAULT_GRID_SIZE = 5;
	public final static int DEFAULT_AGENT_COUNT = 4;

	private final Object lockAgents = new Object();
	private final Observable observable = new Observable();
	private final Snapshot initialState;

	private Map<Integer, Agent> agents = new HashMap<>();

	/**
	 * Constructeur générant les agents aléatoirement.
	 *
	 * @param gridSize   La taille de la grille.
	 * @param agentCount Le nombre d'agents à générer.
	 */
	public Grid(int gridSize, int agentCount, Strategy strategy) {
		super(gridSize);
		PostOffice postOffice = new PostOffice();
		Random random = new Random();
		Position startPosition, aimPosition;

		while (this.positions.size() < agentCount) {
			do {
				startPosition = new Position(random.nextInt(gridSize), random.nextInt(gridSize));
			} while(this.positions.containsKey(startPosition));
			do {
				aimPosition = new Position(random.nextInt(gridSize), random.nextInt(gridSize));
			} while(this.aims.containsKey(aimPosition));
			this.addAgent(new Agent(this, postOffice, aimPosition, startPosition));
		}
		this.currentStrategy = strategy;
		this.applyStrategy();
		this.initialState = this.getSnapshot();
	}
	
	public Grid(int gridSize, int agentCount) {
		this(gridSize, agentCount, Strategy.BASE_STRATEGY);
	}

	public Grid(Snapshot snapshot) {
		super(snapshot);
		PostOffice postOffice = new PostOffice();

		for(int i=0; i<this.positions.size(); i++) {
			Position startPosition = null;
			Position aimPosition = null;
			for (Map.Entry<Position, Integer> entry : this.positions.entrySet()) {
				if (entry.getValue() == i) {
					startPosition = entry.getKey();
					break;
				}
			}
			for (Map.Entry<Position, Integer> entry : this.aims.entrySet()) {
				if (entry.getValue() == i) {
					aimPosition = entry.getKey();
					break;
				}
			}
			this.addAgent(new Agent(this, postOffice, aimPosition, startPosition));
		}
		this.applyStrategy();
		this.initialState = this.getSnapshot();
	}
	

	public int getAgentCount() {
		return agents.size();
	}

	/**
	 * Ajoute un agent à la grille.
	 *
	 * @param agent L'agent à ajouter. L'agent doit avoir des position de départ et de destination valides.
	 */
	private void addAgent(Agent agent) {
		synchronized (this.lockPositions) {
			this.positions.put(agent.getPosition(), agent.getId());
		}
		synchronized (this.lockAims) {
			this.aims.put(agent.getAimPosition(), agent.getId());
		}
		synchronized (this.lockAgents) {
			this.agents.put(agent.getId(), agent);
		}
	}

	public Agent getAgent(int agentId) {
		synchronized (this.lockAgents) {
			return this.agents.get(agentId);
		}
	}
	
	public void setCurrentStrategy(Strategy selectedStrategy) {
		this.currentStrategy = selectedStrategy;	
		this.applyStrategy();
	}

	/**
	 * Déplace un agent d'une case à une autre.
	 *
	 * @param from Position d'origine de l'agent.
	 * @param to   Position de destination de l'agent.
	 */
	public boolean moveAgent(Position from, Position to) {
		int agentId = this.getAgentId(from);
		synchronized (this.lockPositions) {
			if (this.isPositionValid(to) && !this.isPositionOccupied(to)) {
				this.positions.remove(from);
				this.positions.put(to, agentId);
				this.setDirty();
				this.notifyObservers(NotificationCode.AGENT_MOVED);

				return true;
			}
			return false;
		}
	}

	/**
	 * Applique une stratégie donnée aux agents.
	 */
	private void applyStrategy() {
		System.out.println("application de la strategy");
		synchronized (lockAgents) {
			this.currentStrategy.apply(this, this.agents.values());
		}
	}

	/**
	 * Définit si les agents décrivent leurs actions dans la console.
	 *
	 * @param verbose
	 */
	public void setVerbose(boolean verbose) {
		for (Agent agent : agents.values()) {
			agent.setVerbose(verbose);
		}
	}
	
	
	public int getGlobalMoves() {
		int sumMove = 0;
		for (Agent a : this.agents.values()) {
			sumMove += a.getNbMove();
		}
		return sumMove;
	}

	/**
	 * Définit le temps d'attente entre chaque action d'un agent.
	 *
	 * @param latency Temps entre deux actions d'un agent.
	 */
	public void setLatency(long latency) {
		for (Agent agent : agents.values()) {
			agent.setLatency(latency);
		}
	}

	/**
	 * Lance la résolution du puzzle.
	 */
	public void start() {
		synchronized (this.lockAgents) {
			for (Agent agent : this.agents.values()) {
				agent.start();
			}
		}
	}

	/**
	 * Arrête la résolution du puzzle.
	 */
	public void stop() {
		synchronized (this.lockAgents) {
			for (Agent agent : this.agents.values()) {
				agent.stop();
			}
		}
	}

	/**
	 * Determine si le puzzle est terminé.
	 *
	 * @return True si le puzzle est résolu. False sinon.
	 */
	public boolean isSolved() {
		synchronized (this.lockAgents) {
			for (Agent a : this.agents.values()) {
				if (!a.isHappy()) {
					return false;
				}
			}
		}
		return true;
	}

	public enum NotificationCode {
		AGENT_MOVED,
		PUZZLE_SOLVED;
	}

	public Snapshot getSnapshot() {
		return new Snapshot(this);
	}

	public Snapshot getInitialState() {
		return this.initialState;
	}

	@Override
	public void registerObserver(Observer observer) {
		observable.registerObserver(observer);
	}

	@Override
	public void unregisterObserver(Observer observer) {
		observable.unregisterObserver(observer);
	}

	@Override
	public void notifyObservers(Object arg) {
		observable.notifyObservers(arg);
	}

	@Override
	public void setDirty() {
		observable.setDirty();
	}

	
}
