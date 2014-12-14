package SMATP3.model.components;

import SMATP3.model.Grid;

import javax.swing.SpinnerNumberModel;

public class AgentCountSpinnerModel extends SpinnerNumberModel {

	private static final long serialVersionUID = 1L;

	public AgentCountSpinnerModel() {
		this(Grid.DEFAULT_AGENT_COUNT);
	}

	public AgentCountSpinnerModel(int defaultValue) {
		super(defaultValue, 2, 24, 1);
	}
}
