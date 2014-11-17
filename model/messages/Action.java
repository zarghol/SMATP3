package SMATP3.model.messages;

public enum Action {
	MOVE("move"),
	ACCEPTED("accepted"),
	REFUSED("refused");

	private String label;

	Action(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}
