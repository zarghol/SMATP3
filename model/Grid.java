package SMATP3.model;

import SMATP3.utils.IObservable;
import SMATP3.utils.Observable;
import SMATP3.utils.Observer;
import SMATP3.utils.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid extends Snapshot implements IObservable {

	private final Object lockAgents = new Object();
	private final Observable observable = new Observable();

	private Map<Integer, Agent> agents;

	public Grid(int gridSize) {
		super(gridSize);
		agents = new HashMap<Integer, Agent>();
	}

	public void addAgent(Agent agent) {
		if (this.isPositionValid(agent.getPosition())) {
			synchronized (this.lockAgents) {
				this.agents.put(agent.getId(), agent);
			}
			synchronized (this.lockPositions) {
				this.positions.put(agent.getPosition(), agent.getId());
			}
		}
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
	
	/**
	 * Déplace un agent d'une case à une autre.
	 *
	 * @param from Position d'origine de l'agent.
	 * @param to   Position de destination de l'agent.
	 */
	public boolean moveAgent(Position from, Position to) {
		// TODO: revoir
		int agentId = this.getAgentId(from);
		synchronized (this.lockPositions) {
			if (!this.isPositionOccupied(to) && this.isPositionValid(to)) {
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
	 * Définit si les agents décrivent leurs actions dans la console.
	 * @param verbose
	 */
	public void setVerbose(boolean verbose) {
		for(Agent agent : agents.values()) {
			agent.setVerbose(verbose);
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
