package com.example.triviamania;

public class Questionaire {
    String category;
    String correct_answer;
    String difficulty;
    String Optiona, Optionb, Optionc, Optiond;
    String question;
    String type;

    public Questionaire() {

    }
    public Questionaire(Questionaire C) {
        C.formatQ();
        this.question = C.question;
    }
    public void formatQ() {
        question.replaceAll("&quot;", "\"").replaceAll("&amp;","&").replaceAll("&gt;",">").replaceAll("&lt;","<").replaceAll("&#039;", "'");
    }

    public String getRightAnswer() {
        return correct_answer;
    }

    public Questionaire(String category, String correct_answer, String difficulty, String optiona, String optionb, String optionc, String optiond, String question, String type) {
        this.category = category;
        this.correct_answer = correct_answer;
        this.difficulty = difficulty;
        this.Optiona = optiona;
        this.Optionb = optionb;
        this.Optionc = optionc;
        this.Optiond = optiond;

        this.question = question.replaceAll("&quot;", "\"");
        this.type = type;
    }
    public Questionaire(String category, String difficulty, String optiona, String optionb, String optionc, String optiond, String question) {
        this.category = category;
        this.correct_answer = optiona;
        this.difficulty = difficulty;
        this.Optiona = optiona;
        this.Optionb = optionb;
        this.Optionc = optionc;
        this.Optiond = optiond;

        this.question = question.replaceAll("&quot;", "\"");
        this.type = "multiple";
    }

    public Boolean checkRightAns(String optionSelected) {
        return correct_answer.equals(optionSelected);
    }
    public String getOptiona() {
        return Optiona;
    }

    public void setOptiona(String optiona) {
        Optiona = optiona;
    }

    public String getOptionb() {
        return Optionb;
    }

    public void setOptionb(String optionb) {
        Optionb = optionb;
    }

    public String getOptionc() {
        return Optionc;
    }

    public void setOptionc(String optionc) {
        Optionc = optionc;
    }

    public String getOptiond() {
        return Optiond;
    }

    public void setOptiond(String optiond) {
        Optiond = optiond;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
