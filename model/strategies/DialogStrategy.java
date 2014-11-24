package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Action;
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
				agent.sendMessage(message);
			}
		}
	}

	@Override
	public String getName() {
		return "DialogStrategy";
	}

	@Override
	public boolean handleMessage(Message message, Agent agent) {
		
		// Si on nous demande de bouger
		if (message.getPerformative() == Performative.REQUEST && message.getAction() == Action.MOVE) {
			ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition());
			// On construit la réponse
			Message response = agent.getNewMessage();
			response.setPerformative(Performative.INFORM);
			response.addRecipientId(message.getEmitterId());
			
			// TODO: prendre en charge les infos supplémentaires du message : but final de celui qui veut qu'on bouge
			
			// On regarde si on peut se déplacer
			Direction toFollow = Direction.directionDifferential(agent.getPosition(), agent.getAimPosition());
			Position nextPosition = agent.getPosition().towardDirection(toFollow);
			if (!agent.getSnapshot().isPositionOccupied(nextPosition)) {
				response.setAction(Action.ACCEPTED);
				agent.move(nextPosition);
			} else if (emptyNeighbourhood.size() > 0) {
				response.setAction(Action.ACCEPTED);
				agent.move(emptyNeighbourhood.get(new Random().nextInt(emptyNeighbourhood.size())));
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
					agent.move(emptyNeighbourhood.get(new Random().nextInt(emptyNeighbourhood.size())));
				}
			}
		}
		
		return true;
	}

}
