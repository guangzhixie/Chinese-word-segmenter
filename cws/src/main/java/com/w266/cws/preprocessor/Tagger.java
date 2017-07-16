package com.w266.cws.preprocessor;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Tagger {

    private static final String PATTERN = "\\pP|\\pS";

    public List<String> tagOneLine(String originalTextLine) {
	List<String> tags = new LinkedList<>();

	int totalLength = originalTextLine.length();

	for (int i = 0; i < totalLength; i++) {
	    String currentChar = originalTextLine.substring(i, i + 1);

	    if (i == 0) {
		if (i + 1 < totalLength && !isSeparator(originalTextLine.substring(i + 1, i + 2))) {
		    tags.add("b");
		} else {
		    tags.add("s");
		}
	    } else if (isPunctuation(currentChar)) {
		tags.add("s");
	    } else if (!isSeparator(currentChar)) {
		if (isSeparator(originalTextLine.substring(i - 1, i)) && i + 1 < totalLength
			&& isSeparator(originalTextLine.substring(i + 1, i + 2))) {
		    tags.add("s");
		} else if (isSeparator(originalTextLine.substring(i - 1, i)) && i + 1 < totalLength
			&& !isSeparator(originalTextLine.substring(i + 1, i + 2))) {
		    tags.add("b");
		} else if (isSeparator(originalTextLine.substring(i - 1, i)) && i + 1 == totalLength) {
		    tags.add("s");
		} else if (!isSeparator(originalTextLine.substring(i - 1, i)) && i + 1 < totalLength
			&& !isSeparator(originalTextLine.substring(i + 1, i + 2))) {
		    tags.add("m");
		} else {
		    tags.add("e");
		}
	    }
	}

	return tags;
    }

    private boolean isPunctuation(String s) {
	return Pattern.matches(PATTERN, s);
    }

    private boolean isSpace(String s) {
	return s.equals(" ");
    }

    private boolean isSeparator(String s) {
	return isSpace(s) || isPunctuation(s);
    }
}
