package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.Snapshot;
import SMATP3.model.messages.Message;
import SMATP3.model.strategies.dependencies.Dijkstra;
import SMATP3.utils.Position;

import java.util.List;

public class PlanifiedSimpleStrategy implements ThinkingStrategy {
	private List<Position> route;

	@Override
	public void reflexionAction(Agent agent) {
		if (agent.getPosition().equals(agent.getAimPosition())) {
			return;
		}
		
		if (route == null || route.size() <= 0 || agent.getSnapshot().isPositionOccupied(route.get(0))) {
			this.findRoute(agent.getSnapshot(), agent.getPosition(), agent.getAimPosition());
		}		
		
		
		if (route.size() > 0) {
			Position nextPosition = route.remove(0);

			System.out.println("on avance");
			agent.move(nextPosition);
		}
	}

	@Override
	public String getName() {
		return "PlanifiedSimpleStrategy";
	}

	@Override
	public boolean handleMessage(Message message, Agent agent) {
		return true;
	}
	
	private void findRoute(Snapshot snap, Position origin, Position target) {
		//System.out.println("demande dijkstra ; from : " + origin + " to : " + target);
		Dijkstra d = new Dijkstra(snap, origin);
		this.route = d.getShortestPathTo(target);
	}

}
