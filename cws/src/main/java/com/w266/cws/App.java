package com.w266.cws;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.w266.cws.preprocessor.Tagger;

public class App {

    private static final String PKU = "data/train/pku_training.utf8";
    private static final String MSR = "data/train/msr_training.utf8";

    private static Tagger tagger = new Tagger();

    public static void main(String[] args) {

	// simpleTestRun();

	try (BufferedReader trainDataReader = new BufferedReader(
		new InputStreamReader(new FileInputStream(MSR), "utf-8"))) {
	    String trainDataTextLine = trainDataReader.readLine();
	    testRun(trainDataTextLine);

	} catch (Exception e) {
	    System.out.println("Failed: " + e);
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
