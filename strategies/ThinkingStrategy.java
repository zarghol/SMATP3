package SMATP3.strategies;

import SMATP3.Agent;
import SMATP3.messages.Message;

public interface ThinkingStrategy {
	public void reflexionAction(Agent agent);
	public String getName();
	public void handleMessage(Message message, Agent agent);
}
