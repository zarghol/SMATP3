package SMATP3.view;

import SMATP3.model.Agent;

import javax.swing.JLabel;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

public class Cell extends JLabel {

	public final static int DEFAULT_SIZE = 80;

	private Color aimColor = null;
	private Color agentColor = null;

	public Cell() {
		super();

		setSize(DEFAULT_SIZE, DEFAULT_SIZE);
		setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
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
			g2d.fill(getAgentIcon());
		}

		if(aimColor != null) {
			g2d.setColor(aimColor);
			g2d.fill(getAimIcon());
		}
	}

	public static Color colorForAgent(int agentId) {
		if (agentId == Agent.NO_AGENT) {
			return null;
		}
		float hueAngle = ((agentId * 0.3f) % 1.0f);
		return Color.getHSBColor(hueAngle, hueAngle, hueAngle);
	}

	private GeneralPath getAimIcon() {
		int size = Math.max(getWidth(), getHeight());
		GeneralPath aimIcon = new GeneralPath();
		aimIcon.moveTo(0, 0);
		aimIcon.lineTo(size, 0);
		aimIcon.lineTo(size, size);
		aimIcon.lineTo(0, size);
		aimIcon.closePath();
		aimIcon.append(new Ellipse2D.Double(size / 20, size / 20, size - size / 10, size - size / 10), false);
		aimIcon.setWindingRule(GeneralPath.WIND_EVEN_ODD);
		return aimIcon;
	}

	private GeneralPath getAgentIcon() {
		int size = Math.max(getWidth(), getHeight());
		GeneralPath agentIcon = new GeneralPath();
		agentIcon.append(new Ellipse2D.Double(size / 20 + 2, size / 20 + 2, size - size / 10 - 4, size - size / 10 - 4), false);
		agentIcon.setWindingRule(GeneralPath.WIND_NON_ZERO);
		return agentIcon;
	}
}
