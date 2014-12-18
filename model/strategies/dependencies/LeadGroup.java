package SMATP3.model.strategies.dependencies;

import SMATP3.model.Agent;
import SMATP3.model.Grid;

import java.util.Collection;
import java.util.Random;

public class LeadGroup extends Group {
	private int leader;

	public LeadGroup(Grid grid, Collection<Agent> agents) {
		super(grid, agents);
		if (this.agents.size() > 0) {
			this.electLeader();
		}
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
