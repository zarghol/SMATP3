package SMATP3.view;

import SMATP3.Position;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Board extends JPanel {

	private final Map<Position, Cell> cells;

	public Board(int boardSize) {
		super(true); // Double-buffered
		cells = new HashMap<Position, Cell>(boardSize * boardSize);
		setLayout(new GridLayout(boardSize, boardSize, 1, 1));
		for(int column=0; column<boardSize; ++column) {
			for(int row=0; row<boardSize; ++row) {
				Cell cell = new Cell();
				cells.put(new Position(column, row), cell);
				add(cell);
			}
		}

		//-------- Demonstration
		Color c1 = Color.getHSBColor(0.625f, 0.75f, 1.0f);
		Color c2 = Color.getHSBColor(0.0f, 0.75f, 1.0f);
		cells.get(new Position(2, 1)).setAimColor(c1);
		cells.get(new Position(3, 3)).setAimColor(c2);
		cells.get(new Position(0, 0)).setAgentColor(c1);
		cells.get(new Position(2, 1)).setAgentColor(c2);
		//----------------------
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
