package SMATP3.model.messages;

import SMATP3.model.Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Message {

	private final int emitterId;

	private List<Integer> recipientIds;
	private Performative performative;
	private Action action;
	private HashMap<String, Object> complementaryInformations;

	/**
	 * Constructor.
	 * @param emitter The message emitter.
	 */
	public Message(Agent emitter) {
		this.emitterId = emitter.getId();
		this.recipientIds = new ArrayList<Integer>();
		this.complementaryInformations = new HashMap<String, Object>();
	}

	public void addRecipientId(int recipientId) {
		this.recipientIds.add(recipientId);
	}

	public void setPerformative(Performative performative) {
		this.performative = performative;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public int getEmitterId() {
		return emitterId;
	}

	public List<Integer> getRecipientIds() {
		return recipientIds;
	}

	public Performative getPerformative() {
		return performative;
	}
	
	public Action getAction() {
		return action;
	}

	public Object getComplementaryInformationForName(String name) {
		return this.complementaryInformations.get(name);
	}

	public void addComplementaryInformation(Object complementaryInformation, String name) {
		this.complementaryInformations.put(name, complementaryInformation);
	}
	
	
}
