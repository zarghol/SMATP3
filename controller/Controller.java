package SMATP3.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import SMATP3.utils.Position;
import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.PostOffice;
import SMATP3.model.strategies.*;
import SMATP3.view.MainWindow;

import javax.swing.*;

// TODO: linking entre la maj de la grille et l'affichage dans le Window
public class Controller {
	private MainWindow window;
	private Grid grid;
	private StartAction startAction = new StartAction();
	private StopAction stopAction = new StopAction();

	public Controller(int boardSize) {
		this.grid = new Grid(boardSize);
		PostOffice postOffice = new PostOffice();
		List<Agent> agents = new ArrayList<Agent>();

		agents.add(new Agent(this.grid, postOffice, new Position(0, 3), new Position(1, 4)));
		agents.add(new Agent(this.grid, postOffice, new Position(3, 0), new Position(3, 2)));
		agents.add(new Agent(this.grid, postOffice, new Position(1, 2), new Position(2, 2)));
		agents.add(new Agent(this.grid, postOffice, new Position(3, 4), new Position(0, 2)));

		this.applyStrategy(PlanifiedSimpleStrategy.class, agents);
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


	public class StartAction extends AbstractAction {
		public StartAction() {
			super("Start");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
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
			System.out.println("stop");
			grid.stop();
		}
	}
}
