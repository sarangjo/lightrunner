package com.picotech.lightrunnerlibgdx;

import java.io.*;

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

	private int ps;
	private int pt;
	private int ek;
	private int dt;

	public void update(int playerscore, int playertime, int enemieskilled,
			int distancetravelled) {
		ps = playerscore;
		pt = playertime;
		ek = enemieskilled;
		dt = distancetravelled;

		/*
		 * do something like (if gamestate = OVER){} save these four stats to
		 * textfile ^^one set of data on a textfile should look like: 1.
		 * playerscore 2. playertime 3. enemieskilled 4. distancetravelled
		 * (space) 1. 2. 3. 4. and yeah and over again. So when pulling up a top
		 * score menu, read every 5 lines (lines 1, 6, 11, etc.)
		 */
	}

	// public static boolean exists(Path "scores.txt", LinkOption null){

	// }
}
// for Atticus
/**
 * 1. Instantiate a new StatLogger in the World 2. Collect all relevant data 3.
 * Store all statistic variables in here eg: IN WORLD: StatLogger newStatLogger
 * = new StatLogger(); // in the update method newStatLogger.update(playerscore,
 * playertime, enemieskilled, distancetravelled);
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
