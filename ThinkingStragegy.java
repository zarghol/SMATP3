package SMATP3;

public interface ThinkingStragegy {
	public void reflexionAction(Agent agent);
	public String getName();
	public void handleMessage(Message message, Agent agent);
}
