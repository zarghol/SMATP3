package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.strategies.dependencies.Group;
import SMATP3.model.strategies.dependencies.LeadGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public enum Strategy {

	BASE_STRATEGY(BaseStrategy.class),
	DIALOG_STRATEGY(DialogStrategy.class),
	PLANIFIED_SIMPLE_STRATEGY(PlanifiedSimpleStrategy.class),
	SIMPLE_STRATEGY(SimpleStrategy.class),
	UTILITY_DIALOG_STRATEGY(UtilityDialogStrategy.class),
	LEAD_GROUP_STRATEGY(LeadGroupStrategy.class),
	INDEPENDENT_GROUP_STRATEGY(IndependentGroupStrategy.class);

	private Class<?> strategy;

	private Strategy(Class<?> strat) {
		this.strategy = strat;
	}

	public Class<?> getStrategy() {
		return this.strategy;
	}

	@Override
	public String toString() {
		ThinkingStrategy strat;
		try {
			strat = (ThinkingStrategy) this.strategy.newInstance();
		} catch (Exception e) {
			return super.toString();
		}

		return strat.getName();
	}

	public void apply(Grid grid, Collection<Agent> collection) {
		List<Group> groups = new ArrayList<>();


		HashMap<Agent, Group> mapping = new HashMap<Agent, Group>();

		if (this == LEAD_GROUP_STRATEGY || this == INDEPENDENT_GROUP_STRATEGY) {
			int nbGroup = 2;

			for (int i = 0; i < nbGroup; i++) {
				Group toAdd = null;
				if (this == LEAD_GROUP_STRATEGY) {
					toAdd = new LeadGroup(grid, null);
				} else {
					toAdd = new Group(grid, null);
				}
				groups.add(toAdd);
			}

			int i = 0;

			for (Agent a : collection) {
				groups.get(i).addAgent(a.getId());
				mapping.put(a, groups.get(i));
				i = (i + 1) % nbGroup;
			}
		}

		for (Agent a : collection) {
			try {
				ThinkingStrategy strat = (ThinkingStrategy) this.strategy.newInstance();

				if (this == LEAD_GROUP_STRATEGY || this == INDEPENDENT_GROUP_STRATEGY) {
					GroupStrategy st = (GroupStrategy) strat;
					st.setGroup(mapping.get(a));
				}


				a.setStrategy(strat);
			} catch (InstantiationException | IllegalAccessException e) {
				a.setStrategy(new SimpleStrategy());
			}
		}
	}

}
