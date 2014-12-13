package SMATP3.model;

import java.util.*;

import SMATP3.model.strategies.SimpleStrategy;
import SMATP3.model.strategies.Strategy;
import SMATP3.model.strategies.ThinkingStrategy;
import SMATP3.utils.IObservable;
import SMATP3.utils.Observable;
import SMATP3.utils.Observer;
import SMATP3.utils.Position;

public class Grid extends Snapshot implements IObservable {

	public final static int DEFAULT_GRID_SIZE = 5;
	public final static int DEFAULT_AGENT_COUNT = 4;

	private final Object lockAgents = new Object();
	private final Observable observable = new Observable();
	
	private Strategy currentStrategy;

	private Map<Integer, Agent> agents;

	/**
	 * Constructeur par défaut.
	 */
	public Grid() {
		this(DEFAULT_GRID_SIZE, DEFAULT_AGENT_COUNT);
	}

	/**
	 * Constructeur. Les agents doivent être ajoutés séparéments.
	 * @param gridSize La taille de la grille.
	 */
	public Grid(int gridSize) {
		super(gridSize);
		this.agents = new HashMap<Integer, Agent>();
		this.currentStrategy = Strategy.SIMPLESTRATEGY;
	}

	/**
	 * Constructeur générant les agents aléatoirement.
	 *
	 * @param gridSize   La taille de la grille.
	 * @param agentCount Le nombre d'agents à générer.
	 */
	public Grid(int gridSize, int agentCount) {
		this(gridSize);
		PostOffice postOffice = new PostOffice();
		
		// utilisé pour vérifier les positions
		HashMap<Position, Position> positionsAgents = new HashMap<Position, Position>();
		
		while (positionsAgents.size() < agentCount) {
			Random r = new Random();
			Position startPosition = new Position(r.nextInt(this.getGridSize()), r.nextInt(this.getGridSize()));
			Position aimPosition = new Position(r.nextInt(this.getGridSize()), r.nextInt(this.getGridSize()));
			if (!positionsAgents.containsKey(startPosition) && !positionsAgents.containsValue(aimPosition)) {
				positionsAgents.put(startPosition, aimPosition);
				this.addAgent(new Agent(this, postOffice, aimPosition, startPosition));
			}
		}
		this.applyStrategy();
	}
	
	public Grid(int gridSize, int agentCount, Strategy strategy) {
		this(gridSize, agentCount);
		this.currentStrategy = strategy;
		this.applyStrategy();
	}
	

	public int getAgentCount() {
		return agents.size();
	}

	/**
	 * Ajoute un agent à la grille.
	 *
	 * @param agent L'agent à ajouter.
	 * @return True si l'agent a été ajouté. False sinon (position de départ ou de destination invalide).
	 */
	public boolean addAgent(Agent agent) {
		if (!isPositionValid(agent.getPosition()) || !isPositionValid(agent.getAimPosition())) {
			return false;
		}

		synchronized (this.lockPositions) {
			this.positions.put(agent.getPosition(), agent.getId());
		}
		synchronized (this.lockAgents) {
			this.agents.put(agent.getId(), agent);
		}
		return true;
	}

	public void addAgents(List<Agent> agents) {
		synchronized (this.lockAgents) {
			for (Agent a : agents) {
				this.addAgent(a);
			}
		}
	}

	public Agent getAgent(int agentId) {
		synchronized (this.lockAgents) {
			return this.agents.get(agentId);
		}
	}
	
	public Strategy getCurrentStrategy() {
		return this.currentStrategy;
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
				this.notifyObservers();

				return true;
			}
			return false;
		}
	}

	/**
	 * Applique une stratégie donnée aux agents.
	 *
	 * @param strategy La stratégie à appliquer.
	 */
	private void applyStrategy() {
		System.out.println("application de la strategy");
		synchronized (lockAgents) {
			for (Agent a : agents.values()) {
				try {
					a.setStrategy((ThinkingStrategy) this.currentStrategy.getStrategy().newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					a.setStrategy(new SimpleStrategy());
				}
			}
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

	public Snapshot getSnapshot() {
		return new Snapshot(this);
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
	public void notifyObservers() {
		observable.notifyObservers();
	}

	@Override
	public void setDirty() {
		observable.setDirty();
	}

	
}
