package com.hjyoon.study.numberguessing.app;

import com.hjyoon.study.numberguessing.model.PositiveIntegerGenerator;

import java.util.Random;

public class RandomGenerator implements PositiveIntegerGenerator {

    private Random random = new Random();

    @Override
    public int generateLessThanOrEqualToHundred() {
        return 0;
    }
}
