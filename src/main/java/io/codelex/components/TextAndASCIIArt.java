package io.codelex.components;

import java.util.Scanner;

public class TextAndASCIIArt {

    public static void seeQuestionsPrompt(Scanner keyboard, AnswerBucket bucket) {
        System.out.println("To see questions and answers type 'see'");
        keyboard.next();
        bucket.displayBucket();
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
