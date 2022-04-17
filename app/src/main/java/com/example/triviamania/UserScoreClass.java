package com.example.triviamania;

public class UserScoreClass {
    String name, email, score, phone, noOfQues;

    public UserScoreClass() {

    }

    public UserScoreClass(String name, String email, String score, String phone) {
        this.name = name;
        this.email = email;
        this.score = score;
        this.phone = phone;
        this.noOfQues = "0";
    }

    public String getNoOfQues() {
        return noOfQues;
    }

    public void setNoOfQues(String noOfQues) {
        this.noOfQues = noOfQues;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
