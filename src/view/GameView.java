package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

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

	public GamePanel gamePanel;
	public GameManager gameManager;

	class GamePanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// draw grid
			g.setColor(Color.WHITE);
			g.fillRect(PADDING_LEFT, PADDING_TOP, BLOCK_SIZE * ROW, BLOCK_SIZE * COL);
			g.setColor(Color.BLACK);
			g.drawRect(PADDING_LEFT, PADDING_TOP, BLOCK_SIZE * ROW, BLOCK_SIZE * COL);
			for (int i = 0; i < ROW; i++) {
				for (int j=0; j < COL; j++) {
					g.drawRect(PADDING_LEFT + BLOCK_SIZE * i, PADDING_TOP + BLOCK_SIZE * j, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
			// draw snake
			drawSnake(g);

		}
	}

	public GameView(GameManager manager) {
		this.gameManager = manager;

		setTitle("Snake Game");
		setSize(PADDING_LEFT + PADDING_RIGHT + BLOCK_SIZE * COL, PADDING_TOP + PADDING_BOTTOM + BLOCK_SIZE * ROW);

		gamePanel = new GamePanel();


		Timer timer = new Timer(gameManager.PERIOD, e -> {
			gameManager.moveSnake();
			repaint();
		});
		timer.start();

		gamePanel.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_UP:
						if (gameManager.head.getDir() != SnakeBlock.DOWN) {
							gameManager.changeDirection(SnakeBlock.UP);
						}
						break;
					case KeyEvent.VK_DOWN:
						if (gameManager.head.getDir() != SnakeBlock.UP) {
							gameManager.changeDirection(SnakeBlock.DOWN);
						}
						break;
					case KeyEvent.VK_LEFT:
						if (gameManager.head.getDir() != SnakeBlock.RIGHT) {
							gameManager.changeDirection(SnakeBlock.LEFT);
						}
						break;
					case KeyEvent.VK_RIGHT:
						if (gameManager.head.getDir() != SnakeBlock.LEFT) {
							gameManager.changeDirection(SnakeBlock.RIGHT);
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

}
