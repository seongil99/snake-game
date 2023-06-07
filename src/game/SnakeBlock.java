package game;

public class SnakeBlock {

    public final static int RIGHT = 0;
    public final static int LEFT = 1;
    public final static int UP = 2;
    public final static int DOWN = 3;
    public final static int[][] DIRECTION = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
    private final int x;
    private final int y;
    private int dir;

    public SnakeBlock(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
