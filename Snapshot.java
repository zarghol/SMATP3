package SMATP3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Snapshot {

	protected HashMap<Position, Integer> positions;
	protected int gridSize;

	public Snapshot(int gridSize) {
		this.positions = new HashMap<Position, Integer>();
		this.gridSize = gridSize;
	}

	public Snapshot(Snapshot snapshot) {
		for (Map.Entry<Position, Integer> entry : snapshot.positions.entrySet()) {
			this.positions.put(new Position(entry.getKey()), entry.getValue());
		}
		this.gridSize = snapshot.gridSize;
	}

	/**
	 * Retourne le voisinage d'une case
	 *
	 * @param positionChecked La position dont on on tire le voisinage
	 * @return L'ensemble des identifiants des agents autour de la position
	 */
	public ArrayList<Integer> getNeighbourhood(Position positionChecked) {
		ArrayList<Integer> neighbourhood = new ArrayList<Integer>();
		for (Direction d : Direction.allDirections()) {
			Position neighbourPosition = positionChecked.towardDirection(d);
			if (this.positions.containsKey(neighbourPosition)) {
				neighbourhood.add(this.positions.get(neighbourPosition));
			}
		}
		return neighbourhood;
	}
	
	/**
	 * Retourne le voisinage vide d'une case
	 *
	 * @param positionChecked La position dont on on tire le voisinage
	 * @return L'ensemble des positions libres autour de la position indiquée
	 */
	public ArrayList<Position> getEmptyNeighbourhood(Position positionChecked) {
		ArrayList<Position> neighbourhood = new ArrayList<Position>();
		for (Direction d : Direction.allDirections()) {
			Position neighbourPosition = positionChecked.towardDirection(d);
			if (!this.positions.containsKey(neighbourPosition)) {
				neighbourhood.add(neighbourPosition);
			}
		}
		return neighbourhood;
	}
	
	

	/**
	 * Renvoie l'id d'un agent à une position donnée.
	 *
	 * @param position La position de l'agent dont on veut l'id.
	 * @return L'id de l'agent demandé, ou NO_AGENT (-1) si aucun agent ne se trouve à cette position.
	 */
	public int getAgentId(Position position) {
		Integer id = this.positions.get(position);
		return (id == null ? Agent.NO_AGENT : id);
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

	@Override
	public String toString() {
		String string = "";
		for (int i = 0; i < this.gridSize; i++) {
			string += "[";
			for (int j = 0; j < this.gridSize; j++) {
				Position p = new Position(j, i);
				int agentId = this.getAgentId(p);
				if (agentId == -1) {
					string += " ";
				} else {
					string += Agent.getSymbol(agentId);
				}
			}
			string += "]\n";
		}
		
		
		return string;
	}
	
	
	
}
