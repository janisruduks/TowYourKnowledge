package io.codelex.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import static io.codelex.Config.POSSIBLE_ANSWERS;

public class TriviaQuestion {

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
        if (number <= 0) {
            return triviaQuestion.replaceFirst(String.valueOf(Math.abs(number)), "What");
        }
        return triviaQuestion.replaceFirst(String.valueOf(number), "What");
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    private List<Long> generateAnswers() {
        Set<Long> setOfNumbers = new HashSet<>();
        setOfNumbers.add(number);
        while (setOfNumbers.size() < POSSIBLE_ANSWERS) {
            setOfNumbers.add(generateNumberCloseToAnswer());
        }
        List<Long> listOfAnswers = new ArrayList<>(setOfNumbers);
        Collections.shuffle(listOfAnswers);
        return listOfAnswers;
    }

    private long generateNumberCloseToAnswer() {
        Random rng = new Random();
        double nextGaussian;
        nextGaussian = rng.nextGaussian() * 10 + number;
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
