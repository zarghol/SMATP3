package SMATP3.view;


import javax.swing.*;
import java.awt.*;

//TODO: Mettre des contrôles dans le panneau de contrôle

public class MainWindow extends JFrame {

	private static final int LAYOUT_SPACING = 10;

	private Board board;
//	private JPanel controlPanel;

	public MainWindow() throws HeadlessException {
		setTitle("SMA TP3");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		board = new Board(5);

		getContentPane().setLayout(new BorderLayout(LAYOUT_SPACING, LAYOUT_SPACING));
		getContentPane().add(board, BorderLayout.CENTER);
//		getContentPane().add(controlPanel, BorderLayout.WEST);

		pack();
	}
}
