package SMATP3.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid extends Snapshot {

	private final Object lockAgents = new Object();

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

	public void launch(boolean verbose) {
		synchronized (this.lockAgents) {
			for (Agent agent : this.agents.values()) {
				agent.setVerbose(verbose);
				Thread t = new Thread(agent);
				t.run();
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
}
