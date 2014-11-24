package SMATP3.model.components;

import javax.swing.SpinnerNumberModel;

public class GridSizeSpinnerModel extends SpinnerNumberModel {

	private static final long serialVersionUID = 1L;

	public GridSizeSpinnerModel() {
		this(5);
	}

	public GridSizeSpinnerModel(int defaultValue) {
		super(defaultValue, 2, 10, 1);
	}
}
