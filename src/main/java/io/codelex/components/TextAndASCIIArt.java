package io.codelex.components;

import java.util.Scanner;

public class TextAndASCIIArt {

    public static void displayStatistics(int answerAmount, int QUESTION_AMOUNT, long start, long end) {
        System.out.println("-STATISTICS:");
        System.out.println("--Out of " + QUESTION_AMOUNT + " questions, you answered " + answerAmount);
        System.out.println("--It took you " + calculateTimeElapsed(start, end) + " seconds");
    }
    private static float calculateTimeElapsed(long start, long end) {
        float msec = end - start;
        return msec / 1000f;
    }

    public static void welcomeUser(int QUESTION_AMOUNT) {
        System.out.println("Welcome to TRIVIA-TOW-MAN!");
        System.out.println("We are towing your car away, but you can get it back!");
        System.out.println("I will ask you " + QUESTION_AMOUNT + " questions, and you need to answer them!");
        System.out.println("If you do, you get back your car!");
    }

    public static void promptToStartGame(Scanner keyboard) {
        System.out.println("To start type 'start'");
        keyboard.next();
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

    public static void displayCheat(long triviaAnswer, boolean cheatMode) {
        if (cheatMode) {
            System.out.println(triviaAnswer);
        }
    }

    public static void displayLastAnswer(TriviaQuestion answer) {
        System.out.println("-Last trivia was:");
        System.out.println("--Question: " + answer.getTriviaQuestion());
        System.out.println("--Correct answer: " + answer.getNumber());
        System.out.println("--Your answer: " + answer.getUserAnswer());
    }
}
