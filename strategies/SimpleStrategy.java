package SMATP3.strategies;

import SMATP3.Agent;
import SMATP3.Direction;
import SMATP3.Position;
import SMATP3.messages.Message;


import java.util.ArrayList;

public class SimpleStrategy implements ThinkingStrategy {

	@Override
	public void reflexionAction(Agent agent) {
		if (!agent.handlePostOffice()) {
			Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
			Position newPosition = agent.getPosition().towardDirection(toFollow);
			if (agent.getSnapshot().isPositionOccupied(newPosition)) {
				agent.sendMessage(agent.getSnapshot().getAgentId(newPosition));
			} else {
				agent.move(newPosition);
			}
		}
	}

	@Override
	public String getName() {
		return "SimpleStrategy";
	}

	@Override
	public void handleMessage(Message message, Agent agent) {
		if (message.getPerformative().equals("Request") && message.getAction().equals("move")) {
			ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition());
			agent.move(emptyNeighbourhood.get(0));
		}
	}

}
