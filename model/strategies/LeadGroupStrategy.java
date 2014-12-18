package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Action;
import SMATP3.model.messages.ConversationStatus;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;
import SMATP3.model.strategies.dependencies.LeadGroup;
import SMATP3.utils.Direction;
import SMATP3.utils.Position;

public class LeadGroupStrategy extends GroupStrategy {

	@Override
	public void reflexionAction(Agent agent) {
		// si on est le leader
		if (agent.getId() == ((LeadGroup) this.group).getLeader()) {
			for (Agent a : this.group.getMembers()) {
				// strategie particuliere pour chaque agent : semblable a SimpleStrategy
				Direction toFollow = Direction.directionDifferential(a.getPosition(), a.getAimPosition());
				Position newPosition = a.getPosition().towardDirection(toFollow);

				if (!a.getSnapshot().isPositionOccupied(newPosition)) {
					if (a != agent) {
						this.giveOrder(agent, newPosition);
					} else {
						a.move(newPosition);
					}
				}
				
			}
		}
	}
	
	private void giveOrder(Agent leader, Position newPosition) {
		// pour les ordres
		Message m = leader.getNewMessage();
		m.setPerformative(Performative.ORDER);
		m.setAction(Action.MOVE);
		m.addComplementaryInformation(newPosition, "position");
		m.addRecipientId(0);
		leader.sendMessage(m, ConversationStatus.NOCHANGE);
	}

	@Override
	public String getName() {
		return "LeadGroupStrategy";
	}

	@Override
	public ConversationStatus handleMessage(Message message, Agent agent) {
		if (message.getPerformative() == Performative.ORDER) {
			if (message.getAction() == Action.MOVE) {
				Position p = (Position) message.getComplementaryInformationForName("position");
				agent.move(p);
			}
		}
		return ConversationStatus.NOCHANGE;
	}
}
