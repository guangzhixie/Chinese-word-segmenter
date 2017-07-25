package com.w266.cws.preprocessor;

import java.util.LinkedList;
import java.util.List;

import com.w266.cws.util.StringUtil;

public class Tagger {

    public List<String> tagForSentence(String originalTextLine) {
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
	    } else if (StringUtil.isPunctuation(currentChar)) {
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

    private boolean isSeparator(String s) {
	return StringUtil.isSpace(s) || StringUtil.isPunctuation(s);
    }
}
