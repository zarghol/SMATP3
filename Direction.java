package SMATP3;

public enum Direction {
	UP(0, 1),
	DOWN(0, -1),
	LEFT(-1, 0),
	RIGHT(1, 0);

	private int xDir;
	private int yDir;

	private Direction(int xDir, int yDir) {
		this.xDir = xDir;
		this.yDir = yDir;
	}

	public Position toPosition() {
		return new Position(xDir,yDir);
	}

	//TODO: Improve implementation (longest first instead of X first)
	public static Direction directionDifferential(Position from, Position to) {
		int xDiff = to.getX() - from.getX();
		int yDiff = to.getY() - from.getY();

		if (xDiff > 0) {
			return Direction.RIGHT;
		}

		if (xDiff < 0) {
			return Direction.LEFT;
		}

		if (yDiff > 0) {
			return Direction.UP;
		}

		if (yDiff < 0) {
			return Direction.DOWN;
		}

		return null;
	}
}
