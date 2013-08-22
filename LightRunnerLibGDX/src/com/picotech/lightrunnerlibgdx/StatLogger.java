package com.picotech.lightrunnerlibgdx;

import java.io.*;
import java.lang.Object;
import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Scanner;
public class StatLogger {
	// might need to do something with gamestate

	// the 1 is just to make another iteration of these three variables
	private int score1;
	private int totalTime1;
	private int enemiesKilled1;
	
	private BufferedWriter pw1;
	private BufferedWriter pw2;
	private File cumulative;
	private File highScores;
	private BufferedReader br1;;
	private BufferedReader br2;
	Scanner highScanner, cumulScanner;
	
	public StatLogger(){
		cumulative = new File("cumulative.txt");
		highScores = new File("highScore.txt");
		
		try {
			highScanner = new Scanner(highScores);
			cumulScanner = new Scanner(cumulative);
		} catch (FileNotFoundException e) {
			try {
				highScores.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try
		{
			pw1 = new BufferedWriter(new FileWriter(cumulative));
			pw2 = new BufferedWriter(new FileWriter(highScores));
			br1 = new BufferedReader(new FileReader(cumulative));
			br2 = new BufferedReader(new FileReader(highScores));
		} catch (IOException ex){System.out.println("Error in declaring readers/writers");}
		
	}
	
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
		
		cumulative.delete();
		cumulative = new File("cumulative.txt");
		for (int i = 0; i < 3; i++)
		{
			try {
				System.out.println(cumulative);
				pw1.write(0 + "");
				pw1.newLine();
			} catch (IOException ex){}
		}
		try {
			pw1.write((score1 +" " + cumulativeArray[0]));
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
			//pw2.write("4" + "");
			pw2.write(hScore + "");
		} catch (IOException ex) {
			System.out.println("Error");
		}
	}

	// reading the file
	//SO THIS IS WHERE THE PROBLEM IS WITH WRITING TO FILE, THERE IS A
	//NUMBERFORMATEXCEPTION : NULL, AND IT HAPPENS AT THE THE INT PARSING BELOW
	//LETS WORK TOGETHER
	
	public int[] displayCumulative(){
		int[] cumulativeArray = new int[3]; 

		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			
			
			//try {
				//cumulativeArray[i] = Integer.parseInt(br2.readLine());
				//System.out.println(br1);
				while (highScanner.hasNext())
					arrayList.add(highScanner.nextLine());
				//System.out.print(br1.readLine());
				//cumulativeArray[i] = Integer.valueOf(br1.readLine());
				
			//} catch (IOException e) {
			//	e.printStackTrace();
			//}
		}
		
		/*
		try {
			br1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		for (int i = 0; i < arrayList.size(); i++)
		{
			String str = arrayList.get(i);
			cumulativeArray[i] = Integer.parseInt(str); 
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
		
		int temp = -1;
		try {
			//temp = Integer.parseInt(br2.readLine());
			
			br2.close();
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
