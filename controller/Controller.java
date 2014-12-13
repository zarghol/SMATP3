package SMATP3.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import SMATP3.model.Grid;
import SMATP3.model.strategies.Strategy;
import SMATP3.view.MainWindow;

public class Controller {
	private MainWindow window;
	private Grid grid;
	
	private StartAction startAction = new StartAction();
	private StopAction stopAction = new StopAction();
	private ResetAction resetAction = new ResetAction();
	private RandomizeAction randomizeAction = new RandomizeAction();
	private SliderListener speedSliderListener = new SliderListener();
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

	public SliderListener getSpeedSliderListener() {
		return speedSliderListener;
	}
	

	public static void main(String[] args) {
		Controller controller = new Controller();
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
			//TODO: implémenter la réinitialisation
		}
	}

	public class RandomizeAction extends AbstractAction {
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
		}
	}

	private class SliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider)e.getSource();
			if(!slider.getValueIsAdjusting()) {
				grid.setLatency((10 - slider.getValue()) * 100);
			}
		}
	}
	
	// TODO utile ? va-t-on changer la strategie en cours de partie ??
	private class StrategyListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JComboBox<Strategy> comboBox = (JComboBox<Strategy>) e.getSource();
			Strategy selectedStrategy = (Strategy) comboBox.getSelectedItem();
			if (grid.getCurrentStrategy() != selectedStrategy) {
				grid.setCurrentStrategy(selectedStrategy);
			}
		}
	}
	
}
