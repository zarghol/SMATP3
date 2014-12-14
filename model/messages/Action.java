package SMATP3.model.messages;

public enum Action {
	MOVE("move"),
	WAIT("wait"),
	ACCEPTED("accepted"), // TODO a mettre dans les complementary option et pas dans actions ??
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
