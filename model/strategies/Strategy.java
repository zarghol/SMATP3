package SMATP3.model.strategies;

import java.util.Collection;

import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.strategies.dependencies.LeadGroup;

public enum Strategy {
	BASE_STRATEGY(BaseStrategy.class),
	DIALOG_STRATEGY(DialogStrategy.class),
	PLANIFIED_SIMPLE_STRATEGY(PlanifiedSimpleStrategy.class),
	SIMPLE_STRATEGY(SimpleStrategy.class),
	UTILITY_DIALOG_STRATEGY(UtilityDialogStrategy.class),
	LEAD_GROUP_STRATEGY(LeadGroupStrategy.class);
	
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
		LeadGroup group = null;
		
		if (this == LEAD_GROUP_STRATEGY) {
			group = new LeadGroup(grid, collection);
		}
		for (Agent a : collection) {
			try {
				ThinkingStrategy strat = (ThinkingStrategy) this.strategy.newInstance();
				
				if (this == LEAD_GROUP_STRATEGY) {
					LeadGroupStrategy st = (LeadGroupStrategy) strat;
					st.setGroup(group);
				}
				
				
				a.setStrategy(strat);
			} catch (InstantiationException | IllegalAccessException e) {
				a.setStrategy(new SimpleStrategy());
			}
		}
	}
	
}
