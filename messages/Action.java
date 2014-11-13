package SMATP3.messages;

public enum Action {
	MOVE("move");

	private String label;

	Action(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}
