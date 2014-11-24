package SMATP3.view;


import SMATP3.controller.Controller;
import SMATP3.model.Grid;
import SMATP3.model.components.AgentCountSpinnerModel;
import SMATP3.model.components.GridSizeSpinnerModel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.HeadlessException;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int LAYOUT_SPACING = 10;

	private Board board;
	private JToolBar controlPanel;
	private JSpinner gridSizeSpinner;
	private JSpinner agentCountSpinner;
	private JComboBox<String> strategyPicker; //TODO: ajouter liste de sélection de stratégie
	private JButton resetButton;
	private JButton randomizeButton;
	private JButton startStopButton;
	private JSlider speedSlider;

	public MainWindow(Controller controller) throws HeadlessException {
		this.setTitle("SMA TP3");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);


		getContentPane().setLayout(new BorderLayout(LAYOUT_SPACING, LAYOUT_SPACING));
		setGrid(controller.getGrid());

		
		controlPanel = new JToolBar(JToolBar.VERTICAL);
		SpringLayout panelLayout = new SpringLayout();
		controlPanel.setLayout(panelLayout);
		controlPanel.setFloatable(false);

		JLabel gridSizeLabel = new JLabel("Grid size :");
		controlPanel.add(gridSizeLabel);
		panelLayout.putConstraint(SpringLayout.WEST, gridSizeLabel, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, gridSizeLabel, LAYOUT_SPACING, SpringLayout.NORTH, controlPanel);

		gridSizeSpinner = new JSpinner(new GridSizeSpinnerModel());
		gridSizeSpinner.setValue(controller.getGrid().getGridSize());
		controlPanel.add(gridSizeSpinner);
		panelLayout.putConstraint(SpringLayout.EAST, gridSizeSpinner, -LAYOUT_SPACING, SpringLayout.EAST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, gridSizeSpinner, LAYOUT_SPACING, SpringLayout.NORTH, controlPanel);

		JLabel agentCountLabel = new JLabel("Agent count :");
		controlPanel.add(agentCountLabel);
		panelLayout.putConstraint(SpringLayout.WEST, agentCountLabel, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, agentCountLabel, LAYOUT_SPACING, SpringLayout.SOUTH, gridSizeSpinner);

		agentCountSpinner = new JSpinner(new AgentCountSpinnerModel());
		agentCountSpinner.setValue(controller.getGrid().getAgentCount());
		controlPanel.add(agentCountSpinner);
		panelLayout.putConstraint(SpringLayout.EAST, agentCountSpinner, -LAYOUT_SPACING, SpringLayout.EAST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, agentCountSpinner, LAYOUT_SPACING, SpringLayout.SOUTH, gridSizeSpinner);

		randomizeButton = new JButton(controller.getRandomizeAction());
//		try {
//			resetButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("SMATP3/resources/ic_randomize.png"))));
//		} catch(IOException e) {
//			System.err.println("Icon not found.");
//		}
		controlPanel.add(randomizeButton);
		panelLayout.putConstraint(SpringLayout.WEST, randomizeButton, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, randomizeButton, LAYOUT_SPACING, SpringLayout.SOUTH, agentCountSpinner);

		resetButton = new JButton(controller.getResetAction());
//		try {
//			resetButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("SMATP3/resources/ic_reset.png"))));
//		} catch(IOException e) {
//			System.err.println("Icon not found.");
//		}
		controlPanel.add(resetButton);
		panelLayout.putConstraint(SpringLayout.WEST, resetButton, LAYOUT_SPACING, SpringLayout.EAST, randomizeButton);
		panelLayout.putConstraint(SpringLayout.NORTH, resetButton, LAYOUT_SPACING, SpringLayout.SOUTH, agentCountSpinner);

		startStopButton = new JButton(controller.getStartAction());
//		try {
//			resetButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("SMATP3/resources/ic_start.png"))));
//		} catch(IOException e) {
//			System.err.println("Icon not found.");
//		}
		controlPanel.add(startStopButton);
		panelLayout.putConstraint(SpringLayout.WEST, startStopButton, LAYOUT_SPACING, SpringLayout.EAST, resetButton);
		panelLayout.putConstraint(SpringLayout.NORTH, startStopButton, LAYOUT_SPACING, SpringLayout.SOUTH, agentCountSpinner);

		JLabel speedLabel = new JLabel("Speed :");
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

		panelLayout.putConstraint(SpringLayout.EAST, controlPanel, LAYOUT_SPACING, SpringLayout.EAST, startStopButton);

		this.getContentPane().add(controlPanel, BorderLayout.WEST);

		this.pack();
		this.setVisible(true);
	}

	public void setGrid(Grid grid) {
		if(board != null) {
			getContentPane().remove(board);
		}
		board = new Board(grid);
		getContentPane().add(board, BorderLayout.CENTER);
		validate();
		repaint();
	}

	public JSpinner getGridSizeSpinner() {
		return gridSizeSpinner;
	}

	public JSpinner getAgentCountSpinner() {
		return agentCountSpinner;
	}

	public JComboBox<String> getStrategyPicker() {
		return strategyPicker;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public JButton getRandomizeButton() {
		return randomizeButton;
	}

	public JButton getStartStopButton() {
		return startStopButton;
	}

	public JSlider getSpeedSlider() {
		return speedSlider;
	}
}
