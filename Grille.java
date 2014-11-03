package SMATP3;

import java.util.ArrayList;
import java.util.HashMap;


public class Grille {
	private HashMap<Position, Agent> tab;	
	
	public ArrayList<Agent> getNeighbourhood(Agent agent) {
		ArrayList<Agent> result = new ArrayList<Agent>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				Position p = agent.getPosition().cloneadd(i, j);
				Agent a = this.tab.get(p);
				if (a != null) {
					result.add(a);
				}

			}
		}
		return result;
	}

    public boolean isSolved() {
        //TODO
        return false;
    }
}
