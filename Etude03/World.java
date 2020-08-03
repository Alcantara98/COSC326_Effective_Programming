import java.util.*;

public class World {
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		String dumbCoordinate = scan.nextLine();
		
		String coordString[] = {"0", "0", "0", "N", "0", "0", "0", "E"};
		
		String currentString = "";
		
		String currentCoordString = "0";
		
		int inputLength = dumbCoordinate.length();
		
		int currentIndex = 0; //Our index through dumbCoordinate
		
		int caseChangeBy = 0;
		
		int coordCase = 0;
		
		boolean directionFound = false; //So we know if we are expecting N, S, E, W for the second part
		boolean signed = false; //Coordinates will have deg, min, sec, etc.
		
		//We need these info so we know whether to make signed = true if we skipped degrees or minutes
		boolean foundFirstDeg = false;
		boolean foundSecondDeg = false;
		boolean foundFirstMin = false;
		boolean foundSecondMin = false;
		
		// If true, we ask if our interpretation is what they meant
		boolean asterisk = false;
		
		for(int currentCase = 0; currentCase < 16 && currentIndex < inputLength;)
		{
			boolean isWord = false;
			char currentChar = dumbCoordinate.charAt(currentIndex);
			
			if(currentChar == ',')
			{
				coordCase = 4;
				currentCase = 7;
			}
			//Getting Rid of unwanted char characters that may have been accidentally been pressed
			//and taking in alphabets only.
			//and taking in alphabets only.
			while((currentChar < 48) || (currentChar > 57 && currentChar < 65) 
					|| (currentChar > 90 && currentChar < 97) || (currentChar > 122))
			{
				if(currentChar != ' ' || currentChar != ',')
				{
					asterisk = true;
				}
				currentIndex += 1;
				
				if(currentIndex < inputLength)
				{
					currentChar = dumbCoordinate.charAt(currentIndex);
				}
				else 
				{
					break;
				}
			}
			
			//Builds the current string while its reading letters to differentiate with
			//Coordinates in case there is no space between them
			if((currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123))
			{
				isWord = true;
				
				while((currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123)) 
				{
					currentString += currentChar;
					currentIndex += 1;
					
					if(currentIndex < inputLength)
					{
						currentChar = dumbCoordinate.charAt(currentIndex);
					}
					else 
					{
						break;
					}
				}
			}
			
			//Coordinate can only have decimal point, so we need to keep track of this
			boolean decimal = false;
			//Builds the string if it is a number.
			if(isWord == false && ((currentChar > 47 && currentChar < 58) || (currentChar == 46 && decimal == false)))
			{
				String newCoord = "";
				
				while(isWord == false && ((currentChar > 47 && currentChar < 58) || (currentChar == 46 && decimal == false)))
				{
					newCoord += currentChar;
					currentIndex += 1;
					
					if(currentIndex < inputLength)
					{
						currentChar = dumbCoordinate.charAt(currentIndex);
					}
					else 
					{
						break;
					}
				}
				
				//If looking for a Direction but instead got a number at the start
				if(currentCase == 0 || currentCase == 8)
				{
					currentCase += 1;
				}
				else if(currentCase == 7)
				{
					currentCase = 10;
				}
				else if(currentCase == 2 || currentCase == 4 || currentCase == 6 || 
						currentCase == 10 || currentCase == 12)
				{		
					if(currentCase == 2)
					{
						foundFirstDeg = true;
					}
					if(currentCase == 6)
					{
						currentCase += 2;
					}
					if(signed == true)
					{
						System.out.println("Input is invalid 0");
						currentCase = 16;
					}
					signed = false;
					currentCase += 1;
					coordString[coordCase] = currentCoordString;
					currentCoordString = "0";
					coordCase += 1;
					
					if(coordCase == 3)
					{
						coordCase += 1;
					}
				}
				currentCoordString = newCoord;
				currentCase += 1;
				
				if(currentIndex == inputLength && currentCase == 14)
				{
					coordString[coordCase] = currentCoordString;
				}
			} 
			//Cases: Direction, Coord, CoordType, Coord, CoordType, Coord, CoordType, Direction, Comma (not a case), Direction, Coord, CoordType, Coord, CoordType, Coord, CoorType, Direction
			//Index: 0,         1,     2,         3,     4,         5,     6,         7,                             8,         9,     10,        11,    12,        13,    14,       15   
			if(isWord == true) 
			{                  
				if(currentCase == 0)
				{
					if(currentString.equals("N") || currentString.equalsIgnoreCase("north"))
					{
						coordString[3] = "N";
						directionFound = true;
					}	
					if(currentString.equals("S") || currentString.equalsIgnoreCase("south"))
					{
						coordString[3] = "S";
						directionFound = true;
					}		
					if(currentString.equals("E") || currentString.equalsIgnoreCase("east"))
					{
						coordString[3] = "E";
						directionFound = true;
					}		
					if(currentString.equals("W") || currentString.equalsIgnoreCase("west"))
					{
						coordString[3] = "W";
						directionFound = true;
					}		
				}
				if(currentCase == 2)
				{
					if(currentString.equalsIgnoreCase("d") || currentString.equalsIgnoreCase("deg") || 
							currentString.equalsIgnoreCase("degree") || currentString.equalsIgnoreCase("degrees") ||
							currentString.equals("°"))
					{
						signed = true;
						foundFirstDeg = true;
						coordString[coordCase] = currentCoordString;
						coordCase += 1;
						caseChangeBy += 1;
					}
					else
					{
						currentCase += 2;
						coordCase += 1;
					}
						
				}
				//skips the minutes and seconds.
				if(currentCase == 3)
				{
					currentCase += 4;
					coordCase += 3;
				}
				if(currentCase == 4)
				{
					if(currentString.equalsIgnoreCase("m") || currentString.equalsIgnoreCase("min") || 
							currentString.equalsIgnoreCase("minute") || currentString.equalsIgnoreCase("minutesn") ||
							currentString.equals("'"))
					{
						foundFirstMin = true;
						if(foundFirstDeg == false)
						{
							signed = true;
						}
						if(signed == false)
						{
							System.out.println("Input is invalid 1");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordCase += 1;
						caseChangeBy += 1;
					}
					else
					{
						currentCase += 2;
						coordCase += 1;
					}
				}
				//This is needed when there is only degrees and minutes without seconds, skips the third coordinate
				if(currentCase == 5)
				{
					currentCase += 2;
					coordCase += 2;
				}
				if(currentCase == 6)
				{
					if(currentString.equalsIgnoreCase("s") || currentString.equalsIgnoreCase("sec") || 
							currentString.equalsIgnoreCase("second") || currentString.equalsIgnoreCase("seconds") ||
							currentString.equals("\""))
					{
						if(foundFirstDeg == false && foundFirstMin == false)
						{
							signed = true;
						}
						if(signed == false)
						{
							System.out.println("Input is invalid 2");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordCase += 2;
						caseChangeBy += 1;
					}
					else
					{
						currentCase += 1;
					}
				}
				if(currentCase == 7)
				{
					int directionIndex = 3;
					if(directionFound == true)
					{
						directionIndex = 7;
					}
					else
					{
						directionFound = true;
					}
					//If not signed, this ensures that the third coordinate gets recorded
					if(signed == false)
					{
						coordString[coordCase] = currentCoordString;
						coordCase += 2;
					}
					if(currentString.equals("N") || currentString.equalsIgnoreCase("north"))
					{
						coordString[directionIndex] = "N";
						currentCase += 2;
					}	
					else if(currentString.equals("S") || currentString.equalsIgnoreCase("south"))
					{
						coordString[directionIndex] = "S";
						currentCase += 2;
					}		
					else if(currentString.equals("E") || currentString.equalsIgnoreCase("east"))
					{
						coordString[directionIndex] = "E";
						currentCase += 2;
					}		
					else if(currentString.equals("W") || currentString.equalsIgnoreCase("west"))
					{
						coordString[directionIndex] = "W";
						currentCase += 2;
					}	
					else
					{
							System.out.println("Input is invalid 3" + currentString);
							currentCase = 16;
					}

				}
				if(currentCase == 10)
				{
					if(currentString.equalsIgnoreCase("d") || currentString.equalsIgnoreCase("deg") || 
							currentString.equalsIgnoreCase("degree") || currentString.equalsIgnoreCase("degrees"))
					{
						if(signed == false)
						{
							System.out.println("Input is invalid 5");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordCase += 1;
						caseChangeBy += 1;
					}
					else
					{
						currentCase += 2;
						coordCase += 1;
					}
						
				}
				if(currentCase == 11)
				{
					currentCase += 4;
				}
				if(currentCase == 12)
				{
					if(currentString.equalsIgnoreCase("m") || currentString.equalsIgnoreCase("min") || 
							currentString.equalsIgnoreCase("minute") || currentString.equalsIgnoreCase("minutesn n"))
					{
						foundSecondMin = true;
						if(signed == false)
						{
							System.out.println("Input is invalid 6");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordCase += 1;
						caseChangeBy += 1;
					}
					else
					{
						currentCase += 2;
						coordCase += 1;
					}
				}
				if(currentCase == 13)
				{
					currentCase += 2;
					coordCase += 2;
				}
				if(currentCase == 14)
				{
					if(currentString.equals("s") || currentString.equalsIgnoreCase("sec") || 
							currentString.equalsIgnoreCase("second") || currentString.equalsIgnoreCase("seconds"))
					{
						if(foundSecondDeg == false && foundSecondMin == false)
						{
							signed = true; 
						}
						if(signed == false)
						{
							System.out.println("Input is invalid 7");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordCase += 1;
						caseChangeBy += 1;
						}
					else
					{
						 currentCase += 1;
					}
				}
				if(currentCase == 15)
				{
					if(directionFound == false)
					{
						if(signed == false)
						{
							coordString[coordCase] = currentCoordString;
						}
						if(!coordString[3].equals("N") && !coordString[3].equals("S") && 
								(currentString.equals("N") || currentString.equalsIgnoreCase("north")))
						{
							coordString[7] = "N";
							currentCase += 1;
						}	
						else if(!coordString[3].equals("N") && !coordString[3].equals("S") && 
								(currentString.equals("S") || currentString.equalsIgnoreCase("south")))
						{
							coordString[7] = "S";
							currentCase += 1;
						}		
						else if(!coordString[3].equals("E") &&!coordString[3].equals("W") && 
								(currentString.equals("E") || currentString.equalsIgnoreCase("east")))
						{
							coordString[7] = "E";
							currentCase += 1;
						}		
						else if(!coordString[3].equals("E") && (currentString.equals("W") || currentString.equalsIgnoreCase("west")))
						{
							coordString[7] = "W";
							currentCase += 1;
						}	
						else
						{
							System.out.println("Input is invalid 8");
							currentCase = 16;
						}
					}
				}
			}
			currentCase += caseChangeBy;
			caseChangeBy = 0;
			currentString = "";
			
			if(coordCase < 7 && currentCase == 14)
			{
				coordString[coordCase] = currentCoordString;
				coordCase += 1;
			}
			if(currentCase < 8 && currentIndex >= inputLength)
			{
				System.out.println("Which direction? Options to type: N, S, E, W");
				asterisk = true;
			}
			else if(currentCase < 14 && currentIndex >= inputLength)
			{
				System.out.println("Input is invalid 9");
			}
		}
		for(int i = 0; i < 8; i++)
		{
			System.out.println(coordString[i]);
		}
		scan.close();
	} 
}
