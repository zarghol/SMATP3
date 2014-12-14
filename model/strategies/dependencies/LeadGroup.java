package SMATP3.model.strategies.dependencies;

import java.util.Collection;
import java.util.Random;

import SMATP3.model.Agent;
import SMATP3.model.Grid;

public class LeadGroup extends AbstractGroup {
	private int leader;

	public LeadGroup(Grid grid, int... integers) {
		super(grid, integers);
		this.electLeader();
	}
	
	public LeadGroup(Grid grid, Collection<Agent> agents) {
		super(grid, agents);
		this.electLeader();
	}
	
	@Override
	public void addAgent(int id) {
		super.addAgent(id);
		this.electLeader();
	}
	
	public int getLeader() {
		return this.leader;
	}
	
	public void electLeader() {
		Random r = new Random();
		this.leader = r.nextInt(this.agents.size());
	}

}
