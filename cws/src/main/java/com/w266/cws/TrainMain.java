package com.w266.cws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.w266.cws.util.FilePath;
import com.w266.cws.util.StringUtil;

public class TrainMain {

    public static void main(String[] args) {
	System.out.println("Construct training file and output to " + FilePath.TRAIN_PATH);
	try (BufferedReader tagReader = new BufferedReader(
		new InputStreamReader(new FileInputStream(FilePath.PKU_TAG_OUTPUT), "utf-8"));
		BufferedReader featureReader = new BufferedReader(
			new InputStreamReader(new FileInputStream(FilePath.PKU_FEATURE_PATH), "utf-8"));
		BufferedWriter writer = new BufferedWriter(
			new OutputStreamWriter(new FileOutputStream(FilePath.TRAIN_PATH), "utf-8"))) {

	    String tagLine = null;
	    String featureLine = null;
	    while ((tagLine = tagReader.readLine()) != null && (featureLine = featureReader.readLine()) != null) {
		if (!tagLine.equals(StringUtil.NEWLINE) && !featureLine.equals(StringUtil.NEWLINE)) {
		    StringBuilder sb = new StringBuilder();
		    sb.append("tag=");
		    sb.append(tagLine);
		    sb.append(StringUtil.SPACE);
		    sb.append(featureLine);
		    sb.append(StringUtil.NEWLINE);
		    writer.write(sb.toString());
		}
	    }

	} catch (Exception e) {
	    System.out.println("Failed: ");
	    e.printStackTrace();
	}
	System.out.println("Finished constructing training file");
    }

}
