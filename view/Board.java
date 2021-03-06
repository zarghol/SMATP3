package SMATP3.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import SMATP3.model.Grid;
import SMATP3.model.Snapshot;
import SMATP3.utils.Observer;
import SMATP3.utils.Position;

public class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private final Map<Position, Cell> cells;
	private final Grid grid;
	
	public Board(int boardSize) {
		super(true);
		cells = new HashMap<Position, Cell>(boardSize * boardSize);
		grid = null;

		setLayout(new GridLayout(boardSize, boardSize, 1, 1));

		for (int column = 0; column < boardSize; ++column) {
			for (int row = 0; row < boardSize; ++row) {
				Cell cell = new Cell();
				Position p = new Position(column, row);
				cells.put(p, cell);
				this.add(cell);
			}
		}		
	}
	
	public Board(Grid grid) {
		super(true); // Double-buffered

		this.grid = grid;
		grid.registerObserver(this);

		int boardSize = grid.getGridSize();
		cells = new HashMap<Position, Cell>(boardSize * boardSize);
		Map<Position, Integer> aimMap = new HashMap<Position, Integer>();

		setLayout(new GridLayout(boardSize, boardSize, 1, 1));

		for (int column = 0; column < boardSize; ++column) {
			for (int row = 0; row < boardSize; ++row) {
				Cell cell = new Cell();
				Position p = new Position(column, row);
				int agentId = grid.getAgentId(p);

				if (agentId != -1) {
					// si y a la destination d'un agent, on l'affiche
					aimMap.put(grid.getAgent(agentId).getAimPosition(), agentId);
					cell.setAgentColor(Cell.colorForAgent(agentId));
				}

				cells.put(p, cell);
				this.add(cell);
			}
		}

		for (Entry<Position, Integer> entry : aimMap.entrySet()) {
			cells.get(entry.getKey()).setAimColor(Cell.colorForAgent(entry.getValue()));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void update(Object arg) {
		if (arg == Grid.NotificationCode.AGENT_MOVED) {
			synchronized (this) {
				System.out.println("board update");
				Snapshot snapshot = new Snapshot(grid);
				int boardSize = snapshot.getGridSize();
				for (int column = 0; column < boardSize; ++column) {
					for (int row = 0; row < boardSize; ++row) {
						Position position = new Position(column, row);
						int id = snapshot.getAgentId(position);
						Cell cell = cells.get(position);
						cell.setAgentColor(Cell.colorForAgent(id));
					}
				}
				this.updateUI();
			}
		}
	}
}
