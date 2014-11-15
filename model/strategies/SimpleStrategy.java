package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.Direction;
import SMATP3.Position;
import SMATP3.model.messages.Message;

public class SimpleStrategy implements ThinkingStrategy {

	@Override
	public void reflexionAction(Agent agent) {
		Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
		Position newPosition = agent.getPosition().towardDirection(toFollow);
		if (!agent.getSnapshot().isPositionOccupied(newPosition)) {
			agent.move(newPosition);
		}
	}

	@Override
	public String getName() {
		return "VerySimpleStrategy";
	}

	@Override
	public void handleMessage(Message message, Agent agent) {
	}

}
