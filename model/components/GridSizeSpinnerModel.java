package SMATP3.model.components;

import SMATP3.model.Grid;

import javax.swing.SpinnerNumberModel;

public class GridSizeSpinnerModel extends SpinnerNumberModel {

	private static final long serialVersionUID = 1L;

	public GridSizeSpinnerModel() {
		this(Grid.DEFAULT_GRID_SIZE);
	}

	public GridSizeSpinnerModel(int defaultValue) {
		super(defaultValue, 2, 15, 1);
	}
}
