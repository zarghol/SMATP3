package SMATP3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class BoiteAuxLettres {
	private Hashtable<Integer, List<Message>> box;

	public BoiteAuxLettres() {
		this.box = new Hashtable<Integer, List<Message>>();
	}

	public void ajouterAgent(Agent a) {
		this.box.put(a.getId(), new ArrayList<Message>());
	}

	public void envoyerMessage(Message message) {
		this.box.get(message.getRecepteur().getId()).add(message);
	}

	public List<Message> getMessages(Agent a) {
		return this.box.get(a.getId());
	}
}
