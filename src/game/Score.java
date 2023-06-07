package game;

public class Score {
    private int score;
    private int time;

    public Score() {
        score = 0;
        time = 0;
    }

    public Score(int score, int time) {
        this.score = score;
        this.time = time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }
}
