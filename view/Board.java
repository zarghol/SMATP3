package SMATP3.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import SMATP3.Position;
import SMATP3.model.Grid;

public class Board extends JPanel {

	private final Map<Position, Cell> cells;

	public Board(int boardSize, Grid grid) {
		super(true); // Double-buffered
		cells = new HashMap<Position, Cell>(boardSize * boardSize);
		Map<Position, Integer> aimMap = new HashMap<Position, Integer>();
		setLayout(new GridLayout(boardSize, boardSize, 1, 1));
		for(int column = 0; column < boardSize; ++column) {
			for(int row = 0; row < boardSize; ++row) {
				Cell cell = new Cell();
				Position p = new Position(column, row);
				int agentId = grid.getAgentId(p);

				if (agentId != -1) {
					// si y a un agent, on l'affiche					
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
}
