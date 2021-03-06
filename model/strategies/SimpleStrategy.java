package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.ConversationStatus;
import SMATP3.model.messages.Message;
import SMATP3.utils.Direction;
import SMATP3.utils.Position;

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
		return "SimpleStrategy";
	}

	@Override
	public ConversationStatus handleMessage(Message message, Agent agent) {
		return ConversationStatus.NOCHANGE;
	}

}
