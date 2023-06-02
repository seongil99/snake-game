package game;

public class SnakeBlock {
	
	public final static int RIGHT = 0;
	public final static int LEFT = 1;
	public final static int UP = 2;
	public final static int DOWN = 3;
	public final static int[][] DIRECTION = {{1,0}, {-1,0}, {0,-1}, {0,1}};
	
	public SnakeBlock() {}
	public SnakeBlock(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public int x;
	public int y;
	public int dir;
	
	public int getDir() {
		return dir;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

}
