package com.w266.cws.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.w266.cws.enums.CharacterType;

public class StringUtil {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String EQUAL = "=";
    public static final String NEWLINE = "\n";
    public static final String SENTENCE_START = "<s>";
    public static final String SENTENCE_END = "<e>";

    public static final String PUCTUATION_PATTERN = "\\pP|\\pS";
    public static final String LETTER_PATTERN = "[a-zA-Z]+";
    public static final String DIGIT_PATTERN = "\\d+";

    public static final List<String> DATE_CHARS = Arrays.asList("日", "月", "年");
    public static final List<String> NUMBER_CHARS = Arrays.asList("零", "一", "二", "三", "五", "六", "七", "八", "九", "十", "百",
	    "千", "万", "亿", "兆");

    public static boolean isPunctuation(String s) {
	return Pattern.matches(StringUtil.PUCTUATION_PATTERN, s);
    }

    public static boolean isSpace(String s) {
	return s.equals(StringUtil.SPACE);
    }

    public static CharacterType getCharacterType(String s) {
	if (s.equals(SENTENCE_START) || s.equals(SENTENCE_END)) {
	    return CharacterType.BOUNDARY;
	} else if (s.matches(DIGIT_PATTERN) || NUMBER_CHARS.contains(s)) {
	    return CharacterType.NUMBER;
	} else if (DATE_CHARS.contains(s)) {
	    return CharacterType.DATE;
	} else if (s.matches(LETTER_PATTERN)) {
	    return CharacterType.LETTER;
	} else {
	    return CharacterType.OTHER;
	}
    }

}
