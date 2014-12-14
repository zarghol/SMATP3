package SMATP3.view;


import SMATP3.controller.Controller;
import SMATP3.model.Grid;
import SMATP3.model.components.AgentCountSpinnerModel;
import SMATP3.model.components.GridSizeSpinnerModel;
import SMATP3.model.strategies.Strategy;
import SMATP3.utils.Observer;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.HeadlessException;

public class MainWindow extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private static final int LAYOUT_SPACING = 10;

	private Board board;
	private JToolBar controlPanel;
	private JSpinner gridSizeSpinner;
	private JSpinner agentCountSpinner;
	private JComboBox<Strategy> strategyPicker;
	private JButton resetButton;
	private JButton randomizeButton;
	private JButton startStopButton;
	private JSlider speedSlider;

	public MainWindow(Controller controller) throws HeadlessException {
		this.setTitle("SMA TP3");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);

		getContentPane().setLayout(new BorderLayout(LAYOUT_SPACING, LAYOUT_SPACING));
		
		controlPanel = new JToolBar(JToolBar.VERTICAL);
		SpringLayout panelLayout = new SpringLayout();
		controlPanel.setLayout(panelLayout);
		controlPanel.setFloatable(false);

		JLabel gridSizeLabel = new JLabel("Grid size :");
		controlPanel.add(gridSizeLabel);
		panelLayout.putConstraint(SpringLayout.WEST, gridSizeLabel, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, gridSizeLabel, LAYOUT_SPACING, SpringLayout.NORTH, controlPanel);

		gridSizeSpinner = new JSpinner(new GridSizeSpinnerModel());
		gridSizeSpinner.addChangeListener(controller.getGridSizeSpinnerListener());
		controlPanel.add(gridSizeSpinner);
		panelLayout.putConstraint(SpringLayout.EAST, gridSizeSpinner, -LAYOUT_SPACING, SpringLayout.EAST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, gridSizeSpinner, LAYOUT_SPACING, SpringLayout.NORTH, controlPanel);

		JLabel agentCountLabel = new JLabel("Agent count :");
		controlPanel.add(agentCountLabel);
		panelLayout.putConstraint(SpringLayout.WEST, agentCountLabel, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, agentCountLabel, LAYOUT_SPACING, SpringLayout.SOUTH, gridSizeSpinner);

		agentCountSpinner = new JSpinner(new AgentCountSpinnerModel());
		agentCountSpinner.setValue(Grid.DEFAULT_AGENT_COUNT);
		controlPanel.add(agentCountSpinner);
		panelLayout.putConstraint(SpringLayout.EAST, agentCountSpinner, -LAYOUT_SPACING, SpringLayout.EAST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, agentCountSpinner, LAYOUT_SPACING, SpringLayout.SOUTH, gridSizeSpinner);
		
		JLabel strategyPickerLabel = new JLabel("Strategy :");
		controlPanel.add(strategyPickerLabel);
		panelLayout.putConstraint(SpringLayout.WEST, strategyPickerLabel, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, strategyPickerLabel, LAYOUT_SPACING, SpringLayout.SOUTH, agentCountSpinner);

		strategyPicker = new JComboBox<Strategy>(Strategy.values());
		controlPanel.add(strategyPicker);
		panelLayout.putConstraint(SpringLayout.EAST, strategyPicker, -LAYOUT_SPACING, SpringLayout.EAST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, strategyPicker, LAYOUT_SPACING, SpringLayout.SOUTH, agentCountSpinner);
		
		panelLayout.putConstraint(SpringLayout.WEST, strategyPicker, LAYOUT_SPACING, SpringLayout.EAST, strategyPickerLabel);

		

		randomizeButton = new JButton(controller.getRandomizeAction());
//		try {
//			resetButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("SMATP3/resources/ic_randomize.png"))));
//		} catch(IOException e) {
//			System.err.println("Icon not found.");
//		}
		controlPanel.add(randomizeButton);
		panelLayout.putConstraint(SpringLayout.WEST, randomizeButton, LAYOUT_SPACING, SpringLayout.WEST, controlPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, randomizeButton, LAYOUT_SPACING, SpringLayout.SOUTH, strategyPicker);

		resetButton = new JButton(controller.getResetAction());
//		try {
//			resetButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("SMATP3/resources/ic_reset.png"))));
//		} catch(IOException e) {
//			System.err.println("Icon not found.");
//		}
		controlPanel.add(resetButton);
		panelLayout.putConstraint(SpringLayout.WEST, resetButton, LAYOUT_SPACING, SpringLayout.EAST, randomizeButton);
		panelLayout.putConstraint(SpringLayout.NORTH, resetButton, LAYOUT_SPACING, SpringLayout.SOUTH, strategyPicker);

		startStopButton = new JButton(controller.getStartAction());
//		try {
//			resetButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource("SMATP3/resources/ic_start.png"))));
//		} catch(IOException e) {
//			System.err.println("Icon not found.");
//		}
		controlPanel.add(startStopButton);
		panelLayout.putConstraint(SpringLayout.WEST, startStopButton, LAYOUT_SPACING, SpringLayout.EAST, resetButton);
		panelLayout.putConstraint(SpringLayout.NORTH, startStopButton, LAYOUT_SPACING, SpringLayout.SOUTH, strategyPicker);

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
		this.pack();
	}

	public JSpinner getGridSizeSpinner() {
		return gridSizeSpinner;
	}

	public JSpinner getAgentCountSpinner() {
		return agentCountSpinner;
	}

	public JComboBox<Strategy> getStrategyPicker() {
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

	@Override
	public void update(Object arg) {
		if(arg == Grid.NotificationCode.PUZZLE_SOLVED) {
			// Déclenche un clic sur le bouton pour le repasser a 'Start'.
			// Cela n'a pas d'influence sur le modèle puisque les agents sont déjà arrêtés,
			startStopButton.doClick();
			//TODO: notifier la vue depuis le modèle (une seule fois!)
		}
	}
}
