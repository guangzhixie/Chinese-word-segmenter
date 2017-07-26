package com.w266.cws;

import java.io.File;
import java.io.IOException;

import com.w266.cws.util.FilePath;

import opennlp.maxent.GIS;
import opennlp.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.model.AbstractModel;
import opennlp.model.AbstractModelWriter;
import opennlp.model.DataIndexer;
import opennlp.model.FileEventStream;
import opennlp.model.MaxentModel;
import opennlp.model.OnePassDataIndexer;

public class ModelMain {

    public static void main(String[] args) throws IOException {

	System.out.println("Start to train the model with training data " + FilePath.TRAIN_PATH);
	DataIndexer indexer = new OnePassDataIndexer(new FileEventStream(FilePath.TRAIN_PATH));
	MaxentModel trainedMaxentModel = GIS.trainModel(100, indexer);
	System.out.println("Successfully trained the model with training data " + FilePath.TRAIN_PATH);

	// Storing the trained model into a file for later use (gzipped)
	System.out.println("Save the trained model to " + FilePath.MODEL_PATH);
	File outFile = new File(FilePath.MODEL_PATH);
	AbstractModelWriter writer = new SuffixSensitiveGISModelWriter((AbstractModel) trainedMaxentModel, outFile);
	writer.persist();
	System.out.println("Finish saving the trained model to " + FilePath.MODEL_PATH);
    }
}
