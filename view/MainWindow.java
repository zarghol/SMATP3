package SMATP3.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import SMATP3.controller.Controller;
import SMATP3.model.Grid;

//TODO: Mettre plus de contrôles dans le panneau de contrôle

public class MainWindow extends JFrame {

	private static final int LAYOUT_SPACING = 10;

	private Board board;
	private JToolBar controlPanel;

	public MainWindow(Controller controller) throws HeadlessException {
		this.setTitle("SMA TP3");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);

		board = new Board(5, controller.getGrid());

		this.getContentPane().setLayout(new BorderLayout(LAYOUT_SPACING, LAYOUT_SPACING));
		this.getContentPane().add(board, BorderLayout.CENTER);
		
		this.controlPanel = new JToolBar(JToolBar.VERTICAL);
		this.controlPanel.setFloatable(false);
		
		this.controlPanel.addSeparator(new Dimension(0, LAYOUT_SPACING));
		
		this.addButton("Lancer", controller);
		this.addButton("1 pas", controller);

		this.getContentPane().add(controlPanel, BorderLayout.WEST);

		this.pack();
		this.setVisible(true);
	}
	
	private void addButton(String name, ActionListener actionListener) {
		JButton button = new JButton(name);
		button.setActionCommand(name);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.addActionListener(actionListener);
		this.controlPanel.add(button);
		this.controlPanel.addSeparator(new Dimension(0, LAYOUT_SPACING));
	}
}
