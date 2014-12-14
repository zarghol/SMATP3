package SMATP3.model.strategies;

import java.util.List;

import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.strategies.dependencies.LeadGroup;

public enum Strategy {
	BASESTRATEGY(BaseStrategy.class),
	DIALOGSTRATEGY(DialogStrategy.class),
	PLANIFIEDSIMPLESTRATEGY(PlanifiedSimpleStrategy.class),
	SIMPLESTRATEGY(SimpleStrategy.class),
	UTILITYDIALOGSTRATEGY(UtilityDialogStrategy.class),
	LEADGROUPSTRATEGY(LeadGroupStrategy.class);
	
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
	
	public void apply(Grid grid, List<Agent> agents) {
		LeadGroup group = null;
		
		if (this == LEADGROUPSTRATEGY) {
			group = new LeadGroup(grid, agents);
		}
		for (Agent a : agents) {
			try {
				ThinkingStrategy strat = (ThinkingStrategy) this.strategy.newInstance();
				
				if (this == LEADGROUPSTRATEGY) {
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
