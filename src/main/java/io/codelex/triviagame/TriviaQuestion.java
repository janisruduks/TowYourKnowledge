package io.codelex.triviagame;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class TriviaQuestion {

    @JsonProperty("text")
    private String triviaQuestion;
    private long number;
    private boolean found;
    private List<Long> possibleAnswers;
    private int possibleAnswerCount;
    private long userAnswer;

    public TriviaQuestion() {
    }

    public TriviaQuestion(String triviaQuestion, long number, boolean found) {
        this.triviaQuestion = triviaQuestion;
        this.number = number;
        this.found = found;
    }

    public String getTriviaQuestion() {
        return triviaQuestion;
    }

    public void setTriviaQuestion(String triviaQuestion) {
        this.triviaQuestion = triviaQuestion;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public List<Long> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<Long> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public int getPossibleAnswerCount() {
        return possibleAnswerCount;
    }

    public void setPossibleAnswerCount(int possibleAnswerCount) {
        this.possibleAnswerCount = possibleAnswerCount;
    }

    public long getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(long userAnswer) {
        this.userAnswer = userAnswer;
    }
}