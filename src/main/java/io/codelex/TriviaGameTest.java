package io.codelex;

import io.codelex.triviagame.TriviaGame;

public class TriviaGameTest {

    public static void main(String[] args) {

        String[] triviaTypes = {"trivia", "year", "math"};

        TriviaGame game = new TriviaGame(triviaTypes, 20, 3, true, 3);
        game.start();
    }
}
