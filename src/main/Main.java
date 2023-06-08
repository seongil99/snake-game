package main;

import main.view.GameFrame;

public class Main {

    public static void main(String[] args) {

        GameManager gameManager = GameManager.getInstance();
        gameManager.initGame();
        new GameFrame(gameManager);
    }

}
