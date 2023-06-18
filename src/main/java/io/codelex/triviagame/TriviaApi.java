package io.codelex.triviagame;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Random;

class TriviaApi {

    private static final String URL = "http://numbersapi.com/random/";
    private static final String URL_QUERY = "?json&?min=0&max=10000000";
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final String[] triviaTypes;
    private final int maxApiRetries;

    public TriviaApi(String[] triviaTypes, int maxApiRetries) {
        this.triviaTypes = triviaTypes;
        this.maxApiRetries = maxApiRetries;
    }

    public TriviaQuestion getTriviaQuestion() {
        return getTriviaQuestion(0);
    }

    private TriviaQuestion getTriviaQuestion(int retryCount) {
        checkRetryLimitAndExitIfReached(retryCount);
        Optional<TriviaQuestion> optionalTriviaQuestion = getAPIResponse();
        return optionalTriviaQuestion
                .filter(TriviaQuestion::isFound)
                .orElseGet(() -> getTriviaQuestion(retryCount + 1));
    }

    private void checkRetryLimitAndExitIfReached(int retryCount) {
        if (retryCount >= maxApiRetries) {
            System.out.println("API is currently under load, please try again later");
            System.exit(1);
        }
    }

    private Optional<TriviaQuestion> getAPIResponse() {
        try {
            return Optional.ofNullable(MAPPER.readValue(new URI(getFinalizeURL()).toURL(), TriviaQuestion.class));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    private String getFinalizeURL() {
        return URL + getRandomTriviaType() + URL_QUERY;
    }

    private String getRandomTriviaType() {
        Random rng = new Random();
        return triviaTypes[rng.nextInt(triviaTypes.length)];
    }
}
