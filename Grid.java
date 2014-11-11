package SMATP3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Grid extends Snapshot {
//TODO: create a method to get a snapshot of the grid (without copying the agents)

	private HashMap<Integer, Agent> agents;

	public Grid(int gridSize) {
		super(gridSize);
	}

	public void addAgent(Agent agent) {
		if (this.isPositionValid(agent.getPosition())) {
			this.agents.put(agent.getId(), agent);
			this.positions.put(agent.getPosition(), agent.getId());
		}
	}
	
	public void addAgents(List<Agent> agents) {
		for (Agent a : agents) {
			this.addAgent(a);
		}
	}

	/**
	 * Deplace un agent d'une case à une autre
	 *
	 * @param from position d'origine de l'agent
	 * @param to   position de destination de l'agent
	 */
	public void moveAgent(Position from, Position to) {
		int agentId = this.getAgentId(from);
		this.positions.remove(from);
		this.positions.put(to, agentId);
	}
	
	public void launch() {
		for (Agent agent : this.agents.values()) {
			Thread t = new Thread(agent);
			t.run();
		}
	}

	public static void main(String[] args) {
		Grid theGrid = new Grid(5);
		PostOffice po = new PostOffice();
		ArrayList<Agent> agents = new ArrayList<>();
		
		agents.add(new Agent(theGrid, po, new Position(0, 3), new Position(1, 4)));
		agents.add(new Agent(theGrid, po, new Position(3, 0), new Position(3, 2)));
		
		theGrid.addAgents(agents);
		theGrid.launch();
	}

	/**
	 * Determine si le puzzle est terminé (chaque agent à la bonne place)
	 *
	 * @return un booleen montrant l'avancement du puzzle
	 */
	public boolean isSolved() {
//TODO: Ne pourrait-on pas le remonter dans Snapshot ?
		for (Agent a : this.agents.values()) {
			if (!a.isHappy()) {
				return false;
			}
		}
		return true;
	}
}
