package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Action;
import SMATP3.model.messages.ConversationStatus;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;
import SMATP3.utils.Direction;
import SMATP3.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DialogStrategy implements ThinkingStrategy {

	@Override
	public void reflexionAction(Agent agent) {
		if (!agent.handleMessages()) {
			this.move(agent, true);
		}
	}
	
	private void move(Agent agent, boolean sendMessageIfNecessary) {
		List<Direction> directions = Direction.directionsPossible(agent.getPosition(), agent.getAimPosition());

		if (directions.get(0) != Direction.NONE) {
			List<Integer> agentsToContact = new ArrayList<Integer>();
			for (Direction toFollow : directions) {
				Position nextPosition = agent.getPosition().towardDirection(toFollow);
				if (!agent.getSnapshot().isPositionOccupied(nextPosition)) {
					agent.move(nextPosition);
					return;
				} else {
					agentsToContact.add(agent.getSnapshot().getAgentId(nextPosition));
				}
			}
			
			if (sendMessageIfNecessary) {
				Message message = agent.getNewMessage();
				message.setAction(Action.MOVE);
				message.setPerformative(Performative.REQUEST);
				for (Integer i : agentsToContact) {
					message.addRecipientId(i);
				}
				message.addComplementaryInformation(agent.getAimPosition(), "aimPosition");
				agent.sendMessage(message, ConversationStatus.BEGAN);
			}
		}
	}

	@Override
	public String getName() {
		return "DialogStrategy";
	}
	
	private boolean tryToMove(Agent agent, Position futurEmitterPosition) {
		ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition());
		emptyNeighbourhood.remove(futurEmitterPosition);
		Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
		Position nextPosition = agent.getPosition().towardDirection(toFollow);
		if (!agent.getSnapshot().isPositionOccupied(nextPosition)) {
			agent.move(nextPosition);
			return true;
		} else if (emptyNeighbourhood.size() > 0) {
			agent.move(emptyNeighbourhood.get(new Random().nextInt(emptyNeighbourhood.size())));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ConversationStatus handleMessage(Message message, Agent agent) {
		
		// Si on nous demande de bouger
		if (message.getPerformative() == Performative.REQUEST && message.getAction() == Action.MOVE) {
			// On construit la réponse
			Message response = agent.getNewMessage();
			response.setPerformative(Performative.INFORM);
			response.addRecipientId(message.getEmitterId());
			
			Position aimEmitterPosition = (Position) message.getComplementaryInformationForName("aimPosition");
			
			if (aimEmitterPosition.equals(agent.getPosition())) {
				response.setAction(this.tryToMove(agent, agent.getPosition()) ? Action.ACCEPTED : Action.REFUSED);
			} else {
				Direction toFollowForEmitter = Direction.directionDifferential(agent.getPosition(), aimEmitterPosition);
				Position futurEmitterPosition = agent.getPosition().towardDirection(toFollowForEmitter);
				if (agent.getSnapshot().isPositionOccupied(futurEmitterPosition)) {
					response.setAction(Action.REFUSED);
				} else {
					response.setAction(this.tryToMove(agent, futurEmitterPosition) ? Action.ACCEPTED : Action.REFUSED);
				}
			}
			
			agent.sendMessage(response, ConversationStatus.NOCHANGE);
			
		} else if (message.getPerformative() == Performative.INFORM) {
			if (message.getAction() == Action.ACCEPTED) {
				this.move(agent, false);
			} else if (message.getAction() == Action.REFUSED) {
				ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition());
				if (emptyNeighbourhood.size() > 0) {
					agent.move(emptyNeighbourhood.get(new Random().nextInt(emptyNeighbourhood.size())));
				}
			}
			return ConversationStatus.ENDED;
		}
		
		return ConversationStatus.NOCHANGE;
	}
}
