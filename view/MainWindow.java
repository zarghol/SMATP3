package SMATP3.view;


import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import SMATP3.model.Grid;

//TODO: Mettre des contrôles dans le panneau de contrôle

public class MainWindow extends JFrame {

	private static final int LAYOUT_SPACING = 10;

	private Board board;
//	private JPanel controlPanel;

	public MainWindow(Grid grid) throws HeadlessException {
		setTitle("SMA TP3");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		board = new Board(5, grid);

		getContentPane().setLayout(new BorderLayout(LAYOUT_SPACING, LAYOUT_SPACING));
		getContentPane().add(board, BorderLayout.CENTER);
//		getContentPane().add(controlPanel, BorderLayout.WEST);

		pack();
	}
}
