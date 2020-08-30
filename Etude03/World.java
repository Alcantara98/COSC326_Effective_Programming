import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * A Class designed to convert a line of String into standard coordinates if the
 * string could be interpreted as such.
 * 
 * Date Finished: 14/08/20 ID: 4435223
 * 
 * @author Elbert Alcantara
 *
 */
public class World {
	private static int inputType = 1;

	private static String coordString[] = new String[8];
	private static String currentString;
	private static String currentCoordString;
	private static String inputCoordinate;
	private static int inputLength;

	// Our index through dumbCoordinate
	private static int currentIndex;

	// We use this when the next case will cause errors
	// so add the increment at the of the current loop case
	private static int caseChangeBy;

	// keeps track of our index in coordString
	private static int coordCase;

	// So we know what kind of case it is
	private static int coordsFound;

	private static boolean lookingForSign;
	private static boolean confusedS;
	private static boolean invalidInput;
	private static boolean signFirst;
	private static boolean direcAtStart;
	private static boolean isLast;
	private static boolean hasComma;

	// So we know if we are expecting N, S, E, W for the second part
	private static boolean directionFound;

	// Coordinates will have deg, min, sec, etc.
	private static boolean signed;

	// We need these info so we know whether to make signed = true if we skipped
	// degrees or minutes
	private static boolean foundFirstDeg;
	private static boolean foundSecondDeg;
	private static boolean foundFirstMin;
	private static boolean foundSecondMin;

	/**
	 * Uses scanner class to take string input from stdin which will be converted by
	 * classifyInput function.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		if (inputType == 1) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("coordinates.txt")));
			try {
				while ((inputCoordinate = br.readLine()) != null) {
					classifyInput();
				}
			} finally {
				br.close();
			}
		} else {
			Scanner scan = new Scanner(System.in);
			while (scan.hasNextLine()) {
				inputCoordinate = scan.nextLine();
				classifyInput();
			}
			scan.close();
		}
	}

	/**
	 * Classifies each section of user input as coordinate, coordinate sign, or
	 * direction. Coordinates based on their sign will be placed in the coordString
	 * array along with the direction of the set of three coordinates.
	 */
	public static void classifyInput() {
		coordString[0] = "0";
		coordString[1] = "0";
		coordString[2] = "0";
		coordString[3] = "";
		coordString[4] = "0";
		coordString[5] = "0";
		coordString[6] = "0";
		coordString[7] = "";

		currentString = "";

		currentCoordString = "0";

		currentIndex = 0;

		caseChangeBy = 0;

		coordCase = 0;

		lookingForSign = false;
		confusedS = false;
		coordsFound = 0;
		invalidInput = false;
		signFirst = false;
		direcAtStart = false;
		isLast = false;
		hasComma = false;
		directionFound = false;
		signed = false;

		foundFirstDeg = false;
		foundSecondDeg = false;
		foundFirstMin = false;
		foundSecondMin = false;

		inputLength = inputCoordinate.length();

		// Cases: 0 Direction, 1 Coord, 2 CoordType, 3 Coord, 4 CoordType, 5 Coord,
		// 6 CoordType, 7 Direction, Comma (not a case), 8 Direction, 9 Coord,
		// 10 CoordType, 11 Coord, 12 CoordType, 13 Coord, 14 CoorType, 15 Direction
		for (int currentCase = 0; currentCase < 16 && currentIndex < inputLength;) {
			boolean isWord = false;
			char currentChar = inputCoordinate.charAt(currentIndex);

			// For handling when a comma is present
			if (currentChar == ',') {
				hasComma = true;
				if ((currentCase == 6 || currentCase == 4 || currentCase == 2) && signed == false) {
					coordString[coordCase] = currentCoordString;
					coordsFound += 1;
				}
				coordCase = 4;
				if (directionFound == true && direcAtStart == true) {
					currentCase = 7;
				} else {
					currentCase = 8;
					if (signFirst) {
						currentCase = 9;
					}
				}
			}

			// Getting Rid of unwanted char characters that may have been accidentally been
			// pressed and taking in alphabets one, and taking in alphabets only.
			while ((currentChar != '\u00B0' && currentChar != '\u2032' && currentChar != '\u2033')
					&& ((currentChar < 48 && currentChar != 45) || (currentChar > 57 && currentChar < 65)
							|| (currentChar > 90 && currentChar < 97) || (currentChar > 122))) {
				currentIndex += 1;

				if (currentIndex < inputLength) {
					currentChar = inputCoordinate.charAt(currentIndex);
				} else {
					if (coordsFound == 0) {
						System.out.println("1");
						invalidInput = true;
					}
					isLast = true;
					break;
				}
			}

			// Builds the current string while its reading letters to differentiate with
			// Coordinates in case there is no space between them
			if (currentChar == '\u00B0' || currentChar == '\u2032' || currentChar == '\u2033'
					|| (currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123)) {
				isWord = true;
				String newString = "";
				while (currentChar == '\u00B0' || currentChar == '\u2032' || currentChar == '\u2033'
						|| (currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123)) {
					newString += currentChar;
					currentIndex += 1;
					if (currentIndex < inputLength) {
						currentChar = inputCoordinate.charAt(currentIndex);
					} else {
						break;
					}
				}
				currentString = newString;
			}

			// Coordinate can only have one decimal point, so we need to keep track of this
			boolean decimal = false;
			String newCoord = "";

			// Builds coordString if it is a number.
			if (isWord == false && ((currentChar > 47 && currentChar < 58) || (currentChar == 46 && decimal == false)
					|| (currentChar == 45 && newCoord.equals("")))) {
				while (isWord == false && ((currentChar > 47 && currentChar < 58)
						|| (currentChar == 46 && decimal == false) || (currentChar == 45 && newCoord.equals("")))) {
					newCoord += currentChar;
					currentIndex += 1;
					if (currentIndex < inputLength) {
						currentChar = inputCoordinate.charAt(currentIndex);
					} else {
						break;
					}
				}

				// If looking for a Direction but instead got a number at the start
				if (signFirst == false) {
					if (currentCase == 0 || currentCase == 8) {
						currentCase += 1;
					} else if (currentCase == 7) {
						currentCase = 9;
						// this part essentially acts as a for loop when the input turns out to just be
						// numbers with no signs or direction
					} else if (currentCase == 2 || currentCase == 4 || currentCase == 6 || currentCase == 10
							|| currentCase == 12) {
						if (confusedS) {
							directionFound = true;
							coordString[0] = coordString[2];
							coordString[2] = "0";
							coordString[3] = "S";
							confusedS = false;
						}
						if (currentCase == 2) {
							foundFirstDeg = true;
						}
						if (currentCase == 6) {
							currentCase += 2;
						}
						if (signed == true) {
							System.out.println("2");
							invalidInput = true;
							currentCase = 16;
						}
						signed = false;
						currentCase += 1;
						coordString[coordCase] = currentCoordString;
						if (coordCase == 0) {
							foundFirstDeg = true;
						} else if (coordCase == 1) {
							foundFirstMin = true;
						} else if (coordCase == 4) {
							foundSecondDeg = true;
						} else if (coordCase == 5) {
							foundSecondMin = true;
						}
						coordsFound += 1;
						currentCoordString = "0";
						coordCase += 1;

						if (coordCase == 3) {
							coordCase += 1;
						}
					}
					currentCase += 1;
				}
				if (signed == true) {
					lookingForSign = true;
				}
				currentCoordString = newCoord;
			}

			// Checks if the direction comes first.
			if (currentCase == 0 && isWord == true) {
				if (currentString.equalsIgnoreCase("N") || currentString.equalsIgnoreCase("north")) {
					coordString[3] = "N";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				} else if (currentString.equalsIgnoreCase("S") || currentString.equalsIgnoreCase("south")) {
					coordString[3] = "S";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				} else if (currentString.equalsIgnoreCase("E") || currentString.equalsIgnoreCase("east")) {
					coordString[3] = "E";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				} else if (currentString.equalsIgnoreCase("W") || currentString.equalsIgnoreCase("west")) {
					coordString[3] = "W";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				}
			}

			// This section deals with the case when coordinate sign comes before the
			// coordinate
			if (currentCase == 0 && isWord && directionFound == false) {
				signFirst = true;
				currentCase += 1;
			}
			if (signFirst && isWord && !currentString.equalsIgnoreCase("N") && !currentString.equalsIgnoreCase("S")
					&& !currentString.equalsIgnoreCase("E") && !currentString.equalsIgnoreCase("W")
					&& (currentCase == 1 || currentCase == 3 || currentCase == 5 || currentCase == 9
							|| currentCase == 11 || currentCase == 13)) {
				isWord = false;
				caseChangeBy = 1;
			} else if (signFirst && isWord == false && signFirst == true && (currentCase == 2 || currentCase == 4
					|| currentCase == 6 || currentCase == 10 || currentCase == 12 || currentCase == 14)) {
				isWord = true;
			}

			// This deals with all the remaining cases up to 16
			if (isWord == true) {
				if (currentCase == 2) {
					if (currentString.equalsIgnoreCase("d") || currentString.equalsIgnoreCase("deg")
							|| currentString.equalsIgnoreCase("degree") || currentString.equalsIgnoreCase("degrees")
							|| currentString.equals("\u00B0")) {
						signed = true;
						foundFirstDeg = true;
						coordString[coordCase] = currentCoordString;
						coordsFound += 1;
						coordCase += 1;
						caseChangeBy += 1;
					} else {
						currentCase += 2;
						coordCase += 1;
					}
				}
				// skips the minutes and seconds.
				if (currentCase == 3) {
					currentCase += 4;
					coordCase += 3;
				}
				if (currentCase == 4) {
					if (currentString.equalsIgnoreCase("m") || currentString.equalsIgnoreCase("min")
							|| currentString.equalsIgnoreCase("minute") || currentString.equalsIgnoreCase("minutes")
							|| currentString.equals("\u2032")) {
						foundFirstMin = true;
						if (foundFirstDeg == false) {
							signed = true;
						}
						if (signed == false) {
							System.out.println("3");
							invalidInput = true;
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						lookingForSign = false;
						coordsFound += 1;
						coordCase += 1;
						caseChangeBy += 1;
					} else {
						currentCase += 2;
						coordCase += 1;
					}
				}
				// This is needed when there is only degrees and minutes without seconds, skips
				// the third coordinate.
				if (currentCase == 5) {
					currentCase += 2;
					coordCase += 2;
				}
				if (currentCase == 6) {
					if (currentString.equalsIgnoreCase("s") || currentString.equalsIgnoreCase("sec")
							|| currentString.equalsIgnoreCase("second") || currentString.equalsIgnoreCase("seconds")
							|| currentString.equals("\u2033")) {
						if (foundFirstDeg == false && foundFirstMin == false) {
							signed = true;
							if (directionFound == false && currentString.equalsIgnoreCase("s")) {
								confusedS = true;
								signed = false;
							}
						}
						// Because if s or S, then it is probably a direction, so we move to next case.
						if (signed == false && !currentString.equalsIgnoreCase("s")) {
							System.out.println("4");
							invalidInput = true;
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						lookingForSign = false;
						coordsFound += 1;
						coordCase += 2;
						if (signed == false) {
							currentCase += 1;
						} else {
							caseChangeBy += 1;
						}
					} else {
						currentCase += 1;
					}
				}
				if (currentCase == 7) {
					if (lookingForSign == true) {
						System.out.println("5");
						invalidInput = true;
					} else {
						int directionIndex = 3;
						if (directionFound == true) {
							directionIndex = 7;
						} else {
							directionFound = true;
						}
						if (!coordString[3].equalsIgnoreCase("N") && !coordString[3].equals("S")
								&& currentString.equalsIgnoreCase("N") || currentString.equalsIgnoreCase("north")) {
							coordString[directionIndex] = "N";
							currentCase += 2;
						} else if (!coordString[3].equalsIgnoreCase("N") && !coordString[3].equals("S")
								&& currentString.equalsIgnoreCase("S") || currentString.equalsIgnoreCase("south")) {
							coordString[directionIndex] = "S";
							currentCase += 2;
						} else if (!coordString[3].equalsIgnoreCase("E") && !coordString[3].equals("W")
								&& currentString.equalsIgnoreCase("E") || currentString.equalsIgnoreCase("east")) {
							coordString[directionIndex] = "E";
							currentCase += 2;
						} else if (!coordString[3].equalsIgnoreCase("E") && !coordString[3].equals("W")
								&& currentString.equalsIgnoreCase("W") || currentString.equalsIgnoreCase("west")) {
							coordString[directionIndex] = "W";
							currentCase += 2;
						} else {
							System.out.println("6");
							invalidInput = true;
							currentCase = 16;
						}
						if (confusedS && directionFound) {
							confusedS = false;
							signed = true;
						}
						// If not signed, this ensures that the third coordinate gets recorded
						if (signed == false) {
							if (foundFirstDeg == false && foundFirstMin == false) {
								coordString[0] = currentCoordString;
							} else {
								coordString[coordCase] = currentCoordString;
							}
							coordsFound += 1;
							coordCase = 4;
						}
					}
				}
				if (currentCase == 10) {
					if (currentString.equalsIgnoreCase("d") || currentString.equalsIgnoreCase("deg")
							|| currentString.equalsIgnoreCase("degree") || currentString.equalsIgnoreCase("degrees")
							|| currentString.equals("\u00B0")) {
						foundSecondDeg = true;
						if (confusedS) {
							signed = true;
							confusedS = false;
						}
						if (signed == false) {
							System.out.println("7");
							invalidInput = true;
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						lookingForSign = false;
						coordsFound += 1;
						coordCase += 1;
						caseChangeBy += 1;
					} else {
						currentCase += 2;
						coordCase += 1;
					}

				}
				if (currentCase == 11) {
					currentCase += 4;
				}
				if (currentCase == 12) {
					if (currentString.equalsIgnoreCase("m") || currentString.equalsIgnoreCase("min")
							|| currentString.equalsIgnoreCase("minute") || currentString.equalsIgnoreCase("minutes")
							|| currentString.equals("\u2032")) {
						foundSecondMin = true;
						if (confusedS) {
							signed = true;
							confusedS = false;
						}
						if (signed == false) {
							System.out.println("8");
							invalidInput = true;
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						lookingForSign = false;
						coordsFound += 1;
						coordCase += 1;
						caseChangeBy += 1;
					} else {
						currentCase += 2;
						coordCase += 1;
					}
				}
				if (currentCase == 13) {
					currentCase += 2;
					coordCase += 2;
				}
				if (currentCase == 14) {
					if (currentString.equalsIgnoreCase("s") || currentString.equalsIgnoreCase("sec")
							|| currentString.equals("\u2033") || currentString.equalsIgnoreCase("second")
							|| currentString.equalsIgnoreCase("seconds")) {
						if (foundSecondDeg == false && foundSecondMin == false) {
							signed = true;
							confusedS = false;
						}
						if (signed == false) {
							System.out.println("9");
							invalidInput = true;
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						lookingForSign = false;
						coordsFound += 1;
						coordCase += 1;
						caseChangeBy += 1;
					} else {
						currentCase += 1;
					}
				}
				if (currentCase == 15) {
					if (lookingForSign == true) {
						System.out.println("10 " + currentString + " " + currentCoordString);
						invalidInput = true;
					} else {
						if (confusedS) {
							confusedS = false;
							directionFound = true;
							coordString[0] = coordString[2];
							coordString[2] = "0";
							coordString[3] = "S";
						}
						if (directionFound == true) {
							if (signed == false) {
								if (foundSecondDeg == false && foundSecondMin == false) {
									coordString[4] = currentCoordString;
								} else {
									coordString[coordCase] = currentCoordString;
								}
								coordsFound += 1;
							}
							if (!coordString[3].equalsIgnoreCase("N") && !coordString[3].equals("S")
									&& (currentString.equalsIgnoreCase("N")
											|| currentString.equalsIgnoreCase("north"))) {
								coordString[7] = "N";
								currentCase += 1;
							} else if (!coordString[3].equalsIgnoreCase("N") && !coordString[3].equals("S")
									&& (currentString.equalsIgnoreCase("S")
											|| currentString.equalsIgnoreCase("south"))) {
								coordString[7] = "S";
								currentCase += 1;
							} else if (!coordString[3].equalsIgnoreCase("E") && !coordString[3].equals("W")
									&& (currentString.equalsIgnoreCase("E")
											|| currentString.equalsIgnoreCase("east"))) {
								coordString[7] = "E";
								currentCase += 1;
							} else if (!coordString[3].equalsIgnoreCase("E") && (currentString.equalsIgnoreCase("W")
									|| currentString.equalsIgnoreCase("west"))) {
								coordString[7] = "W";
								currentCase += 1;
							} else {
								System.out.println("11");
								invalidInput = true;
								currentCase = 16;
							}
						}
					}
				}
			}
			currentCase += caseChangeBy;
			caseChangeBy = 0;

			// This ensures that we record the last coordinate
			if (coordCase < 7 && (isLast || currentIndex >= inputLength)
					&& (currentCase == 2 || currentCase == 4 | currentCase == 6 || currentCase == 10
							|| currentCase == 12 || currentCase == 14)) {
				coordString[coordCase] = currentCoordString;
				if (coordsFound == 0) {
					System.out.println("12");
					invalidInput = true;
				}
				coordsFound += 1;
				coordCase += 1;
				if (currentCase == 6) {
					coordCase += 1;
				}
			}

			// This deals with the case when there are only two numbers with no signs or
			// direction
			if (coordsFound == 2 && coordString[1] != "0" && hasComma == false && !signed
					&& (isLast || currentIndex >= inputLength) && !directionFound) {
				coordString[4] = coordString[1];
				coordString[1] = "0";
			}

			// This deals with the case when there is no comma and there are 3 digits
			if (currentCase == 6 && currentIndex >= inputLength) {
				System.out.println("Which direction? Options to type: N, S, E, W");
				// Scanner scan2 = new Scanner(System.in);
				String userInput = "N";
				if (userInput.equalsIgnoreCase("N") || userInput.equalsIgnoreCase("S")) {
					coordString[3] = userInput;
					coordString[7] = "E";
				} else if (userInput.equalsIgnoreCase("E") || userInput.equalsIgnoreCase("W")) {
					coordString[3] = userInput;
					coordString[7] = "N";
				}
			} else if (currentCase < 14 && currentCase > 9 && currentIndex >= inputLength && hasComma == false) {
				System.out.println("13");
				invalidInput = true;
			}

			// This is to ensure that if there extra are char after we have found the
			// coordinates, input is invalid
			if (currentCase >= 16) {
				while (currentIndex < inputLength) {
					if (inputCoordinate.charAt(currentIndex) != ' ') {
						System.out.println("14");
						invalidInput = true;
						break;
					}
					currentIndex += 1;
				}
			}
		}
		if(!directionFound) {
			coordString[3] = "N";
			coordString[7] = "E";
		}

		if (invalidInput) {
			System.out.println("Unable to process: " + inputCoordinate);
		}

		for (String a : coordString) {
			System.out.println(a);
		}

		convertStandard(coordString);
	}

	/**
	 * Takes an array in the format of coordString array in the class' data field
	 * and converts it into standard coordinates of two numbers representing degrees
	 * in directions N and E.
	 * 
	 * @param coordinates
	 */
	public static void convertStandard(String[] coordinates) {
		// converting coordinates into float for use in calculations
		float deg1 = Float.parseFloat(coordinates[0]);
		float min1 = Float.parseFloat(coordinates[1]);
		float sec1 = Float.parseFloat(coordinates[2]);
		float deg2 = Float.parseFloat(coordinates[4]);
		float min2 = Float.parseFloat(coordinates[5]);
		float sec2 = Float.parseFloat(coordinates[6]);

		if (min1 > 60 || sec1 > 60 || min2 > 60 || sec2 > 60) {
			System.out.println("Unable to process: " + inputCoordinate);
		} else {
			// These lines converts the three coordinates (deg, min, sec) into just
			// degrees.
			float[] floatCoord = new float[2];
			if (coordinates[3].equalsIgnoreCase("N") || coordinates[7].equalsIgnoreCase("S")) {
				floatCoord[0] = deg1 + (min1 + sec1 / 60) / 60;
				floatCoord[1] = deg2 + (min2 + sec2 / 60) / 60;
			} else {
				floatCoord[0] = deg2 + (min2 + sec2 / 60) / 60;
				floatCoord[1] = deg1 + (min1 + sec1 / 60) / 60;
			}
			System.out.println(directionFound);
			if (Math.abs(floatCoord[1]) > 90 || (directionFound && (floatCoord[0] < 0 || floatCoord[1] < 0))) {
				System.out.println("Unable to process: " + inputCoordinate);
			} else {

				if (Math.abs(floatCoord[0]) > 180) {
					floatCoord[0] = floatCoord[0] % 180;
				}
				if (coordinates[3].equalsIgnoreCase("S") || coordinates[3].equalsIgnoreCase("W")) {
					floatCoord[0] = floatCoord[0] * -1;
				}
				if (coordinates[7].equalsIgnoreCase("S") || coordinates[7].equalsIgnoreCase("W")) {
					floatCoord[1] = floatCoord[1] * -1;
				}

				String[] stringCoord = new String[2];
				stringCoord[0] = Float.toString(floatCoord[0]);
				stringCoord[1] = Float.toString(floatCoord[1]);

				// ensuring that the output has 6 decimal places in all instances
				for (int i = 0; i < 2; i++) {
					String coord = stringCoord[i];
					String newCoord = "";
					boolean foundPoint = false;
					int decimalCount = 0;

					for (int j = 0; j < coord.length() && decimalCount < 7; j++) {
						newCoord += coord.charAt(j);
						if (foundPoint) {
							decimalCount += 1;
						}
						if (coord.charAt(j) == '.') {
							foundPoint = true;
						}
					}
					if (decimalCount < 6) {
						while (decimalCount != 6) {
							newCoord += "0";
							decimalCount += 1;
						}
					}
					stringCoord[i] = newCoord;
				}

				// Final output if program was able to deduce coordinates out of the input
				// string
				if (!invalidInput) {
					System.out.println(stringCoord[0] + ", " + stringCoord[1]);
				}
			}
		}
	}
}
