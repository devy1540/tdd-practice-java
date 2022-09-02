package com.hjyoon.study.numberguessing.app;

import com.hjyoon.study.numberguessing.model.AppModel;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AppModel appModel = new AppModel(new RandomGenerator());
        Scanner scanner = new Scanner(System.in);

        runLoop(appModel, scanner);
        scanner.close();
    }

    public static void runLoop(AppModel model, Scanner scanner) {
        while(!model.isCompleted()) {
            System.out.println(model.flushOutput());
            model.processInput(scanner.nextLine());
        }
    }
}
