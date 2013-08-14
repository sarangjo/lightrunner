package com.picotech.lightrunnerlibgdx;

import java.io.*;
import java.lang.Object;
import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;

public class StatLogger {
	// might need to do something with gamestate

	// the 1 is just to make another iteration of these three variables
	private int score1;
	private int totalTime1;
	private int enemiesKilled1;

	public void update(int score, int totalTime, int enemiesKilled) {
		score1 = score;
		totalTime1 = totalTime;
		enemiesKilled1 = enemiesKilled;

	}

	/*
	 * what needs to happen for high scores: 1. write all the integers to one
	 * text file 2. sort them all from highest to lowest
	 */
	public void writeToFile() throws FileNotFoundException {
		int[] cumulativeArray = displayCumulative();
		// compare lines to pick the top number to read, then display

		BufferedWriter pw1 = null;
		BufferedWriter pw2 = null;
		File cumulative = new File("cumulative.txt");
		File highScore = new File("highScore.txt");
		cumulative.delete();
		try {
			pw1 = new BufferedWriter(new FileWriter(cumulative));
			pw1.write((score1 + cumulativeArray[0]) + "");
			// System.out.println((cumulativeArray[0]) + "");
			pw1.newLine();
			pw1.write((totalTime1 + cumulativeArray[1]) + "");
			// System.out.println((cumulativeArray[1]) + "");
			pw1.newLine();
			pw1.write((enemiesKilled1 + cumulativeArray[2]) + "");
			// System.out.println((cumulativeArray[2]) + "");
			pw1.close();
		} catch (IOException e) {
		}

		int hScore;
		hScore = displayHighScore();
		hScore = updateHighScore(score1, hScore);
		//highScore.delete();
		try {
			pw2 = new BufferedWriter(new FileWriter(highScore));
			pw2.write("4" + "");
			//pw2.write(hScore + "");
		} catch (IOException ex) {
			System.out.println("Error");
		}
	}

	// reading the file
	public int[] displayCumulative() {

		BufferedReader br = null;
		int[] cumulativeArray = new int[3];

		try {
			br = new BufferedReader(new FileReader("cumulative.txt"));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		for (int i = 0; i < 3; i++) {
			try {
				cumulativeArray[i] = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cumulativeArray;
	}

	// returns the new high score
	public int updateHighScore(int score, int hScore) {
		if (score >= hScore)
			return score;
		else
			return hScore;
	}

	public int displayHighScore() {
		BufferedReader br = null;
		int temp = -1;
		try {
			br = new BufferedReader(new FileReader("highScore.txt"));
			//System.out.println("Does this work");
		} catch (FileNotFoundException e) {
			System.out.println("Error");
		}
		try {
			temp = Integer.parseInt(br.readLine());
			//System.out.println("This works");
			br.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		return temp;
	}
}
// for Atticus
/**
 * 1. Instantiate a new StatLogger in the World 2. Collect all relevant data 3.
 * Store all statistic variables in here eg: IN WORLD: StatLogger newStatLogger
 * = new StatLogger(); // in the update method newStatLogger.update(playerscore,
 * playertime, enemieskilled);
 * 
 * // when the player dies/game ends newStatLogger.writeToFile();
 * 
 * IN STATLOGGER: public void update(int playerscore, int playertime, int
 * enemieskilled, int distancetravelled){ // stores to private variables in this
 * class to write to files when the game is over }
 * 
 * public ArrayList<Statistic> getInfo(){ return allInfo; }
 * 
 * IN GAMESCREEN: StatLogger statReader = new StatLogger();
 * statReader.getInfo(); 4. Research on google how to store files locally
 * (different between desktop and android versions)
 * 
 * Notes: store as MANY statistics as you can. Jetpack joyride literally keeps
 * track of EVERYTHING, which is cool helps with missions, achievements, sense
 * of progress
 */
