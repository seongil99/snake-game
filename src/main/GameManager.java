package main;

import main.game.Score;
import main.game.SnakeBlock;
import main.view.GameFrame;

import java.util.LinkedList;
import java.util.Optional;

public class GameManager {

    private static final GameManager snakeManager = new GameManager();
    private final Score bestScore = new Score();
    private final int borderHeight = 20;
    private final int borderWidth = 20;
    public LinkedList<SnakeBlock> body = new LinkedList<>();
    public SnakeBlock head;
    public SnakeBlock food;
    public int PERIOD = GameFrame.REFRESH_PERIOD * 5;
    public boolean isGameOver = false;
    public boolean isPaused = false;
    public boolean isMainScreen = true;
    private int next_dir;
    private int score;
    private int time;

    private GameManager() {
    }

    public static GameManager getInstance() {
        return snakeManager;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setBestScore(int score, int time) {
        this.bestScore.setScore(score);
        this.bestScore.setTime(time);
    }

    public Score getBestScore() {
        return bestScore;
    }

    public void initGame() {
        body = new LinkedList<>();
        body.add(new SnakeBlock(1, 1, SnakeBlock.RIGHT));
        body.add(new SnakeBlock(2, 1, SnakeBlock.RIGHT));
        next_dir = SnakeBlock.RIGHT;
        head = body.getFirst();
        isPaused = false;
        score = 0;
        time = 0;
    }

    public boolean checkCollisionWithSelf() {
        Optional<SnakeBlock> collisionBlock = body.stream()
                .skip(1)  // 머리와 자신의 몸통이 처음에는 항상 겹치므로 첫 번째 블록을 건너뜁니다.
                .filter(block -> block.getX() == head.getX() && block.getY() == head.getY())
                .findFirst();

        return collisionBlock.isPresent();
    }

    public boolean checkCollisionWithWall(int boardWidth, int boardHeight) {
        return head.getX() < 0 || head.getX() >= boardWidth || head.getY() < 0 || head.getY() >= boardHeight;
    }

    public boolean checkGameOver() {
        return checkCollisionWithSelf() || checkCollisionWithWall(borderWidth, borderHeight);
    }

    public void moveSnake() {
        int nextX = head.getX() + SnakeBlock.DIRECTION[head.getDir()][0];
        int nextY = head.getY() + SnakeBlock.DIRECTION[head.getDir()][1];
        body.addFirst(new SnakeBlock(nextX, nextY, head.getDir()));
        body.removeLast();
        head = body.getFirst();
    }

    public void changeDirection() {
        head.setDir(next_dir);
    }

    public void setNextDirection(int dir) {
        if (head.getDir() == SnakeBlock.RIGHT && dir == SnakeBlock.LEFT) {
            return;
        }
        if (head.getDir() == SnakeBlock.LEFT && dir == SnakeBlock.RIGHT) {
            return;
        }
        if (head.getDir() == SnakeBlock.UP && dir == SnakeBlock.DOWN) {
            return;
        }
        if (head.getDir() == SnakeBlock.DOWN && dir == SnakeBlock.UP) {
            return;
        }
        next_dir = dir;
    }

    public void addSnake() {
        int nextX = head.getX() + SnakeBlock.DIRECTION[head.getDir()][0];
        int nextY = head.getY() + SnakeBlock.DIRECTION[head.getDir()][1];
        body.addFirst(new SnakeBlock(nextX, nextY, head.getDir()));
        head = body.getFirst();
    }

    public void setFood() {
        // food cannot be placed on the snake
        int x = (int) (Math.random() * borderWidth);
        int y = (int) (Math.random() * borderHeight);
        if (body.stream().anyMatch(block -> block.getX() == x && block.getY() == y)) {
            setFood();
        } else {
            food = new SnakeBlock(x, y, SnakeBlock.RIGHT);
        }
    }

    public boolean checkEatFood() {
        return head.getX() == food.getX() && head.getY() == food.getY();
    }

    public void setPeriod(int period) {
        PERIOD = period;
    }

    public void restartGame() {
        if (score > bestScore.getScore() || (score == bestScore.getScore() && time < bestScore.getTime())) {
            setBestScore(score, time);
        }
        isGameOver = false;
        initGame();
    }

}
