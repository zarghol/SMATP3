package SMATP3.model.strategies;

public enum Strategy {
	BASESTRATEGY(BaseStrategy.class),
	DIALOGSTRATEGY(DialogStrategy.class),
	PLANIFIEDSIMPLESTRATEGY(PlanifiedSimpleStrategy.class),
	SIMPLESTRATEGY(SimpleStrategy.class),
	UTILITYDIALOGSTRATEGY(UtilityDialogStrategy.class);
	
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
	
}
