package io.codelex;

import io.codelex.components.*;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static io.codelex.Config.QUESTION_AMOUNT;
import static io.codelex.Config.cheatMode;
import static io.codelex.components.TextAndASCIIArt.*;

public class TriviaApp {

    private static int answeredQuestions = 0;
    private static boolean answeredWrong = false;

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        AnswerBucket answerBucket = new AnswerBucket();

        welcomeUser(QUESTION_AMOUNT);
        promptToStartGame(keyboard);

        long timeAtBeginning = System.currentTimeMillis();

        while (!answeredWrong && answeredQuestions != QUESTION_AMOUNT) {
            answerTheQuestion(keyboard, TriviaApi.getTriviaQuestion(), answerBucket);
        }
        endGame(answerBucket, timeAtBeginning);
    }

    private static void answerTheQuestion(Scanner keyboard, TriviaQuestion question, AnswerBucket bucket) {
        showQuestion(question);
        prepareAndDisplayAnswers(question);
        updateQuestion(keyboard, question);
        updateBucketAndStatus(question, bucket);
    }

    private static void showQuestion(TriviaQuestion question) {
        displayCheat(question.getNumber(), cheatMode);
        System.out.println("-Question " + (answeredQuestions + 1) + ". " + question.getTriviaQuestion());
    }

    private static void prepareAndDisplayAnswers(TriviaQuestion question) {
        question.setPossibleAnswers(); // we need to set manually because answer now = 0
        question.displayPossibleAnswers();
    }

    private static void updateQuestion(Scanner keyboard, TriviaQuestion question) {
        Optional<Long> optionalLong = getUserAnswerInputOrOptional(keyboard);
        optionalLong.ifPresentOrElse(
                question::setUserAnswer,
                () -> {
                    System.out.println("Error: Invalid input, please try again");
                    updateQuestion(keyboard, question);
                }
        );
    }

    private static Optional<Long> getUserAnswerInputOrOptional(Scanner keyboard) {
        try {
            return Optional.of(keyboard.nextLong());
        } catch (InputMismatchException e) {
            keyboard.nextLine();
            return Optional.empty();
        }
    }

    private static void updateBucketAndStatus(TriviaQuestion question, AnswerBucket bucket) {
        if (question.getUserAnswer() == question.getNumber()) {
            question.setAnsweredCorrectly(true);
        } else {
            question.setAnsweredCorrectly(false);
            answeredWrong = true;
        }
        bucket.addAnsweredTrivia(question);
        answeredQuestions++;
    }

    private static void endGame(AnswerBucket bucket, long timeAtBeginning) {
        if (answeredQuestions == QUESTION_AMOUNT) {
            displayVictoryMessage();
        } else {
            displayDefeatMessage();
            displayLastAnswer(bucket.getLastAnswer());
        }
        displayStatistics(bucket.getAnswersAmount(), QUESTION_AMOUNT, timeAtBeginning, System.currentTimeMillis());
    }
}