package com.example.triviamania;

public class Questionaire {
    String category;
    String correct_answer;
    String difficulty;
    String [] incororrect_answers;
    String question;
    String type;

    public Questionaire() {

    }

    public Questionaire(String category, String correct_answer, String difficulty, String[] incororrect_answers, String question, String type) {
        this.category = category;
        this.correct_answer = correct_answer;
        this.difficulty = difficulty;
        this.incororrect_answers = incororrect_answers;
        this.question = question;
        this.type = type;
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

    public String[] getIncororrect_answers() {
        return incororrect_answers;
    }

    public void setIncororrect_answers(String[] incororrect_answers) {
        this.incororrect_answers = incororrect_answers;
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
