package main;

import view.GameView;

public class Main {

	public static void main(String[] args) {
		
		GameManager gameManager = GameManager.getInstance();
		GameView mainView = new GameView(gameManager);		
		
		gameManager.initGame();
	}

}
