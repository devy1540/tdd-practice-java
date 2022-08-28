package com.hjyoon.study.refineText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public List<String> getBannedWords() {
        List<String> list = new ArrayList<>();
        list.add("purist");
        list.add("mockist");

        return list;
    }
    public String refineText(String source, List<String> options) {
        source = this.normalizeWhiteSpaces(source);
        source = this.compactWhiteSpaces(source);
        source = this.maskBannedWords(source, (!Objects.isNull(options))?(options):(this.getBannedWords()));

        return source;
    }
    public String compactWhiteSpaces(String source) {
        return !source.contains("  ")
                ? source
                : compactWhiteSpaces(source.replace("  ", " "));
    }

    public String normalizeWhiteSpaces(String value) {
        return value.replace("\t", " ");
    }

    public String maskBannedWords(String source, List<String> bannedWords) {
        if(!Objects.isNull(bannedWords)) {
            for(String bannedWord : bannedWords) {
                source = this.maskBannedWord(source, bannedWord);
            }
        } else {
            for(String bannedWord : getBannedWords()) {
                source = this.maskBannedWord(source, bannedWord);
            }
        }
        return source;
    }

    public String maskBannedWord(String source, String bannedWord) {
        return source.replace(bannedWord, "*".repeat(bannedWord.length()));
    }
}
