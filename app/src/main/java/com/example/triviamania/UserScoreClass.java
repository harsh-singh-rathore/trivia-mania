package com.example.triviamania;

public class UserScoreClass {
    String name, email, score, phone;

    public UserScoreClass() {

    }

    public UserScoreClass(String name, String email, String score) {
        this.name = name;
        this.email = email;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
