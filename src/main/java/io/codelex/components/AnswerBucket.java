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

    TriviaQuestion getLastAnswer() {
        return answerBucket.get(answerBucket.size() - 1);
    }
}
