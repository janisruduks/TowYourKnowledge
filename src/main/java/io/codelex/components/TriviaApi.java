package io.codelex.components;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

public class TriviaApi {

    private static final String URL = "http://numbersapi.com/random/";
    private static final String URL_QUERY = "?json&?min=0&max=10000000";
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static TriviaQuestion getTriviaQuestion() {
        String finalUrl = URL + getRandomTriviaType() + URL_QUERY;
        Optional<TriviaQuestion> optionalTriviaQuestion = getAPIResponse(finalUrl, TriviaQuestion.class);
        return optionalTriviaQuestion.orElse(null);
    }

    private static <T> Optional<T> getAPIResponse(String url, Class<T> response) {
        try {
            return Optional.ofNullable(MAPPER.readValue(new URL(url), response));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return Optional.empty();
    }

    private static String getRandomTriviaType() {
        Random rng = new Random();
        String[] triviaTypes = {"trivia", "year", "math"};
        return triviaTypes[rng.nextInt(3)];
    }
}
