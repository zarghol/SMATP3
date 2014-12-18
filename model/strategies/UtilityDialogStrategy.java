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

public class UtilityDialogStrategy implements ThinkingStrategy {

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
				message.addComplementaryInformation(1, "attempt");
				for (Integer i : agentsToContact) {
					message.addRecipientId(i);
				}
				agent.sendMessage(message, ConversationStatus.BEGAN);
			}
		}
	}

	@Override
	public String getName() {
		return "UtilityDialogStrategy";
	}

	@Override
	public ConversationStatus handleMessage(Message message, Agent agent) {
		// Si on nous demande de bouger
		if (message.getPerformative() == Performative.REQUEST && message.getAction() == Action.MOVE) {
			// On construit la réponse
			Message response = agent.getNewMessage();
			response.setPerformative(Performative.INFORM);
			response.addRecipientId(message.getEmitterId());
			
			int numTentative = (int) message.getComplementaryInformationForName("attempt");
			if (numTentative == 1) {
				if (agent.isHappy() || agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition()).size() == 0) {
					response.setAction(Action.REFUSED);
					response.addComplementaryInformation(agent.getAimPosition(), "aimPosition");
				} else {
					response.setAction(Action.ACCEPTED);
					this.move(agent, true);
				}
			} else if (numTentative == 2) {
//				Position p = (Position) message.getComplementaryInformationForName("suggestedPosition");
				this.move(agent, false);
				response.setAction(Action.ACCEPTED);
				agent.sendMessage(response, ConversationStatus.ENDED);
			} else if (numTentative == 3) {
				double otherUtility = (double) message.getComplementaryInformationForName("utility");
				if (agent.getUtility() <= otherUtility) {
					response.setAction(Action.ACCEPTED);
					this.move(agent, true);
				} else {
					response.setAction(Action.REFUSED);
				}
			}
			
			response.addComplementaryInformation(numTentative, "attempt");

			agent.sendMessage(response, response.getAction() == Action.ACCEPTED ? ConversationStatus.ENDED : ConversationStatus.CONTINUED);
			return ConversationStatus.NOCHANGE;

		} else if (message.getPerformative() == Performative.INFORM) {
			if (message.getAction() == Action.ACCEPTED) {
				this.move(agent, false);
				return ConversationStatus.ENDED;
			} else if (message.getAction() == Action.REFUSED) {
				// NAN MAIS OH ! T'AS PAS LE DROIT DE REFUSER !! GRRRRR !
				// recherche alternative pour l'autre
				int numTentative = (int) message.getComplementaryInformationForName("attempt");
				if (numTentative == 1) {
					Position p = (Position) message.getComplementaryInformationForName("aimPosition");
					ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(p);
					if (emptyNeighbourhood.size() > 0) {
						Message response = agent.getNewMessage();
						response.setPerformative(Performative.REQUEST);
						response.setAction(Action.MOVE);
//						response.addComplementaryInformation(emptyNeighbourhood.get(new Random().nextInt(emptyNeighbourhood.size())), "suggestedPosition");
						response.addComplementaryInformation(2, "attempt");
						agent.sendMessage(response, ConversationStatus.CONTINUED);
						return ConversationStatus.NOCHANGE;
					} else {
						Message response = agent.getNewMessage();
						response.setPerformative(Performative.REQUEST);
						response.setAction(Action.MOVE);
						response.addComplementaryInformation(agent.getUtility(), "utility");
						response.addComplementaryInformation(3, "attempt");
						agent.sendMessage(response, ConversationStatus.CONTINUED);
						return ConversationStatus.NOCHANGE;
					}
				} else {
					this.move(agent, true);
					return ConversationStatus.ENDED;
				}
			}
		}
		return ConversationStatus.NOCHANGE;
	}

}
