package com.hjyoon.study.variance;

public class Main {
    public static void main(String[] args) {
        Utils utils = new Utils();

        int n = args.length;

        if(n == 0) {
            System.out.println("입력된 데이터가 없습니다.");
        } else if(n == 1) {
            System.out.println("2개 이상의 데이터를 입력하세요");
        } else {
            System.out.println(utils.getVariance(args));
        }
    }
}