package com.picotech.lightrunnerlibgdx;

public class StatLogger {
	// for Atticus
	/**
	 * 1. Instantiate a new StatLogger in the World
	 * 2. Collect all relevant data
	 * 3. Store all statistic variables in here
	 * 		eg: 
	 * 		IN WORLD:
	 * 			StatLogger newStatLogger = new StatLogger();
	 * 			// in the update method
	 * 			newStatLogger.update(playerscore, playertime, enemieskilled, distancetravelled);
	 * 
	 * 			// when the player dies/game ends
	 * 			newStatLogger.writeToFile();
	 * 
	 * 		IN STATLOGGER:
	 * 			public void update(int playerscore, int playertime, int enemieskilled, int distancetravelled){
	 * 				// stores to private variables in this class to write to files when the game is over
	 * 			}
	 * 
	 * 			public ArrayList<Statistic> getInfo(){
	 * 				return allInfo;
	 * 			}
	 * 
	 * 		IN GAMESCREEN:
	 * 			StatLogger statReader = new StatLogger();
	 * 			statReader.getInfo();
	 * 4. Research on google how to store files locally (different between desktop and android versions)
	 * 
	 * Notes:
	 * 	store as MANY statistics as you can. Jetpack joyride literally keeps track of EVERYTHING, which is cool
	 *  helps with missions, achievements, sense of progress
	 */
}
