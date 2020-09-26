import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Arrays;

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

	// Input Dictionary.
	private ArrayList<String> rawDictionary;

	// HashTable containing original unsorted words.
	private Hashtable<String, String> hashDictionary;

	// will contain all words from wholeDictionary that can be formed from the
	// current word.
	private ArrayList<String> smallDictionary;

	// Contains all words from input.
	private ArrayList<String> words;

	// Keeps track at which index in the dictionary we got the word.
	private ArrayList<Integer> indexPoints;

	// Stores the current best Anagram.
	private ArrayList<String> currentAnagram;

	// This is where we place words as we create a new Anagram to compare against
	// the current best anagram.
	private ArrayList<String> testCurrentAnagram;

	// This stores the current characters available.
	private ArrayList<Character> currentWord;

	// As we do DFS, we store the currentWord for each node we have visited in
	// current path.
	private ArrayList<ArrayList<Character>> currentWordList;

	// Length of the current word we are looking anagrams for.
	private int currentWordLength;

	// This will contain dictionaries for each layer.
	private ArrayList<ArrayList<String>> dicArray;

	// Current Dictionary Size.
	private int dicSize;

	// Size of best current anagram, the lower the better, hence set to max int
	// value (worst) initially.
	private int bestSize = Integer.MAX_VALUE;

	/**
	 * To use this application a dictionary pool, and a list of words ()
	 * 
	 * @param inputDictionaryIn Dictionary which will be the raw anagram pool.
	 * @param wordsInput        Will contain the words we will look anagrams for.
	 */
	public Anagram(ArrayList<String> inputDictionaryIn, ArrayList<String> wordsInput) {
		rawDictionary = new ArrayList<>(inputDictionaryIn);
		hashDictionary = new Hashtable<>();
		smallDictionary = new ArrayList<String>();
		words = new ArrayList<String>(wordsInput);

		currentWordList = new ArrayList<ArrayList<Character>>();
		dicArray = new ArrayList<ArrayList<String>>();
		indexPoints = new ArrayList<Integer>();
		currentWord = new ArrayList<Character>();

		currentAnagram = new ArrayList<String>();
		testCurrentAnagram = new ArrayList<String>();

		currentWordLength = 0;
	}

	/**
	 * We go through the dictionary using DFS algorithm (iterative). Where n is the
	 * size of the dictionary, there would be n^d nodes, where d is the depth which
	 * would depend on the current word. Pruning is vital.
	 */
	public void run() {

		// This iterates through the words we want to find anagrams for.
		for (int i = 0; i < words.size(); i++) {
			//long startTimeZero = System.nanoTime();
			// Retrieves characters from current word into arraylist of chars for easier
			// manipulation.
			char[] newWord = words.get(i).toCharArray();
			Arrays.sort(newWord);
			for (char c : newWord) {
				currentWord.add(c);
			}

			// This for loop finds words in the dictionary that could be built using the
			// characters in the current word.
			initialReduce();
			initialSorting();
			dicSize = smallDictionary.size();
			currentWordLength = currentWord.size();
			
			// Iterates through all words in small dictionary for testing.
			for (int j = 0; j < dicSize; j++) {
				String dicWord = smallDictionary.get(j);
				int dicWordLength = dicWord.length();
				if (testCurrentAnagram.size() == 0 && currentAnagram.size() != 0
						&& currentWordLength / dicWordLength > currentAnagram.size()) {
					break;
				}

				// We check and remove which characters are used in current word. It is already
				// guarnteed to be formed with the available characters since we check when
				// calling reduceDictionary, but we don't know which characters are used. Saving
				// which characters are used takes longer than just checking it again here.
				// Removing reduceDictionary requires that w = dicWordLength, if not, the word
				// cannot be formed with remaining characters.
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

				// if(w == dicWordLength){

				// Add current node information to lists as it is a node in current path in DFS.
				indexPoints.add(j);
				currentWordList.add(currentWord);
				dicArray.add(new ArrayList<String>(smallDictionary));

				// Add to test Anagram.
				testCurrentAnagram.add(hashDictionary.get(dicWord));

				// Set currentWord to remaining chracters.
				currentWord = currentWordTemp;

				// Reduce smallDictionary size to words that can be formed with remaining
				// characters.
				reduceDictionary(new ArrayList<String>(smallDictionary.subList(j, dicSize)));
				dicSize = smallDictionary.size();

				// If condition is true, then we have used all characters in word and have found
				// an anagram.
				if (currentWord.size() == 0) {
					j = smallDictionary.size() - 1;
				} else {
					j = -1;
				}

				// }

				if (j == dicSize - 1) {
					if (currentWord.size() == 0) {
						compareAnagrams();
					}

					// Moves us back in layers in tree once certain conditions are met.
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
			System.out.println(words.get(i) + ":" + anagram);

			// Clears our array lists for the next word.
			indexPoints.clear();
			currentAnagram.clear();
			testCurrentAnagram.clear();
			currentWord.clear();
			smallDictionary.clear();
			bestSize = Integer.MAX_VALUE;
			dicArray.clear();
			currentWordList.clear();
			hashDictionary.clear();

			// long endTime = System.nanoTime();
			// System.out.println("Total Time: " + (endTime - startTimeZero) / 1000000);
		}

	}

	/**
	 * This function creates a new smallDictionary. Words from input 'dictionary'
	 * that can be built with the characters available from the current word will be
	 * added.
	 * 
	 * @param dictionary Word pool to check.
	 */
	public void reduceDictionary(ArrayList<String> dictionary) {
		ArrayList<String> smallTemp = new ArrayList<String>();
		smallDictionary.clear();

		// More efficient starting backwards with smaller words so when we reach a word
		// that is larger than the pool of available characters, we break the looop
		// since the words following will also be larger.
		for (int i = dictionary.size() - 1; i >= 0; i--) {
			String wordCurrent = dictionary.get(i);
			// String dicWordString = "";
			// for(char c: currentWord){
			// dicWordString += c;
			// }
			// System.out.println(wordCurrent + " " + dicWordString);
			if (wordCurrent.length() <= currentWord.size()) {
				if (testCurrentAnagram.size() + 1 < bestSize
						|| (testCurrentAnagram.size() + 1 == bestSize && currentWord.size() == wordCurrent.length())) {
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
						smallTemp.add(wordCurrent);
					}
				}
			} else {
				break;
			}
		}

		// We sort from largest to smallest again (just flip it) as a result of going
		// backwards in the loop above.
		for (int i = smallTemp.size() - 1; i >= 0; i--) {
			smallDictionary.add(smallTemp.get(i));
		}
	}

	/**
	 * Slightly different from reduceDictionary, works on rawDictionary so words are
	 * unsorted in characters.
	 */
	public void initialReduce() {
		// if (TIMECHECK)
		// startTimeOne = System.nanoTime();

		ArrayList<String> smallTemp = new ArrayList<String>();
		smallDictionary.clear();

		// More efficient starting backwards with smaller words so when we reach a word
		// that is larger than the pool of available characters, we break the looop
		// since the words following will also be larger.
		for (int i = rawDictionary.size() - 1; i >= 0; i--) {
			String wordString = rawDictionary.get(i);
			char[] wordCurrent = wordString.toCharArray();
			Arrays.sort(wordCurrent);

			if (wordCurrent.length <= currentWord.size()) {
				int j = 0;
				int k = 0;
				while (j < wordCurrent.length && k < currentWord.size()) {
					if (wordCurrent[j] == currentWord.get(k)) {
						j++;
						k++;
					} else if (wordCurrent[j] < currentWord.get(k)) {
						break;
					} else {
						k++;
					}
				}
				if (j == wordCurrent.length) {
					smallTemp.add(wordString);
				}
			}

		}

		// We sort from largest to smallest again (just flip it) as a result of going
		// backwards in the loop above.
		for (int i = smallTemp.size() - 1; i >= 0; i--) {
			smallDictionary.add(smallTemp.get(i));
		}
	}

	/**
	 * This function takes us back to a higer level node in DFS, a visited node in
	 * current chosen path.
	 */
	public void moveBack() {
		indexPoints.remove(indexPoints.size() - 1);

		testCurrentAnagram.remove(testCurrentAnagram.size() - 1);

		smallDictionary = dicArray.get(dicArray.size() - 1);
		dicArray.remove(dicArray.size() - 1);
		dicSize = smallDictionary.size();

		currentWord = currentWordList.get(currentWordList.size() - 1);
		currentWordList.remove(currentWordList.size() - 1);
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
		int currentAnagramSize = anagram.size();
		for (int k = currentAnagramSize - 2; k >= 0; k--) {
			for (int x = k; x < currentAnagramSize - 1; x++) {
				if (anagram.get(x).length() == anagram.get(x + 1).length()) {
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
	public void compareAnagrams() {
		// if (TIMECHECK)
		// startTimeFour = System.nanoTime();

		// If currentAnagram is not initialized or contained more words then
		// testCurrentAnagram, currentAnagram gets replaced since less words is better.
		if (currentAnagram.size() == 0 || currentAnagram.size() > testCurrentAnagram.size()) {

			currentAnagram = sortAnagram(new ArrayList<String>(testCurrentAnagram));
			bestSize = currentAnagram.size();

		} else if (currentAnagram.size() == testCurrentAnagram.size()) {

			// We create a duplicate of testCurrentAnagram which we sort from largest to
			// smallest to compare with currentAnagram. We can't sort testCurrentAnagram
			// since if we do, the corresponding information in the other array lists will
			// no longer be valid, so we only sort it if it becomes the best anagram.
			ArrayList<String> testCurrentAnagramOne = sortAnagram(new ArrayList<String>(testCurrentAnagram));

			boolean done = false;

			// We compare from largest word to smallest, if at any instance the word from
			// testCurrentAnagram is larger, it becomes currentAnagram.
			for (int k = 0; k < currentAnagram.size() - 1; k++) {
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

			// If all words at corresponding indexes have equal length between the two, we
			// iterate through them all again starting from the largest, and if at any
			// instance, the first character in a word in testCurrentAnagram comes first in
			// the alphabet than the corresponding first character in the word from
			// currentAnagram, then testCurrentAnagram becomes currentAnagram.
			if (!done) {
				for (int j = 0; j < currentAnagram.size(); j++) {
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
							j = currentAnagram.size();
							break;
						} else if (currentChar < testChar) {
							j = currentAnagram.size();
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * This function sorts the characters of each word in smallDictionary. It also
	 * sorts smallDictionary from smallest to largest. For words that use the same
	 * characters, it picks the best one (best being alphabetically higher, where (a
	 * > z). Then the unsorted original word is placed in hashDictionary where the
	 * key is the sorted char.
	 * 
	 * This could be done initially with the rawDictionary, but I'm currently using
	 * bubble sort which works well with smaller sizes, and I'm not in the mood to
	 * implement Merge Sort atm.
	 */
	public void initialSorting() {
		ArrayList<String> inputDictionary = new ArrayList<String>(smallDictionary);
		smallDictionary.clear();

		for (int i = 0; i < inputDictionary.size(); i++) {
			char[] currentWord = inputDictionary.get(i).toCharArray();
			Arrays.sort(currentWord);
			String wordString = "";
			for (char c : currentWord) {
				wordString += c;
			}
			smallDictionary.add(wordString);
		}

		// Similar to sortAnagram, except works on two array lists.
		for (int k = smallDictionary.size() - 2; k >= 0; k--) {
			for (int x = k; x < smallDictionary.size() - 1; x++) {
				if (smallDictionary.get(x).length() < smallDictionary.get(x + 1).length()) {
					String duplicate = smallDictionary.get(x);
					smallDictionary.set(x, smallDictionary.get(x + 1));
					smallDictionary.set(x + 1, duplicate);

					duplicate = inputDictionary.get(x);
					inputDictionary.set(x, inputDictionary.get(x + 1));
					inputDictionary.set(x + 1, duplicate);
				} else if (smallDictionary.get(x).length() == smallDictionary.get(x + 1).length()) {
					boolean found = false;
					for (int y = 0; y < smallDictionary.get(x).length(); y++) {

						char currentChar = smallDictionary.get(x).charAt(y);
						char testChar = smallDictionary.get(x + 1).charAt(y);

						if (currentChar < 91) {
							currentChar += 32;
						}
						if (testChar < 91) {
							testChar += 32;
						}
						if (currentChar > testChar) {
							String duplicate = smallDictionary.get(x);
							smallDictionary.set(x, smallDictionary.get(x + 1));
							smallDictionary.set(x + 1, duplicate);

							duplicate = inputDictionary.get(x);
							inputDictionary.set(x, inputDictionary.get(x + 1));
							inputDictionary.set(x + 1, duplicate);
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

		//
		for (int i = 0; i < smallDictionary.size() - 1; i++) {
			if (smallDictionary.get(i).equalsIgnoreCase(smallDictionary.get(i + 1))) {
				if (!inputDictionary.get(i).equalsIgnoreCase(inputDictionary.get(i + 1))) {
					for (int y = 0; y < inputDictionary.get(i).length(); y++) {

						char currentChar = inputDictionary.get(i).charAt(y);
						char testChar = inputDictionary.get(i + 1).charAt(y);

						if (currentChar < 91) {
							currentChar += 32;
						}
						if (testChar < 91) {
							testChar += 32;
						}
						if (currentChar > testChar) {
							inputDictionary.remove(i);
							smallDictionary.remove(i);
							break;
						} else if (currentChar < testChar) {
							inputDictionary.remove(i + 1);
							smallDictionary.remove(i + 1);
							break;
						}
					}
				} else {
					inputDictionary.remove(i);
					smallDictionary.remove(i);
				}
				i -= 1;
			} else {
				hashDictionary.put(smallDictionary.get(i), inputDictionary.get(i));
			}
		}

		hashDictionary.put(smallDictionary.get(smallDictionary.size() - 1),
				inputDictionary.get(smallDictionary.size() - 1));
	}
}
