package SMATP3.model.components;

import javax.swing.SpinnerNumberModel;

public class AgentCountSpinnerModel extends SpinnerNumberModel {

	private static final long serialVersionUID = 1L;

	public AgentCountSpinnerModel() {
		this(4);
	}

	public AgentCountSpinnerModel(int defaultValue) {
		super(defaultValue, 2, 10, 1);
	}
}
