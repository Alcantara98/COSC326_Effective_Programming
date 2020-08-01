import java.util.*;

public class World {
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		String dumbCoordinate = scan.nextLine();
		
		String coordString[] = {"0", "0", "0", "N", "0", "0", "0", "E"};
		
		int inputLength = dumbCoordinate.length();
		
		int currentCoord = 0;
		
		//For knowing if we have found the direction already, otherwise
		//to ensure we don't have conflicting directions.
		boolean directionFound = false;
		
		String currentString = "";
		String currentCoordString = "0";
		
		int coordCase = 0;
		boolean signed = true;
		for(int currentCase = 0; currentCase < 16 && currentCoord < inputLength;)
		{
			boolean isWord = false;
			char currentChar = dumbCoordinate.charAt(currentCoord);
			
			if(currentChar == ',')
			{
				currentCase = 8;
				coordCase = 4;
				currentCoord += 1;
				
				if(currentCoord < inputLength)
				{
					currentChar = dumbCoordinate.charAt(currentCoord);
				}
				else 
				{
					break;
				}
			}
			//Getting Rid of unwanted char characters that may have been accidentally been pressed
			//and taking in alphabets only.
			//and taking in alphabets only.
			while((currentChar < 48) || (currentChar > 57 && currentChar < 65) 
					|| (currentChar > 90 && currentChar < 97) || (currentChar > 122))
			{
				currentCoord += 1;
				
				if(currentCoord < inputLength)
				{
					currentChar = dumbCoordinate.charAt(currentCoord);
				}
				else 
				{
					coordString[coordCase] = currentCoordString;
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
					currentCoord += 1;
					
					if(currentCoord < inputLength)
					{
						currentChar = dumbCoordinate.charAt(currentCoord);
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
					currentCoord += 1;
					
					if(currentCoord < inputLength)
					{
						currentChar = dumbCoordinate.charAt(currentCoord);
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
				else if(currentCase == 2 || currentCase == 4 || currentCase == 6 || 
						currentCase == 10 || currentCase == 12)
				{		
					if(currentCase == 6)
					{
						currentCase += 2;
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
				
				if(currentCoord == inputLength && currentCase == 14)
				{
					coordString[coordCase] = currentCoordString;
				}
			} 
			int caseChangeBy = 0;
			if(isWord == true)
			{
				if(currentCase == 0)
				{
					if((isStringEqual(currentString, "n") || isStringEqual(currentString, "north")))
					{
						coordString[3] = "N";
						directionFound = true;
					}	
					if((isStringEqual(currentString, "s") || isStringEqual(currentString, "south")))
					{
						coordString[3] = "S";
						directionFound = true;
					}		
					if((isStringEqual(currentString, "e") || isStringEqual(currentString, "east")))
					{
						coordString[3] = "E";
						directionFound = true;
					}		
					if((isStringEqual(currentString, "w") || isStringEqual(currentString, "west")))
					{
						coordString[3] = "W";
						directionFound = true;
					}		
				}
				if(currentCase == 6)
				{
					if(isStringEqual(currentString, "d") || isStringEqual(currentString, "sec") ||
					   isStringEqual(currentString, "second") || isStringEqual(currentString, "seconds")
					   || isStringEqual(currentString, "\""))
					{
						if(signed == false)
						{
							System.out.println("Input is invalid 1");
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
					if(directionFound == false)
					{
						if(signed == false)
						{
							coordString[coordCase] = currentCoordString;
							coordCase += 2;
						}
						if(isStringEqual(currentString, "n") || isStringEqual(currentString, "north"))
						{
							coordString[3] = "N";
							currentCase += 2;
						}	
						else if(isStringEqual(currentString, "s") || isStringEqual(currentString, "south"))
						{
							coordString[3] = "S";
							currentCase += 2;
						}		
						else if(isStringEqual(currentString, "e") || isStringEqual(currentString, "east"))
						{
							coordString[3] = "E";
							currentCase += 2;
						}		
						else if(isStringEqual(currentString, "w") || isStringEqual(currentString, "west"))
						{
							coordString[3] = "W";
							currentCase += 2;
						}	
						else
						{
							System.out.println("Input is invalid");
							currentCase = 16;
						}
					}
					else if(directionFound == true)
					{
						if((isStringEqual(currentString, "n") || isStringEqual(currentString, "north")))
						{
							coordString[7] = "N";
							currentCase += 2;
						}	
						else if((isStringEqual(currentString, "s") || isStringEqual(currentString, "south")))
						{
							coordString[7] = "S";
							currentCase += 2;
						}		
						else if((isStringEqual(currentString, "e") || isStringEqual(currentString, "east")))
						{
							coordString[7] = "E";
							currentCase += 2;
						}		
						else if((isStringEqual(currentString, "w") || isStringEqual(currentString, "west")))
						{
							coordString[7] = "W";
							currentCase += 2;
						}
						else
						{
							System.out.println("Input is invalid");
							currentCase = 16;
						}
					}
				}
				
				if(currentCase == 14)
				{
					if(isStringEqual(currentString, "s") || isStringEqual(currentString, "sec") ||
							   isStringEqual(currentString, "second") || isStringEqual(currentString, "seconds")
							   || isStringEqual(currentString, "\""))
							{
								if(signed == false)
								{
									System.out.println("Input is invalid");
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
			}
			currentCase += caseChangeBy;
			caseChangeBy = 0;
			currentString = "";
		}
		for(int i = 0; i < 8; i++)
		{
			System.out.println(coordString[i]);
		}
		System.out.println("\"");
		scan.close();
	} 
	
	//Function to check if two strings are equal disregarding whether the 
	//char is capitalized or not.
	public static boolean isStringEqual(String one, String two)
	{
		if(one.length() != two.length())
		{
			return false;
		}
		for(int i = 0; i < one.length(); i++)
		{
			int charOne = one.charAt(i);
			int charTwo = two.charAt(i);
			
			if(charOne < 97)
			{
				charOne += 32;
			}
			if(charTwo < 97)
			{
				charTwo += 32;
			}
			
			if(charOne != charTwo)
			{
				return false;
			}
		}
		return true;
	}
}
