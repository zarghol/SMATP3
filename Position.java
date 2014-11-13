package SMATP3;

public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int val) {
		this.x = val;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int val) {
		this.y = val;
	}

	/**
	 * Makes the sum of the current instance and the one given in parameter.
	 * @param position The position to sum up with the current one.
	 * @return A new Position instance containing the sum of the current instance and the one given in parameter
	 */
	public Position sum(Position position) {
		return new Position(this.x + position.getX(), this.y + position.getY());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	public Position towardDirection(Direction direction) {
		return this.sum(direction.toPosition());
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Position other = (Position) obj;

		return this.x == other.x && this.y == other.y;
	}
}
