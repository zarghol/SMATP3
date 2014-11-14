package SMATP3.model.strategies;

import java.util.ArrayList;
import java.util.List;

import SMATP3.model.Agent;
import SMATP3.Position;
import SMATP3.model.messages.Message;

public class PlanifiedSimpleStrategy implements ThinkingStrategy {
	private List<Position> route;

	@Override
	public void reflexionAction(Agent agent) {
		Position nextPosition = route.remove(0);
		if (route == null || agent.getSnapshot().isPositionOccupied(nextPosition)) {
			// calcul itinéraire
			this.findRoute();
		} 
		// on avance
		agent.move(nextPosition);
		
	}

	@Override
	public String getName() {
		return "PlanifiedSimpleStrategy";
	}

	@Override
	public void handleMessage(Message message, Agent agent) {
	}
	
	private void findRoute() {
		List<Position> route = new ArrayList<Position>();
		// TODO: find route (djikstra, etc..)
		
		
		this.route = route;
	}

}
