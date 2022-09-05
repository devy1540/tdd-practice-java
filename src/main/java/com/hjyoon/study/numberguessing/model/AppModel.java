package com.hjyoon.study.numberguessing.model;

import java.util.stream.Stream;

public class AppModel {

    interface Processor {
        Processor run(String input);
    }

    private final static String NEW_LINE = System.lineSeparator();
    private final static String SELECT_MODE_MSG = "1: Single player game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ";
    private boolean completed;
//    private String output;
    private final StringBuffer outputBuffer;
    
    private final PositiveIntegerGenerator positiveIntegerGenerator;
    private Processor processor;

    public AppModel(PositiveIntegerGenerator positiveIntegerGenerator) {
        this.positiveIntegerGenerator = positiveIntegerGenerator;
        completed = false;
//        output = SELECT_MODE_MSG;
//        answer = positiveIntegerGenerator.generateLessThanOrEqualToHundred();
        processor = this::processModeSelection;
        outputBuffer = new StringBuffer(SELECT_MODE_MSG);
    }

    //완료여부
    public Boolean isCompleted() {
        return completed;
    }

    //출력 및 버퍼삭제
    public String flushOutput() {
        String s = outputBuffer.toString();
        outputBuffer.setLength(0);
        return s;
    }

    //
    public void processInput(String input) {
        processor = processor.run(input);
//        int i = Integer.parseInt(input);

//        if(singlePlayerMode) {
//            getSinglePlayerProcessor(1);
//        } else {
//            processModeSelection(input);
//        }
    }

    private void print(String message) {
        outputBuffer.append(message);
    }

    private void println(String message) {
        outputBuffer.append(message).append(NEW_LINE);
    }

    private Processor processModeSelection(String input) {
        int i = Integer.parseInt(input);
        if (i == 1) {
            println("Single player game");
            println("I'm thinking of a number between 1 and 100.");
            print("Enter your guess: ");
            int answer = positiveIntegerGenerator.generateLessThanOrEqualToHundred();
            return getSinglePlayerProcessor(answer, 1);
        } else if (i == 2) {
            println("Multiplayer game");
            print("Enter player names seperated with comas: ");
            return startMultiplayerGame();
        } else {
            completed = true;
            return null;
        }
    }

    private Processor startMultiplayerGame() {
        return input -> {
            Object[] players = Stream.of(input.split(",")).map(String::trim).toArray();
            println("I'm thinking of a number between 1 and 100.");
            int answer = positiveIntegerGenerator.generateLessThanOrEqualToHundred();
            return getMultiPlayerProcessor(players, answer, 1);
        };
    }

    private Processor getMultiPlayerProcessor(Object[] players, int answer, int tries) {
        outputBuffer.append(" Enter ").append(players[(tries - 1) % players.length]).append("'s guess: ");
        return input -> {
            int guess = Integer.parseInt(input);

            if(guess < answer) {
                outputBuffer.append(players[(tries - 1) % players.length]).append("'s guess is too low.").append(NEW_LINE);
            } else if(guess > answer) {
                outputBuffer.append(players[(tries - 1) % players.length]).append("'s guess is too high.").append(NEW_LINE);
            } else {
                outputBuffer.append("Correct! ").append(players[(tries - 1) % players.length]).append(" wins.").append(NEW_LINE);
                outputBuffer.append(SELECT_MODE_MSG);
                return this::processModeSelection;

            }

            return getMultiPlayerProcessor(players, answer, tries + 1);
        };
    }

    private Processor getSinglePlayerProcessor(int answer, int tries) {
        return input -> {
            int i = Integer.parseInt(input);
            if (i < answer) {
                outputBuffer.append("Your guess is too low.").append(NEW_LINE).append("Enter your guess: ");
                return getSinglePlayerProcessor(answer, tries + 1);
            } else if (i > answer) {
                outputBuffer.append("Your guess is too high.").append(NEW_LINE).append("Enter your guess: ");
                return getSinglePlayerProcessor(answer, tries + 1);
            } else {
                outputBuffer.append("Correct! ").append(tries).append((tries == 1) ? (" guess.") : (" guesses.")).append(NEW_LINE).append(SELECT_MODE_MSG);
                return this::processModeSelection;
            }
        };
    }
}
