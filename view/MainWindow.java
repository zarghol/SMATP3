package SMATP3.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import SMATP3.controller.Controller;

//TODO: Mettre plus de contrôles dans le panneau de contrôle

public class MainWindow extends JFrame {

	private static final int LAYOUT_SPACING = 10;

	private Board board;
	private JToolBar controlPanel;

	public MainWindow(Controller controller) throws HeadlessException {
		this.setTitle("SMA TP3");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);

		board = new Board(controller.getGrid());

		this.getContentPane().setLayout(new BorderLayout(LAYOUT_SPACING, LAYOUT_SPACING));
		this.getContentPane().add(board, BorderLayout.CENTER);
		
		this.controlPanel = new JToolBar(JToolBar.VERTICAL);
		this.controlPanel.setFloatable(false);
		
		this.controlPanel.addSeparator(new Dimension(0, LAYOUT_SPACING));

		JButton launchButton = new JButton(controller.getStartAction());
		launchButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.controlPanel.add(launchButton);
		this.controlPanel.addSeparator(new Dimension(0, LAYOUT_SPACING));


		JButton stopButton = new JButton(controller.getStopAction());
		stopButton.setHorizontalAlignment(SwingConstants.CENTER);
//		stopButton.setEnabled(false);
		this.controlPanel.add(stopButton);
//		this.controlPanel.addSeparator(new Dimension(0, LAYOUT_SPACING));
		//TODO: Ajouter un contrôle pour la vitesse d'exécution

		this.getContentPane().add(controlPanel, BorderLayout.WEST);

		this.pack();
		this.setVisible(true);
	}
}
