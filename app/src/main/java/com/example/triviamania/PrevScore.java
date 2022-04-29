package com.example.triviamania;

public class PrevScore {
    String right, skip, wrong;

    public PrevScore() {
        this.right = "0";
        this.skip = "0";
        this.wrong = "0";
    }

    public PrevScore(String right, String skip, String wrong) {
        this.right = right;
        this.skip = skip;
        this.wrong = wrong;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }
}
