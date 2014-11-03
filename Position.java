package SMATP3;


public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	public Position cloneadd(int x, int y) {
		return new Position(this.x + x, this.y + y);
	}
	
}
