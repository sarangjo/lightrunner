package com.picotech.lightrunnerlibgdx;

import java.io.*;
import java.util.Scanner;

public class StatLogger {
	/*
	 * This class first needs to update the four below fields by calling update(),
	 * then the writeTo methods can be called to write the fields
	 * to corresponding files.
	 * 
	 * Original messed up class is below this one.
	 */
	
	private int hScore;
	private int totalScore;	//first thing written in cum file
	private int totalTime;	//second thing written in cum file
	private int enemiesKilled;//third thing written in cum file
	
	private boolean ifFirstTime;
	
	
	private File cumulative;
	private File highScores;
	private Scanner cumScanner;
	private Scanner highScanner;
	private FileWriter cumWriter;
	private FileWriter highWriter;
	
	public StatLogger()
	{
		cumulative = new File("cumulative.txt");
		highScores = new File("highScores.txt");
		
		try {
			cumScanner = new Scanner(cumulative);
			highScanner = new Scanner(highScores);
			cumWriter = new FileWriter(cumulative, true);
			highWriter = new FileWriter(highScores, true);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		try {
			if (cumulative.length() == 0) {
				for (int i = 0; i < 3; i++)
					cumWriter.append("0\n");
				ifFirstTime = true;
			}
			if (highScores.length() == 0 ) {
				highWriter.append("0");
				ifFirstTime = true;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/*Update must be called first to set the values
	 * of the data that will be written to the files
	 */
	public void update(int score, int time, int eKilled) {
		//read in and assign the values from the files
		hScore = getHScore();
		int[] temp = getCumulative();
		totalScore = temp[0];
		totalTime = temp[1];
		enemiesKilled = temp[2];
		
		if (score > hScore)
			hScore = score;
		
		totalScore += score;
		totalTime += time;
		enemiesKilled += eKilled;
	}
	
	public void writeCumulativeToFile()
	{
		int[] currentCum = {totalScore, totalTime, enemiesKilled};
		try {
			cumulative.delete();
			cumulative.createNewFile();
			cumWriter = new FileWriter(cumulative, true);
			for (int i : currentCum){
				cumWriter.write(currentCum[i] + "\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try { cumWriter.close(); }
			catch (IOException e) {}
		}
	}
	
	public void writeHighToFile()
	{
		try{
			highScores.delete();
			highScores.createNewFile();
			highWriter = new FileWriter(highScores);
			highWriter.write(hScore);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try { highWriter.close(); }
			catch (IOException e) {}
		}
	}
	
	public int[] getCumulative(){
		int[] cumulativeArray = new int[3]; 

		for (int i = 0; i < 3; i++) {
			if (cumScanner.hasNextInt())
				cumulativeArray[i] = cumScanner.nextInt();
			else
				break;
		}
		return cumulativeArray;
	}
	
	public int getHScore()
	{
		if (highScanner.hasNextInt())
			return highScanner.nextInt();
		else
			return -1;	//debug value
	}	
}