package SMATP3.model.strategies.dependencies;

import SMATP3.model.Agent;
import SMATP3.model.Grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractGroup {
	protected Grid grid;
	protected List<Integer> agents;
	// ordres => message, dans le postOffice

	public AbstractGroup(Grid grid, Collection<Agent> agents) {
		this.grid = grid;
		this.agents = new ArrayList<Integer>();
		for (Agent a : agents) {
			this.agents.add(a.getId());
		}
	}

	public List<Agent> getMembers() {
		List<Agent> agents = new ArrayList<Agent>();
		for (Integer i : this.agents) {
			agents.add(this.grid.getAgent(i));
		}

		return agents;
	}

	public void addAgent(int id) {
		this.agents.add(id);
	}
}
