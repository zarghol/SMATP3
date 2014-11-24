package SMATP3.controller;

import SMATP3.model.Grid;
import SMATP3.model.strategies.DialogStrategy;
import SMATP3.view.MainWindow;

import javax.swing.AbstractAction;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JSlider;
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
	private SliderListener speedSliderListener = new SliderListener();
	private BoundedRangeModel speedBounds = new DefaultBoundedRangeModel(5, 0, 0, 10);

	public Controller() {
		this.grid = new Grid();
		this.window = new MainWindow(this);

		//FIXME: définir la stratégie en fonction de la sélection sur la fenêtre
		this.grid.applyStrategy(DialogStrategy.class);
		this.grid.setVerbose(true);
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
		public StartAction() {
			super("Start");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			window.getStartStopButton().setAction(stopAction);
			System.out.println("start");
			grid.start();
		}
	}

	public class StopAction extends AbstractAction {
		public StopAction() {
			super("Stop");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			window.getStartStopButton().setAction(startAction);
			System.out.println("stop");
			grid.stop();
		}
	}

	public class ResetAction extends AbstractAction {
		public ResetAction() {
			super("Reset");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: implémenter la réinitialisation
		}
	}

	public class RandomizeAction extends AbstractAction {
		public RandomizeAction() {
			super("Randomize");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			grid = new Grid((Integer) window.getGridSizeSpinner().getValue(), (Integer) window.getAgentCountSpinner().getValue());
			window.setGrid(grid);
			//FIXME: définir la stratégie en fonction de la sélection sur la fenêtre
			grid.applyStrategy(DialogStrategy.class);
			grid.setVerbose(true);
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
}
