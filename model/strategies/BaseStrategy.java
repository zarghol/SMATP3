package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Action;
import SMATP3.model.messages.ConversationStatus;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;
import SMATP3.utils.Direction;
import SMATP3.utils.Position;

import java.util.ArrayList;

public class BaseStrategy implements ThinkingStrategy {

	@Override
	public void reflexionAction(Agent agent) {
		// si on a pas de message a attendre => pas de conversation ouverte
		if (!agent.handleMessages()) {
			Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
			if (toFollow != Direction.NONE)  {
				Position nextPosition = agent.getPosition().towardDirection(toFollow);
				if (agent.getSnapshot().isPositionOccupied(nextPosition)) {
					Message message = agent.getNewMessage();
					message.setAction(Action.MOVE);
					message.setPerformative(Performative.REQUEST);
					message.addRecipientId(agent.getSnapshot().getAgentId(nextPosition));
					agent.sendMessage(message, ConversationStatus.BEGAN);
				} else {
					agent.move(nextPosition);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "BaseStrategy";
	}

	@Override
	public ConversationStatus handleMessage(Message message, Agent agent) {
		if (message.getPerformative() == Performative.REQUEST && message.getAction() == Action.MOVE) {
			ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition());
			Message response = agent.getNewMessage();
			response.setPerformative(Performative.INFORM);

			if (emptyNeighbourhood.size() > 0) {
				agent.move(emptyNeighbourhood.get(0));
				response.setAction(Action.ACCEPTED);
			} else {
				response.setAction(Action.REFUSED);
			}
			response.addRecipientId(message.getEmitterId());
			agent.sendMessage(response, ConversationStatus.NOCHANGE);
		} else if (message.getPerformative() == Performative.INFORM) {
			return ConversationStatus.ENDED;
		}
		return ConversationStatus.NOCHANGE;
	}

}
