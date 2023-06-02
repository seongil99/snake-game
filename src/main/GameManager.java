package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import game.SnakeBlock;

public class GameManager {
	
	private GameManager() {}
	
	private static GameManager snakeManager = new GameManager();
	
	public LinkedList<SnakeBlock> body = new LinkedList<SnakeBlock>();
	public SnakeBlock head;
	
	private final int borderHeight = 5;
	private final int borderWidth = 5;
	public int PERIOD = 250;
	
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
		return checkCollisionWithSelf() && checkCollisionWithWall(borderWidth, borderHeight);
	}
	
	public void moveSnake() {
		int nextX = head.getX() + SnakeBlock.DIRECTION[head.getDir()][0];
		int nextY = head.getY() + SnakeBlock.DIRECTION[head.getDir()][1];
		body.addFirst(new SnakeBlock(nextX, nextY, head.getDir()));
		body.removeLast();
		head = body.getFirst();
	}

}
