package SMATP3.strategies;

import SMATP3.Agent;
import SMATP3.Direction;
import SMATP3.Position;
import SMATP3.messages.Message;

public class VerySimpleStrategy implements ThinkingStrategy {

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
