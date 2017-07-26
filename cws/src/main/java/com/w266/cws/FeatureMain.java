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
import com.w266.cws.util.FilePath;
import com.w266.cws.util.StringUtil;

public class FeatureMain {

    private static FeatureExtractor featureExtractor = new FeatureExtractor();

    public static void main(String[] args) {
	extractFeatureForCorups(FilePath.PKU_CORPUS, FilePath.PKU_FEATURE_PATH);
	extractFeatureForCorups(FilePath.MSR_CORPUS, FilePath.MSR_FEATURE_PATH);
    }

    private static void extractFeatureForCorups(String corpusPath, String outputFilePath) {
	System.out.println("Extracting features for corpus " + corpusPath + " to " + outputFilePath);
	try (BufferedReader reader = new BufferedReader(
		new InputStreamReader(new FileInputStream(corpusPath), "utf-8"));
		BufferedWriter writer = new BufferedWriter(
			new OutputStreamWriter(new FileOutputStream(outputFilePath), "utf-8"))) {
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		List<Map<String, String>> featureDictList = featureExtractor.extractFeatureForSentence(line);
		for (Map<String, String> dict : featureDictList) {
		    // outputFeatureDictAsJsonString(dict, writer);
		    outputFeatureDictAsStringLine(dict, writer);
		}
	    }
	} catch (Exception e) {
	    System.out.println("Failed: ");
	    e.printStackTrace();
	}
	System.out.println("Finished extracting features for corpus " + corpusPath);
    }

    private static void outputFeatureDictAsJsonString(Map<String, String> dict, BufferedWriter writer)
	    throws JsonProcessingException, IOException {
	writer.write(featureExtractor.writeMapAsJsonString(dict));
	writer.write(StringUtil.NEWLINE);
    }

    private static void outputFeatureDictAsStringLine(Map<String, String> dict, BufferedWriter writer)
	    throws JsonProcessingException, IOException {
	writer.write(featureExtractor.writeMapAsStringLine(dict));
	writer.write(StringUtil.NEWLINE);
    }
}
