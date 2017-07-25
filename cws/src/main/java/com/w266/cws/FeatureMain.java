package com.w266.cws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import com.w266.cws.preprocessor.FeatureExtractor;
import com.w266.cws.util.StringUtil;

public class FeatureMain {
    private static final String PKU = "data/training/pku_training.utf8";
    private static final String MSR = "data/training/msr_training.utf8";

    private static final String PKU_FEATURE_OUTPUT = "data/feature/pku_feature.utf8";
    private static final String MSR_FEATURE_OUTPUT = "data/feature/msr_feature.utf8";

    private static FeatureExtractor featureExtractor = new FeatureExtractor();

    public static void main(String[] args) {
	System.out.println(StringUtil.getCharacterType("一"));
	extractFeatureForCorups(PKU, PKU_FEATURE_OUTPUT);
    }

    private static void extractFeatureForCorups(String corpusName, String outputFilePath) {
	System.out.println("Extracting features for corpus " + corpusName + " to " + outputFilePath);
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(corpusName), "utf-8"));
	    BufferedWriter writer = new BufferedWriter(
		    new OutputStreamWriter(new FileOutputStream(outputFilePath), "utf-8"));
	    // String line = null;
	    // while ((line = reader.readLine()) != null) {
	    // List<String> tagList = tagger.tagForSentence(line);
	    // tagList.stream().forEach(tag -> outputTag(writer, tag));
	    // }
	    String line = reader.readLine();
	    System.out.println(line);
	    List<Map<String, String>> featureDictList = featureExtractor.extractFeatureForSentence(line);
	    for (Map<String, String> dict : featureDictList) {
		System.out.println(featureExtractor.writeMapAsJsonString(dict));
	    }

	    reader.close();
	    writer.close();
	} catch (Exception e) {
	    System.out.println("Failed: ");
	    e.printStackTrace();
	}
	System.out.println("Finished extracting features for corpus " + corpusName);
    }
}
