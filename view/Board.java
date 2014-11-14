package SMATP3.view;

import javax.swing.*;

public class Board extends JPanel {

	private static int DEFAULT_CELL_SIZE = 60;

	private int boardSize;
	private double zoomFactor;

	public Board(int boardSize) {
		super(true); // Double-buffered
		this.boardSize = boardSize;
		this.zoomFactor = 1.0;
	}

	//TODO: Dessiner le plateau
}
