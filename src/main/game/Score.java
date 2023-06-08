package main.game;

public class Score {
    private int score;
    private int time;

    public Score() {
        score = 0;
        time = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
