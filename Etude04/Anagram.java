import java.util.*;

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
	private ArrayList<char[]> rawDictionary;

	// HashTable containing original unsorted words.
	private Hashtable<char[], char[]> hashDictionary;

	// Contains all words from input.
	private ArrayList<char[]> words;

	// Stores the current best Anagram.
	private ArrayList<char[]> currentAnagram;

	// This is where we place words as we create a new Anagram to compare against
	// the current best anagram.
	private ArrayList<char[]> testCurrentAnagram;

	// This stores the current characters available.
	// private ArrayList<Character> currentWord;

	// As we do DFS, we store the currentWord for each node we have visited in
	// current path.

	private boolean breakRecursion;
	private int currentWordLength;

	// Current Dictionary Size.
	// private int dicSize;

	// Size of best current anagram, the lower the better, hence set to max int
	// value (worst) initially.
	private int bestSize = Integer.MAX_VALUE;
	ArrayList<char[]> smallDictionary;

	/**
	 * To use this application a dictionary pool, and a list of words ()
	 * 
	 * @param dictionaryIn Dictionary which will be the raw anagram pool.
	 * @param wordsInput   Will contain the words we will look anagrams for.
	 */
	public Anagram(ArrayList<char[]> dictionaryIn, ArrayList<char[]> wordsInput) {
		rawDictionary = new ArrayList<char[]>(dictionaryIn);
		hashDictionary = new Hashtable<char[], char[]>();

		words = new ArrayList<char[]>(wordsInput);

		currentAnagram = new ArrayList<char[]>();
		testCurrentAnagram = new ArrayList<char[]>();
		breakRecursion = false;
		smallDictionary = new ArrayList<char[]>();
	}

	/**
	 * We go through the dictionary using DFS algorithm recursively using the
	 * recursive function. Where n is the size of the dictionary, there would be n^d
	 * nodes, where d is the depth which would depend on the current word. Pruning
	 * is vital.
	 */
	public void run() {

		// This iterates through the words we want to find anagrams for.
		for (int i = 0; i < words.size(); i++) {
			long startTimeZero = System.nanoTime();

			currentWordLength = words.get(i).length;

			// Retrieves characters from current word into arraylist of chars for easier
			// manipulation.
			char[] newWord = words.get(i).clone();
			Arrays.sort(newWord);

			// This for loop finds words in the dictionary that could be built using the
			// characters in the current word.
			ArrayList<char[]> smallDictionary = new ArrayList<char[]>(initialSorting(initialReduce(newWord)));

			// System.out.println(smallDictionary.size());
			recursive(smallDictionary, newWord);

			// Prints out result once we go through the whole dictionary.
			String anagram = "";
			for (char[] c : currentAnagram) {
				anagram += " " + String.valueOf(c);
			}
			System.out.println(String.valueOf(words.get(i)) + ":" + anagram);

			currentAnagram.clear();
			testCurrentAnagram.clear();
			bestSize = Integer.MAX_VALUE;
			hashDictionary.clear();
			breakRecursion = false;

			long endTimeZero = System.nanoTime();
			System.out.println("Total Time: " + (endTimeZero - startTimeZero) / 1000000);
		}
	}

	/**
	 * Recursive Depth First Search function that searches through dictionary input
	 * for the best anagram.
	 * 
	 * @param dictionaryInput Anagram Pool.
	 * @param wordInput       Word we are finding the anagram for.
	 */
	public void recursive(ArrayList<char[]> dictionaryInput, char[] wordInput) {
		if (wordInput.length == 0) {
			compareAnagrams();
			return;
		}

		// Goes through all the words in dictionaryInput.
		for (int j = 0; j < dictionaryInput.size() && !breakRecursion; j++) {

			// Return condition.
			if (currentAnagram.size() != 0
					&& (testCurrentAnagram.size() + wordInput.length / dictionaryInput.get(j).length > currentAnagram
							.size())
					|| (testCurrentAnagram.size() + Math
							.ceil((double) wordInput.length / dictionaryInput.get(j).length) == currentAnagram.size()
							&& dictionaryInput.get(j).length < currentAnagram.get(testCurrentAnagram.size()).length
							&& testCurrentAnagram.size() != currentAnagram.size() - 1)) {
				return;
			} else {

				int w = 0;
				int z = 0;
				int i = 0;
				char[] currentWordTemp = new char[wordInput.length - dictionaryInput.get(j).length];
				while (w < dictionaryInput.get(j).length) {
					if (dictionaryInput.get(j)[w] == wordInput[z]) {
						w++;
						z++;
					} else {
						currentWordTemp[i] = wordInput[z];
						i++;
						z++;
					}
				}
				while (z < wordInput.length) {
					currentWordTemp[i] = wordInput[z];
					i++;
					z++;
				}

				// Add word to test Anagram.
				testCurrentAnagram.add(hashDictionary.get(dictionaryInput.get(j)));

				// Reduce smallDictionary size to words that can be formed with the remaining
				// characters.
				recursive(reduceDictionary(dictionaryInput.subList(j, dictionaryInput.size()), currentWordTemp),
						currentWordTemp);

				// Remove word from test anagram.
				testCurrentAnagram.remove(testCurrentAnagram.size() - 1);
			}
		}
		return;
	}

	/**
	 * This function creates a new smallDictionary. Words from input 'dictionary'
	 * that can be built with the characters available, from the current word, will
	 * be added.
	 * 
	 * @param dictionary Word pool to check.
	 * @return reduced dictionary, containing only word that can be created with
	 *         remaining characters.
	 */
	public ArrayList<char[]> reduceDictionary(List<char[]> dictionary, char[] currentWord) {
		ArrayList<char[]> smallDictionary = new ArrayList<char[]>();

		for (int i = 0; i < dictionary.size(); i++) {
			char[] wordCurrent = dictionary.get(i);

			if (wordCurrent.length <= currentWord.length) {
				if (testCurrentAnagram.size() + 1 < bestSize
						|| (testCurrentAnagram.size() + 1 == bestSize && currentWord.length == wordCurrent.length)) {
					int j = 0;
					int k = 0;
					while (j < wordCurrent.length && k < currentWord.length) {
						if (wordCurrent[j] == currentWord[k]) {
							j++;
							k++;
						} else if (wordCurrent[j] < currentWord[k]) {
							break;
						} else {
							k++;
						}
					}
					if (j == wordCurrent.length) {
						smallDictionary.add(wordCurrent);
					}
				}
			}
		}
		return smallDictionary;
	}

	/**
	 * Slightly different from reduceDictionary, works on rawDictionary so words are
	 * unsorted in characters.
	 */
	public ArrayList<char[]> initialReduce(char[] currentWord) {
		ArrayList<char[]> smallDictionary = new ArrayList<char[]>();

		for (int i = 0; i < rawDictionary.size(); i++) {
			char[] wordCurrent = rawDictionary.get(i).clone();
			Arrays.sort(wordCurrent);

			if (wordCurrent.length <= currentWord.length) {
				int j = 0;
				int k = 0;
				while (j < wordCurrent.length && k < currentWord.length) {
					if (wordCurrent[j] == currentWord[k]) {
						j++;
						k++;
					} else if (wordCurrent[j] < currentWord[k]) {
						break;
					} else {
						k++;
					}
				}
				if (j == wordCurrent.length) {
					smallDictionary.add(rawDictionary.get(i));
				}
			}

		}

		return smallDictionary;
	}

	/**
	 * This function compares testCurrentAngram and currentAnagram and determines
	 * which is better. if testCurrentAnagram is better, currentAnagram gets
	 * replaced.
	 */
	public void compareAnagrams() {
		if (currentAnagram.size() == 0 || currentAnagram.size() > testCurrentAnagram.size()) {

			currentAnagram = new ArrayList<char[]>(testCurrentAnagram);

			// Checks if currentAnagram is optimal.
			if ((currentWordLength / currentAnagram.get(0).length == currentAnagram.size()
					&& currentWordLength % currentAnagram.get(0).length == 0)
					|| ((currentWordLength / currentAnagram.get(0).length) + 1 == currentAnagram.size()
							&& currentWordLength % currentAnagram.get(0).length == currentAnagram
									.get(currentAnagram.size() - 1).length)) {
				breakRecursion = true;
			}

			bestSize = currentAnagram.size();

		} else if (currentAnagram.size() == testCurrentAnagram.size()) {

			// We create a duplicate of testCurrentAnagram which we sort from largest to
			// smallest to compare with currentAnagram. We can't sort testCurrentAnagram
			// since if we do, the corresponding information in the other array lists will
			// no longer be valid, so we only sort it if it becomes the best anagram.
			ArrayList<char[]> testCurrentAnagramOne = new ArrayList<char[]>(testCurrentAnagram);

			boolean done = false;

			// We compare from largest word to smallest, if at any instance the word from
			// testCurrentAnagram is larger, it becomes currentAnagram.
			for (int k = 0; k < currentAnagram.size() - 1; k++) {
				if (testCurrentAnagramOne.get(k).length < currentAnagram.get(k).length) {
					done = true;
					break;
				}
				if (testCurrentAnagramOne.get(k).length > currentAnagram.get(k).length) {
					currentAnagram = new ArrayList<char[]>(testCurrentAnagramOne);

					// Checks if currentAnagram is optimal.
					if ((currentWordLength / currentAnagram.get(0).length == currentAnagram.size()
							&& currentWordLength % currentAnagram.get(0).length == 0)
							|| ((currentWordLength / currentAnagram.get(0).length) + 1 == currentAnagram.size()
									&& currentWordLength % currentAnagram.get(0).length == currentAnagram
											.get(currentAnagram.size() - 1).length)) {
						breakRecursion = true;
					}

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
					for (int k = 0; k < currentAnagram.get(j).length; k++) {

						char currentChar = currentAnagram.get(j)[k];
						char testChar = testCurrentAnagramOne.get(j)[k];

						if (currentChar < 91) {
							currentChar += 32;
						}
						if (testChar < 91) {
							testChar += 32;
						}
						if (currentChar > testChar) {
							currentAnagram = new ArrayList<char[]>(testCurrentAnagramOne);

							// Checks if currentAnagram is optimal.
							if ((currentWordLength / currentAnagram.get(0).length == currentAnagram.size()
									&& currentWordLength % currentAnagram.get(0).length == 0)
									|| ((currentWordLength / currentAnagram.get(0).length) + 1 == currentAnagram.size()
											&& currentWordLength % currentAnagram.get(0).length == currentAnagram
													.get(currentAnagram.size() - 1).length)) {
								breakRecursion = true;
							}

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
	 * > z). Then we re-sort the character-sorted words alphabetically based on the
	 * unsorted word that is still in inputDictionary, hence why we sort it
	 * alongside smallDictionary (we use sortAnagram function below for this part). 
	 * Then we add the unsorted words to the hashTable with the corresponding sorted 
	 * word (sorted in characters).
	 * 
	 * This could be done initially with the rawDictionary, but I'm currently using
	 * insertion sort which works well with smaller sizes.
	 * 
	 * @param dictionary word pool to be sorted and added to hashTable.
	 * @return sorted word pool.
	 */
	public ArrayList<char[]> initialSorting(ArrayList<char[]> dictionary) {
		ArrayList<char[]> inputDictionary = new ArrayList<char[]>(dictionary);
		ArrayList<char[]> smallDictionary = new ArrayList<char[]>();

		smallDictionary.clear();

		for (int i = 0; i < inputDictionary.size(); i++) {
			char[] currentWord = inputDictionary.get(i).clone();
			Arrays.sort(currentWord);

			smallDictionary.add(currentWord);
		}

		// Similar to sortAnagram, except works on two array lists.
		for (int k = smallDictionary.size() - 2; k >= 0; k--) {
			for (int x = k; x < smallDictionary.size() - 1; x++) {
				if (smallDictionary.get(x).length < smallDictionary.get(x + 1).length) {
					char[] duplicate = smallDictionary.get(x).clone();
					smallDictionary.set(x, smallDictionary.get(x + 1));
					smallDictionary.set(x + 1, duplicate);

					duplicate = inputDictionary.get(x).clone();
					inputDictionary.set(x, inputDictionary.get(x + 1));
					inputDictionary.set(x + 1, duplicate);
				} else if (smallDictionary.get(x).length == smallDictionary.get(x + 1).length) {
					boolean found = false;
					for (int y = 0; y < smallDictionary.get(x).length; y++) {

						char currentChar = smallDictionary.get(x)[y];
						char testChar = smallDictionary.get(x + 1)[y];

						if (currentChar < 91) {
							currentChar += 32;
						}
						if (testChar < 91) {
							testChar += 32;
						}
						if (currentChar > testChar) {
							char[] duplicate = smallDictionary.get(x).clone();
							smallDictionary.set(x, smallDictionary.get(x + 1));
							smallDictionary.set(x + 1, duplicate);

							duplicate = inputDictionary.get(x).clone();
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

		for (int i = 0; i < smallDictionary.size() - 1; i++) {
			if (Arrays.equals(smallDictionary.get(i), (smallDictionary.get(i + 1)))) {
				if (!Arrays.equals(inputDictionary.get(i), (inputDictionary.get(i + 1)))) {
					for (int y = 0; y < inputDictionary.get(i).length; y++) {

						char currentChar = inputDictionary.get(i)[y];
						char testChar = inputDictionary.get(i + 1)[y];

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
			}
		}

		smallDictionary = sortAnagram(smallDictionary, inputDictionary);

		for (int i = 0; i < smallDictionary.size(); i++) {
			hashDictionary.put(smallDictionary.get(i), inputDictionary.get(i));
		}

		return smallDictionary;
	}

	/**
	 * This function takes an ArrayList<char[]> where the word is sorted alphabetically and
	 * sort it based on the corresponding unsorted original word.
	 * 
	 * @param anagram
	 * @return sorted anagram
	 */
	public ArrayList<char[]> sortAnagram(ArrayList<char[]> sortedChar, ArrayList<char[]> unsortedChar) {
		for (int k = sortedChar.size() - 2; k >= 0; k--) {
			for (int x = k; x < sortedChar.size() - 1; x++) {
				if (sortedChar.get(x).length == sortedChar.get(x + 1).length) {

					boolean found = false;
					char[] wordOne = unsortedChar.get(x);
					char[] wordTwo = unsortedChar.get(x + 1);

					for (int y = 0; y < sortedChar.get(x).length; y++) {

						char currentChar = wordOne[y];
						char testChar = wordTwo[y];

						if (currentChar < 91) {
							currentChar += 32;
						}
						if (testChar < 91) {
							testChar += 32;
						}
						if (currentChar > testChar) {
							char[] duplicate = sortedChar.get(x).clone();
							sortedChar.set(x, sortedChar.get(x + 1));
							sortedChar.set(x + 1, duplicate);

							duplicate = unsortedChar.get(x).clone();
							unsortedChar.set(x, unsortedChar.get(x + 1));
							unsortedChar.set(x + 1, duplicate);
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
		return sortedChar;
	}
}
