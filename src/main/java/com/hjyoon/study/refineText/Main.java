package com.hjyoon.study.refineText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Utils utils = new Utils();

//        String source = Arrays.toString(args);
        String source = "hello     world";


        System.out.println(utils.refineText(source, null));
    }
}
