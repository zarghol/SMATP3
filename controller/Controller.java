package SMATP3.controller;

import SMATP3.model.Agent;
import SMATP3.model.Grid;
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
	private RandomizerListener agentCountSpinnerListener = new RandomizerListener();
	private BoundedRangeModel speedBounds = new DefaultBoundedRangeModel(5, 0, 0, 10);

	public Controller() {
		System.out.println("creation de la fenetre");
		this.window = new MainWindow(this);
	}
	
	public void launch() {
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
	
	public RandomizerListener getAgentCountSpinnerListener() {
		return agentCountSpinnerListener;
	}

	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.launch();
	}

	public class StartAction extends AbstractAction {
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

	public class StopAction extends AbstractAction {
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

	public class ResetAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ResetAction() {
			super("Reset");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Agent.resetIds();
			grid = new Grid(grid.getInitialState());
			window.setGrid(grid);
		}
	}

	public class RandomizeAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public RandomizeAction() {
			super("Randomize");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Agent.resetIds();
			grid = new Grid((Integer) window.getGridSizeSpinner().getValue(), (Integer) window.getAgentCountSpinner().getValue(), (Strategy) window.getStrategyPicker().getSelectedItem());
			grid.setVerbose(false);
			window.setGrid(grid);
			getStopAction().actionPerformed(null);
		}
	}

	public class SpeedSliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider)e.getSource();
			if(!slider.getValueIsAdjusting()) {
				grid.setLatency((10 - slider.getValue()) * 100);
			}
		}
	}

	public class GridSizeSpinnerListener implements ChangeListener {

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
			getRandomizeAction().actionPerformed(null);
		}
	}
	
	public class RandomizerListener implements ChangeListener {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			getRandomizeAction().actionPerformed(null);
		}
	}
}
