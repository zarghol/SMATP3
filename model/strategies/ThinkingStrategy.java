package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Message;

public interface ThinkingStrategy {
	public void reflexionAction(Agent agent);
	public String getName();
	public void handleMessage(Message message, Agent agent);
}
