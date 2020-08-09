import java.util.Scanner;

public class World {
	private static String coordString[] = { "0", "0", "0", "N", "0", "0", "0", "E" };

	private static String currentString = "";

	private static String currentCoordString = "0";

	private static Scanner scan;

	private static String dumbCoordinate;

	private static int inputLength;

	// Our index through dumbCoordinate
	private static int currentIndex = 0;

	// We use this when the next case will cause errors
	// so add the increment at the of the current loop case
	private static int caseChangeBy = 0;

	// keeps track of our index in coordString
	private static int coordCase = 0;

	// So we know what kind of case it is
	private static int coordsFound = 0;

	private static boolean signFirst = false;
	private static boolean direcAtStart = false;
	private static boolean isLast = false;
	private static boolean hasComma = false;
	private static boolean directionFound = false; // So we know if we are expecting N, S, E, W for the second part
	private static boolean signed = false; // Coordinates will have deg, min, sec, etc.

	// We need these info so we know whether to make signed = true if we skipped
	// degrees or minutes
	private static boolean foundFirstDeg = false;
	private static boolean foundSecondDeg = false;
	private static boolean foundFirstMin = false;
	private static boolean foundSecondMin = false;

	// If true, we ask if our interpretation is what they meant
	private static boolean asterisk = false;

	public static void main(String args[]) {
		scan = new Scanner(System.in);

		dumbCoordinate = scan.nextLine();

		inputLength = dumbCoordinate.length();

		for (int currentCase = 0; currentCase < 16 && currentIndex < inputLength;) {
			boolean isWord = false;
			char currentChar = dumbCoordinate.charAt(currentIndex);

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
			// pressed
			// and taking in alphabets only.
			// and taking in alphabets only.
			while ((currentChar < 48 && currentChar != 45) || (currentChar > 57 && currentChar < 65)
					|| (currentChar > 90 && currentChar < 97) || (currentChar > 122)) {
				if (currentChar != ' ' || currentChar != ',') {
					asterisk = true;
				}
				currentIndex += 1;

				if (currentIndex < inputLength) {
					currentChar = dumbCoordinate.charAt(currentIndex);
				} else {
					isLast = true;
					break;
				}
			}

			// Builds the current string while its reading letters to differentiate with
			// Coordinates in case there is no space between them
			if ((currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123)) {
				isWord = true;
				String newString = "";
				while ((currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123)) {
					newString += currentChar;
					currentIndex += 1;

					if (currentIndex < inputLength) {
						currentChar = dumbCoordinate.charAt(currentIndex);
					} else {
						break;
					}
				}
				currentString = newString;
			}

			// Coordinate can only have one decimal point, so we need to keep track of this
			boolean decimal = false;
			String newCoord = "";
			// Builds the string if it is a number.
			if (isWord == false && ((currentChar > 47 && currentChar < 58) || (currentChar == 46 && decimal == false)
					|| (currentChar == 45 && newCoord.equals("")))) {
				while (isWord == false && ((currentChar > 47 && currentChar < 58)
						|| (currentChar == 46 && decimal == false) || (currentChar == 45 && newCoord.equals("")))) {
					newCoord += currentChar;
					currentIndex += 1;

					if (currentIndex < inputLength) {
						currentChar = dumbCoordinate.charAt(currentIndex);
					} else {
						break;
					}
				}

				// If looking for a Direction but instead got a number at the start
				if (signFirst == false) {
					if (currentCase == 0 || currentCase == 8) {
						currentCase += 1;
					} else if (currentCase == 7) {
						currentCase = 10;
					} else if (currentCase == 2 || currentCase == 4 || currentCase == 6 || currentCase == 10
							|| currentCase == 12) {
						if (currentCase == 2) {
							foundFirstDeg = true;
						}
						if (currentCase == 6) {
							currentCase += 2;
						}
						if (signed == true) {
							System.out.println("Input is invalid 0");
							currentCase = 16;
						}
						signed = false;
						currentCase += 1;
						coordString[coordCase] = currentCoordString;
						coordsFound += 1;
						currentCoordString = "0";
						coordCase += 1;

						if (coordCase == 3) {
							coordCase += 1;
						}
					}
					currentCase += 1;
				}
				currentCoordString = newCoord;
			}

			if (currentCase == 0 && isWord == true) {
				if (currentString.equals("N") || currentString.equalsIgnoreCase("north")) {
					coordString[3] = "N";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				} else if (currentString.equals("S") || currentString.equalsIgnoreCase("south")) {
					coordString[3] = "S";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				} else if (currentString.equals("E") || currentString.equalsIgnoreCase("east")) {
					coordString[3] = "E";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				} else if (currentString.equals("W") || currentString.equalsIgnoreCase("west")) {
					coordString[3] = "W";
					directionFound = true;
					direcAtStart = true;
					caseChangeBy = 1;
				}
			}
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
			// Cases: 0 Direction, 1 Coord, 2 CoordType, 3 Coord, 4 CoordType, 5 Coord,
			// 6 CoordType, 7 Direction, Comma (not a case), 8 Direction, 9 Coord,
			// 10 CoordType, 11 Coord, 12 CoordType, 13 Coord, 14 CoorType, 15 Direction
			if (isWord == true) {
				if (currentCase == 2) {
					if (currentString.equalsIgnoreCase("d") || currentString.equalsIgnoreCase("deg")
							|| currentString.equalsIgnoreCase("degree") || currentString.equalsIgnoreCase("degrees")
							|| currentString.equals("°")) {
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
							|| currentString.equals("'")) {
						foundFirstMin = true;
						if (foundFirstDeg == false) {
							signed = true;
						}
						if (signed == false) {
							System.out.println("Input is invalid 1");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordsFound += 1;
						coordCase += 1;
						caseChangeBy += 1;
					} else {
						currentCase += 2;
						coordCase += 1;
					}
				}
				// This is needed when there is only degrees and minutes without seconds, skips
				// the third coordinate
				if (currentCase == 5) {
					currentCase += 2;
					coordCase += 2;
				}
				if (currentCase == 6) {
					if (currentString.equalsIgnoreCase("s") || currentString.equalsIgnoreCase("sec")
							|| currentString.equalsIgnoreCase("second") || currentString.equalsIgnoreCase("seconds")
							|| currentString.equals("\"")) {
						if (foundFirstDeg == false && foundFirstMin == false) {
							signed = true;
						}
						if (signed == false && !currentString.equalsIgnoreCase("s")) {
							System.out.println("Input is invalid 2" + currentString);
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordsFound += 1;
						coordCase += 2;
						caseChangeBy += 1;
					} else {
						currentCase += 1;
					}
				}
				if (currentCase == 7) {
					int directionIndex = 3;
					if (directionFound == true) {
						directionIndex = 7;
					} else {
						directionFound = true;
					}
					// If not signed, this ensures that the third coordinate gets recorded
					if (signed == false) {
						if (foundFirstDeg == false && foundFirstMin == false) {
							coordString[0] = currentCoordString;
						} else {
							coordString[coordCase] = currentCoordString;
						}
						coordsFound += 1;
						coordCase += 2;
					}
					if (currentString.equals("N") || currentString.equalsIgnoreCase("north")) {
						coordString[directionIndex] = "N";
						currentCase += 2;
					} else if (currentString.equals("S") || currentString.equalsIgnoreCase("south")) {
						coordString[directionIndex] = "S";
						currentCase += 2;
					} else if (currentString.equals("E") || currentString.equalsIgnoreCase("east")) {
						coordString[directionIndex] = "E";
						currentCase += 2;
					} else if (currentString.equals("W") || currentString.equalsIgnoreCase("west")) {
						coordString[directionIndex] = "W";
						currentCase += 2;
					} else {
						System.out.println("Input is invalid 3" + currentString + currentCase);
						currentCase = 16;
					}
				}
				if (currentCase == 10) {
					if (currentString.equalsIgnoreCase("d") || currentString.equalsIgnoreCase("deg")
							|| currentString.equalsIgnoreCase("degree") || currentString.equalsIgnoreCase("degrees")) {
						if (signed == false) {
							System.out.println("Input is invalid 5");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
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
							|| currentString.equalsIgnoreCase("minute") || currentString.equalsIgnoreCase("minutes")) {
						foundSecondMin = true;
						if (signed == false) {
							System.out.println("Input is invalid 6");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
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
					if (currentString.equals("s") || currentString.equalsIgnoreCase("sec")
							|| currentString.equalsIgnoreCase("second") || currentString.equalsIgnoreCase("seconds")) {
						if (foundSecondDeg == false && foundSecondMin == false) {
							signed = true;
						}
						if (signed == false) {
							System.out.println("Input is invalid 7");
							currentCase = 16;
						}
						coordString[coordCase] = currentCoordString;
						coordsFound += 1;
						coordCase += 1;
						caseChangeBy += 1;
					} else {
						currentCase += 1;
					}
				}
				if (currentCase == 15) {
					if (directionFound == true) {
						if (signed == false) {
							if (foundSecondDeg == false && foundSecondMin == false) {
								coordString[4] = currentCoordString;
							} else {
								coordString[coordCase] = currentCoordString;
							}
							coordsFound += 1;
						}
						if (!coordString[3].equals("N") && !coordString[3].equals("S")
								&& (currentString.equals("N") || currentString.equalsIgnoreCase("north"))) {
							coordString[7] = "N";
							currentCase += 1;
						} else if (!coordString[3].equals("N") && !coordString[3].equals("S")
								&& (currentString.equals("S") || currentString.equalsIgnoreCase("south"))) {
							coordString[7] = "S";
							currentCase += 1;
						} else if (!coordString[3].equals("E") && !coordString[3].equals("W")
								&& (currentString.equals("E") || currentString.equalsIgnoreCase("east"))) {
							coordString[7] = "E";
							currentCase += 1;
						} else if (!coordString[3].equals("E")
								&& (currentString.equals("W") || currentString.equalsIgnoreCase("west"))) {
							coordString[7] = "W";
							currentCase += 1;
						} else {
							System.out.println("Input is invalid 8");
							currentCase = 16;
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
				coordsFound += 1;
				coordCase += 1;
				if (currentCase == 6) {
					coordCase += 1;
				}
			}
			if (coordsFound == 2 && coordString[1] != "0" && hasComma == false && !signed
					&& (isLast || currentIndex >= inputLength) && !directionFound) {
				coordString[4] = coordString[1];
				coordString[1] = "0";
			}

			if (currentCase == 6 && currentIndex >= inputLength) {
				System.out.println("Which direction? Options to type: N, S, E, W");
				Scanner scan2 = new Scanner(System.in);
				String userInput = scan2.nextLine();
				if (userInput.equalsIgnoreCase("N") || userInput.equalsIgnoreCase("S")) {
					coordString[3] = userInput;
					coordString[7] = "E";
				} else if (userInput.equalsIgnoreCase("E") || userInput.equalsIgnoreCase("W")) {
					coordString[3] = userInput;
					coordString[7] = "N";
				}
				asterisk = true;
			} else if (currentCase < 14 && currentCase > 9 && currentIndex >= inputLength && hasComma == false) {
				System.out.println("Input is invalid 9");
			}

			// This is to ensure that if there extra are char after we have found the
			// coordinates, input is invalid
			if (currentCase >= 16) {
				while (currentIndex < inputLength) {
					if (dumbCoordinate.charAt(currentIndex) != ' ') {
						System.out.println("Input is invalid 10");
						break;
					}
					currentIndex += 1;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			System.out.println(coordString[i]);
		}
		scan.close();

		convertStandard(coordString);
	}

	public static void convertStandard(String[] coordinates) {
		float deg1 = Float.parseFloat(coordinates[0]);
		float min1 = Float.parseFloat(coordinates[1]);
		float sec1 = Float.parseFloat(coordinates[2]);
		float deg2 = Float.parseFloat(coordinates[4]);
		float min2 = Float.parseFloat(coordinates[5]);
		float sec2 = Float.parseFloat(coordinates[6]);

		float firstCoord = deg1 + (min1 + sec1 / 60) / 60;
		float secondCoord = deg2 + (min2 + sec2 / 60) / 60;

		float[] floatCoord = new float[2];
		floatCoord[0] = firstCoord % 360;
		floatCoord[1] = secondCoord % 360;

		for (int i = 0; i < 2; i++) {
			if (floatCoord[i] > 180) {
				floatCoord[i] = floatCoord[i] - 360;
			}
			if ((i == 0 && (coordString[3] == "W" || coordString[3] == "S"))
					|| (i == 1 && (coordString[7] == "W" || coordString[7] == "S"))) {
				floatCoord[i] = -1 * floatCoord[i];
			}
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

		if (coordinates[3].equalsIgnoreCase("N") || coordinates[3].equalsIgnoreCase("S")) {
			System.out.println(stringCoord[0] + ", " + stringCoord[1]);
		} else {
			System.out.println(stringCoord[1] + ", " + stringCoord[0]);
		}
	}
}
