package com.picotech.lightrunnerlibgdx;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class StatLogger2 {
	FileHandle highScoresFile;
	public ArrayList<Integer> scores = new ArrayList<Integer>();

	public StatLogger2() {
		highScoresFile = Gdx.files.local("highScores.txt");
	}

	public void writeHSToFile(int score) {
		scores.add(new Integer(score));
		sortD(scores);
		if (!highScoresFile.exists())
			try {
				highScoresFile.file().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		String s = "";
		for (Integer i : scores) {
			s += i.intValue() + ";";
		}
		highScoresFile.writeString(s, false);
	}

	private void sortA(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i);
			for (int j = i; j < list.size(); j++) {
				if (list.get(i) > list.get(j)) {
					swap(list, i, j);
				}
			}
		}
	}
	private void sortD(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i);
			for (int j = i; j < list.size(); j++) {
				if (list.get(i) < list.get(j)) {
					swap(list, i, j);
				}
			}
		}
	}

	private <T> void swap(ArrayList<T> list, int i, int j) {
		T temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

	public void update(int score) {
		scores = readHSFromFile();
		writeHSToFile(score);
	}

	private ArrayList<Integer> readHSFromFile() {
		// This represents the entire string of the file.
		String fileString = highScoresFile.readString();
		// The entire string is broken down into characters.
		char[] fileArray = fileString.toCharArray();
		// The characters are to be parsed into individual strings representing each of the scores.
		ArrayList<String> scoreList = new ArrayList<String>();
		int start = 0, end = 0;
		// Going through each of the characters.
		for(int i = 0; i < fileArray.length; i++) {
			// Once a semicolon is hit...
			if(fileArray[i] == ';') {
				// The end variable is set to the current position
				end = (i-1 >= start) ? i : 0;
				// This string will represent the concatenation of the score to be added to the score array.
				String score = "";
				for(int j = start; j < end; j++) {
					score += fileArray[j];
				}
				start = end + 1;
				scoreList.add(score);
			}
		}
		// Parsing the string array
		// For each string in the string array, there is a corresponding array of Integers.
		ArrayList<Integer> newScores = new ArrayList<Integer>();
		for (int i = 0; i < scoreList.size(); i++) {
			newScores.add(Integer.parseInt(scoreList.get(i)));
		}
		return newScores;
	}
}
