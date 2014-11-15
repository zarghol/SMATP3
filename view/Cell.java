package SMATP3.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

public class Cell extends JLabel {

	private static final int SIZE = 60;
	private static final GeneralPath AIM_ICON = new GeneralPath();
	private static final GeneralPath AGENT_ICON = new GeneralPath();
	static {
		AIM_ICON.moveTo(0, 0);
		AIM_ICON.lineTo(60, 0);
		AIM_ICON.lineTo(60, 60);
		AIM_ICON.lineTo(0, 60);
		AIM_ICON.closePath();
		AIM_ICON.append(new Ellipse2D.Double(5, 5, 50, 50), false);
		AIM_ICON.setWindingRule(GeneralPath.WIND_EVEN_ODD);

		AGENT_ICON.append(new Ellipse2D.Double(7, 7, 46, 46), false);
		AGENT_ICON.setWindingRule(GeneralPath.WIND_NON_ZERO);
	}

	private Color aimColor = null;
	private Color agentColor = null;

	public Cell() {
		super();

		setSize(SIZE, SIZE);
		setPreferredSize(new Dimension(SIZE, SIZE));
	}

	public void setAgentColor(Color agentColor) {
		this.agentColor = agentColor;
	}

	public void setAimColor(Color aimColor) {
		this.aimColor = aimColor;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if(agentColor != null) {
			g2d.setColor(agentColor);
			g2d.fill(AGENT_ICON);
		}

		if(aimColor != null) {
			g2d.setColor(aimColor);
			g2d.fill(AIM_ICON);
		}
	}
}
