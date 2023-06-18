package io.codelex.triviagame;

import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Scanner;

import static io.codelex.triviagame.TextAndASCIIArt.*;

public class TriviaGame {

    private final Scanner keyboard;
    public int possibleAnswerCount;
    public String[] triviaTypes;
    protected int questionAmount;
    protected boolean cheatMode;
    private int answeredQuestions = 0;
    private boolean answeredWrong = false;
    private final LinkedHashSet<TriviaQuestion> allTriviaQuestions;
    private final TriviaApi triviaApi;

    public TriviaGame(String[] triviaTypes, int questionAmount, int possibleAnswersCount, boolean cheatMode, int maxApiRetries) {
        this.possibleAnswerCount = possibleAnswersCount;
        this.questionAmount = questionAmount;
        this.triviaTypes = triviaTypes;
        this.cheatMode = cheatMode;
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

    private void answerTheQuestion(TriviaQuestion question) {
        showQuestion(question);
        prepareAndDisplayAnswers(question);
        updateQuestion(keyboard, question);
        updateBucketAndStatus(question);
    }

    private void showQuestion(TriviaQuestion question) {
        displayCheat(question.getNumber(), cheatMode);
        System.out.println("-Question " + (answeredQuestions + 1) + ". " + question.getTriviaQuestion());
    }

    private void prepareAndDisplayAnswers(TriviaQuestion question) {
        question.setPossibleAnswerCount(possibleAnswerCount);
        question.setPossibleAnswers(); // we need to set manually because answer now = 0
        question.displayPossibleAnswers();
    }

    private void updateQuestion(Scanner keyboard, TriviaQuestion question) {
        Optional<Long> optionalLong = getUserAnswerInputOrOptional(keyboard);
        optionalLong.ifPresentOrElse(
                question::setUserAnswer,
                () -> {
                    System.out.println("Error: Invalid input, please try again");
                    updateQuestion(keyboard, question);
                }
        );
    }

    private Optional<Long> getUserAnswerInputOrOptional(Scanner keyboard) {
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
