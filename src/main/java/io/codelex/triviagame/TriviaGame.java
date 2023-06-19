package io.codelex.triviagame;

import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Scanner;

import static io.codelex.triviagame.TextAndASCIIArt.*;

public class TriviaGame {

    private final Scanner keyboard;
    private final LinkedHashSet<TriviaQuestion> allTriviaQuestions;
    private final TriviaApi triviaApi;
    private final int possibleAnswerCount;
    private final int questionAmount;
    private int answeredQuestions = 0;
    private boolean answeredWrong = false;

    public TriviaGame(String[] triviaTypes, int questionAmount, int possibleAnswersCount, int maxApiRetries) {
        this.possibleAnswerCount = possibleAnswersCount;
        this.questionAmount = questionAmount;
        this.allTriviaQuestions = new LinkedHashSet<>();
        this.triviaApi = new TriviaApi(triviaTypes, maxApiRetries);
        this.keyboard = new Scanner(System.in);
    }

    public void start() {
        welcomeUser(questionAmount);
        promptToStartGame(keyboard);
        long timeAtBeginning = System.currentTimeMillis();

        TriviaQuestion triviaQuestion = null;
        while (!answeredWrong && answeredQuestions != questionAmount) {
            triviaQuestion = checkIfNoDuplication();
            answerTheQuestion(triviaQuestion);
        }
        endGame(triviaQuestion, timeAtBeginning);
    }


    private TriviaQuestion checkIfNoDuplication() {
        TriviaQuestion triviaQuestion;
        do {
            triviaQuestion = triviaApi.getTriviaQuestion();
        } while (!allTriviaQuestions.add(triviaQuestion));
        return triviaQuestion;
    }

    private void answerTheQuestion(TriviaQuestion triviaQuestion) {
        displayQuestion(triviaQuestion);
        prepareAndDisplayAnswers(triviaQuestion);
        updateQuestion(triviaQuestion);
        updateBucketAndStatus(triviaQuestion);
    }

    private void displayQuestion(TriviaQuestion triviaQuestion) {
        System.out.println("-Question " + (answeredQuestions + 1) + ". " + triviaQuestion.getTriviaQuestion());
    }

    private void prepareAndDisplayAnswers(TriviaQuestion triviaQuestion) {
        triviaQuestion.setPossibleAnswerCount(possibleAnswerCount);
        triviaQuestion.setPossibleAnswers(); // we need to set manually because answer now = 0
        triviaQuestion.displayPossibleAnswers();
    }

    private void updateQuestion(TriviaQuestion triviaQuestion) {
        Optional<Long> optionalLong = getUserAnswerInputOrOptional();
        optionalLong.ifPresentOrElse(
                triviaQuestion::setUserAnswer,
                () -> {
                    System.out.println("Error: Invalid input, please try again");
                    updateQuestion(triviaQuestion);
                }
        );
    }

    private Optional<Long> getUserAnswerInputOrOptional() {
        try {
            return Optional.of(keyboard.nextLong());
        } catch (InputMismatchException e) {
            keyboard.nextLine();
            return Optional.empty();
        }
    }

    private void updateBucketAndStatus(TriviaQuestion question) {
        if (question.getUserAnswer() == question.getNumber()) {
            question.setAnsweredCorrectly(true);
        } else {
            question.setAnsweredCorrectly(false);
            answeredWrong = true;
        }
        answeredQuestions++;
    }

    private void endGame(TriviaQuestion triviaQuestion, long timeAtBeginning) {
        if (answeredQuestions == questionAmount) {
            displayVictoryMessage();
        } else {
            displayDefeatMessage();
            displayLastAnswer(triviaQuestion);
        }
        displayStatistics(answeredQuestions, questionAmount, timeAtBeginning, System.currentTimeMillis());
    }
}