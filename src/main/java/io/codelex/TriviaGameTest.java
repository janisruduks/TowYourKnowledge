package io.codelex;

import io.codelex.triviagame.TriviaGame;

public class TriviaGameTest {

    public static void main(String[] args) {

        String[] triviaTypes = {"trivia", "year", "math"};
        TriviaGame game = new TriviaGame(triviaTypes, 25, 3, 3);
        game.start();
    }
}
