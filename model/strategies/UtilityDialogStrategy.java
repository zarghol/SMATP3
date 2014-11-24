package SMATP3.model.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import SMATP3.model.Agent;
import SMATP3.model.messages.Action;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;
import SMATP3.utils.Direction;
import SMATP3.utils.Position;

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
				agent.sendMessage(message);
			}
		}
	}

	@Override
	public String getName() {
		return "UtilityDialogStrategy";
	}

	@Override
	public boolean handleMessage(Message message, Agent agent) {
		// Si on nous demande de bouger
		if (message.getPerformative() == Performative.REQUEST && message.getAction() == Action.MOVE) {
			// On construit la réponse
			Message response = agent.getNewMessage();
			response.setPerformative(Performative.INFORM);
			response.addRecipientId(message.getEmitterId());
			
			int numTentative = (int) message.getComplementaryInformationForName("attempt");
			// TODO : switch ou pattern Strategie / Etat ????
			if (numTentative == 1) {
				if (agent.isHappy() || agent.getSnapshot().getEmptyNeighbourhood(agent.getPosition()).size() == 0) {
					response.setAction(Action.REFUSED);
					response.addComplementaryInformation(agent.getAimPosition(), "aimPosition");
				} else {
					response.setAction(Action.ACCEPTED);
					this.move(agent, true);
				}
			} else if (numTentative == 2) {
				Position p = (Position) message.getComplementaryInformationForName("suggestedPosition");
				response.setAction(Action.ACCEPTED);
				agent.sendMessage(response);
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

			agent.sendMessage(response);
			return response.getAction() == Action.ACCEPTED;

		} else if (message.getPerformative() == Performative.INFORM) {
			if (message.getAction() == Action.ACCEPTED) {
				this.move(agent, false);
				return true;
			} else if (message.getAction() == Action.REFUSED) {
				// NAN MAIS OH ! T'AS PAS LE DROIT DE REFUSER !! GRRRRR !
				// recherche alternative pour l'autre
				int numTentative = (int) message.getComplementaryInformationForName("attempt");
				// TODO : switch ou pattern Strategie / Etat ????
				if (numTentative == 1) {
					Position p = (Position) message.getComplementaryInformationForName("aimPosition");
					ArrayList<Position> emptyNeighbourhood = agent.getSnapshot().getEmptyNeighbourhood(p);
					if (emptyNeighbourhood.size() > 0) {
						Message response = agent.getNewMessage();
						response.setPerformative(Performative.REQUEST);
						response.setAction(Action.MOVE);
						response.addComplementaryInformation(emptyNeighbourhood.get(new Random().nextInt(emptyNeighbourhood.size())), "suggestedPosition");
						response.addComplementaryInformation(2, "attempt");
						agent.sendMessage(response);
						return false;
					} else {
						Message response = agent.getNewMessage();
						response.setPerformative(Performative.REQUEST);
						response.setAction(Action.MOVE);
						response.addComplementaryInformation(agent.getUtility(), "utility");
						response.addComplementaryInformation(3, "attempt");
						agent.sendMessage(response);
						return false;
					}
				} else {
					this.move(agent, true);
					return true;
				}
			}
		}
		return true;
	}

}
