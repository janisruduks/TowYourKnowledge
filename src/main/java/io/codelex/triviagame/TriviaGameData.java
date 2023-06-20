package io.codelex.triviagame;

class TriviaGameData {

    private final int possibleAnswerCount;
    private final int questionAmount;
    private int answeredQuestions;
    private boolean answeredWrong;

    public TriviaGameData(int questionAmount, int possibleAnswerCount) {
        this.possibleAnswerCount = possibleAnswerCount;
        this.questionAmount = questionAmount;
        this.answeredQuestions = 0;
        this.answeredWrong = false;
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
}
