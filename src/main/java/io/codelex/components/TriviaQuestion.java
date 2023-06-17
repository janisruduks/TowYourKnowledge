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

    public TriviaQuestion() {}

    public TriviaQuestion(String triviaQuestion, int number, boolean found) {
        this.triviaQuestion = triviaQuestion;
        this.number = number;
        this.found = found;
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

    public void setPossibleAnswers() {
        this.possibleAnswers = getUniquePossibleAnswers();
    }

    private List<Long> getUniquePossibleAnswers() {
        Set<Long> setOfNumbers = new HashSet<>();
        setOfNumbers.add(number);
        while (setOfNumbers.size() < POSSIBLE_ANSWERS) {
            setOfNumbers.add(generateNumberCloseToAnswer());
        }
        return setListAndShuffle(setOfNumbers);
    }

    private long generateNumberCloseToAnswer() {
        Random rng = new Random();
        return Math.round(rng.nextGaussian() * 10 + number);
    }

    private List<Long> setListAndShuffle(Set<Long> setOfNumbers) {
        List<Long> listOfAnswers = new ArrayList<>(setOfNumbers);
        Collections.shuffle(listOfAnswers);
        return listOfAnswers;
    }

    public long getNumber() {
        return number;
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

    public void displayPossibleAnswers() {
        System.out.println("--Possible answers:");
        for (int i = 0; i < possibleAnswers.size(); i++) {
            System.out.println("---" + (i + 1) + ": " + possibleAnswers.get(i));
        }
    }
}
