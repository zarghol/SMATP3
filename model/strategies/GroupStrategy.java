package SMATP3.model.strategies;

import SMATP3.model.Agent;
import SMATP3.model.messages.ConversationStatus;
import SMATP3.model.messages.Message;
import SMATP3.model.strategies.dependencies.Group;

public abstract class GroupStrategy implements ThinkingStrategy {
	protected Group group;

	@Override
	public abstract void reflexionAction(Agent agent);

	@Override
	public abstract String getName();

	@Override
	public abstract ConversationStatus handleMessage(Message message, Agent agent);

	public void setGroup(Group group) {
		this.group = group;
	}
}
