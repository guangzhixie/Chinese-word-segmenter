package com.w266.cws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.w266.cws.preprocessor.Tagger;

public class TagMain {

    private static final String PKU = "data/training/pku_training.utf8";
    private static final String MSR = "data/training/msr_training.utf8";

    private static final String PKU_TAG_OUTPUT = "data/feature/pku_tag.utf8";
    private static final String MSR_TAG_OUTPUT = "data/feature/msr_tag.utf8";

    private static Tagger tagger = new Tagger();

    public static void main(String[] args) {
	tagForCorups(PKU, PKU_TAG_OUTPUT);
	tagForCorups(MSR, MSR_TAG_OUTPUT);
    }

    private static void tagForCorups(String corpusName, String outputFilePath) {
	System.out.println("Processing corpus " + corpusName + " to " + outputFilePath);
	try (BufferedReader reader = new BufferedReader(
		new InputStreamReader(new FileInputStream(corpusName), "utf-8"));
		BufferedWriter writer = new BufferedWriter(
			new OutputStreamWriter(new FileOutputStream(outputFilePath), "utf-8"))) {
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		List<String> tagList = tagger.tagOneLine(line);
		tagList.stream().forEach(tag -> outputTag(writer, tag));
	    }
	} catch (Exception e) {
	    System.out.println("Failed: " + e);
	}
	System.out.println("Finished processing corpus " + corpusName);
    }

    private static void outputTag(BufferedWriter writer, String tag) {
	try {
	    writer.write(tag);
	    writer.write("\n");
	} catch (IOException e) {
	    System.out.println("Failed to output" + e);
	}
    }

    public static void simpleTestRun() {
	String testStr = "测试  一！  通过： 333。";
	testRun(testStr);
    }

    public static void testRun(String testLine) {
	System.out.println("Test Run String: " + testLine);
	System.out.println("Test Run Result: " + tagger.tagOneLine(testLine));
    }
}
