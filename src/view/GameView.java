package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import game.SnakeBlock;
import main.GameManager;

public class GameView extends JFrame {
	
	private static final int BLOCK_SIZE = 50;
	private static final int ROW = 10;
	private static final int COL = 10;
	private static final int PADDING_TOP = 10;
	private static final int PADDING_LEFT = 10;
	
	public GamePanel gamePanel;
	public GameManager gameManager;
	
	class GamePanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// draw grid
			g.setColor(Color.WHITE);
			g.fillRect(10, 10, BLOCK_SIZE * ROW, BLOCK_SIZE * COL);
			g.setColor(Color.BLACK);
			g.drawRect(10, 10, BLOCK_SIZE * ROW, BLOCK_SIZE * COL);
			for (int i = 0; i < 10; i++) {
				for (int j=0; j < 10; j++) {
					g.drawRect(10 + BLOCK_SIZE * i, 10 + BLOCK_SIZE * j, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
			// draw snake
			drawSnake(g);
			
		}
	}
	
	public GameView(GameManager manager) {
		this.gameManager = manager;
		
		setTitle("Snake Game");
		setSize(10 + 10 + BLOCK_SIZE * COL, 10 + 10 +BLOCK_SIZE * ROW);
		
		gamePanel = new GamePanel();
		add(gamePanel);
		
		Timer timer = new Timer(gameManager.PERIOD, e -> {
			gameManager.moveSnake();
			repaint();
		});
		timer.start();
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void drawSnake(Graphics g) {
		g.setColor(Color.BLACK);
		
		SnakeBlock head = gameManager.head;
		g.fillRect(
				10 + head.getX() * BLOCK_SIZE,
				10 + head.getY() * BLOCK_SIZE,
				BLOCK_SIZE,
				BLOCK_SIZE
				);
		for(SnakeBlock body: gameManager.body) {
			g.fillRect(
					10 + body.getX() * BLOCK_SIZE,
					10 + body.getY() * BLOCK_SIZE,
					BLOCK_SIZE,
					BLOCK_SIZE
					);
		}
	}

}
