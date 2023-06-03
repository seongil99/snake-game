package main;

import java.util.LinkedList;
import java.util.Optional;

import game.SnakeBlock;
import view.GameView;

public class GameManager {
	
	private GameManager() {}
	
	private static GameManager snakeManager = new GameManager();
	
	public LinkedList<SnakeBlock> body = new LinkedList<SnakeBlock>();
	public SnakeBlock head;
	public SnakeBlock food;
	
	private final int borderHeight = 20;
	private final int borderWidth = 20;
	public int PERIOD = GameView.REFRESH_PERIOD * 5;
	public boolean isGameOver = false;
	
	public static GameManager getInstance() {
		return snakeManager;
	}
	
	public void initGame() {
		body.removeAll(body);
		body.add(new SnakeBlock(1, 1, SnakeBlock.RIGHT));
		body.add(new SnakeBlock(2, 1, SnakeBlock.RIGHT));
		head = body.getFirst();
	}
	
	public boolean checkCollisionWithSelf() {
		Optional<SnakeBlock> collisionBlock = body.stream()
                .skip(1)  // 머리와 자신의 몸통이 처음에는 항상 겹치므로 첫 번째 블록을 건너뜁니다.
                .filter(block -> block.getX() == head.getX() && block.getY() == head.getY())
                .findFirst();

        return collisionBlock.isPresent();
	}
	
	public boolean checkCollisionWithWall(int boardWidth, int boardHeight) {
        if (head.getX() < 0 || head.getX() >= boardWidth || head.getY() < 0 || head.getY() >= boardHeight) {
            return true;
        }

        return false;
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

	public void changeDirection(int dir) {
		head.dir = dir;
	}

	public void addSnake() {
		int nextX = head.getX() + SnakeBlock.DIRECTION[head.getDir()][0];
		int nextY = head.getY() + SnakeBlock.DIRECTION[head.getDir()][1];
		body.addFirst(new SnakeBlock(nextX, nextY, head.getDir()));
		head = body.getFirst();
	}

	public void setFood() {
		// food cannot be placed on the snake
		int x = (int)(Math.random() * borderWidth);
		int y = (int)(Math.random() * borderHeight);
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

}
