package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.ConversationStatus;
import SMATP3.model.messages.Message;

public interface ThinkingStrategy {
	public abstract void reflexionAction(Agent agent);
	public abstract String getName();
	
	/**
	 * Gere les messages recu pour l'agent
	 * @param message le message reçu
	 * @param agent l'agent recevant le message
	 * @return true si on ferme la discussion, false sinon
	 */
	public abstract ConversationStatus handleMessage(Message message, Agent agent);
	
}
