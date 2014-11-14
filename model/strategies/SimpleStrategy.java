package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.Direction;
import SMATP3.Position;
import SMATP3.model.messages.Action;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;


import java.util.ArrayList;

public class SimpleStrategy implements ThinkingStrategy {

	@Override
	public void reflexionAction(Agent agent) {
		if (!agent.handlePostOffice()) {
			Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
			Position nextPosition = agent.getPosition().towardDirection(toFollow);
			if (agent.getSnapshot().isPositionOccupied(nextPosition)) {
				Message message = agent.getNewMessage();
				message.setAction(Action.MOVE);
				message.setPerformative(Performative.REQUEST);
				message.addRecipientId(agent.getSnapshot().getAgentId(nextPosition));
				agent.sendMessage(message);
			} else {
				agent.move(nextPosition);
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
