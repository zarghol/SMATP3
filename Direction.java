package SMATP3;

public class Direction {
	public static Direction Up = new Direction(0, 1);
	public static Direction Down = new Direction(0, -1);
	public static Direction Left = new Direction(-1, 0);
	public static Direction Right = new Direction(1, 0);

	private int xDir;
	private int yDir;

	private Direction(int xDir, int yDir) {
		this.xDir = xDir;
		this.yDir = yDir;
	}

	public static Direction directionDifferential(Position from, Position to) {
		int xDiff = to.getX() - from.getX();
		int yDiff = to.getY() - from.getY();

		if (xDiff > 0) {
			return Direction.Right;
		}

		if (xDiff < 0) {
			return Direction.Left;
		}

		if (yDiff > 0) {
			return Direction.Up;
		}

		if (yDiff < 0) {
			return Direction.Down;
		}

		return null;
	}

	public Position newPosition(Position position) {
		return position.sum(xDir, yDir);
	}
}
