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
	private ArrayList<String> testCurrentAnagram;

	private ArrayList<Character> currentWord;
	private ArrayList<ArrayList<Character>> currentWordList;

	private ArrayList<ArrayList<Character>> currentAvailable;
	private ArrayList<ArrayList<ArrayList<Character>>> currentAvailableList;

	// This will contain dictionaries for each layer.
	private ArrayList<ArrayList<Words>> dicArray;
	private int dicSize;
	private int bestSize = Integer.MAX_VALUE;

	// private long testTimeOne = 0;
	// private long testTimeTwo = 0;
	private long testTimeThree = 0;
	private long testTimeFour = 0;
	private long testTimeFive = 0;

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
		currentAvailable = new ArrayList<ArrayList<Character>>();
		currentAvailableList = new ArrayList<ArrayList<ArrayList<Character>>>();
	}

	/**
	 * We go through the dictionary using DFS algorithm. Where n is the size of the
	 * dictionary, there would be n^n nodes. Pruning is vital.
	 */
	public void run() {
		// This iterates through the words we want to find anagrams for.
		for (int i = 0; i < words.size(); i++) {
			long startTime = System.nanoTime();
			// long start = System.nanoTime();

			for (char c : words.get(i).getWord()) {
				currentWord.add(c);
			}

			// This for loop finds words in the dictionary that could be built using the
			// characters in the current word.
			reduceDictionary(wholeDictionary);

			dicSize = smallDictionary.size();

			// Iterates through all words in small dictionary for testing.
			for (int j = 0; j < dicSize; j++) {

				Words dicWord = smallDictionary.get(j);
				int dicWordLength = dicWord.length();
				int w = 0;
				int z = 0;
				ArrayList<Character> currentWordTemp = new ArrayList<Character>(currentWord);
				while (w < dicWordLength && z < currentWordTemp.size()) {
					if (dicWord.charAt(w) == currentWordTemp.get(z)) {
						currentWordTemp.remove(z);
						w++;
					} else if (dicWord.charAt(w) < currentWordTemp.get(z)) {
						break;
					} else {
						z++;
					}
				}
				// if (w == dicWordLength) {
				indexPoints.add(j);
				testCurrentAnagram.add(dicWord.getOrigWord());

				currentWordList.add(currentWord);
				currentWord = currentWordTemp;

				// The following code creates a new dictionary that will be smaller since we now
				// have fewer available characters, it would be useless to keep all words in the
				// current dictionary.
				dicArray.add(new ArrayList<Words>(smallDictionary));
				reduceDictionary(new ArrayList<Words>(smallDictionary.subList(j, dicSize)));
				dicSize = smallDictionary.size();

				if (currentWord.size() == 0) {
					j = smallDictionary.size() - 1;
				} else {
					j = -1;
				}
				// }

				if (j == dicSize - 1) {

					// If condition is true, then we have used all characters in word and have found
					// an anagram.
					if (currentWord.size() == 0) {
						compareAnagrams();
					}

					while (indexPoints.size() > 0 && j == dicSize - 1) {
						j = indexPoints.get(indexPoints.size() - 1);
						moveBack();
					}
				}
			}

			// Prints out result once we go through the whole dictionary.
			String anagram = "";
			for (int k = 0; k < currentAnagram.size(); k++) {
				anagram += " " + currentAnagram.get(k);
			}
			System.out.println(words.get(i).getOrigWord() + ":" + anagram);

			// Clears our array lists for the next word.
			indexPoints.clear();
			currentAnagram.clear();
			testCurrentAnagram.clear();
			currentWord.clear();
			smallDictionary.clear();
			bestSize = Integer.MAX_VALUE;
			dicArray.clear();
			currentWordList.clear();
			currentAvailable.clear();
			currentAvailableList.clear();

			// long end = System.nanoTime();
			// testTimeFive += end - start;
			// Time testing.
			long endTime = System.nanoTime();
			System.out.println("Total Time: " + (endTime - startTime) / 1000000);
			// System.out.println("Anagram Sorting Alg: " + testTimeOne / 1000000);
			// System.out.println("Compare Anagram: " + testTimeTwo / 1000000);
			System.out.println("Move Back: " + testTimeThree / 1000000);
			System.out.println("Reduce Dictionary: " + testTimeFour / 1000000);
			System.out.println("While: " + testTimeFive / 1000000);
			System.out.println("");
			// testTimeOne = 0;
			// testTimeTwo = 0;
			testTimeThree = 0;
			testTimeFour = 0;
			testTimeFive = 0;
		}

	}

	/**
	 * This function creates a new smallDictionary. Words from input 'dictionary'
	 * that can be built with the characters available from the current word will be
	 * added.
	 * 
	 * @param dictionary Word pool to check.
	 */
	public void reduceDictionary(ArrayList<Words> dictionary) {
		smallDictionary.clear();
		long startTime = System.nanoTime();
		for (int i = 0; i < dictionary.size(); i++) {
			Words wordCurrent = dictionary.get(i);
			if (wordCurrent.length() <= currentWord.size() && (testCurrentAnagram.size() + 1 < bestSize
					|| (testCurrentAnagram.size() + 1 == bestSize && currentWord.size() == wordCurrent.length()))) {
				int j = 0;
				int k = 0;
				while (j < wordCurrent.length() && k < currentWord.size()) {
					if (wordCurrent.charAt(j) == currentWord.get(k)) {
						j++;
						k++;
					} else if (wordCurrent.charAt(j) < currentWord.get(k)) {
						break;
					} else {
						k++;
					}
				}
				if (j == wordCurrent.length()) {
					smallDictionary.add(wordCurrent);
				}
			}
		}
		long endTime = System.nanoTime();
		testTimeFour += endTime - startTime;
	}

	/**
	 * This function takes us back to an earlier state in DFS.
	 */
	public void moveBack() {
		long startTime = System.nanoTime();
		indexPoints.remove(indexPoints.size() - 1);

		testCurrentAnagram.remove(testCurrentAnagram.size() - 1);

		smallDictionary = dicArray.get(dicArray.size() - 1);
		dicArray.remove(dicArray.size() - 1);
		dicSize = smallDictionary.size();

		currentWord = currentWordList.get(currentWordList.size() - 1);
		currentWordList.remove(currentWordList.size() - 1);
		long endTime = System.nanoTime();
		testTimeThree += endTime - startTime;
	}

	/**
	 * This function takes an ArrayList<String> and sorts it according to size
	 * (index 0 = largest). If two words have equal length, they will be sorted by
	 * comparing alphabetical order of each char (a first before b).
	 * 
	 * @param anagram
	 * @return sorted anagram
	 */
	public ArrayList<String> sortAnagram(ArrayList<String> anagram) {
		// long startTimeOne = System.nanoTime();
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
		// long endTimeOne = System.nanoTime();
		// testTimeOne += endTimeOne - startTimeOne;
		return anagram;
	}

	/**
	 * This function compares testCurrentAngram and currentAnagram and determines
	 * which is better. if testCurrentAnagram is better, currentAnagram gets
	 * replaced.
	 */
	public void compareAnagrams() {
		// long startTimeTwo = System.nanoTime();
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
		// long endTimeTwo = System.nanoTime();
		// testTimeTwo += endTimeTwo - startTimeTwo;
	}
}
