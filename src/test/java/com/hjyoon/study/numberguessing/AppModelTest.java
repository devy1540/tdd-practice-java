package com.hjyoon.study.numberguessing;


import com.hjyoon.study.numberguessing.model.AppModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class AppModelTest {

    private final static String NEW_LINE = System.lineSeparator();

    @Test
    void isCompletedByInitializing() {
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        boolean actual = appModel.isCompleted();
        assertFalse(actual);
    }

    @Test
    void correctlyPrintSelectModeMessage() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        String expected = "1: Single player game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ";
        String actual = appModel.flushOutput();

        //then
        assertEquals(expected, actual);

    }

    @Test
    void correctlyExits() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));


        //when
        appModel.processInput("3");
        boolean actual = appModel.isCompleted();

        //then
        assertTrue(actual);

    }

    @Test
    void correctlyPrintSinglePlayerGameStartMessage() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.flushOutput();
        appModel.processInput("1");

        String actual = appModel.flushOutput();
        String expected = "Single player game" + NEW_LINE + "I'm thinking of a number between 1 and 100." + NEW_LINE + "Enter your guess: ";

        //then
        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @CsvSource({ "50, 40", "30, 29", "89, 9" })
    void correctlyPrintsTooLowMessageInSinglePlayerGame(int answer, int guess) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(answer));


        //when
        appModel.processInput("1");
        appModel.flushOutput();
        appModel.processInput(Integer.toString(guess));

        String actual = appModel.flushOutput();
        String expected = "Your guess is too low." + NEW_LINE + "Enter your guess: ";


        //then
        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @CsvSource({ "50, 60", "80, 81" })
    void correctlyPrintTooHighMessageInSinglePlayerGame(int answer, int guess) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(answer));

        //when
        appModel.processInput("1");
        appModel.flushOutput();
        appModel.processInput(Integer.toString(guess));

        String actual = appModel.flushOutput();
        String expected = "Your guess is too high." + NEW_LINE + "Enter your guess: ";

        //then
        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 10, 100})
    void correctlyPrintCorrectMessageInSinglePlayerGame(int answer) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(answer));

        //when
        appModel.processInput("1");
        appModel.flushOutput();
        appModel.processInput(Integer.toString(answer));

        String actual = appModel.flushOutput();
        String expected = "Correct! ";

        //then
//        assertEquals(expected, actual);
        assertThat(actual).contains(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100})
    void correctlyPrintGuessCountIfSinglePlayerGameFinished(int fails) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("1");
        for(int i = 0; i < fails; i++) {
            appModel.processInput("30");
        }
        appModel.flushOutput();
        appModel.processInput("50");

        String actual = appModel.flushOutput();


        //then
        assertThat(actual).contains((fails + 1) + " guesses." + NEW_LINE);
    }

    @Test
    void correctlyPrintOneGuessIfSinglePlayerGameFinished() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("1");
        appModel.flushOutput();
        appModel.processInput("50");

        String actual = appModel.flushOutput();

        //then
        assertThat(actual).contains("1 guess.");
    }

    @Test
    void printSelectModeMessageIfSinglePlayerGameFinished() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("1");
        appModel.flushOutput();
        appModel.processInput("50");

        String actual = appModel.flushOutput();
        String expected = "1: Single player game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ";

        //then
        assertThat(actual).endsWith(expected);

    }

    @Test
    void returnToModeSelectionIfSinglePlayerGameFinished() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("1");
        appModel.processInput("50");
        appModel.processInput("3");

        boolean actual = appModel.isCompleted();

        //then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = "100, 10, 1")
    void generateAnswerForEachGame(String source) {
        //given
        int[] answers = Stream.of(source.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(answers));

        //when
        for(int answer : answers) {
            appModel.processInput("1");
            appModel.flushOutput();
            appModel.processInput(Integer.toString(answer));
        }

        String actual = appModel.flushOutput();
        String expected = "Correct! ";

        //then
        assertThat(actual).startsWith(expected);
    }

    @Test
    void correctlyPrintMultiPlayerGameSetupMessage() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));



        //when
        appModel.flushOutput();
        appModel.processInput("2");

        String actual = appModel.flushOutput();

        //then
        assertThat(actual).isEqualTo("Multiplayer game" + NEW_LINE + "Enter player names seperated with comas: ");

    }

    @Test
    void correctlyPrintMultiPlayerStartMessage() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));


        //when
        appModel.processInput("2");
        appModel.flushOutput();

        appModel.processInput("Foo, Bar");

        String actual = appModel.flushOutput();
        //then

        assertThat(actual).startsWith("I'm thinking of a number between 1 and 100" + NEW_LINE);

    }

    @ParameterizedTest
    @CsvSource({ "Foo, Bar, Baz", "Bar, Baz, Foo", "Baz, Foo, Bar" })
    void correctlyPromptsFirstPlayerName(String player1, String player2, String player3) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("2");
        appModel.flushOutput();

        appModel.processInput(String.join(", ", player1, player2, player3));

        String actual = appModel.flushOutput();

        //then
        assertThat(actual).endsWith("Enter " + player1 + "'s guess: ");

    }

    @ParameterizedTest
    @CsvSource({ "Foo, Bar, Baz", "Bar, Baz, Foo", "Baz, Foo, Bar" })
    void correctlyPromptsSecondPlayerName(String player1, String player2, String player3) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("2");
        appModel.processInput(String.join(", ", player1, player2, player3));
        appModel.flushOutput();

        appModel.processInput("10");
        String actual = appModel.flushOutput();

        //then
        assertThat(actual).endsWith("Enter " + player2 + "'s guess: ");

    }

    @ParameterizedTest
    @CsvSource({ "Foo, Bar, Baz", "Bar, Baz, Foo", "Baz, Foo, Bar" })
    void correctlyPromptsThirdPlayerName(String player1, String player2, String player3) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("2");
        appModel.processInput(String.join(", ", player1, player2, player3));
        appModel.processInput("90");
        appModel.flushOutput();

        appModel.processInput("90");
        String actual = appModel.flushOutput();

        //then
        assertThat(actual).endsWith("Enter " + player3 + "'s guess: ");

    }

    @ParameterizedTest
    @CsvSource({ "Foo, Bar, Baz", "Bar, Baz, Foo", "Baz, Foo, Bar" })
    void correctlyRoundsPlayers(String player1, String player2, String player3) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("2");
        appModel.processInput(String.join(", ", player1, player2, player3));
        appModel.processInput("10");
        appModel.processInput("10");
        appModel.flushOutput();

        appModel.processInput("10");
        String actual = appModel.flushOutput();

        //then
        assertThat(actual).endsWith("Enter " + player1 + "'s guess: ");

    }

    @ParameterizedTest
    @CsvSource({ "50, 40, 1, Foo", "30, 29, 2, Bar" })
    void correctlyPrintTooLowMessageInMultiPlayerGame(int answer, int guess, int fails, String lastPlayer) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(answer));

        //when
        appModel.processInput("2");
        appModel.processInput("Foo, Bar, Baz");
        for (int i = 0; i < fails - 1; i++) {
            appModel.processInput(Integer.toString(guess));
        }
        appModel.flushOutput();
        appModel.processInput(Integer.toString(guess));

        String actual = appModel.flushOutput();

        //then
        assertThat(actual).contains(lastPlayer + "'s guess is too low." + NEW_LINE);

    }

    @ParameterizedTest
    @CsvSource({ "50, 60, 1, Foo", "9, 81, 2, Bar" })
    void correctlyPrintTooHighMessageInMultiPlayerGame(int answer, int guess, int fails, String lastPlayer) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(answer));

        //when
        appModel.processInput("2");
        appModel.processInput("Foo, Bar, Baz");
        for (int i = 0; i < fails - 1; i++) {
            appModel.processInput(Integer.toString(guess));
        }
        appModel.flushOutput();
        appModel.processInput(Integer.toString(guess));

        String actual = appModel.flushOutput();

        //then
        assertThat(actual).contains(lastPlayer + "'s guess is too high." + NEW_LINE);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100})
    void correctlyPrintCorrectMessageInMultiPlayerGame(int answer) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(answer));

        //when
        appModel.processInput("2");
        appModel.processInput("Foo, Bar, Baz");
        appModel.flushOutput();

        int guess = answer;
        appModel.processInput(Integer.toString(guess));
        String actual = appModel.flushOutput();

        //then
        assertThat(actual).startsWith("Correct! ");
    }

    @ParameterizedTest
    @CsvSource({ "0, Foo", "1, Bar", "2, Baz", "99, Foo", "100, Bar" })
    void correctlyPrintWinnerIfMultiPlayerGameFinished(int fails, String winner) {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("2");
        appModel.processInput("Foo, Bar, Baz");
        for (int i = 0; i < fails; i++) {
            appModel.processInput("30");
        }
        appModel.flushOutput();
        appModel.processInput("50");

        String actual = appModel.flushOutput();

        //then
        assertThat(actual).contains(winner + " wins." + NEW_LINE);
    }

    @Test
    void printSSelectModeMessageIfMultiPlayerGameFinished() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("2");
        appModel.processInput("Foo, Bar, Baz");
        appModel.flushOutput();
        appModel.processInput("50");

        String actual = appModel.flushOutput();
        //then

        assertThat(actual).endsWith("1: Single player game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ");

    }

    @Test
    void returnsToModeSelectionIfMultiPlayerGameFinished() {
        //given
        AppModel appModel = new AppModel(new PositiveIntegerGeneratorStub(50));

        //when
        appModel.processInput("2");
        appModel.processInput("Foo, Bar, Baz");
        appModel.processInput("20");
        appModel.processInput("50");
        appModel.processInput("3");

        boolean actual = appModel.isCompleted();
        //then

        assertThat(actual).isTrue();
    }
}
