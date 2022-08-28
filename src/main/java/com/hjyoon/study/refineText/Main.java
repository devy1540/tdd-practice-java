package com.hjyoon.study.refineText;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Utils utils = new Utils();

//        String source = Arrays.toString(args);
        String source = "hello     world";

        source = source.replace("\t", " ");
        source = utils.compactWhiteSpaces(source);

        System.out.println(source);
    }
}
