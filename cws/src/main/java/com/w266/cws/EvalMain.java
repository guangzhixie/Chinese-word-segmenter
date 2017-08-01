package com.w266.cws;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import com.w266.cws.util.FilePath;
import com.w266.cws.util.StringUtil;

import opennlp.maxent.io.GISModelReader;
import opennlp.model.DataReader;
import opennlp.model.MaxentModel;
import opennlp.model.PlainTextFileDataReader;

public class EvalMain {

    public static void main(String[] args) throws IOException {
	System.out.println("Loading the gzipped model from a file " + FilePath.MODEL_PATH);
	FileInputStream inputStream = new FileInputStream(FilePath.MODEL_PATH);
	InputStream decodedInputStream = new GZIPInputStream(inputStream);
	DataReader modelReader = new PlainTextFileDataReader(decodedInputStream);
	MaxentModel loadedMaxentModel = new GISModelReader(modelReader).getModel();

	System.out.println("Start to evaluate...");
	try (BufferedReader featureReader = new BufferedReader(
		new InputStreamReader(new FileInputStream(FilePath.MSR_FEATURE_PATH), "utf-8"));
		BufferedReader tagReader = new BufferedReader(
			new InputStreamReader(new FileInputStream(FilePath.MSR_TAG_OUTPUT), "utf-8"))) {

	    String line = null;
	    String expectedTag = null;

	    int correctCount = 0;
	    for (int i = 0; i < 1000; i++) {
		line = featureReader.readLine();
		expectedTag = tagReader.readLine();
		// Now predicting the outcome using the loaded model
		double[] outcomeProbs = loadedMaxentModel.eval(line.split(StringUtil.SPACE));
		String outcome = loadedMaxentModel.getBestOutcome(outcomeProbs);
		System.out.println(outcome + " vs " + expectedTag);
		if (expectedTag.equals(outcome)) {
		    correctCount++;
		}
	    }

	    System.out.println("Accuracy: " + correctCount / 1000.0);

	} catch (Exception e) {
	    System.out.println("Failed: ");
	    e.printStackTrace();
	}

    }

}
