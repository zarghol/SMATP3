package SMATP3;

import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
	/**
	 * On ne conserve en mémoire que les agents, avec leurs positions
	 */
	private HashMap<Position, Agent> agents;
	/**
	 * la taille du tableau
	 */
	private int gridSize;
	
	public Grid(int gridSize) {
		this.agents = new HashMap<Position, Agent>();
		this.gridSize = gridSize;
	}
	
	public void addAgent(Agent a, Position p) {
		if (this.isDestinationValid(p)) {
			this.agents.put(p, a);
		}
	}
	

	/**
	 * Retourne le voisinage d'un agent
	 *
	 * @param agent l'agent duquel on tire le voisinage
	 * @return l'ensemble des agents autour d'un agent
	 */
	public ArrayList<Agent> getNeighbourhood(Agent agent) {
		ArrayList<Agent> result = new ArrayList<Agent>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				Position p = agent.getPosition().sum(i, j);
				Agent a = this.getAgent(p);
				if (a != null) {
					result.add(a);
				}
			}
		}
		return result;
	}

	/**
	 * Determine si le puzzle est terminé (chaque agent à la bonne place)
	 *
	 * @return un booleen montrant l'avancement du puzzle
	 */
	public boolean isSolved() {
		for (Agent a : this.agents.values()) {
			if (!a.isHappy()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Trouve si une case dans la grille existe
	 *
	 * @param position la position de la case
	 * @return retourne vrai si la position est dans la limite de la grille
	 */
	public boolean isDestinationValid(Position position) {
		return position.getX() >= 0 && position.getX() < this.gridSize && position.getY() >= 0 && position.getY() < this.gridSize;
	}

	/**
	 * Trouve si une case est occupée
	 *
	 * @param position la position de la case a vérifier
	 * @return retourne vrai si la case existe et qu'elle est occupée
	 */
	public boolean isPositionOccupied(Position position) {
		return this.isDestinationValid(position) && this.agents.containsKey(position);
	}

	/**
	 * Renvoi un agent sur la grille
	 *
	 * @param position la position de l'agent à retourner
	 * @return l'agent demandé
	 */
	public Agent getAgent(Position position) {
		return this.agents.get(position);
	}

	/**
	 * Deplace un agent d'une case à une autre
	 *
	 * @param fromPosition position d'origine de l'agent
	 * @param toPosition   position de destination de l'agent
	 */
	public void moveAgent(Position fromPosition, Position toPosition) {
		Agent a = this.getAgent(fromPosition);
		this.agents.remove(fromPosition);
		this.agents.put(toPosition, a);
	}

	@Override
	public Object clone() {
		Grid g = new Grid(this.gridSize);
		g.agents = (HashMap<Position, Agent>) this.agents.clone();
		return g;
	}
}
