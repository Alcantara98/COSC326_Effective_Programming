package Ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ant 
{	
	public static void main(String[] args) 
	{
		HashMap<Character, String> DNA = new HashMap<Character, String>();
		int stepsToFollow;

		Scanner scan = null;
		try {
			scan = new Scanner(new File("C:\\Users\\viper\\Documents\\2K20\\326\\326 Etude 2\\bin\\Ant\\dnaText.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean firstLine = true;
		char initialState = ' ';
		while(true) 
		{
			String dnaLine = scan.nextLine();
			if(!dnaLine.isEmpty()) {
				if (dnaLine.length() == 11 && dnaLine.charAt(0) != '#') 
				{	
					if(firstLine)
					{
						initialState = dnaLine.charAt(0);
						firstLine = false;
					}
					DNA.put(dnaLine.charAt(0), dnaLine);
				}
				else if (dnaLine.charAt(0) != '#')
				{
					stepsToFollow = Integer.parseInt(dnaLine);
					calcFinalCoord(DNA, stepsToFollow, initialState);
					DNA.clear();
					stepsToFollow = 0;
					firstLine = true;
				}
			}
		}
	}
	
	public static void calcFinalCoord(HashMap<Character, String> DNA, int stepsToFollow, char initialState) 
	{	
		long startTime = System.nanoTime();
		
		HashMap<String, Character> visitedTiles = new HashMap<String, Character>();
		
		int currentX = 0;
		int currentY = 0;
		
		int xBiggest = 0;
		int yBiggest = 0;
		char currentTileSign = initialState;
		char initialTileSign = currentTileSign;
		char prevDirection = 'N'; //NESW respectively
		
		visitedTiles.put(currentX + "," + currentY, currentTileSign);
		
		for(int i = 0; i < stepsToFollow; i++)
		{
			String dnaString = DNA.get(currentTileSign);
			switch(prevDirection) 
			{
			case 'N':
				prevDirection = dnaString.charAt(2);
				visitedTiles.put(currentX + "," + currentY, dnaString.charAt(7));
				break;
				
			case 'E':
				prevDirection = dnaString.charAt(3);
				visitedTiles.put(currentX + "," + currentY, dnaString.charAt(8));
				break;
				
			case 'S':
				prevDirection = dnaString.charAt(4);
				visitedTiles.put(currentX + "," + currentY, dnaString.charAt(9));
				break;
				
			case 'W':
				prevDirection = dnaString.charAt(5);
				visitedTiles.put(currentX + "," + currentY, dnaString.charAt(10));
				break;
			}

			switch(prevDirection) 
			{
			case 'N':
				currentY += 1;
				break;
			case 'E':
				currentX += 1;
				break;
			case 'S':
				currentY -= 1;
				break;
			case 'W':
				currentX -= 1;
				break;
			}
			
			
			//char tileSign = visitedTiles.get(currentX + "," + currentY);
			if(Math.abs(currentX) > xBiggest && Math.abs(currentY) > yBiggest)
			{
				visitedTiles.put(currentX + "," + currentY, initialTileSign);
				currentTileSign = initialTileSign;
				xBiggest = Math.abs(currentX);
				yBiggest = Math.abs(currentY);
			}
			else if(visitedTiles.get(currentX + "," + currentY) != null) 
			{
				currentTileSign = visitedTiles.get(currentX + "," + currentY);
			}
			else if(visitedTiles.get(currentX + "," + currentY) == null) 
			{
				visitedTiles.put(currentX + "," + currentY, initialTileSign);
				currentTileSign = initialTileSign;
			}
		}
		long endTime = System.nanoTime();
		
		long timeElapsed = endTime - startTime;
		System.out.println(timeElapsed);
		System.out.println("X: " + currentX + "  Y: " + currentY);
	}
}
