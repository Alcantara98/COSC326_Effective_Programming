import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Anagram {
	// Contains all the words from the input dictionary.
	private static ArrayList<String> wholeDictionary = new ArrayList<String>();

	// Will be updated for the current word, and will contain all words from
	// wholeDictionary that can be formed from the current word.
	private static ArrayList<String> smallDictionary = new ArrayList<String>();
	private static ArrayList<String> words = new ArrayList<String>();

	// Keeps track at which index in the dictionary we got the word
	private static ArrayList<Integer> indexPoints = new ArrayList<Integer>();
	// Keeps track of how many characters it has used at that point
	private static ArrayList<Integer> numberTakenList = new ArrayList<Integer>();
	// Keeping track which characters have been used
	private static ArrayList<int[]> takenChars = new ArrayList<int[]>();
	// Stores the current anagram
	private static ArrayList<String> currentAnagram = new ArrayList<String>();
	private static ArrayList<String> testCurrentAnagram = new ArrayList<String>();

	private static boolean flag = false;
	private static final boolean DEBUGGING = true;
	private static int inputType = 1;
	private static String currentWord;
	private static int wordLength;
	// If the index is 1, then that char in word at that index has been used
	private static int[] currentTaken;
	// Number of characters taken
	private static int numberTaken;

	private static int[] testCurrentTaken;
	private static int testNumberTaken;

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		if (inputType == 1) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input2.txt")));
			try {
				String inputLine = "";
				while ((inputLine = br.readLine()) != null) {
					if (inputLine.isEmpty()) {
						flag = true;
					} else {
						inputLine = inputLine.toLowerCase();

						if (!flag) {
							words.add(inputLine);
						} else {
							wholeDictionary.add(inputLine);
						}
					}

				}
			} finally {
				br.close();
			}
		} else {
			Scanner sc = new Scanner(System.in);
			while (sc.hasNext()) {
				String inputLine = sc.nextLine();
				if (inputLine.isEmpty()) {
					flag = true;
				} else {
					inputLine = inputLine.toLowerCase();

					if (!flag) {
						words.add(inputLine);
					} else {
						wholeDictionary.add(inputLine);
					}
				}
			}
		}

		for (int i = 0; i < words.size(); i++) {
			currentWord = "";
			for (int j = 0; j < words.get(i).length(); j++) {
				char c = words.get(i).charAt(j);
				if (c >= 'a' && c <= 'z') {
					currentWord = currentWord + c;
				}
			}

			wordLength = currentWord.length();
			currentTaken = new int[wordLength];
			numberTaken = 0;

			for (int j = 0; j < wordLength; j++) {
				currentTaken[j] = 0;
			}

			numberTakenList.add(numberTaken);
			takenChars.add(currentTaken.clone());
			indexPoints.add(0);
			// duplicateCount.add(0);

			testCurrentTaken = currentTaken.clone();
			testNumberTaken = numberTaken;

			for (int j = 0; j < wholeDictionary.size(); j++) {

				String dicWord = wholeDictionary.get(j);
				int dicWordLength = dicWord.length();
				boolean wordValid = true;

				if (wordLength >= dicWordLength) {
					for (int x = 0; x < dicWordLength; x++) {
						boolean foundChar = false;
						for (int y = 0; y < wordLength; y++) {
							if (dicWord.charAt(x) == currentWord.charAt(y) && testCurrentTaken[y] == 0) {
								testNumberTaken += 1;
								testCurrentTaken[y] = 1;
								foundChar = true;
								break;
							}
						}
						if (foundChar == false) {
							wordValid = false;
							break;
						}
					}
				} else {
					wordValid = false;
				}
				if (wordValid == true) {
					smallDictionary.add(dicWord);
				}
				testNumberTaken = numberTaken;
				testCurrentTaken = currentTaken.clone();
			}

			if (DEBUGGING)
				System.out.println("Dictionary Size: " + smallDictionary.size());

			for (int j = 0; j < smallDictionary.size(); j++) {
				String dicWord = smallDictionary.get(j);
				int dicWordLength = dicWord.length();

				boolean wordValid = true;
				if (wordLength - testNumberTaken >= dicWordLength) {
					for (int x = 0; x < dicWordLength; x++) {
						boolean foundChar = false;
						for (int y = 0; y < wordLength; y++) {
							if (dicWord.charAt(x) == currentWord.charAt(y) && testCurrentTaken[y] == 0) {
								testNumberTaken += 1;
								testCurrentTaken[y] = 1;
								foundChar = true;
								break;
							}
						}
						if (foundChar == false) {
							wordValid = false;
							testNumberTaken = numberTaken;
							testCurrentTaken = currentTaken.clone();
							break;
						}
					}
				} else {
					wordValid = false;
				}
				if (wordValid == true) {
					numberTaken = testNumberTaken;
					currentTaken = testCurrentTaken.clone();
					numberTakenList.add(numberTaken);
					indexPoints.add(j);
					takenChars.add(currentTaken.clone());
					testCurrentAnagram.add(dicWord);
					j -= 1;
				}

				if (j == smallDictionary.size() - 1) {
					if (DEBUGGING) {
						System.out.println("First");
						System.out.println(j);

						for (int b : indexPoints) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (int b : numberTakenList) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (String b : testCurrentAnagram) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (String b : currentAnagram) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (int[] b : takenChars) {
							for (int c : b)
								System.out.print(c + ", ");
							System.out.print("  ;  ");
						}
						System.out.println("");
						System.out.println("");
					}

					if (numberTaken == wordLength) {
						compareAnagrams();
					}

					if (indexPoints.size() == 1 || indexPoints.get(1) == smallDictionary.size() - 1) {
						String anagram = "";
						for (int k = 0; k < currentAnagram.size(); k++) {
							anagram += " " + currentAnagram.get(k);
						}
						System.out.println(words.get(i) + ":" + anagram);
						break;
					}
					int initial_J = j;

					j = indexPoints.get(indexPoints.size() - 1);
					moveBack();

					if (j == smallDictionary.size() - 1) {
						while (j == initial_J) {
							j = indexPoints.get(indexPoints.size() - 1);
							moveBack();
						}
						if (DEBUGGING) {
							System.out.println("poof");
							System.out.println(j);

							for (int b : indexPoints) {
								System.out.print(b + " ");
							}
							System.out.println("");
							for (int b : numberTakenList) {
								System.out.print(b + " ");
							}
							System.out.println("");
							for (String b : testCurrentAnagram) {
								System.out.print(b + " ");
							}
							System.out.println("");
							for (String b : currentAnagram) {
								System.out.print(b + " ");
							}
							System.out.println("");
							for (int[] b : takenChars) {
								for (int c : b)
									System.out.print(c + ", ");
								System.out.print("  ;  ");
							}
							System.out.println("");
							System.out.println("");
						}

						/*
						 * if (indexPoints.size() > 1) { if (j != initial_J - 1 ||
						 * indexPoints.get(indexPoints.size() - 1) == j) { j =
						 * indexPoints.get(indexPoints.size() - 1); moveBack(); } else { while
						 * (indexPoints.get(indexPoints.size() - 2) == j - 1) { j =
						 * indexPoints.get(indexPoints.size() - 1); moveBack(); } } }
						 */

					}
					if (DEBUGGING) {
						System.out.println("Second");
						System.out.println(j);

						for (int b : indexPoints) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (int b : numberTakenList) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (String b : testCurrentAnagram) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (String b : currentAnagram) {
							System.out.print(b + " ");
						}
						System.out.println("");
						for (int[] b : takenChars) {
							for (int c : b)
								System.out.print(c + ", ");
							System.out.print("  ;  ");
						}
						System.out.println("");
						System.out.println("");
					}
				}
			}
			indexPoints.clear();
			numberTakenList.clear();
			takenChars.clear();
			currentAnagram.clear();
			testCurrentAnagram.clear();
			smallDictionary.clear();
		}

		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println(timeElapsed);
	}

	public static void moveBack() {
		numberTakenList.remove(numberTakenList.size() - 1);
		indexPoints.remove(indexPoints.size() - 1);
		takenChars.remove(takenChars.size() - 1);

		// Removes current word.
		if (testCurrentAnagram.size() != 0) {
			testCurrentAnagram.remove(testCurrentAnagram.size() - 1);
		}

		int[] duplicate = takenChars.get(takenChars.size() - 1);
		currentTaken = duplicate.clone();

		numberTaken = numberTakenList.get(numberTakenList.size() - 1);
		testCurrentTaken = currentTaken.clone();
		testNumberTaken = numberTaken;
	}

	public static void sortCurrentAnagram() {
		int currentAnagramSize = currentAnagram.size();
		for (int k = currentAnagramSize - 2; k >= 0; k--) {
			for (int x = k; x < currentAnagramSize - 1; x++) {
				if (currentAnagram.get(x).length() < currentAnagram.get(x + 1).length()) {
					String duplicate = currentAnagram.get(x);
					currentAnagram.set(x, currentAnagram.get(x + 1));
					currentAnagram.set(x + 1, duplicate);
				} else {
					break;
				}
			}
		}
	}

	public static void compareAnagrams() {
		int currentAnagramSize = currentAnagram.size();
		int testCurrentAnagramSize = testCurrentAnagram.size();

		if (currentAnagram.isEmpty() || currentAnagramSize == 0 || currentAnagramSize > testCurrentAnagramSize) {

			currentAnagram = new ArrayList<String>(testCurrentAnagram);

		} else if (currentAnagramSize == testCurrentAnagramSize) {

			ArrayList<String> currentAnagramOne = new ArrayList<String>(currentAnagram);
			ArrayList<String> testCurrentAnagramOne = new ArrayList<String>(testCurrentAnagram);

			for (int k = currentAnagramSize - 2; k >= 0; k--) {
				for (int x = k; x < testCurrentAnagramOne.size() - 1; x++) {
					if (testCurrentAnagramOne.get(x).length() < testCurrentAnagramOne.get(x + 1).length()) {
						String duplicate = testCurrentAnagramOne.get(x);
						testCurrentAnagramOne.set(x, testCurrentAnagramOne.get(x + 1));
						testCurrentAnagramOne.set(x + 1, duplicate);
					} else {
						break;
					}
				}
				for (int x = k; x < currentAnagramSize - 1; x++) {
					if (currentAnagramOne.get(x).length() < currentAnagramOne.get(x + 1).length()) {
						String duplicate = currentAnagramOne.get(x);
						currentAnagramOne.set(x, currentAnagramOne.get(x + 1));
						currentAnagramOne.set(x + 1, duplicate);
					} else {
						break;
					}
				}
			}

			boolean done = false;

			if (currentAnagramOne.get(0).length() == testCurrentAnagramOne.get(0).length()) {
				for (int k = 0; k < currentAnagramSize - 1; k++) {
					if (testCurrentAnagramOne.get(k).length() > currentAnagramOne.get(k).length()) {
						System.out.println("Swapped One");
						currentAnagram = new ArrayList<String>(testCurrentAnagram);
						done = true;
						break;
					}
				}
			}

			// Sorts anagram in alphabetical order.
			if (!done) {
				for (int k = 0; k < currentAnagramSize - 1; k++) {
					char currentChar = currentAnagramOne.get(k).charAt(0);
					char testChar = testCurrentAnagramOne.get(k).charAt(0);
					if (currentChar < 91) {
						currentChar += 32;
					}
					if (testChar < 91) {
						testChar += 32;
					}
					if (currentChar > testChar) {
						System.out.println("Swapped Two");
						currentAnagram = new ArrayList<String>(testCurrentAnagram);
						break;
					}
				}
			}
		}
	}

}
