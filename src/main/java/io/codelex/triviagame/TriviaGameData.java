package io.codelex.triviagame;

import java.util.HashSet;
import java.util.Set;

class TriviaGameData {

    private final int possibleAnswerCount;
    private final int questionAmount;
    private int answeredQuestions;
    private boolean answeredWrong;
    private final Set<TriviaQuestion> questionSet;

    public TriviaGameData(int questionAmount, int possibleAnswerCount) {
        this.possibleAnswerCount = possibleAnswerCount;
        this.questionAmount = questionAmount;
        this.answeredQuestions = 0;
        this.answeredWrong = false;
        this.questionSet = new HashSet<>();
    }

    public int getPossibleAnswerCount() {
        return possibleAnswerCount;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(int answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public boolean isAnsweredWrong() {
        return answeredWrong;
    }

    public void setAnsweredWrong(boolean answeredWrong) {
        this.answeredWrong = answeredWrong;
    }

    public boolean addQuestionIfUnique(TriviaQuestion question) {
        return questionSet.add(question);
    }
}
