package io.codelex.components;

import java.util.Scanner;

public class TextAndASCIIArt {

    public static void displayQuestionPromptAndDisplayStatistics(
            Scanner keyboard, int QUESTION_AMOUNT, AnswerBucket bucket, long start, long end) {
        System.out.println("To see last question and statistics type 'see'");
        keyboard.next();
        displayStatistics(bucket, QUESTION_AMOUNT, start, end);
    }

    private static void displayStatistics(AnswerBucket bucket, int QUESTION_AMOUNT, long start, long end) {
        System.out.println("STATISTICS:");
        System.out.println("Out of " + QUESTION_AMOUNT + " questions, you answered " + bucket.getAnswersAmount());
        System.out.println("It took you " + calculateTimeElapsed(start, end) + " seconds");
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

    public static void displayCarWonASCII() {
        System.out.println("""
                  ______
                 /|_||_\\`.__
                (   _    _ _\\
                =`-(_)--(_)-'  hjw
                """);
    }

    public static void displayCarLostASCII() {
        System.out.println("""
                                    /\\\\      _____  bye bye \s
                     ,-----,       /  \\\\____/__|__\\\s
                  ,--'---:---`--, /  |  _     |     `| \s
                 ==(o)-----(o)==J    `(o)-------(o)=   \s
                ``````````````````````````````````````` dp
                """);
    }
}
