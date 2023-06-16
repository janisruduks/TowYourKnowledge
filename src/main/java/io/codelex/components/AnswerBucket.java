package io.codelex.components;

import java.util.ArrayList;
import java.util.List;

public class AnswerBucket {

    private final List<TriviaQuestion> answerBucket = new ArrayList<>();

    public void addAnsweredTrivia(TriviaQuestion question) {
        answerBucket.add(question);
    }

    public int getAnswersAmount() {
        return answerBucket.size();
    }

    public void displayLastAnswer() {
        TriviaQuestion answer = getLastAnswer();
        System.out.println("Last question was:");
        System.out.println("Question: " + answer.getTriviaQuestion());
        System.out.println("Correct answer: " + answer.getNumber());
        System.out.println("Your answer: " + answer.getUserAnswer());
    }

    private TriviaQuestion getLastAnswer() {
        return answerBucket.get(answerBucket.size() - 1);
    }
}
