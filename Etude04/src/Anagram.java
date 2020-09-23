import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An Application that finds the best Anagram.
 * 
 * Conditions for best Anagram:
 * 
 * • Anagrams using fewer words are better than ones using more words.
 * 
 * • If two anagrams use the same number of words, they should first be compared
 * by word length — if the longest words differ in length, then the one with a
 * longer first word is better. If the longest words are equal in length but the
 * second longest words differ in length then the one with a longer second
 * longest word is better. And so on.
 * 
 * • If two anagrams have the same number of words, and corresponding words have
 * the same length, then the earlier one in alphabetical order is better
 * 
 * @author Elbert Alcantara
 * @author Minh Tran
 */
public class Anagram {
	// Contains all the words from the input dictionary.
	private static ArrayList<String> wholeDictionary = new ArrayList<String>();

	// will contain all words from wholeDictionary that can be formed from the
	// current word.
	private static ArrayList<String> smallDictionary = new ArrayList<String>();

	// Contains all words from input.
	private static ArrayList<String> words = new ArrayList<String>();

	// Keeps track at which index in the dictionary we got the word.
	private static ArrayList<Integer> indexPoints = new ArrayList<Integer>();

	// Keeps track of how many characters it has used at that point.
	private static ArrayList<Integer> numberTakenList = new ArrayList<Integer>();

	// Keeping track which characters have been used.
	private static ArrayList<int[]> takenChars = new ArrayList<int[]>();

	// Stores the current best Anagram.
	private static ArrayList<String> currentAnagram = new ArrayList<String>();

	// This is where we place words as we create an new Anagram to compare against
	// current best.
	private static ArrayList<String> testCurrentAnagram = new ArrayList<String>();

	// This will contain dictionaries for each layer.
	private static ArrayList<ArrayList<String>> dicArray = new ArrayList<ArrayList<String>>();

	private static int bestSize = Integer.MAX_VALUE;
	private static int dicSize;
	private static boolean flag = false;
	private static int inputType = 0;
	private static String currentWord;
	private static int wordLength;

	// If the index is 1, then that char in word at that index has been used
	private static int[] currentTaken;

	// Number of characters taken
	private static int numberTaken;

	/*
	 * These test duplicates aren't necessary but will kept us from using .get(x)
	 * from array lists which may take longer.
	 */
	private static int[] testCurrentTaken;
	private static int testNumberTaken;

	/**
	 * Main function of application.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Can take input from a text file.
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

		/*
		 * This iterates through the words we want to find anagrams for.
		 */
		for (int i = 0; i < words.size(); i++) {
			boolean printed = false;
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

			/*
			 * This for loop finds words in the dictionary that could be built using the
			 * characters in the current word.
			 */
			for (int j = 0; j < wholeDictionary.size(); j++) {

				String dicWord = wholeDictionary.get(j);
				int dicWordLength = dicWord.length();
				boolean wordValid = true;

				if (wordLength >= dicWordLength) {
					for (int x = 0; x < dicWordLength; x++) {
						boolean foundChar = false;
						for (int y = 0; y < wordLength; y++) {
							if (dicWord.charAt(x) == currentWord.charAt(y) && testCurrentTaken[y] == 0) {
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
				testCurrentTaken = currentTaken.clone();
			}
			dicSize = smallDictionary.size();

			// Iterates through all words in small dictionary for testing.
			for (int j = 0; j < dicSize; j++) {
				String dicWord = smallDictionary.get(j);
				int dicWordLength = dicWord.length();

				boolean wordValid = true;
				if (testCurrentAnagram.size() + 1 < bestSize || (testCurrentAnagram.size() + 1 == bestSize
						&& testNumberTaken + dicWordLength == wordLength)) {
					/*
					 * The following loops will check if the current word from small dictionary can
					 * be built using the remaining characters.
					 */
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
						// If one char is not found, then the word cannot be built.
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

				/*
				 * If word is valid, we add the current state to our array lists which we could
				 * fall back later. We are essentially doing a depth first search, and this
				 * keeps track of where we are at each level as we traverse the tree.
				 */
				if (wordValid == true) {
					numberTaken = testNumberTaken;
					currentTaken = testCurrentTaken.clone();
					numberTakenList.add(numberTaken);
					indexPoints.add(j);
					takenChars.add(currentTaken.clone());
					testCurrentAnagram.add(dicWord);

					/*
					 * The following code creates a new dictionary that will be smaller since we now
					 * have fewer available characters, it would be useless to keep all words in the
					 * current dictionary.
					 */
					dicArray.add(new ArrayList<String>(smallDictionary));

					ArrayList<String> temp = new ArrayList<String>(smallDictionary);
					smallDictionary = new ArrayList<String>(smallDictionary.subList(0, j + 1));

					for (int k = j + 1; k < temp.size(); k++) {

						String dicWordTemp = temp.get(k);
						int dicWordLengthTemp = dicWordTemp.length();
						boolean wordValidTemp = true;

						if (wordLength - testNumberTaken >= dicWordLengthTemp) {
							for (int x = 0; x < dicWordLengthTemp; x++) {
								boolean foundCharTemp = false;
								for (int y = 0; y < wordLength; y++) {
									if (dicWordTemp.charAt(x) == currentWord.charAt(y) && testCurrentTaken[y] == 0) {
										testCurrentTaken[y] = 1;
										foundCharTemp = true;
										break;
									}
								}
								if (foundCharTemp == false) {
									wordValidTemp = false;
									break;
								}
							}
						} else {
							wordValidTemp = false;
						}
						if (wordValidTemp == true) {
							smallDictionary.add(dicWordTemp);
						}
						testCurrentTaken = currentTaken.clone();
					}
					dicSize = smallDictionary.size();

					if (numberTaken == wordLength) {
						j = smallDictionary.size() - 1;
					}else{
						j -= 1;
					}
				} else {
					wordValid = false;
					testNumberTaken = numberTaken;
					testCurrentTaken = currentTaken.clone();
				}

				if (j == smallDictionary.size() - 1) {

					/*
					 * If condition is true, then we have used all characters in word and have found
					 * an anagram.
					 */
					if (numberTaken == wordLength) {
						compareAnagrams();
					}

					int initial_J = j;
					int initialSize = indexPoints.size();

					/*
					 * Once we reach the end of the dictionary, we are always guaranteed to always
					 * have to move back to an initial state at least once.
					 * 
					 * We always initialize j to the previous j before going back to the initial
					 * state, before the j value we want gets deleted, because if we don't and
					 * initialize it after, we will find that same word again, and have an infinite
					 * loop.
					 *
					 * If we moved back and j is still the same, we keep moving back until j is
					 * different otherwise the for loop will end.
					 */
					do {
						if (indexPoints.size() == 1 || (initial_J != dicSize - 1 && initialSize != 2
								&& indexPoints.size() == 2 && initial_J == indexPoints.get(1))) {
							break;
						} else {
							j = indexPoints.get(indexPoints.size() - 1);
							moveBack();
						}
					} while (j == initial_J);
				}
			}

			if (!printed) {
				String anagram = "";
				for (int k = 0; k < currentAnagram.size(); k++) {
					anagram += " " + currentAnagram.get(k);
				}
				System.out.println(words.get(i) + ":" + anagram);
			}
			// Clears our array lists for the next word.
			indexPoints.clear();
			numberTakenList.clear();
			takenChars.clear();
			currentAnagram.clear();
			testCurrentAnagram.clear();
			smallDictionary.clear();
			dicArray.clear();
			bestSize = Integer.MAX_VALUE;
		}

	}

	/**
	 * This function takes us back to an earlier state
	 */
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

		smallDictionary = new ArrayList<String>(dicArray.get(dicArray.size() - 1));
		dicArray.remove(dicArray.size() - 1);

		dicSize = smallDictionary.size();
	}

	/**
	 * This function takes an ArrayList<String> and sorts it according to size
	 * (index 0 = largest). If two words have equal length, they will be sorted by
	 * comparing alphabetical order of each char (a first before b).
	 * 
	 * @param anagram
	 * @return sorted anagram
	 */
	public static ArrayList<String> sortAnagram(ArrayList<String> anagram) {
		int currentAnagramSize = anagram.size();
		for (int k = currentAnagramSize - 2; k >= 0; k--) {
			for (int x = k; x < currentAnagramSize - 1; x++) {
				if (anagram.get(x).length() < anagram.get(x + 1).length()) {
					String duplicate = anagram.get(x);
					anagram.set(x, anagram.get(x + 1));
					anagram.set(x + 1, duplicate);
				} else if (anagram.get(x).length() == anagram.get(x + 1).length()) {
					boolean found = false;
					for (int y = 0; y < anagram.get(x).length(); y++) {

						char currentChar = anagram.get(x).charAt(y);
						char testChar = anagram.get(x + 1).charAt(y);

						if (currentChar < 91) {
							currentChar += 32;
						}
						if (testChar < 91) {
							testChar += 32;
						}
						if (currentChar > testChar) {
							String duplicate = anagram.get(x);
							anagram.set(x, anagram.get(x + 1));
							anagram.set(x + 1, duplicate);
							break;
						} else if (currentChar < testChar) {
							found = true;
							break;
						}
					}
					if (found) {
						break;
					}
				} else {
					break;
				}
			}
		}
		return anagram;
	}

	/**
	 * This function compares testCurrentAngram and currentAnagram and determines
	 * which is better. if testCurrentAnagram is better, currentAnagram gets
	 * replaced.
	 */
	public static void compareAnagrams() {
		int currentAnagramSize = currentAnagram.size();
		int testCurrentAnagramSize = testCurrentAnagram.size();

		/*
		 * If currentAnagram is not initialized or contained more words then
		 * testCurrentAnagram, currentAnagram gets replaced since less words is better.
		 */
		if (currentAnagramSize == 0 || currentAnagramSize > testCurrentAnagramSize) {

			currentAnagram = sortAnagram(new ArrayList<String>(testCurrentAnagram));
			bestSize = currentAnagram.size();

		} else if (currentAnagramSize == testCurrentAnagramSize) {

			/*
			 * We create a duplicate of testCurrentAnagram which we sort from largest to
			 * smallest which we use to compare with currentAnagram. We can't sort
			 * testCurrentAnagram since if we do, the corresponding information in the other
			 * array lists will no longer be valid, so we only sort it once it becomes the
			 * currentAnagram.
			 */
			ArrayList<String> testCurrentAnagramOne = sortAnagram(new ArrayList<String>(testCurrentAnagram));

			boolean done = false;

			/*
			 * We compare from largest word to smallest, if at any instance the word from
			 * testCurrentAnagram is larger, it becomes currentAnagram.
			 */
			for (int k = 0; k < currentAnagramSize - 1; k++) {
				if (testCurrentAnagramOne.get(k).length() < currentAnagram.get(k).length()) {
					done = true;
					break;
				}
				if (testCurrentAnagramOne.get(k).length() > currentAnagram.get(k).length()) {
					currentAnagram = new ArrayList<String>(testCurrentAnagramOne);
					done = true;
					break;
				}
			}

			/*
			 * If all words at corresponding indexes have equal length between the two, we
			 * iterate through them all again starting from the largest, and if at any
			 * instance, the first character in a word in testCurrentAnagram comes first in
			 * the alphabet than the corresponding first character in the word from
			 * currentAnagram, then testCurrentAnagram becomes currentAnagram.
			 */
			if (!done) {
				boolean found = false;
				for (int j = 0; j < currentAnagramSize && !found; j++) {
					for (int k = 0; k < currentAnagram.get(j).length(); k++) {

						char currentChar = currentAnagram.get(j).charAt(k);
						char testChar = testCurrentAnagramOne.get(j).charAt(k);

						if (currentChar < 91) {
							currentChar += 32;
						}
						if (testChar < 91) {
							testChar += 32;
						}
						if (currentChar > testChar) {
							currentAnagram = new ArrayList<String>(testCurrentAnagramOne);
							found = true;
							break;
						} else if (currentChar < testChar) {
							found = true;
							break;
						}
					}
				}
			}
		}
	}
}
