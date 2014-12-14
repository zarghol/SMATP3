package SMATP3.model.strategies.dependencies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import SMATP3.model.Agent;
import SMATP3.model.Grid;

public class Group {
	protected Grid grid;
	protected List<Integer> agents;
	// ordres => message, dans le postOffice
	
	public Group(Grid grid, Collection<Agent> agents) {
		this.grid = grid;
		this.agents = new ArrayList<Integer>();
		if (agents != null) {
			for (Agent a : agents) {
				this.agents.add(a.getId());
			}
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
	
	public boolean containAgent(int id) {
		return this.agents.contains(id);
		
	}

}
