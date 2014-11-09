package SMATP3;

import java.util.List;

public class PerceivedEnvironment {
	private List<Message> messagesToRead;
	private Grid gridVision;
	
	public static PerceivedEnvironment getPerceptionFromAgent(Agent agent) {
		return null;
	}
	
	private PerceivedEnvironment(Agent a) {
		this.messagesToRead = MailBox.getInstance().getMessages(a);
		this.gridVision = (Grid) Grid.getInstance().clone();
	}
	
	public List<Message> getMessagesToRead() {
		return this.messagesToRead;
	}
	
	public Grid getGridVision() {
		return this.gridVision;
	}

}
