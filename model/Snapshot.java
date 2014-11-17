package SMATP3.model;

import SMATP3.utils.Direction;
import SMATP3.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Snapshot {

	protected final Object lockPositions = new Object();
	protected final int gridSize;

	protected Map<Position, Integer> positions;

	/**
	 * Constructor.
	 *
	 * @param gridSize The dimension of the grid.
	 */
	public Snapshot(int gridSize) {
		this.gridSize = gridSize;
		this.positions = new HashMap<Position, Integer>();
	}

	/**
	 * Copy constructor.
	 *
	 * @param snapshot The Snapshot to copy.
	 */
	public Snapshot(Snapshot snapshot) {
		this(snapshot.gridSize);
		synchronized (snapshot.lockPositions) {
			for (Map.Entry<Position, Integer> entry : snapshot.positions.entrySet()) {
				this.positions.put(new Position(entry.getKey()), entry.getValue());
			}
		}
	}

	/**
	 * Retourne le voisinage d'une case.
	 *
	 * @param positionChecked La position dont on on tire le voisinage.
	 * @return L'ensemble des identifiants des agents autour de la position.
	 */
	public ArrayList<Integer> getNeighbourhood(Position positionChecked) {
		// Déterminer s'il faut mettre toute la méthode dans synchronized ou seulement la ligne utilisant this.positions
		// REP: Il faut tout mettre : on ajoute la position en haut, puis celle du bas, mais pendant celle du bas, on modifie celle du haut : pas bon

		ArrayList<Integer> neighbourhood = new ArrayList<Integer>();
		Integer id;
		synchronized (this.lockPositions) {

			for (Direction d : Direction.values()) {
				Position neighbourPosition = positionChecked.towardDirection(d);
				id = this.positions.get(neighbourPosition);
				if (id != null) {
					neighbourhood.add(id);
				}
			}
		}
		return neighbourhood;
	}

	/**
	 * Retourne le voisinage vide d'une case.
	 *
	 * @param positionChecked La position dont on on tire le voisinage.
	 * @return L'ensemble des positions libres autour de la position indiquée.
	 */
	public ArrayList<Position> getEmptyNeighbourhood(Position positionChecked) {
		// Déterminer s'il faut mettre toute la méthode dans synchronized ou seulement la ligne utilisant this.positions
		// REP: Il faut tout mettre : on ajoute la position en haut, puis celle du bas, mais pendant celle du bas, on modifie celle du haut : pas bon
		ArrayList<Position> neighbourhood = new ArrayList<Position>();
		synchronized (this.lockPositions) {
			for (Direction d : Direction.values()) {
				Position neighbourPosition = positionChecked.towardDirection(d);
				if (this.isPositionValid(neighbourPosition) && !this.positions.containsKey(neighbourPosition)) {
					neighbourhood.add(neighbourPosition);
				}
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
		Integer id;
		synchronized (this.lockPositions) {
			id = this.positions.get(position);
		}
		return (id == null ? Agent.NO_AGENT : id);
	}

	/**
	 * Trouve si une case est occupée.
	 *
	 * @param position La position de la case a vérifier.
	 * @return True si la case existe et est occupée. False sinon.
	 */
	public boolean isPositionOccupied(Position position) {
		synchronized (this.lockPositions) {
			return this.positions.containsKey(position);
		}
	}

	/**
	 * Détermine si une case dans la grille existe.
	 *
	 * @param position La position de la case.
	 * @return True si la position est dans la limite de la grille. False sinon.
	 */
	public boolean isPositionValid(Position position) {
		return position.getX() >= 0
				&& position.getX() < this.gridSize
				&& position.getY() >= 0
				&& position.getY() < this.gridSize;
	}

	public int getGridSize() {
		return gridSize;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.gridSize; i++) {
			sb.append("[");
			for (int j = 0; j < this.gridSize; j++) {
				Position p = new Position(j, i);
				int agentId = this.getAgentId(p);
				if (agentId == -1) {
					sb.append(" ");
				} else {
					sb.append(agentId);
				}
			}
			sb.append("]\n");
		}
		return sb.toString();
	}
}
