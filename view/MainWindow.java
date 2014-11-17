package SMATP3.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.*;

import SMATP3.controller.Controller;

//TODO: Mettre plus de contrôles dans le panneau de contrôle

public class MainWindow extends JFrame {

	private static final int LAYOUT_SPACING = 10;

	private Board board;
	private JToolBar controlPanel;
	private JButton startStopButton;
	private JSlider speedSlider;

	public MainWindow(Controller controller) throws HeadlessException {
		this.setTitle("SMA TP3");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);

		board = new Board(controller.getGrid());

		getContentPane().setLayout(new BorderLayout(LAYOUT_SPACING, LAYOUT_SPACING));
		getContentPane().add(board, BorderLayout.CENTER);
		
		controlPanel = new JToolBar(JToolBar.VERTICAL);
		SpringLayout panelLayout = new SpringLayout();
		controlPanel.setLayout(panelLayout);
		controlPanel.setFloatable(false);

		startStopButton = new JButton(controller.getStartAction());
		startStopButton.setHorizontalAlignment(SwingConstants.CENTER);
		controlPanel.add(startStopButton);
		panelLayout.putConstraint(SpringLayout.WEST, startStopButton, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, startStopButton, LAYOUT_SPACING, SpringLayout.NORTH, controlPanel);

		JLabel speedLabel = new JLabel("Speed :");
		speedLabel.setHorizontalAlignment(SwingConstants.LEFT);
		controlPanel.add(speedLabel);
		panelLayout.putConstraint(SpringLayout.WEST, speedLabel, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, speedLabel, LAYOUT_SPACING, SpringLayout.SOUTH, startStopButton);

		speedSlider = new JSlider(controller.getSpeedBounds());
		speedSlider.setOrientation(JSlider.HORIZONTAL);
		speedSlider.setMajorTickSpacing(5);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(controller.getSpeedSliderListener());
		controlPanel.add(speedSlider);
		panelLayout.putConstraint(SpringLayout.WEST, speedSlider, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, speedSlider, LAYOUT_SPACING, SpringLayout.SOUTH, speedLabel);

		panelLayout.putConstraint(SpringLayout.EAST, controlPanel, LAYOUT_SPACING, SpringLayout.EAST, speedSlider);

		this.getContentPane().add(controlPanel, BorderLayout.WEST);

		this.pack();
		this.setVisible(true);
	}

	public JButton getStartStopButton() {
		return startStopButton;
	}
}
