package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Action;
import SMATP3.model.messages.Message;
import SMATP3.model.messages.Performative;
import SMATP3.model.strategies.dependencies.LeadGroup;
import SMATP3.utils.Position;

public class LeadGroupStrategy implements ThinkingStrategy {
	// TODO voir plus tard avec une liste de groupe (pas forcement une seule figure a realiser)
	private LeadGroup group;

	@Override
	public void reflexionAction(Agent agent) {
		// si on est le leader
		if (agent.getId() == this.group.getLeader()) {
			// TODO on donne des ordres en fonction de la situation actuelle
			// comment ? utilisation de djikstra ?
			
			// pour les ordres
			Message m = agent.getNewMessage();
			m.setPerformative(Performative.ORDER);
			m.setAction(Action.MOVE);
			Position newPosition = new Position(0, 0);
			m.addComplementaryInformation(newPosition, "position");
			m.addRecipientId(0);
			
			// TODO on se déplace soi meme
		}
		
		// sinon on suit les ordres qui est dans un message, donc ici, rien

	}

	@Override
	public String getName() {
		return "LeadGroupStrategy";
	}

	@Override
	public boolean handleMessage(Message message, Agent agent) {
		if (message.getPerformative() == Performative.ORDER) {
			if (message.getAction() == Action.MOVE) {
				Position p = (Position) message.getComplementaryInformationForName("position");
				agent.move(p);
			}
		}
		return true;
	}
	
	public void setGroup(LeadGroup group) {
		this.group = group;
	}

}
