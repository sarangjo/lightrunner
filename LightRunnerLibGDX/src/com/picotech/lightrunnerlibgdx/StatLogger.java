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
			if (cumScanner.hasNextInt() == false){
				cumulative.createNewFile();
				cumWriter = new FileWriter(cumulative, true);
				cumScanner = new Scanner(cumulative);
			}
			if (highScanner.hasNextInt() == false){
				highScores.createNewFile();
				highWriter = new FileWriter(highScores, true);
				highScanner = new Scanner(highScores);
			}
			
			if (cumulative.length() == 0) {
				for (int i = 0; i < 3; i++)
					cumWriter.write("0\n");
			}
			if (highScores.length() == 0 ) {
				highWriter.write("0");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		//read in and assign the values from the files
		hScore = getHScore();
		int[] temp = getCumulative();
		totalScore = temp[0];
		totalTime = temp[1];
		enemiesKilled = temp[2];
	}
	
	/*Update must be called first to set the values
	 * of the data that will be written to the files
	 */
	public void update(int score, int time, int eKilled) {
		if (score > hScore)
			hScore = score;
		
		totalScore += score;
		totalTime += time;
		enemiesKilled += eKilled;
	}
	
	public void writeCumulativeToFile()
	{
		int[] currentCum = getCumulative();
		
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
		hScore = getHScore();
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

		for (int i : cumulativeArray) {
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