package com.w266.cws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.w266.cws.preprocessor.FeatureExtractor;

public class FeatureMain {
    private static final String PKU = "data/training/pku_training.utf8";
    private static final String MSR = "data/training/msr_training.utf8";

    private static final String PKU_FEATURE_OUTPUT = "data/feature/pku_feature.utf8";
    private static final String MSR_FEATURE_OUTPUT = "data/feature/msr_feature.utf8";

    private static FeatureExtractor featureExtractor = new FeatureExtractor();

    public static void main(String[] args) {
	extractFeatureForCorups(PKU, PKU_FEATURE_OUTPUT);
	extractFeatureForCorups(MSR, MSR_FEATURE_OUTPUT);
    }

    private static void extractFeatureForCorups(String corpusName, String outputFilePath) {
	System.out.println("Extracting features for corpus " + corpusName + " to " + outputFilePath);
	try (BufferedReader reader = new BufferedReader(
		new InputStreamReader(new FileInputStream(corpusName), "utf-8"));
		BufferedWriter writer = new BufferedWriter(
			new OutputStreamWriter(new FileOutputStream(outputFilePath), "utf-8"))) {
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		List<Map<String, String>> featureDictList = featureExtractor.extractFeatureForSentence(line);
		for (Map<String, String> dict : featureDictList) {
		    outputFeatureDict(dict, writer);
		}
	    }
	} catch (Exception e) {
	    System.out.println("Failed: ");
	    e.printStackTrace();
	}
	System.out.println("Finished extracting features for corpus " + corpusName);
    }

    private static void outputFeatureDict(Map<String, String> dict, BufferedWriter writer)
	    throws JsonProcessingException, IOException {
	writer.write(featureExtractor.writeMapAsJsonString(dict));
	writer.write("\n");
    }
}
