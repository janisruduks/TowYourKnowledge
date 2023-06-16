package io.codelex;

import io.codelex.components.AnswerBucket;
import io.codelex.components.TextAndASCIIArt;
import io.codelex.components.TriviaApi;
import io.codelex.components.TriviaQuestion;

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

        TextAndASCIIArt.welcomeUser(QUESTION_AMOUNT);
        System.out.println("To start type 'start'");
        keyboard.next();

        long timeAtBeginning = System.currentTimeMillis();

        while (!answeredWrong && answeredQuestions != QUESTION_AMOUNT) {
            answerTheQuestion(keyboard, validateTriviaQuestion(TriviaApi.getTriviaQuestion()), answerBucket);
        }
        endGame(answerBucket, keyboard, timeAtBeginning);
    }

    private static void answerTheQuestion(Scanner keyboard, TriviaQuestion question, AnswerBucket bucket) {
        showQuestion(question);
        prepareAndDisplayAnswers(question);
        updateQuestion(keyboard, question);
        updateBucketAndStatus(question, bucket);
    }

    private static void showQuestion(TriviaQuestion question) {
        displayCheat(question);
        System.out.println("-Question " + (answeredQuestions + 1) + ". " + question.getTriviaQuestion());
    }

    private static void displayCheat(TriviaQuestion question) {
        if (cheatMode) {
            System.out.println(question.getNumber());
        }
    }

    private static void prepareAndDisplayAnswers(TriviaQuestion question) {
        question.setPossibleAnswers(); // we need to set manually because answer now = 0
        question.displayPossibleAnswers();
    }

    private static Optional<Long> getUserAnswerInputOrOptional(Scanner keyboard) {
        try {
            return Optional.of(keyboard.nextLong());
        } catch (InputMismatchException e) {
            keyboard.nextLine();
            return Optional.empty();
        }
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

    private static TriviaQuestion validateTriviaQuestion(TriviaQuestion question) {
        if (question != null && question.isFound()) {
            return question;
        } else {
            TriviaQuestion newQuestion = TriviaApi.getTriviaQuestion();
            return validateTriviaQuestion(newQuestion);
        }
    }

    private static void endGame(AnswerBucket bucket, Scanner keyboard, long timeAtBeginning) {
        if (answeredQuestions == QUESTION_AMOUNT) {
            System.out.println("You got your car back!");
            displayCarWonASCII();
            displayQuestionPromptAndDisplayStatistics(
                    keyboard, QUESTION_AMOUNT, bucket, timeAtBeginning, System.currentTimeMillis()
            );
        } else {
            System.out.println("You didn't guess correctly...");
            displayCarLostASCII();
            displayQuestionPromptAndDisplayStatistics(
                    keyboard, QUESTION_AMOUNT, bucket, timeAtBeginning, System.currentTimeMillis()
            );
            bucket.displayLastAnswer();
        }
    }
}
