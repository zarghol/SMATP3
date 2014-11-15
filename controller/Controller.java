package SMATP3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import SMATP3.Position;
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
	private LaunchAction launchAction = new LaunchAction();
	private StepAction stepAction = new StepAction();

	public Controller(int boardSize) {
		this.grid = new Grid(boardSize);
		PostOffice postOffice = new PostOffice();
		List<Agent> agents = new ArrayList<Agent>();

		agents.add(new Agent(this.grid, postOffice, new Position(0, 3), new Position(1, 4)));
		agents.add(new Agent(this.grid, postOffice, new Position(3, 0), new Position(3, 2)));
		agents.add(new Agent(this.grid, postOffice, new Position(1, 2), new Position(2, 2)));
		agents.add(new Agent(this.grid, postOffice, new Position(3, 4), new Position(0, 2)));
		// FIXME : verifier pourquoi seul l'agent 0 parle
		for (Agent a : agents) {
			a.setStrategy(new SimpleStrategy());
		}
		//this.applyStrategy(SimpleStrategy.class);
		this.grid.addAgents(agents);

		this.window = new MainWindow(this);
	}
	
	/*
	 * TODO: faire fonctionner -> Ce serait cool
	private void applyStrategy(Class<ThinkingStrategy> strategy) {
		for (Agent a : this.agents) {
			try {
				a.setStrategy(strategy.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				a.setStrategy(new SimpleStrategy());
			}
		}
	}
	*/
	
	public Grid getGrid() {
		return this.grid;
	}

	public LaunchAction getLaunchAction() {
		return launchAction;
	}

	public StepAction getStepAction() {
		return stepAction;
	}

	public static void main(String[] args) {
		Controller controller = new Controller(5);
	}


	public class LaunchAction extends AbstractAction {
		public LaunchAction() {
			super("Launch");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("launch");
			grid.launch(true);
		}
	}

	public class StepAction extends AbstractAction {
		public StepAction() {
			super("Step");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: implémentation étape par étape
		}
	}
}
