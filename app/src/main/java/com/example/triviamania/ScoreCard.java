package com.example.triviamania;

public class ScoreCard {
    private String userName;
    private String score;

    public ScoreCard() {
        this.userName = "";
        this.score = "";
    }

    public ScoreCard(String userName, String score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
