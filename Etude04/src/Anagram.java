import java.util.ArrayList;

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
	private ArrayList<Words> wholeDictionary;

	// will contain all words from wholeDictionary that can be formed from the
	// current word.
	private ArrayList<Words> smallDictionary;

	// Contains all words from input.
	private ArrayList<Words> words;

	// Keeps track at which index in the dictionary we got the word.
	private ArrayList<Integer> indexPoints;

	// Stores the current best Anagram.
	private ArrayList<String> currentAnagram;

	// This is where we place words as we create an new Anagram to compare against
	// current best.
<<<<<<< HEAD
	private ArrayList<String> testCurrentAnagram;
=======
	private static ArrayList<String> testCurrentAnagram = new ArrayList<String>();

	private static boolean flag = false;
	private static int inputType = 1;
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
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
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
>>>>>>> parent of 3fe94e1... Anagram Working Version

	ArrayList<Character> currentWord;
	private ArrayList<ArrayList<Character>> currentWordList;

	// This will contain dictionaries for each layer.
	private ArrayList<ArrayList<Words>> dicArray;
	private int dicSize;
	private int bestSize = Integer.MAX_VALUE;

	public Anagram(ArrayList<Words> wholeDictionaryInput, ArrayList<Words> wordsInput) {
		wholeDictionary = new ArrayList<Words>(wholeDictionaryInput);
		words = new ArrayList<Words>(wordsInput);
		smallDictionary = new ArrayList<Words>();
		indexPoints = new ArrayList<Integer>();
		currentAnagram = new ArrayList<String>();
		currentWord = new ArrayList<Character>();
		currentWordList = new ArrayList<ArrayList<Character>>();
		testCurrentAnagram = new ArrayList<String>();
		dicArray = new ArrayList<ArrayList<Words>>();
	}

	public void run() {
		/*
		 * This iterates through the words we want to find anagrams for.
		 */
		for (int i = 0; i < words.size(); i++) {
<<<<<<< HEAD
			long startTime = System.nanoTime();
			for (char c : words.get(i).getWord()) {
				currentWord.add(c);
=======
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
>>>>>>> parent of 3fe94e1... Anagram Working Version
			}

			/*
			 * This for loop finds words in the dictionary that could be built using the
			 * characters in the current word.
			 */
			reduceDictionary(wholeDictionary, smallDictionary, currentWord);

<<<<<<< HEAD
			dicSize = smallDictionary.size();

			// Iterates through all words in small dictionary for testing.
			for (int j = 0; j < dicSize; j++) {
				Words dicWord = smallDictionary.get(j);
				int dicWordLength = dicWord.length();
				if (testCurrentAnagram.size() + 1 < bestSize
						|| (testCurrentAnagram.size() + 1 == bestSize && currentWord.size() - dicWordLength == 0)) {
					int w = 0;
					int z = 0;
					ArrayList<Character> currentWordTemp = new ArrayList<Character>(currentWord);
					while (w < dicWordLength && z < currentWordTemp.size()) {
						if (dicWord.charAt(w) == currentWordTemp.get(z)) {
							currentWordTemp.remove(z);
							w++;
						} else if (dicWord.charAt(w) < currentWordTemp.get(z)) {
=======
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

			// Iterates through all words in small dictionary for testing.
			for (int j = 0; j < smallDictionary.size(); j++) {
				String dicWord = smallDictionary.get(j);
				int dicWordLength = dicWord.length();

				boolean wordValid = true;

				/*
				 * If the remaining available characters is less than current word from small
				 * dictionary, then we don't check it since it obviously cannot be built with
				 * the remaining characters.
				 */
				if (wordLength - testNumberTaken >= dicWordLength) {

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
>>>>>>> parent of 3fe94e1... Anagram Working Version
							break;
						} else {
							z++;
						}
					}

<<<<<<< HEAD
					if (w == dicWordLength) {
						indexPoints.add(j);
						testCurrentAnagram.add(dicWord.getOrigWord());

						currentWordList.add(new ArrayList<Character>(currentWord));
						currentWord = new ArrayList<Character>(currentWordTemp);

						/*
						 * The following code creates a new dictionary that will be smaller since we now
						 * have fewer available characters, it would be useless to keep all words in the
						 * current dictionary.
						 */
						// dicArray.add(new ArrayList<Words>(smallDictionary));

						// // System.out.print("Index: " + j + " Before: " + dicSize + " ");
						// ArrayList<Words> dicTemp = new ArrayList<Words>(smallDictionary.subList(j, dicSize));
						// smallDictionary.clear();
						// reduceDictionary(dicTemp, smallDictionary, currentWord);
						// dicSize = smallDictionary.size();

						// System.out.print("After: " + dicSize + "\n");

						// for (String b : testCurrentAnagram) {
						// System.out.print(b + " ");
						// }
						// System.out.println("");

						if (currentWord.size() == 0) {
							j = smallDictionary.size() - 1;
						}
						// else if(dicSize == 0){
						// j = 0;
						// }
						else {
							j = -1;
						}
					}
=======
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
					j -= 1;
>>>>>>> parent of 3fe94e1... Anagram Working Version
				}

				if (j == smallDictionary.size() - 1) {

					/*
					 * If condition is true, then we have used all characters in word and have found
					 * an anagram.
					 */
					if (currentWord.size() == 0) {
						compareAnagrams();
					}

<<<<<<< HEAD
					// int initial_J = j;
					// int initialSize = indexPoints.size();
=======
					/*
					 * We end the for loop and print the currentAnagram and claim it as the best
					 * once the first index in indexPoints is at the index of the last word in the
					 * small dictionary since this means, we have exhausted all possible anagrams.
					 * 
					 * In the depth first search, this means that at the first level, we have
					 * reached the node at the far right.
					 */
					if (indexPoints.get(1) == smallDictionary.size() - 1) {
						String anagram = "";
						for (int k = 0; k < currentAnagram.size(); k++) {
							anagram += " " + currentAnagram.get(k);
						}
						System.out.println(words.get(i) + ":" + anagram);
						break;
					}
					int initial_J = j;
>>>>>>> parent of 3fe94e1... Anagram Working Version

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
					// do {
					// System.out.print("Index: " + j + " Before: " + dicSize + " " + "After: " +
					// dicSize + "\n");

					// for (int b : indexPoints) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// for (String b : testCurrentAnagram) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// System.out.println("");
					// if (indexPoints.size() == 1 || (initial_J != dicSize - 1 && initialSize != 2
					// && indexPoints.size() == 2 && initial_J == indexPoints.get(1))) {
					// break;
					// } else {
					// j = indexPoints.get(indexPoints.size() - 1);
					// moveBack();
					// }
					// System.out.print("Index: " + j + " Before: " + dicSize + " " + "After: " +
					// dicSize + "\n");

					// for (int b : indexPoints) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// for (String b : testCurrentAnagram) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// System.out.println("");
					// } while (j == initial_J);
					// System.out.print("Index: " + j + " Before: " + dicSize + "\n");

					// for (int b : indexPoints) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// for (String b : testCurrentAnagram) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// System.out.println("");
					do {
						j = indexPoints.get(indexPoints.size() - 1);
						moveBack();
<<<<<<< HEAD
					} while (indexPoints.size() > 0 && j == dicSize - 1);
					// System.out.print("Index: " + j + " After: " + dicSize + "\n");

					// for (int b : indexPoints) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// for (String b : testCurrentAnagram) {
					// System.out.print(b + " ");
					// }
					// System.out.println("");
					// System.out.println("");
				}
			}
			String anagram = "";
			for (int k = 0; k < currentAnagram.size(); k++) {
				anagram += " " + currentAnagram.get(k);
			}
			System.out.println(words.get(i).getOrigWord() + ":" + anagram);
=======
					} while (j == initial_J);
				}
			}
>>>>>>> parent of 3fe94e1... Anagram Working Version

			// Clears our array lists for the next word.
			indexPoints.clear();
			currentAnagram.clear();
			testCurrentAnagram.clear();
			currentWord.clear();
			smallDictionary.clear();
<<<<<<< HEAD
			bestSize = Integer.MAX_VALUE;
			dicArray.clear();
			currentWordList.clear();

			long endTime = System.nanoTime();
			System.out.println((endTime - startTime) / 1000000);
=======
>>>>>>> parent of 3fe94e1... Anagram Working Version
		}
	}

	public ArrayList<Words> reduceDictionary(ArrayList<Words> dictionary, ArrayList<Words> newDictionary,
			ArrayList<Character> word) {
		for (int i = 0; i < dictionary.size(); i++) {
			Words wordCurrent = dictionary.get(i);

			if (wordCurrent.length() <= word.size()) {
				int j = 0;
				int k = 0;
				while (j < wordCurrent.length() && k < word.size()) {
					if (wordCurrent.charAt(j) == word.get(k)) {
						j++;
						k++;
					} else if (wordCurrent.charAt(j) < word.get(k)) {
						break;
					} else {
						k++;
					}
				}
				if (j == wordCurrent.length()) {
					newDictionary.add(wordCurrent);
				}
			}
		}
		return newDictionary;
	}

	/**
	 * This function takes us back to an earlier state
	 */
	public void moveBack() {
		indexPoints.remove(indexPoints.size() - 1);

		// Removes current word.
		if (testCurrentAnagram.size() != 0) {
			testCurrentAnagram.remove(testCurrentAnagram.size() - 1);
		}

<<<<<<< HEAD
		// smallDictionary = new ArrayList<Words>(dicArray.get(dicArray.size() - 1));
		// dicArray.remove(dicArray.size() - 1);

		// dicSize = smallDictionary.size();

		currentWord = new ArrayList<Character>(currentWordList.get(currentWordList.size() - 1));
		currentWordList.remove(currentWordList.size() - 1);
=======
		int[] duplicate = takenChars.get(takenChars.size() - 1);
		currentTaken = duplicate.clone();

		numberTaken = numberTakenList.get(numberTakenList.size() - 1);
		testCurrentTaken = currentTaken.clone();
		testNumberTaken = numberTaken;
>>>>>>> parent of 3fe94e1... Anagram Working Version
	}

	/**
	 * This function sorts currentAnagram from largest word to smallest word.
	 */
<<<<<<< HEAD
	public ArrayList<String> sortAnagram(ArrayList<String> anagram) {
		int currentAnagramSize = anagram.size();
=======
	public static void sortCurrentAnagram() {
		int currentAnagramSize = currentAnagram.size();
>>>>>>> parent of 3fe94e1... Anagram Working Version
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

	/**
	 * This function compares testCurrentAngram and currentAnagram and determines
	 * which is better. if testCurrentAnagram is better, currentAnagram gets
	 * replaced.
	 */
	public void compareAnagrams() {
		int currentAnagramSize = currentAnagram.size();
		int testCurrentAnagramSize = testCurrentAnagram.size();

		/*
		 * If currentAnagram is not initialized or contained more words then
		 * testCurrentAnagram, currentAnagram gets replaced since less words is better.
		 */
		if (currentAnagram.isEmpty() || currentAnagramSize == 0 || currentAnagramSize > testCurrentAnagramSize) {

			currentAnagram = new ArrayList<String>(testCurrentAnagram);
			sortCurrentAnagram();

		} else if (currentAnagramSize == testCurrentAnagramSize) {

			/*
			 * We create a duplicate of testCurrentAnagram which we sort from largest to
			 * smallest which we use to compare with currentAnagram. We can't sort
			 * testCurrentAnagram since if we do, the corresponding information in the other
			 * array lists will no longer be valid, so we only sort it once it becomes the
			 * currentAnagram.
			 */
			ArrayList<String> testCurrentAnagramOne = new ArrayList<String>(testCurrentAnagram);

			// Sorts testCurrentAnagramOne from largest to smallest.
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
			}

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
					// sortCurrentAnagram();
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
