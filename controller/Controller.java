package SMATP3.controller;

import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.PostOffice;
import SMATP3.model.strategies.DialogStrategy;
import SMATP3.model.strategies.SimpleStrategy;
import SMATP3.model.strategies.ThinkingStrategy;
import SMATP3.utils.Position;
import SMATP3.view.MainWindow;

import javax.swing.AbstractAction;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	private MainWindow window;
	private Grid grid;
	private StartAction startAction = new StartAction();
	private StopAction stopAction = new StopAction();
	private SliderListener speedSliderListener = new SliderListener();
	private BoundedRangeModel speedBounds = new DefaultBoundedRangeModel(5, 0, 0, 10);

	public Controller(int boardSize) {
		this.grid = new Grid(boardSize);
		PostOffice postOffice = new PostOffice();
		List<Agent> agents = new ArrayList<>();

		agents.add(new Agent(this.grid, postOffice, new Position(0, 3), new Position(1, 4)));
		agents.add(new Agent(this.grid, postOffice, new Position(3, 0), new Position(3, 2)));
		agents.add(new Agent(this.grid, postOffice, new Position(1, 2), new Position(2, 2)));
		agents.add(new Agent(this.grid, postOffice, new Position(3, 4), new Position(0, 2)));

		this.applyStrategy(DialogStrategy.class, agents);
		this.grid.addAgents(agents);
		this.grid.setVerbose(true);

		this.window = new MainWindow(this);
	}
	
	private void applyStrategy(Class<?> strategy, List<Agent> agents) {
		for (Agent a : agents) {
			try {
				a.setStrategy((ThinkingStrategy) strategy.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				a.setStrategy(new SimpleStrategy());
			}
		}
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
	

	public static void main(String[] args) {
		Controller controller = new Controller(5);
	}

	public BoundedRangeModel getSpeedBounds() {
		return speedBounds;
	}

	public SliderListener getSpeedSliderListener() {
		return speedSliderListener;
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
