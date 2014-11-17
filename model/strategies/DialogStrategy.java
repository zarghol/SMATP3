package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Action;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;
import SMATP3.utils.Direction;
import SMATP3.utils.Position;

import java.util.ArrayList;
import java.util.Random;

public class DialogStrategy implements ThinkingStrategy {

	@Override
	public void reflexionAction(Agent agent) {
		if (!agent.handleMessages()) {
			this.move(agent, true);
		}
	}
	
	private void move(Agent agent, boolean sendMessageIfNecessary) {
		Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
		Position nextPosition = agent.getPosition().towardDirection(toFollow);
		if (toFollow != Direction.NONE) {
			if (agent.getSnapshot().isPositionOccupied(nextPosition) && sendMessageIfNecessary) {
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
		return "DialogStrategy";
	}

	@Override
	public void handleMessage(Message message, Agent agent) {
		if (message.getPerformative() == Performative.REQUEST && message.getAction() == Action.MOVE) {
			ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition());
			Message response = agent.getNewMessage();
			response.setPerformative(Performative.INFORM);
			response.addRecipientId(message.getEmitterId());
			
			Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
			Position nextPosition = agent.getPosition().towardDirection(toFollow);
			if (!agent.getSnapshot().isPositionOccupied(nextPosition)) {
				response.setAction(Action.ACCEPTED);
				agent.move(nextPosition);
			} else if (emptyNeighbourhood.size() > 0) {
				response.setAction(Action.ACCEPTED);
				agent.move(emptyNeighbourhood.get(0));
			} else {
				response.setAction(Action.REFUSED);
			}
			agent.sendMessage(response);
			
		} else if (message.getPerformative() == Performative.INFORM) {
			if (message.getAction() == Action.ACCEPTED) {
				this.move(agent, false);
			} else if (message.getAction() == Action.REFUSED) {
				ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition());
				if (emptyNeighbourhood.size() > 0) {
					Random r = new Random();
					agent.move(emptyNeighbourhood.get(r.nextInt(emptyNeighbourhood.size())));
				}
			}
		}
	}

}
