package SMATP3.utils;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
	UP(0, 1),
	DOWN(0, -1),
	LEFT(-1, 0),
	RIGHT(1, 0),
	NONE(0, 0);

	private int xDir;
	private int yDir;

	private Direction(int xDir, int yDir) {
		this.xDir = xDir;
		this.yDir = yDir;
	}

	public Position toPosition() {
		return new Position(xDir, yDir);
	}

	public static Direction directionDifferential(Position from, Position to) {
		int xDiff = to.getX() - from.getX();
		int yDiff = to.getY() - from.getY();
		
		if (from.equals(to)) {
			return Direction.NONE;
		}else if (Math.abs(xDiff) > Math.abs(yDiff)) {
			// Horizontal Direction
			if (xDiff > 0) {
				return Direction.RIGHT;
			} else {
				return Direction.LEFT;
			}
		} else {
			// Vertical Direction
			if (yDiff > 0) {
				return Direction.UP;
			} else {
				return Direction.DOWN;
			}
		}
	}
	
	public static List<Direction> directionsPossible(Position from, Position to) {
		int xDiff = to.getX() - from.getX();
		int yDiff = to.getY() - from.getY();
		
		List<Direction> positions = new ArrayList<Direction>();
		if (from.equals(to)) {
			positions.add(Direction.NONE);
		}else {
			// Horizontal Direction
			if (xDiff > 0) {
				positions.add(Direction.RIGHT);
			} else if (xDiff < 0){
				positions.add(Direction.LEFT);
			}
			// Vertical Direction
			if (yDiff > 0) {
				positions.add(Direction.UP);
			} else if (yDiff < 0) {
				positions.add(Direction.DOWN);
			}
		} 
		
		
		return positions;
	}
}
