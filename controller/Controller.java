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

// TODO: linking entre la maj de la grille et l'affichage dans le Window
public class Controller implements ActionListener {
	private MainWindow window;
	private Grid grid;
	private PostOffice postOffice;
	private List<Agent> agents;
	
	
	public Controller() {
		this.grid = new Grid(5);
		this.postOffice = new PostOffice();
		this.agents = new ArrayList<Agent>();

		this.agents.add(new Agent(this.grid, this.postOffice, new Position(0, 3), new Position(1, 4)));
		this.agents.add(new Agent(this.grid, this.postOffice, new Position(3, 0), new Position(3, 2)));
		this.agents.add(new Agent(this.grid, this.postOffice, new Position(1, 2), new Position(2, 2)));
		this.agents.add(new Agent(this.grid, this.postOffice, new Position(3, 4), new Position(0, 2)));
		// FIXME : verifier pourquoi seul l'agent 0 parle
		for (Agent a : this.agents) {
			a.setStrategy(new SimpleStrategy());
		}
		//this.applyStrategy(SimpleStrategy.class);
		this.grid.addAgents(this.agents);
		
		this.window = new MainWindow(this);
	}
	
	/*
	 * TODO: faire fonctionner -> Ce serait cool
	public void applyStrategy(Class<ThinkingStrategy> strategy) {
		for (Agent a : this.agents) {
			try {
				a.setStrategy(strategy.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				a.setStrategy(new SimpleStrategy());
			}
		}
	}
	*/
	
	public void launch() {
		System.out.println("launch");
		this.grid.launch(true);
	}
	
	public Grid getGrid() {
		return this.grid;
	}
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals("Lancer")) {
			this.launch();
		}
	}
}
