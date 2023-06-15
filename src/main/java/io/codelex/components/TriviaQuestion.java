package io.codelex.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TriviaQuestion {

    private static final int POSSIBLE_ANSWERS = 3;
    @JsonProperty("text")
    private String triviaQuestion;
    private long number;
    private boolean found;
    private boolean answeredCorrectly;
    private List<Long> possibleAnswers;
    private long userAnswer;

    public TriviaQuestion(String triviaQuestion, int number, boolean found) {
        this.triviaQuestion = triviaQuestion;
        this.number = number;
        this.found = found;
    }

    public TriviaQuestion() {
    }

    public String getTriviaQuestion() {
        return triviaQuestion.replaceFirst(String.valueOf(number), "What");
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    private List<Long> generateAnswers() {
        List<Long> listOfAnswers = new ArrayList<>();
        listOfAnswers.add(number);
        for (int i = 1; i < POSSIBLE_ANSWERS; i++) {
            listOfAnswers.add(generateNumberCloseToAnswer());
        }
        Collections.shuffle(listOfAnswers);
        return listOfAnswers;
    }

    private long generateNumberCloseToAnswer() {
        Random rng = new Random();
        double nextGaussian;
        if (number > 1000) {
            nextGaussian = rng.nextGaussian() * 100 + number;
        } else {
            nextGaussian = rng.nextGaussian() * 10 + number;
        }
        return Math.round(nextGaussian);
    }

    public void setPossibleAnswers() {
        this.possibleAnswers = generateAnswers();
    }

    public long getNumber() {
        return number;
    }

    public void displayPossibleAnswers() {
        System.out.println("--Possible answers:");
        for (int i = 0; i < possibleAnswers.size(); i++) {
            System.out.println("---" + (i + 1) + ": " + possibleAnswers.get(i));
        }
    }

    public long getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(long userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isFound() {
        return found;
    }
}
