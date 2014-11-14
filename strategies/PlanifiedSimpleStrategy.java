package SMATP3.strategies;

import java.util.List;

import SMATP3.Agent;
import SMATP3.Position;
import SMATP3.Snapshot;
import SMATP3.messages.Message;

public class PlanifiedSimpleStrategy implements ThinkingStrategy {
	private List<Position> route;

	@Override
	public void reflexionAction(Agent agent) {
		Position nextPosition = route.remove(0);
		if (route == null || agent.getSnapshot().isPositionOccupied(nextPosition)) {
			// calcul itinéraire
			this.findRoute(agent.getSnapshot(), agent.getPosition(), agent.getAimPosition());
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
	
	private void findRoute(Snapshot snap, Position origin, Position target) {
		Djikstra d = new Djikstra(snap, origin);		
		this.route = d.getShortestPathTo(target);
	}

}
