/**
 * IceGame.java
 * Chris G, 4 October 2020
 * COSC326, etude 10
 *
 * The Ice Game, based of the 'i' before 'e' except after 'c'  rule in English.
 */

package playice;

import java.util.*;
import java.lang.Math;

/**
 * The Ice Game is used for determining whether words are contained within a
 * language and is based on the 'i' before 'e' except after 'c' rule in English.
 * Valid words within this game are words with only contain letters within a
 * given alphabet and follow the rules. The rules being a list of forbidden
 * letter combinations followed by zero or more exceptions. The structure being
 * words which contain forbidden strings are not allowed with in the language
 * unless the are immediatedly preceeded by an exception.
 * @author Christopher Groenewegen
 */
public class IceGame {
    private String alphabet;
    private ArrayList<String> rules;

    /**
     * A constructor for creating an instance of the Ice Game.
     * @param alphabet A string consisting of lowercase letters that will be
     * used as the alphabet for our language.
     * @param rules An array of rules formatted as so: The first section are the
     * forbidden letter combinations. Followed by a space, then there are a list
     * of zero or more exceptions seperated by spaces. The forbidden substrings
     * and their prefixes are never more than three characters long.
     */
    public IceGame(String alphabet, ArrayList<String> rules) {
        this.alphabet = alphabet;
        this.rules = rules;
    }

    /**
     * Prints out "Valid" if a word is valid within our given language,
     * otherwise prints out "Invalid".
     * @param word A string which might be either in our language or not.
     */
    public void game(String word) {
        if (validWord(word)) {
            System.out.println("Valid");
        } else {
            System.out.println("Invalid");
        }
    }

    /**
     * Given a positive integer prints out the number of valid strings there are
     * of that length adhering to our alphabet and rules.
     * @param num A positive integer which corresponds to the length strings we
     * are looking to.
     */
    public void game(int num) {
        long total = numberOfValid(num);
        System.out.println(total);
    }

    /**
     * Computes whether a word is valid within our given language.
     * @param word A string which might be either in our language or not.
     * @return True if word is within our language, otherwise false.
     */
    private boolean validWord(String word) {
        /* Checking whether word only contains characters within alphabet. */
        for (int i = 0; i < word.length(); i++) {
            boolean validLetter = false;
            for (int j = 0; j < alphabet.length(); j++) {
                if (word.charAt(i) == alphabet.charAt(j)) {
                    validLetter = true;
                }
            }
            if (!validLetter) {
                return false;
            }
        }

        /* Checking if word contains any forbidden substrings. */
        for (String rule : rules) {
            /* Assume rule will always contain a forbidden substring at least
               one character long. Then followed by zero or more exceptions. */
            String[] splitRule = rule.split(" ");

            for (int i = 0; i < word.length(); i++) {
                boolean containsForbidden = false;
                int substringIndex = 0;
                
                /* Finds a matching character to the forbbiden string, then
                   looks ahead to see if the rest of substring is within the
                   word. */
                /* If looking ahead doesn't put us out of bounds. */
                if (i + splitRule[0].length() - 1 < word.length()) {
                    while (substringIndex < splitRule[0].length()
                           && splitRule[0].charAt(substringIndex)
                           == word.charAt(i + substringIndex)) {
                        substringIndex++;
                    }
                    if (substringIndex == splitRule[0].length()) {
                        containsForbidden = true;
                    }
                    
                    /* Look to see if the forbidden section contains an
                       exception prefix. */
                    if (containsForbidden) {
                        boolean exception = false;
                        for (int j = 1; !exception
                                 && j < splitRule.length; j++) {
                            int lookBehind = -1;
                            int exceptionIndex = 0;

                            while (!exception
                                   && i + lookBehind >= 0
                                   && exceptionIndex < splitRule[j].length()
                                   && splitRule[j].charAt(exceptionIndex)
                                   == word.charAt(i + lookBehind)) {
                                lookBehind--;
                                if (exceptionIndex >= splitRule[j].length()-1) {
                                    exception = true;
                                }
                                exceptionIndex++;
                            }
                        }
                        if (!exception) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Given a positive integer calculates the number of valid strings there are
     * of that length adhering to our alphabet and rules.
     * @param num A positive integer which corresponds to the length strings we
     * are looking to.
     * @return A long which represents the number of valid strings there are of
     * a given length.
     */
    private long numberOfValid(int num) {
        long total = 0;

        if (num <= 5) {
            /* Brute force count. */

            /* num is guarenteed to be at least 1 and no greater than 5. */
            /* Below isn't the best written code as there is a lot of code
               duplication, however this was written for the sake of getting
               working code down. */
            for (int i1 = 0; i1 < alphabet.length(); i1++) {
                if (num >= 2) {
                    for (int i2 = 0; i2 < alphabet.length(); i2++) {
                        if (num >= 3) {
                            for (int i3 = 0; i3 < alphabet.length(); i3++) {
                                if (num >= 4) {
                                    for (int i4 = 0; i4 < alphabet.length(); i4++) {
                                        if (num >= 5) {
                                            for (int i5 = 0; i5 < alphabet.length(); i5++) {
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(alphabet.charAt(i1));
                                                sb.append(alphabet.charAt(i2));
                                                sb.append(alphabet.charAt(i3));
                                                sb.append(alphabet.charAt(i4));
                                                sb.append(alphabet.charAt(i5));
                                                if (validWord(sb.toString())) {
                                                    total++;
                                                }
                                            }
                                        } else {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(alphabet.charAt(i1));
                                            sb.append(alphabet.charAt(i2));
                                            sb.append(alphabet.charAt(i3));
                                            sb.append(alphabet.charAt(i4));
                                            if (validWord(sb.toString())) {
                                                total++;
                                            }
                                        }
                                    }
                                } else {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(alphabet.charAt(i1));
                                    sb.append(alphabet.charAt(i2));
                                    sb.append(alphabet.charAt(i3));
                                    if (validWord(sb.toString())) {
                                        total++;
                                    }
                                }
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(alphabet.charAt(i1));
                            sb.append(alphabet.charAt(i2));
                            if (validWord(sb.toString())) {
                                total++;
                            }
                        }
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(alphabet.charAt(i1));
                    if (validWord(sb.toString())) {
                        total++;
                    }
                }
            }
        } else {
            /* Dealing exclusively with length 3 rules and length 3
               exceptions. All smaller cases will be lumped in together. */

            /* Beginning with a brute force count of length 5 permutations. */
            
            int numCombintations = (int)Math.pow(alphabet.length(), 5);
            String[] possibleCombinations = new String[numCombintations];
            /* Five letter permutations stored with an integer corrisponding
               to where the corrisponding permutations are stored in other
               arrays. */
            HashMap<String, Integer> permutations = new HashMap<>();
            int count = 0;
            for (int i1 = 0; i1 < alphabet.length(); i1++) {
                for (int i2 = 0; i2 < alphabet.length(); i2++) {
                    for (int i3 = 0; i3 < alphabet.length(); i3++) {
                        for (int i4 = 0; i4 < alphabet.length(); i4++) {
                            for (int i5 = 0; i5 < alphabet.length(); i5++) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(alphabet.charAt(i1));
                                sb.append(alphabet.charAt(i2));
                                sb.append(alphabet.charAt(i3));
                                sb.append(alphabet.charAt(i4));
                                sb.append(alphabet.charAt(i5));
                                possibleCombinations[count] = sb.toString();
                                permutations.put(sb.toString(), count);
                                count++;
                            }
                        }
                    }
                }
            }

            /* num is guarenteed to be 6 or greater. */
            int tableRows = num - 5;

            /* Represents how many strings ending in 5 letter long combinations
               there are for each additional letter appended. */
            long[][] frequencyTable = new long[numCombintations][tableRows];

            /* Represents whether appending a letter (for each letter of the
               alphabet) to the end of any 5 letter combination results in a
               valid string under our language. */
            boolean[][] letterAdditions
                = new boolean[numCombintations][alphabet.length()];

            /* Creating the initial fequency for the 1st generation of string
               (this is counting the number of valid 6 letter strings (which
               should be either a 1 or a 0). */
            for (int i = 0; i < numCombintations; i++) {
                for (int j = 0; j < tableRows; j++) {
                    frequencyTable[i][j] = 0;
                }
                long frequency = 0;
                for (int j = 0; j < alphabet.length(); j++) {
                    StringBuilder sb
                        = new StringBuilder(possibleCombinations[i]);
                    sb.append(alphabet.charAt(j));
                    letterAdditions[i][j] = validWord(sb.toString());
                    if (letterAdditions[i][j]) {
                        frequency++;
                    }
                }
                frequencyTable[i][0] = frequency;
            }

            /* For each table row calculate the subtotal of possible strings
               ending in our 5 letter permutations. */
            for (int j = 1; j < tableRows; j++) {
                for (int i = 0; i < numCombintations; i++) {
                    long frequency = 0;
                    String current = possibleCombinations[i];
                    for (int k = 0; k < alphabet.length(); k++) {
                        if (letterAdditions[i][k]) {
                            StringBuilder sb = new StringBuilder(current);
                            sb.deleteCharAt(0);
                            sb.append(alphabet.charAt(k));
                            int index = permutations.get(sb.toString());
                            frequency += frequencyTable[index][j - 1];
                        }
                    }
                    frequencyTable[i][j] = frequency;
                }
            }

            /* Summing up the final row in our frequency table which should
               yield the total possible string combinations of length num for
               num greater than 5. */
            for (int i = 0; i < numCombintations; i++) {
                total += frequencyTable[i][tableRows - 1];
            }
            
        }
        
        return total;
    }
}
