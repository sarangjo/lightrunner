package com.picotech.lightrunnerlibgdx;

import java.io.*;

import com.badlogic.gdx.Gdx;

public class StatLogger {
	// might need to do something with gamestate
	public static int check = 0;

	public static int[] getScores() {
		File f = new File("scores.txt");

		// 1000 is just a big number
		int[] scores = new int[1000];

		if (f.exists()) {
			FileReader fr;
			try {
				fr = new FileReader("scores.txt");
				int integer;
				// keep 10 top scores
				for (int i = 0; i < 9; i++) {
					try {
						integer = fr.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return scores;
	}

	//the 1 is just to make another iteration of these three variables
	private int score1;
	private int totalTime1;
	private int enemiesKilled1;

	public void update(int score, int totalTime, int enemiesKilled) {
		score1 = score;
		totalTime1 = totalTime;
		enemiesKilled1 = enemiesKilled;

		/*
		 * do something like 
		 * textfile ^^one set of data on a textfile should look like: 1.
		 * score 2. totalTime 3. enemiesKilled
		 * (space) 1. 2. 3. and yeah and over again. So when pulling up a top
		 * score menu, read every 5 lines (lines 1, 6, 11, etc.)
		 */
	}

	/*
	 * what needs to happen for high scores:
	 * 1. write all the integers to one text file
	 * 2. sort them all from highest to lowest
	 * 
	 */
	public void writeToFile() throws FileNotFoundException{
		//keeps track of high scores only
		File f = new File(("data/highScores.txt"));
		PrintWriter pw1 = new PrintWriter("highScores.txt");
		pw1.write(score1);
		pw1.close();
		
		/* keeps track of everything cumulative, a lifetime kind of thing
		 * keeps running cumulative totals of score, totalTime, and enemiesKilled
		 * FOR THE FUTURE: keeps track of number of different enemies (ie red, blue, etc.) 
		 */
		PrintWriter pw2 = new PrintWriter("cumulative.txt");
		pw2.write(score1);
		pw2.write(totalTime1);
		pw2.write(enemiesKilled1);
		pw2.close();
	}
	
	
	// public static boolean exists(Path "scores.txt", LinkOption null){

	// }
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

// SARANG's COMMENT: Probably an important import.
// FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
// fos.write(outputString.getBytes());
// fos.close();
