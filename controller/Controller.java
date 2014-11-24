package SMATP3.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import SMATP3.utils.Position;
import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.PostOffice;
import SMATP3.model.strategies.*;
import SMATP3.view.MainWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		
		for (Entry<Position, Position> entry : this.createRandomAgents(12).entrySet()) {
			agents.add(new Agent(this.grid, postOffice, entry.getKey(), entry.getValue()));
		}

		this.applyStrategy(DialogStrategy.class, agents);
		this.grid.addAgents(agents);
		this.grid.setVerbose(true);

		this.window = new MainWindow(this);
	}
	
	private HashMap<Position, Position> createRandomAgents(int nbAgents) {
		HashMap<Position, Position> positionsAgents = new HashMap<Position, Position>();
		while (positionsAgents.size() < nbAgents) {
			Random r = new Random();
			Position p1 = new Position(r.nextInt(this.grid.getGridSize()), r.nextInt(this.grid.getGridSize()));
			Position p2 = new Position(r.nextInt(this.grid.getGridSize()), r.nextInt(this.grid.getGridSize()));
			if (!positionsAgents.containsKey(p1) && !positionsAgents.containsValue(p2)) {
				positionsAgents.put(p1, p2);
			}
			//Agent a = new Agent(this.grid, postOffice, aimPosition, startPosition)
		}
		return positionsAgents;
		
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
