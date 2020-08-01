import java.util.*;

public class World {
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		String dumbCoordinate = scan.nextLine();
		
		String coordString[] = new String[6];
		String tempCoord[] = new String[3];
		
		for(int i = 0; i < 6; i++)
		{
			coordString[i] = "0";
		}
		
		int currentCoord = 0;
		
		String currentString = "";
		
		boolean isWord = false;
		boolean isNumber = false;
		
		for(int i = 0; i < 17; i++)
		{
			char currentChar = dumbCoordinate.charAt(currentCoord);
			while((currentChar < 48) || (currentChar > 57 && currentChar < 65) 
					|| (currentChar > 90 && currentChar < 97) || (currentChar > 122))
			{
				currentCoord += 1;
				currentChar = dumbCoordinate.charAt(currentCoord);
			}
			while((currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123)) 
			{
				currentString += dumbCoordinate.charAt(currentCoord);
				isWord = true;
				currentCoord += 1;
				currentChar = dumbCoordinate.charAt(currentCoord);
				
			}
			while(isWord == false && (currentChar == 46 || (currentChar > 47 && currentChar >)))
			{
				
			}
				
				
			if(i == 0 || i == 4 || i == 5 || i == 9)
			{
				
			}
			 
		}
		
		scan.close();
	} 
}
