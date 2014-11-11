package SMATP3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PostOffice {
	private Hashtable<Integer, List<Message>> mailboxes;

	public PostOffice() {
		this.mailboxes = new Hashtable<Integer, List<Message>>();
	}

	/**
	 * Ajoute un agent emetteur/récepteur de messages.
	 * @param agent L'agent à ajouter à la boîte aux lettres.
	 */
	public void addAgent(Agent agent) {
		this.mailboxes.put(agent.getId(), new ArrayList<Message>());
	}

	/**
	 * Envoie un message à un agent.
	 * @param message Le message à transmettre.
	 */
	public void sendMessage(Message message) {
		synchronized (this.mailboxes.get(message.getRecipientId())) {
			this.mailboxes.get(message.getRecipientId()).add(message);
		}
	}

	/**
	 * Renvoie le message suivant destiné à un agent.
	 * @param agent L'agent dont on veut le prochain message non lu.
	 * @return Le prochain message non lu ou null si la boite aux lettres est vide.
	 */
	public Message getNextMessage(Agent agent) {
		synchronized (this.mailboxes.get(agent.getId())) {
			List<Message> mailbox = this.mailboxes.get(agent.getId());
			if(mailbox.isEmpty()) {
				return null;
			}

			return mailbox.remove(0);
		}
	}
}
