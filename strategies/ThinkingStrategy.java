package SMATP3.strategies;

import SMATP3.Agent;
import SMATP3.messages.Message;

public interface ThinkingStrategy {
	public void reflexionAction(Agent agent);
	// traiter messages
	// verifier
	// raisonne
	// // si case vers son chemin est libre
	// // // in se déplace
	// // sinon
	// // // on envoit un message
	// effectuer les actions

	public String getName();
	public void handleMessage(Message message, Agent agent);
}
