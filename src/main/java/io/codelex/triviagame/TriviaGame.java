package io.codelex.triviagame;

import java.util.*;

import static io.codelex.triviagame.TextAndASCIIArt.*;

public class TriviaGame {

    private final Scanner keyboard;
    private final LinkedHashSet<TriviaQuestion> allTriviaQuestions;
    private final TriviaApi triviaApi;
    private final TriviaGameData triviaGameData;
    private final TriviaQuestionService triviaQuestionService = new TriviaQuestionService();
    private TriviaQuestion triviaQuestion = null;
    private Timer timer;
    private final TimerService timerService = new TimerService();

    public TriviaGame(String[] triviaTypes, int questionAmount, int possibleAnswersCount) {
        this.allTriviaQuestions = new LinkedHashSet<>();
        this.triviaApi = new TriviaApi(triviaTypes);
        this.keyboard = new Scanner(System.in);
        this.triviaGameData = new TriviaGameData(questionAmount, possibleAnswersCount);
    }

    public void start() {
        welcomeUser(triviaGameData.getQuestionAmount());
        promptToStartGame(keyboard);
        timer = new Timer(System.currentTimeMillis());
        while (!triviaGameData.isAnsweredWrong()
                && triviaGameData.getAnsweredQuestions() != triviaGameData.getQuestionAmount()) {
            triviaQuestion = getUniqueTriviaQuestion();
            triviaQuestion.setTriviaQuestion(setFormattedTriviaText());
            answerTheQuestion();
        }
        endGame();
    }

    private String setFormattedTriviaText() {
       String formattedText = triviaQuestion.getTriviaQuestion();
       long triviaQuestionNumber = triviaQuestion.getNumber();
       return triviaQuestionService.getFormattedTriviaText(triviaQuestionNumber, formattedText);
    }

    private TriviaQuestion getUniqueTriviaQuestion() {
        TriviaQuestion triviaQuestion;
        do {
            triviaQuestion = triviaApi.getTriviaQuestion();
        } while (!allTriviaQuestions.add(triviaQuestion));
        return triviaQuestion;
    }

    private void answerTheQuestion() {
        displayQuestion(triviaQuestion.getTriviaQuestion(), triviaGameData.getAnsweredQuestions());
        prepareAndDisplayAnswers();
        updateQuestion();
        updateTriviaQuestionStatus();
    }

    private void prepareAndDisplayAnswers() {
        triviaQuestion.setPossibleAnswerCount(triviaGameData.getPossibleAnswerCount());
        List<Long> uniqueAnswers = triviaQuestionService.getUniquePossibleAnswers(
                triviaQuestion.getNumber(),
                triviaGameData.getPossibleAnswerCount()
        );
        triviaQuestion.setPossibleAnswers(uniqueAnswers);
        displayPossibleAnswers(triviaQuestion.getPossibleAnswers());
    }

    private void updateQuestion() {
        Optional<Long> optionalLong = getUserAnswerInputOrOptional();
        optionalLong.ifPresentOrElse(
                triviaQuestion::setUserAnswer,
                () -> {
                    System.out.println("Error: Invalid input, please try again");
                    updateQuestion();
                }
        );
    }

    private Optional<Long> getUserAnswerInputOrOptional() {
        try {
            return Optional.of(keyboard.nextLong());
        } catch (InputMismatchException e) {
            keyboard.nextLine();
            return Optional.empty();
        }
    }

    private void updateTriviaQuestionStatus() {
        if (triviaQuestion.getUserAnswer() == triviaQuestion.getNumber()) {
            triviaGameData.setAnsweredQuestions(triviaGameData.getAnsweredQuestions() + 1);
        } else {
            triviaGameData.setAnsweredWrong(true);
        }
    }

    private void endGame() {
        if (triviaGameData.getAnsweredQuestions() == triviaGameData.getQuestionAmount()) {
            displayVictoryMessage();
        } else {
            displayDefeatMessage();
            displayLastAnswer(triviaQuestion);
        }
        float time = timerService.calculateTimeElapsedInSeconds(timer.timeAtBeginning(), System.currentTimeMillis());
        displayStatistics(triviaGameData.getAnsweredQuestions(), triviaGameData.getQuestionAmount(), time);
    }
}