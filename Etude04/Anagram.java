import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Anagram {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		ArrayList<String> dictionary = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();

		// Keeps track at which index in the dictionary we got the word
		ArrayList<Integer> indexPoints = new ArrayList<Integer>();
		// Keeps track of how many characters it has used at that point
		ArrayList<Integer> numberTakenList = new ArrayList<Integer>();
		// Keeping track which characters have been used
		ArrayList<int[]> takenChars = new ArrayList<int[]>();
		// Stores the current anagram
		ArrayList<String> currentAnagram = new ArrayList<String>();
		ArrayList<String> testCurrentAnagram = new ArrayList<String>();
		/*
		 * // For keeping count of current duplicates ArrayList<Integer> duplicateCount
		 * = new ArrayList<Integer>();
		 */
		boolean flag = false;

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
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
						dictionary.add(inputLine);
					}
				}

			}
		} finally {
			br.close();
		}

		/*
		 * Scanner sc = new Scanner(System.in); while (sc.hasNext()) { String inputLine
		 * = sc.nextLine(); if (inputLine.isEmpty()) { flag = true; } else { inputLine =
		 * inputLine.toLowerCase();
		 * 
		 * if (!flag) { words.add(inputLine); } else { dictionary.add(inputLine); } } }
		 */

		for (int i = 0; i < words.size(); i++) {
			String currentWord = "";
			for (int j = 0; j < words.get(i).length(); j++) {
				char c = words.get(i).charAt(j);
				if (c >= 'a' && c <= 'z') {
					currentWord = currentWord + c;
				}
			}

			int wordLength = currentWord.length();
			// If the index is 1, then that char in word at that index has been used
			int[] currentTaken = new int[wordLength];
			// Number of characters taken
			int numberTaken = 0;

			for (int j = 0; j < wordLength; j++) {
				currentTaken[j] = 0;
			}

			numberTakenList.add(numberTaken);
			takenChars.add(currentTaken.clone());
			indexPoints.add(0);
			// duplicateCount.add(0);

			int[] testCurrentTaken = currentTaken.clone();
			int testNumberTaken = numberTaken;
			for (int j = 0; j < dictionary.size(); j++) {
				String dicWord = dictionary.get(j);
				int dicWordLength = dicWord.length();

				boolean wordValid = true;
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
				if (wordValid == true) {
					numberTaken = testNumberTaken;
					currentTaken = testCurrentTaken.clone();
					/*
					 * for (int v : testCurrentTaken) { System.out.print(v + " "); }
					 */

					numberTakenList.add(numberTaken);
					indexPoints.add(j);
					takenChars.add(currentTaken.clone());
					testCurrentAnagram.add(dicWord);
					/*
					 * if (testCurrentAnagram.size() == 1) { duplicateCount.add(0); } else { String
					 * previous = testCurrentAnagram.get(testCurrentAnagram.size() - 2); if
					 * (previous.equals(dicWord)) {
					 * duplicateCount.add(duplicateCount.get(duplicateCount.size() - 1) + 1); } else
					 * { duplicateCount.add(0); } }
					 */
					j -= 1;
				}


				if (j == dictionary.size() - 1) {
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
					for (int[] b : takenChars) {
						for (int c : b)
							System.out.print(c + ", ");
						System.out.print("  ;  ");
					}
					System.out.println("");
					System.out.println("");
					if (numberTaken == wordLength) {
						if (currentAnagram.isEmpty() || currentAnagram.size() == 0
								|| currentAnagram.size() < testCurrentAnagram.size()) {
							currentAnagram = (ArrayList<String>) testCurrentAnagram.clone();
							testCurrentAnagram.clear();
						} else if (currentAnagram.size() == testCurrentAnagram.size()) {
							for (int k = 0; i < currentAnagram.size(); k++) {
								String current = currentAnagram.get(k);
								String newOne = testCurrentAnagram.get(k);
								if (current.length() >= newOne.length()) {
									break;
								} else {
									currentAnagram = (ArrayList<String>) testCurrentAnagram.clone();
									testCurrentAnagram.clear();
								}
							}
						}
					}

					numberTakenList.remove(numberTakenList.size() - 1);
					// indexPoints.remove(indexPoints.size() - 1);
					takenChars.remove(takenChars.size() - 1);
					if (testCurrentAnagram.size() != 0) {
						testCurrentAnagram.remove(testCurrentAnagram.size() - 1);
					}

					/*
					 * int duplicates = duplicateCount.get(duplicateCount.size() - 1) + 1; for (int
					 * x = 0; x < duplicates; x++) { numberTakenList.remove(numberTakenList.size() -
					 * 1); indexPoints.remove(indexPoints.size() - 1);
					 * takenChars.remove(takenChars.size() - 1); if (testCurrentAnagram.size() != 0)
					 * { testCurrentAnagram.remove(testCurrentAnagram.size() - 1); }
					 * duplicateCount.remove(duplicateCount.size() - 1); }
					 */


					int[] duplicate = takenChars.get(takenChars.size() - 1);
					currentTaken = duplicate.clone();
					numberTaken = numberTakenList.get(numberTakenList.size() - 1);
					testCurrentTaken = currentTaken.clone();
					testNumberTaken = numberTaken;
					j = indexPoints.get(indexPoints.size() - 1);
					indexPoints.remove(indexPoints.size() - 1);
					if (j == dictionary.size() - 1) {
						numberTakenList.remove(numberTakenList.size() - 1);
						// indexPoints.remove(indexPoints.size() - 1);
						takenChars.remove(takenChars.size() - 1);
						if (testCurrentAnagram.size() != 0) {
							testCurrentAnagram.remove(testCurrentAnagram.size() - 1);
						}

						int[] duplicate2 = takenChars.get(takenChars.size() - 1);
						currentTaken = duplicate2.clone();
						numberTaken = numberTakenList.get(numberTakenList.size() - 1);
						testCurrentTaken = currentTaken.clone();
						testNumberTaken = numberTaken;
						j = indexPoints.get(indexPoints.size() - 1);
						indexPoints.remove(indexPoints.size() - 1);
					}
					if (j == dictionary.size() - 1) {
						String anagram = "";
						for (int k = 0; k < currentAnagram.size(); k++) {
							anagram += " " + currentAnagram.get(k);
						}
						System.out.println(currentWord + ":" + anagram);
						break;
					}

				}
			}
			indexPoints.clear();
			numberTakenList.clear();
			takenChars.clear();
			currentAnagram.clear();
			testCurrentAnagram.clear();
		}
	}
}
