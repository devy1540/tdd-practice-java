package com.hjyoon.study.refineText;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class RefineTextTest {
    List<String> list = new ArrayList<>();
    List<String> maskedList = new ArrayList<>();

    Utils refineTextUtils = new Utils();

    @BeforeEach
    void init() {
        list.add("hello world");
        list.add("hello  world");
        list.add("hello   world");
        list.add("hello    world");
        list.add("hello     world");
        list.add("hello      world");
        list.add("hello\tworld");

        maskedList.add("hello mockist");
        maskedList.add("hello purist");

    }

    @Test
    @DisplayName("공백 제거 테스트")
    void trimTest() {
        for (String s : list) {
            assertEquals("hello world", refineTextUtils.refineText(s, null));
        }
    }

    @Test
    @DisplayName("마스킹 테스트")
    void maskingTest() {
        for(String s : maskedList) {
            String masked = s.replaceAll("(?<=.{6}).", "*");
            assertEquals(masked, refineTextUtils.maskBannedWords(s, null));
        }
    }
}