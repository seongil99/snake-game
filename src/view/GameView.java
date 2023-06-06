package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import game.SnakeBlock;
import main.GameManager;

public class GameView extends JFrame {

	private static final int BLOCK_SIZE = 25;
	private static final int ROW = 20;
	private static final int COL = 20;
	private static final int PADDING_TOP = 10;
	private static final int PADDING_LEFT = 10;
	private static final int PADDING_BOTTOM = 50;
	private static final int PADDING_RIGHT = 10;
	private static final int REFRESH_RATE = 60;
	public static final int REFRESH_PERIOD = 1000 / REFRESH_RATE;

	public GamePanel gamePanel;
	public GameManager gameManager;
	private Timer timer;
	private JButton restartButton;

	class GamePanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (!gameManager.isGameOver) {
				// draw grid
				g.setColor(Color.WHITE);
				g.fillRect(PADDING_LEFT, PADDING_TOP, BLOCK_SIZE * ROW, BLOCK_SIZE * COL);
				g.setColor(Color.BLACK);
				g.drawRect(PADDING_LEFT, PADDING_TOP, BLOCK_SIZE * ROW, BLOCK_SIZE * COL);
				for (int i = 0; i < ROW; i++) {
					for (int j = 0; j < COL; j++) {
						g.drawRect(PADDING_LEFT + BLOCK_SIZE * i, PADDING_TOP + BLOCK_SIZE * j, BLOCK_SIZE, BLOCK_SIZE);
					}
				}
				// draw snake
				drawSnake(g);
				// draw food
				if (gameManager.food != null) {
					drawFood(g);
				}
				// draw game over
			} else if (gameManager.isGameOver) {
				drawGameOver(g);
			}
		}
	}

	public GameView(GameManager manager) {
		this.gameManager = manager;

		setTitle("Snake Game");
		setSize(PADDING_LEFT + PADDING_RIGHT + BLOCK_SIZE * COL, PADDING_TOP + PADDING_BOTTOM + BLOCK_SIZE * ROW);

		gamePanel = new GamePanel();

		timer = new Timer(REFRESH_PERIOD, e -> {
			gameManager.setTime(gameManager.getTime() + REFRESH_PERIOD);
			if(gameManager.food == null) {
				gameManager.setFood();
			}
			if (!gameManager.isGameOver && gameManager.getTime() % gameManager.PERIOD == 0) {
				gameManager.changeDirection();
				if (gameManager.checkEatFood()){
					gameManager.setScore(gameManager.getScore() + 1);
					gameManager.addSnake();
					gameManager.setFood();
				}
				else {
					gameManager.moveSnake();
				}
			}
			gameManager.isGameOver = gameManager.checkGameOver();
			if (gameManager.isGameOver) {
				((Timer)e.getSource()).stop();
			}
			repaint();
		});

		gamePanel.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_UP:
						if (gameManager.head.getDir() != SnakeBlock.DOWN) {
							gameManager.setNextDirection(SnakeBlock.UP);
						}
						break;
					case KeyEvent.VK_DOWN:
						if (gameManager.head.getDir() != SnakeBlock.UP) {
							gameManager.setNextDirection(SnakeBlock.DOWN);
						}
						break;
					case KeyEvent.VK_LEFT:
						if (gameManager.head.getDir() != SnakeBlock.RIGHT) {
							gameManager.setNextDirection(SnakeBlock.LEFT);
						}
						break;
					case KeyEvent.VK_RIGHT:
						if (gameManager.head.getDir() != SnakeBlock.LEFT) {
							gameManager.setNextDirection(SnakeBlock.RIGHT);
						}
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		add(gamePanel);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		timer.start();
	}

	public void drawSnake(Graphics g) {
		g.setColor(Color.BLACK);

		for(SnakeBlock body: gameManager.body) {
			g.fillRect(
					PADDING_LEFT + body.getX() * BLOCK_SIZE,
					PADDING_TOP + body.getY() * BLOCK_SIZE,
					BLOCK_SIZE,
					BLOCK_SIZE
			);
		}
	}

	public void drawGameOver(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		int width = g.getFontMetrics().stringWidth("Game Over");
		g.drawString("Game Over", PADDING_LEFT + BLOCK_SIZE * ROW / 2 - width / 2, PADDING_TOP + BLOCK_SIZE * COL / 2);
		if (restartButton == null) {
			restartButton = new JButton("Restart");
			restartButton.addActionListener(e -> {
				gameManager.restartGame();
				gamePanel.requestFocusInWindow();
				timer.restart();
				gamePanel.remove((JButton) (e.getSource()));
				gamePanel.revalidate();
				gamePanel.repaint();
				restartButton = null;
			});
		}
		restartButton.setBounds(PADDING_LEFT + BLOCK_SIZE * ROW / 2 - 50, PADDING_TOP + BLOCK_SIZE * COL / 2 + 50, 100, 50);
		gamePanel.add(restartButton);
	}

	public void drawFood(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(
				PADDING_LEFT + gameManager.food.getX() * BLOCK_SIZE,
				PADDING_TOP + gameManager.food.getY() * BLOCK_SIZE,
				BLOCK_SIZE,
				BLOCK_SIZE
		);
	}

}
