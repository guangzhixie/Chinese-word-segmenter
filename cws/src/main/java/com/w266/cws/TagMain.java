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
import com.w266.cws.util.FilePath;
import com.w266.cws.util.StringUtil;

public class TagMain {

    private static Tagger tagger = new Tagger();

    public static void main(String[] args) {
	tagForCorups(FilePath.PKU_CORPUS, FilePath.PKU_TAG_OUTPUT);
	tagForCorups(FilePath.MSR_CORPUS, FilePath.MSR_TAG_OUTPUT);
    }

    private static void tagForCorups(String corpusPath, String outputFilePath) {
	System.out.println("Processing corpus " + corpusPath + " to " + outputFilePath);
	try (BufferedReader reader = new BufferedReader(
		new InputStreamReader(new FileInputStream(corpusPath), "utf-8"));
		BufferedWriter writer = new BufferedWriter(
			new OutputStreamWriter(new FileOutputStream(outputFilePath), "utf-8"))) {
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		List<String> tagList = tagger.tagForSentence(line);
		tagList.stream().forEach(tag -> outputTag(writer, tag));
	    }
	} catch (Exception e) {
	    System.out.println("Failed: ");
	    e.printStackTrace();
	}
	System.out.println("Finished processing corpus " + corpusPath);
    }

    private static void outputTag(BufferedWriter writer, String tag) {
	try {
	    writer.write(tag);
	    writer.write(StringUtil.NEWLINE);
	} catch (IOException e) {
	    System.out.println("Failed to output: ");
	    e.printStackTrace();
	}
    }

    public static void simpleTestRun() {
	String testStr = "测试  一！  通过： 333。";
	testRun(testStr);
    }

    public static void testRun(String testLine) {
	System.out.println("Test Run String: " + testLine);
	System.out.println("Test Run Result: " + tagger.tagForSentence(testLine));
    }
}
