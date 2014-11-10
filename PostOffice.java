package SMATP3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PostOffice {
	private Hashtable<Integer, List<Message>> mailboxes;

	public PostOffice() {
		this.mailboxes = new Hashtable<Integer, List<Message>>();
	}

	public void addAgent(Agent a) {
		this.mailboxes.put(a.getId(), new ArrayList<Message>());
	}

	public void sendMessage(Message message) {
		synchronized (this.mailboxes.get(message.getRecipient().getId())) {
			this.mailboxes.get(message.getRecipient().getId()).add(message);
		}
	}

	public Message getNextMessage(Agent a) {
		synchronized (this.mailboxes.get(a.getId())) {
			return this.mailboxes.get(a.getId()).remove(0);
		}
	}
}
