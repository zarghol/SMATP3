package SMATP3.controller;

import SMATP3.model.Grid;
import SMATP3.model.Snapshot;
import SMATP3.model.components.AgentCountSpinnerModel;
import SMATP3.model.strategies.Strategy;
import SMATP3.view.MainWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;

public class Controller {
	private MainWindow window;
	private Grid grid;
	
	private StartAction startAction = new StartAction();
	private StopAction stopAction = new StopAction();
	private ResetAction resetAction = new ResetAction();
	private RandomizeAction randomizeAction = new RandomizeAction();
	private SpeedSliderListener speedSliderListener = new SpeedSliderListener();
	private GridSizeSpinnerListener gridSizeSpinnerListener = new GridSizeSpinnerListener();
	private BoundedRangeModel speedBounds = new DefaultBoundedRangeModel(5, 0, 0, 10);

	public Controller() {
		System.out.println("creation de la fenetre");
		this.window = new MainWindow(this);
		this.randomizeAction.actionPerformed(null);
	}

	public Grid getGrid() {
		return this.grid;
	}

	public StartAction getStartAction() {
		return this.startAction;
	}

	public StopAction getStopAction() {
		return this.stopAction;
	}

	public ResetAction getResetAction() {
		return this.resetAction;
	}

	public RandomizeAction getRandomizeAction() {
		return randomizeAction;
	}

	public void setRandomizeAction(RandomizeAction randomizeAction) {
		this.randomizeAction = randomizeAction;
	}

	public BoundedRangeModel getSpeedBounds() {
		return speedBounds;
	}

	public SpeedSliderListener getSpeedSliderListener() {
		return speedSliderListener;
	}
	

	public GridSizeSpinnerListener getGridSizeSpinnerListener() {
		return gridSizeSpinnerListener;
	}

	public static void main(String[] args) {
		Controller controller = new Controller();
	}

	private class StartAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public StartAction() {
			super("Start");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			window.getAgentCountSpinner().setEnabled(false);
			window.getStrategyPicker().setEnabled(false);
			window.getGridSizeSpinner().setEnabled(false);
			window.getStartStopButton().setAction(stopAction);
			System.out.println("start");
			grid.start();
		}
	}

	private class StopAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public StopAction() {
			super("Stop");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			window.getAgentCountSpinner().setEnabled(true);
			window.getStrategyPicker().setEnabled(true);
			window.getGridSizeSpinner().setEnabled(true);

			window.getStartStopButton().setAction(startAction);
			System.out.println("stop");
			grid.stop();
		}
	}

	private class ResetAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		private Snapshot originalState;

		public ResetAction() {
			super("Reset");
		}
		
		public void setOriginalState(Snapshot snap) {
			this.originalState = snap;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
//			grid = new Grid(this.originalState);
//			window.setGrid(grid);
		}
	}

	private class RandomizeAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public RandomizeAction() {
			super("Randomize");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("creation grille");
			grid = new Grid((Integer) window.getGridSizeSpinner().getValue(), (Integer) window.getAgentCountSpinner().getValue(), (Strategy) window.getStrategyPicker().getSelectedItem());
			grid.setVerbose(false);
			
			System.out.println("application de la grille a la window");
			window.setGrid(grid);
			
			getResetAction().setOriginalState(grid.getSnapshot());
			getStopAction().actionPerformed(null);
		}
	}

	private class SpeedSliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider)e.getSource();
			if(!slider.getValueIsAdjusting()) {
				grid.setLatency((10 - slider.getValue()) * 100);
			}
		}
	}

	private class GridSizeSpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSpinner gridSizeSpinner = (JSpinner) e.getSource();
			int gridSize = ((Integer) gridSizeSpinner.getValue());
			int max = gridSize * gridSize - 1;

			JSpinner agentCountSpinner = window.getAgentCountSpinner();
			AgentCountSpinnerModel agentCountModel = (AgentCountSpinnerModel) agentCountSpinner.getModel();
			agentCountModel.setMaximum(max);
			if(((Integer) agentCountSpinner.getValue()) > max) {
				agentCountSpinner.setValue(max);
			}
		}
	}
}
