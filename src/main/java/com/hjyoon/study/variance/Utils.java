package com.hjyoon.study.variance;

import java.util.Arrays;

public class Utils {

    public String getVariance(String[] args) {
        double[] source = this.parseArguments(args, args.length);
        double mean = this.calculateMean(source);
        double sumOfSquares = this.calculateSumOfSquares(source, mean);
        double variance = sumOfSquares / (args.length - 1);

       return String.format("분산: %f", variance);
    }

    public double[] parseArguments(String[] args, int n) {
        /*double[] source = new double[n];
        for(int i = 0; i < n; i++) {
            source[i] = Double.parseDouble(args[i]);
        }
        return source;*/
        return Arrays.stream(args).mapToDouble(Double::parseDouble).toArray();
    }

    public double calculateMean(double[] source) {
        /*double sum = 0.0;
        for(int i = 0; i < args.length; i++) {
            sum += source[i];
        }

        return sum / args.length;*/
        return Arrays.stream(source).average().orElseThrow();
    }

    public double calculateSumOfSquares(double[] source, double mean) {
        /*double sumOfSquares = 0.0;

        for (double v : source) {
            sumOfSquares += (v - mean) * (v - mean);
        }
        return sumOfSquares;*/
        return Arrays.stream(source).map(x -> x -mean).map(x -> x * x).sum();
    }
}
