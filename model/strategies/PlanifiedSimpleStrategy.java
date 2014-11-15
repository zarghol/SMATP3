package SMATP3.model.strategies;

import SMATP3.Position;
import SMATP3.model.Agent;
import SMATP3.model.Snapshot;
import SMATP3.model.messages.Message;

import java.util.List;

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
		Dijkstra d = new Dijkstra(snap, origin);
		this.route = d.getShortestPathTo(target);
	}

}
