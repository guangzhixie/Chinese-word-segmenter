package com.w266.cws.preprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w266.cws.enums.CharacterType;
import com.w266.cws.util.StringUtil;

public class FeatureExtractor {

    private ObjectMapper mapper = new ObjectMapper();

    public List<Map<String, String>> extractFeatureForSentence(String originalTextLine) {
	String lineWithoutSpace = originalTextLine.replaceAll(StringUtil.SPACE, StringUtil.EMPTY);
	int totalLength = lineWithoutSpace.length();

	List<Map<String, String>> featureDictList = new ArrayList<>(totalLength);

	for (int i = 0; i < totalLength; i++) {
	    Map<String, String> featureDict = new HashMap<>();
	    String c_2 = i - 2 < 0 ? StringUtil.SENTENCE_START : lineWithoutSpace.substring(i - 2, i - 1);
	    String c_1 = i - 1 < 0 ? StringUtil.SENTENCE_START : lineWithoutSpace.substring(i - 1, i);
	    String c0 = lineWithoutSpace.substring(i, i + 1);
	    String c1 = i + 1 < totalLength ? lineWithoutSpace.substring(i + 1, i + 2) : StringUtil.SENTENCE_END;
	    String c2 = i + 2 < totalLength ? lineWithoutSpace.substring(i + 2, i + 3) : StringUtil.SENTENCE_END;

	    CharacterType t_2 = StringUtil.getCharacterType(c_2);
	    CharacterType t_1 = StringUtil.getCharacterType(c_1);
	    CharacterType t0 = StringUtil.getCharacterType(c0);
	    CharacterType t1 = StringUtil.getCharacterType(c1);
	    CharacterType t2 = StringUtil.getCharacterType(c2);

	    boolean isPunctuation = StringUtil.isPunctuation(c0);

	    featureDict.put("c_2", c_2);
	    featureDict.put("c_1", c_1);
	    featureDict.put("c0", c0);
	    featureDict.put("c1", c1);
	    featureDict.put("c2", c2);
	    featureDict.put("c_2c_1", c_2 + c_1);
	    featureDict.put("c_1c0", c_1 + c0);
	    featureDict.put("c0c1", c0 + c1);
	    featureDict.put("c1c2", c1 + c2);
	    featureDict.put("c_1c1", c_1 + c1);
	    featureDict.put("pu", String.valueOf(isPunctuation));
	    featureDict.put("T", t_2.getTypeNumber() + t_1.getTypeNumber() + t0.getTypeNumber() + t1.getTypeNumber()
		    + t2.getTypeNumber());

	    featureDictList.add(featureDict);

	}
	return featureDictList;
    }

    public String writeMapAsJsonString(Map<String, String> featureDict) throws JsonProcessingException {
	return mapper.writeValueAsString(featureDict);
    }

    public String writeMapAsStringLine(Map<String, String> featureDict) throws JsonProcessingException {
	StringBuilder sb = new StringBuilder();
	featureDict.entrySet().forEach(entry -> constructLine(sb, entry));
	sb.deleteCharAt(sb.length() - 1); // remove last space
	return sb.toString();
    }

    private void constructLine(StringBuilder sb, Entry<String, String> entry) {
	sb.append(entry.getKey());
	sb.append(StringUtil.EQUAL);
	sb.append(entry.getValue());
	sb.append(StringUtil.SPACE);
    }
}
