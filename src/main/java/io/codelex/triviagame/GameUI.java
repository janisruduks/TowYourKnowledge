package io.codelex.triviagame;

import java.util.List;
import java.util.Scanner;

class GameUI {

    public static void welcomeUser(int questionAmount) {
        System.out.println("Welcome to TRIVIA-TOW-MAN!");
        System.out.println("We are towing your car away, but you can get it back!");
        System.out.printf("I will ask you %d questions, and you need to answer them\n", questionAmount);
        System.out.println("If you do, you get back your car!");
    }

    public static void promptToStartGame(Scanner keyboard) {
        System.out.println("To start type 'start'");
        keyboard.next();
    }

    public static void displayQuestion(String triviaQuestion, int answeredQuestions) {
        System.out.printf("-Question %d. %s\n", answeredQuestions + 1, triviaQuestion);
    }

    public static void displayPossibleAnswers(List<Long> possibleAnswers) {
        System.out.println("--Possible answers:");
        for (int i = 0; i < possibleAnswers.size(); i++) {
            System.out.printf("--- %d: %s\n", i + 1, possibleAnswers.get(i));
        }
    }

    public static void displayVictoryMessage() {
        System.out.println("You got your car back!");
        displayCarWonASCII();
    }

    private static void displayCarWonASCII() {
        System.out.println("""
                  ______
                 /|_||_\\`.__
                (   _    _ _\\
                =`-(_)--(_)-'  hjw
                """);
    }

    public static void displayDefeatMessage() {
        System.out.println("You didn't guess correctly...");
        displayCarLostASCII();
    }

    private static void displayCarLostASCII() {
        System.out.println("""
                                    /\\\\      _____  bye bye \s
                     ,-----,       /  \\\\____/__|__\\\s
                  ,--'---:---`--, /  |  _     |     `| \s
                 ==(o)-----(o)==J    `(o)-------(o)=   \s
                ``````````````````````````````````````` dp
                """);
    }

    public static void displayLastAnswer(TriviaQuestion triviaQuestion) {
        System.out.println("-Last trivia was:");
        System.out.println("--Question: " + triviaQuestion.getTriviaQuestion());
        System.out.println("--Correct answer: " + triviaQuestion.getNumber());
        System.out.println("--Your answer: " + triviaQuestion.getUserAnswer());
    }

    public static void displayStatistics(int answerAmount, int questionAmount, float time) {
        System.out.println("-STATISTICS:");
        System.out.printf("--Out of %s questions, you answered %d correctly\n", questionAmount, answerAmount);
        System.out.printf("--It took you %.2f seconds\n", time);
    }
}
