package SMATP3.controller;

import SMATP3.Position;
import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.PostOffice;
import SMATP3.model.strategies.BaseStrategy;
import SMATP3.model.strategies.ThinkingStrategy;
import SMATP3.view.MainWindow;

import java.util.ArrayList;
import java.util.List;

public class Controller {
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
		
		for (Agent a : this.agents) {
			a.setStrategy(new BaseStrategy());
		}
		//this.applyStrategy(SimpleStrategy.class);
		this.grid.addAgents(this.agents);
		
		this.window = new MainWindow();
		this.window.setVisible(true);
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
		this.grid.launch(true);
	}
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}
}
