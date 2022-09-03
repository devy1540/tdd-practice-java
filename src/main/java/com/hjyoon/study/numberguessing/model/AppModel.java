package com.hjyoon.study.numberguessing.model;

public class AppModel {

    interface Processor {
        Processor run(String input);
    }

    private final static String NEW_LINE = System.lineSeparator();
    private final static String SELECT_MODE_MSG = "1: Single player game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ";
    private boolean completed;
    private String output;
    private final PositiveIntegerGenerator positiveIntegerGenerator;
    private Processor processor;

    public AppModel(PositiveIntegerGenerator positiveIntegerGenerator) {
        this.positiveIntegerGenerator = positiveIntegerGenerator;
        completed = false;
        output = SELECT_MODE_MSG;
//        answer = positiveIntegerGenerator.generateLessThanOrEqualToHundred();
        processor = this::processModeSelection;
    }

    //완료여부
    public Boolean isCompleted() {
        return completed;
    }

    //출력 및 버퍼삭제
    public String flushOutput() {
        return output;
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

    private Processor processModeSelection(String input) {
        int i = Integer.parseInt(input);
        if (i == 1) {
            output = "Single player game" + NEW_LINE + "I'm thinking of a number between 1 and 100." + NEW_LINE + "Enter your guess: ";
            int answer = positiveIntegerGenerator.generateLessThanOrEqualToHundred();
            return getSinglePlayerProcessor(answer, 1);
        } else {
            completed = true;
            return null;
        }
    }

    private Processor getSinglePlayerProcessor(int answer, int tries) {
        return input -> {
            int i = Integer.parseInt(input);
            if (i < answer) {
                output = "Your guess is too low." + NEW_LINE + "Enter your guess: ";
                return getSinglePlayerProcessor(answer, tries + 1);
            } else if (i > answer) {
                output = "Your guess is too high." + NEW_LINE + "Enter your guess: ";
                return getSinglePlayerProcessor(answer, tries + 1);
            } else {
                output = "Correct! " + tries + ((tries == 1) ? (" guess.") : (" guesses.")) + NEW_LINE + SELECT_MODE_MSG;
                return this::processModeSelection;
            }
        };
    }
}
