package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.Message;

public interface ThinkingStrategy {
	public void reflexionAction(Agent agent);
	public String getName();
	// boolean -> if this method close the discussion
	public boolean handleMessage(Message message, Agent agent);
}
