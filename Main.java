package SMATP3;

import SMATP3.model.Agent;
import SMATP3.model.Grid;
import SMATP3.model.PostOffice;
import SMATP3.model.strategies.SimpleStrategy;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		Grid theGrid = new Grid(5);
		PostOffice po = new PostOffice();
		ArrayList<Agent> agents = new ArrayList<Agent>();

		agents.add(new Agent(theGrid, po, new Position(0, 3), new Position(1, 4)));
		agents.add(new Agent(theGrid, po, new Position(3, 0), new Position(3, 2)));

		for (Agent a : agents) {
			a.setStrategy(new SimpleStrategy());
		}

		theGrid.addAgents(agents);
		theGrid.launch(true);
	}
}
