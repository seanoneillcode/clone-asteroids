package com.halycon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ScoreStorage {

	private static final String SPLIT_TOKEN = " ";
	private static final String NEWLINE = "\n";
	private static final String SCORES_FILE = "res/scores.txt";
	
	public static List<ScoreEntry> readScoresFile() {
		BufferedReader br = null;
		List<ScoreEntry> scores = new ArrayList<ScoreEntry>();
		try {
			String currentLine;
			
			br = new BufferedReader(new FileReader(SCORES_FILE));
			
			while ((currentLine = br.readLine()) != null) {
				String[] tokens = currentLine.split(SPLIT_TOKEN);
				if (tokens != null && tokens.length > 1) {
					String name = tokens[0];
					Integer score = Integer.valueOf(tokens[1]);
					if (name != null && score != null) {
						scores.add(new ScoreEntry(name, score));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return scores;
	}
	
	public static void writeScoresFile(List<ScoreEntry> scores) {
		Writer writer = null;
		try {
			
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					SCORES_FILE), "utf-8"));
			
			for (ScoreEntry entry : scores) {
				writer.write(String.format("%s%s%s%s", 
						entry.getName(), 
						SPLIT_TOKEN, 
						entry.getValue().toString(), 
						NEWLINE));
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
