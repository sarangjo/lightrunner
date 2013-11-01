package com.picotech.lightrunnerlibgdx;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatLogger2 {
	public static FileHandle highScoresFile = Gdx.files.local("highScores.txt");
	public static FileHandle eKilledFile = Gdx.files.local("eKilled.txt");
	public static FileHandle timesFile = Gdx.files.local("times.txt");

	public static ArrayList<Integer> scores = new ArrayList<Integer>();
	public static ArrayList<Integer> enemiesKilled = new ArrayList<Integer>();
	public static ArrayList<Integer> times = new ArrayList<Integer>();

	// Cumulative data will be written in file as such:
	// totScore;totEKilled;totTime;
	public static FileHandle cumulFile = Gdx.files.local("cumulative.txt");

	public static int totScore, totEKilled, totTime;
	public static final int MAX_S_D = 5;

	private static void sortA(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size(); j++) {
				if (list.get(i) > list.get(j)) {
					swap(list, i, j);
				}
			}
		}
	}

	private static void sortD(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size(); j++) {
				if (list.get(i) < list.get(j)) {
					swap(list, i, j);
				}
			}
		}
	}

	private static <T> void swap(ArrayList<T> list, int i, int j) {
		T temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

	public static void endGame(int score, int eK, int time) {
		// File Read
		readAllStats(true);
		
		// Updates local variables
		addToTotal(score, eK, time);
		
		// File Write
		writeAllStats(score, eK, time);
		writeTotalsToFile();
	}

	public static void addToTotal(int score, int eKilled, int time) {
		int[] dataFromFile = readTotFromFile();
		totScore = dataFromFile[0] + score;
		totEKilled = dataFromFile[1] + eKilled;
		totTime = dataFromFile[2] + time;
	}

	/**
	 * Reads all the totals from one file.
	 * 
	 * @return An array containing all the totals.
	 */
	public static int[] readTotFromFile() {
		int[] totData = new int[3];
		if (cumulFile.exists()) {
			int counter = 0;
			String cumData = cumulFile.readString();
			String currentData = "";
			for (int i = 0; i < cumData.length(); i++) {
				char c = cumData.charAt(i);
				if (c == ';') {
					totData[counter] = Integer.parseInt(currentData);
					currentData = "";
					counter++;
				} else
					currentData += c;
			}
		} else {
			try {
				cumulFile.file().createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return totData;
	}

	/**
	 * Reads the high scores from the associated file. Basically parses
	 * <b>ALL</b> the data from the file and compounds the ArrayList.
	 * {@link scores}.
	 */
	public static void readAllStats(boolean isEndGame) {
		scores = readStatsFromFile(highScoresFile, scores, isEndGame);
		enemiesKilled = readStatsFromFile(eKilledFile, enemiesKilled, isEndGame);
		times = readStatsFromFile(timesFile, times, isEndGame);
		
		//if(!isEndGame) {
		int[] totals = readTotFromFile();
		totScore = totals[0];
		totEKilled = totals[1];
		totTime = totals[2];
		//}
	}

	public static void writeAllStats(int score, int eK, int time) {
		writeStatToFile(score, scores, highScoresFile);
		writeStatToFile(eK, enemiesKilled, eKilledFile);
		writeStatToFile(time, times, timesFile);
	}

	/**
	 * This only stores the first {@code MAX_S_D} values from the FileHandle.
	 * 
	 * @param f
	 *            The FileHandle to read the data from.
	 * @param list
	 *            The ArrayList of Integers to store the data in.
	 */
	public static ArrayList<Integer> readStatsFromFile(FileHandle f,
			ArrayList<Integer> list, boolean isEndGame) {
		list = new ArrayList<Integer>();
		if (f.exists()) {
			String fileString = f.readString();
			ArrayList<String> stringList = new ArrayList<String>();
			int start = 0, end = 0;
			for (int i = 0; i < fileString.length(); i++) {
				if (fileString.charAt(i) == ';') {
					end = (i - 1 >= start) ? i : 0;
					stringList.add(fileString.substring(start, end));
					start = end + 1;
				}
			}
			for (int i = 0; i < stringList.size(); i++) {
				list.add(Integer.parseInt(stringList.get(i)));
			}
		} else {
			try {
				f.file().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (list.size() < 1 && !isEndGame)
			list.add(new Integer(0));
		return list;
	}

	public static void writeStatToFile(int value, ArrayList<Integer> list,
			FileHandle file) {
		list.add(new Integer(value));
		sortD(list);
		while (list.size() > MAX_S_D) {
			list.remove(MAX_S_D);
		}
		if (!file.exists())
			try {
				file.file().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		String s = "";
		for (Integer i : list) {
			s += i.intValue() + ";";

		}
		file.writeString(s, false);
	}

	public static void writeTotalsToFile() {
		cumulFile.writeString(
				totScore + ";" + totEKilled + ";" + totTime + ";", false);
	}

	public static void draw(SpriteBatch batch) {
		// Scores arrayList
		// for (int i = 0; i < ((scores.size() <= 10) ? scores.size() : 10);
		// i++) {
		// Assets.text(batch, scores.get(i).intValue() + "", 300, 500 - 80 * i);
		// }
		// Top scores
		Assets.textWhite(batch, "Highest:", 300 * GameScreen.defS.x, 540 * GameScreen.defS.y);
		Assets.textWhite(batch, scores.get(0) + " points", 300 * GameScreen.defS.x, 480 * GameScreen.defS.y);
		Assets.textWhite(batch, times.get(0) + " seconds", 300 * GameScreen.defS.x, 420 * GameScreen.defS.y);
		Assets.textWhite(batch, enemiesKilled.get(0) + " enemies", 300 * GameScreen.defS.x, 360 * GameScreen.defS.y);
		
		// All-time
		Assets.textWhite(batch, "All-time:", 650 * GameScreen.defS.x, 540 * GameScreen.defS.y);
		Assets.textWhite(batch, totScore + " points", 650 * GameScreen.defS.x, 480 * GameScreen.defS.y);
		Assets.textWhite(batch, totTime + " seconds", 650 * GameScreen.defS.x, 420 * GameScreen.defS.y);
		Assets.textWhite(batch, totEKilled + " enemies", 650 * GameScreen.defS.x, 360 * GameScreen.defS.y);
	}

	public static void reset() {
		if (highScoresFile.exists())
			highScoresFile.delete();
		if (eKilledFile.exists())
			eKilledFile.delete();
		if (timesFile.exists())
			timesFile.delete();
		if (cumulFile.exists())
			cumulFile.delete();

		try {
			highScoresFile.file().createNewFile();
			eKilledFile.file().createNewFile();
			timesFile.file().createNewFile();
			cumulFile.file().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/* Old StatLogger shizzo:
 * public static void readHSFromFile() { scores = new ArrayList<Integer>(); if
 * (highScoresFile.exists()) { String fileString = highScoresFile.readString();
 * ArrayList<String> scoreList = new ArrayList<String>(); int start = 0, end =
 * 0; for (int i = 0; i < fileString.length(); i++) { if (fileString.charAt(i)
 * == ';') { // The end variable is set to the current position end = (i - 1 >=
 * start) ? i : 0; // This string will represent the concatenation of the score
 * // to be added to the score array. start = end + 1;
 * scoreList.add(fileString.substring(start, end)); } } for (int i = 0; i <
 * scoreList.size(); i++) { scores.add(Integer.parseInt(scoreList.get(i))); } }
 * else { try { highScoresFile.file().createNewFile(); } catch (IOException e) {
 * e.printStackTrace(); } } } public static void readStatsFromFile(FileHandle f,
 * ArrayList<Integer> list) { if (f.exists()) { for (int i = 0; i < MAX_S_D;
 * i++) list.add(Integer.parseInt(f.readString())); } else { try {
 * f.file().createNewFile(); } catch (IOException e) { e.printStackTrace(); } }
 * } public static void writeHSToFile(int score) { scores.add(new
 * Integer(score)); sortD(scores); while (scores.size() > MAX_S_D) {
 * scores.remove(MAX_S_D); } if (!highScoresFile.exists()) try {
 * highScoresFile.file().createNewFile(); } catch (IOException e) {
 * e.printStackTrace(); } String s = ""; for (Integer i : scores) { s +=
 * i.intValue() + ";";
 * 
 * } highScoresFile.writeString(s, false); }
 */
