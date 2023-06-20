package io.codelex.triviagame;

import java.util.*;

class TriviaQuestionService {

    private final Random rng = new Random();

    public String getFormattedTriviaText(long number, String triviaQuestionText) {
        if (number <= 0) {
            return triviaQuestionText.replaceFirst(String.valueOf(Math.abs(number)), "What");
        }
        return triviaQuestionText.replaceFirst(String.valueOf(number), "What");
    }

    public List<Long> getUniquePossibleAnswers(Long triviaNumber, int possibleAnswerCount) {
        HashSet<Long> setOfNumbers = new HashSet<>();
        setOfNumbers.add(triviaNumber);
        while (setOfNumbers.size() < possibleAnswerCount) {
            setOfNumbers.add(generateNumberCloseToAnswer(triviaNumber));
        }
        return setListAndShuffle(setOfNumbers);
    }

    private long generateNumberCloseToAnswer(long triviaNumber) {
        return Math.round(rng.nextGaussian() * 10 + triviaNumber);
    }

    private List<Long> setListAndShuffle(Set<Long> setOfNumbers) {
        List<Long> listOfAnswers = new ArrayList<>(setOfNumbers);
        Collections.shuffle(listOfAnswers);
        return listOfAnswers;
    }
}
