package com.hjyoon.study.refineText;

public class Utils {

    public String normalizeWhiteSpaces(String value) {
        return value.replace("\t", " ");
    }

    public String compactWhiteSpaces(String value) {
        return !value.contains("  ")
                ? value
                : compactWhiteSpaces(value.replace("  ", " "));
    }

    public String maskBannedWord(String value, String bannedWord) {
        return value.replace(bannedWord, "*".repeat(bannedWord.length()));
    }
}
