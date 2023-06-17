package io.codelex.components;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Random;

import static io.codelex.Config.MAX_API_RETRIES;
import static io.codelex.Config.TRIVIA_TYPES;

public class TriviaApi {

    private static final String URL = "http://numbersapi.com/random/";
    private static final String URL_QUERY = "?json&?min=0&max=10000000";
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static TriviaQuestion getTriviaQuestion() {
        return getTriviaQuestion(0);
    }

    private static TriviaQuestion getTriviaQuestion(int retryCount) {
        checkRetryLimitAndExitIfReached(retryCount);
        Optional<TriviaQuestion> optionalTriviaQuestion = getAPIResponse();
        return optionalTriviaQuestion
                .filter(TriviaQuestion::isFound)
                .orElseGet(() -> getTriviaQuestion(retryCount + 1));
    }

    private static void checkRetryLimitAndExitIfReached(int retryCount) {
        if (retryCount >= MAX_API_RETRIES) {
            System.out.println("API is currently under load, please try again later");
            System.exit(1);
        }
    }

    private static Optional<TriviaQuestion> getAPIResponse() {
        try {
            return Optional.ofNullable(MAPPER.readValue(new URI(getFinalizeURL()).toURL(), TriviaQuestion.class));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    private static String getFinalizeURL() {
        return URL + getRandomTriviaType() + URL_QUERY;
    }

    private static String getRandomTriviaType() {
        Random rng = new Random();
        return TRIVIA_TYPES[rng.nextInt(TRIVIA_TYPES.length)];
    }
}
