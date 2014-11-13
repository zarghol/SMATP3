package SMATP3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Snapshot {

	protected HashMap<Position, Integer> positions;
	protected int gridSize;

	public Snapshot(int gridSize) {
		this.positions = new HashMap<>();
		this.gridSize = gridSize;
	}

	public Snapshot(Snapshot snapshot) {
		for (Map.Entry<Position, Integer> entry : snapshot.positions.entrySet()) {
			this.positions.put(new Position(entry.getKey()), entry.getValue());
		}
		this.gridSize = snapshot.gridSize;
	}

	/**
	 * Retourne le voisinage d'un agent
	 *
	 * @param agent L'agent duquel on tire le voisinage
	 * @return L'ensemble des agents autour d'un agent
	 */
	public ArrayList<Integer> getNeighbourhood(Agent agent) {
		ArrayList<Integer> neighbourhood = new ArrayList<>();
		for (Direction d : Direction.allDirections()) {
			Position neighbourPosition = agent.getPosition().move(d);
			if (this.positions.containsKey(neighbourPosition)) {
				neighbourhood.add(this.positions.get(neighbourPosition));
			}
		}
		return neighbourhood;
	}

	/**
	 * Renvoie l'id d'un agent à une position donnée.
	 *
	 * @param position La position de l'agent dont on veut l'id.
	 * @return L'id de l'agent demandé.
	 */
	public int getAgentId(Position position) {
		return this.positions.get(position);
	}

	/**
	 * Trouve si une case est occupée
	 *
	 * @param position la position de la case a vérifier
	 * @return retourne vrai si la case existe et qu'elle est occupée
	 */
	public boolean isPositionOccupied(Position position) {
		return this.positions.containsKey(position);
	}

	/**
	 * Trouve si une case dans la grille existe
	 *
	 * @param position la position de la case
	 * @return retourne vrai si la position est dans la limite de la grille
	 */
	public boolean isPositionValid(Position position) {
		return position.getX() >= 0
				&& position.getX() < this.gridSize
				&& position.getY() >= 0
				&& position.getY() < this.gridSize;
	}
}
