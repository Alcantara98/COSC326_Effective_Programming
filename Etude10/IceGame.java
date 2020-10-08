/**
 * IceGame.java
 * Chris G, 5 October 2020
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
     * Given a positive integer, prints out the number of valid strings there
     * are of that length adhering to our alphabet and rules.
     * @param num A positive integer which corresponds to the length strings we
     * are looking to.
     */
    public void game(long num) {
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
     * Given a positive integer, calculates the number of valid strings there
     * are of that length adhering to our alphabet and rules.
     * @param num A positive integer which corresponds to the length strings we
     * are looking to.
     * @return A long which represents the number of valid strings there are of
     * a given length.
     */
    private long numberOfValid(long num) {
        long total = 0;

        if (num <= 5) {
            // int[] indices = new int[num];
            // for (int i = 0; i < indices.length; i++) {
            //     indices[i] = 0;
            // }

            // while (
            
            /* Brute force count. */
            /* num is guarenteed to be at least 1 and no greater than 5. */
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

            /* Number of possible length 5 permutations made out of alphabet's
               characters without the rules being applied.. */
            int numCombinations = (int)Math.pow(alphabet.length(), 5);

            /* An array that will contain 5 letter permutations. */
            String[] possibleCombinations = new String[numCombinations];
            
            /* A hashtable storing 5 letter combinations and their index in the
               possibleCombinations array. */
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

            /* Represents how many strings ending in 5 letter long combinations
               there are for each additional letter appended. */
            //long[][] frequencyTable = new long[numCombintations][tableRows];
            long[] currentFrequency = new long[numCombinations];

            long[] previousFrequency = new long[numCombinations];
            
            /* Represents whether appending a letter (for each letter of the
               alphabet) to the end of any 5 letter combination results in a
               valid string under our language. */
            boolean[][] letterAdditions
                = new boolean[numCombinations][alphabet.length()];
            
            /* Creating the initial fequency for the 1st generation of strings
               (this is counting the number of valid 6 letter strings (which
               should be either a 1 or a 0). */
            for (int i = 0; i < numCombinations; i++) {
                if (validWord(possibleCombinations[i])) {
                    currentFrequency[i] = 1;
                } else {
                    currentFrequency[i] = 0;
                }
                
                for (int j = 0; j < alphabet.length(); j++) {
                    StringBuilder sb
                        = new StringBuilder(possibleCombinations[i]);
                    sb.append(alphabet.charAt(j));
                    letterAdditions[i][j] = validCheckEnd(sb.toString());
                }
            }
            
            for (long k = 0; k < num - 5; k++) {
                for (int i = 0; i < numCombinations; i++) {
                    previousFrequency[i] = currentFrequency[i];
                    currentFrequency[i] = 0;
                }
                for (int i = 0; i < numCombinations; i++) {
                    String current = possibleCombinations[i];
                    for (int j = 0; j < alphabet.length(); j++) {
                        StringBuilder sb = new StringBuilder(current);
                        sb.append(alphabet.charAt(j));
                        sb.deleteCharAt(0);
                        int index = permutations.get(sb.toString());
                        if (letterAdditions[i][j]) {
                            currentFrequency[index] += previousFrequency[i];
                        }
                    }
                }
            }

            for (int i = 0; i < numCombinations; i++) {
                total += currentFrequency[i];
            }
            
        }
        
        return total;
    }

    /**
     * Computes whether a six letter word checks whether the last letter creates
     * any new rules violations.
     * @param word A six letter word.
     * @return Returns false if word if there are any new violations, otherwise
     * returns true.
     */
    private boolean validCheckEnd(String word) {
        for (String rule : rules) {
            String[] ruleSplit = rule.split(" ");
            if (word.endsWith(ruleSplit[0])) {
                StringBuilder sb = new StringBuilder(word);
                sb.delete(sb.length() - ruleSplit[0].length(), sb.length());
                boolean containsException = false;
                for (int i = 1; i < ruleSplit.length; i++) {
                    if (sb.toString().endsWith(ruleSplit[i])) {
                        containsException = true;
                    }
                }
                if (!containsException) {
                    return false;
                }
            }
        }
        return true;
    }           
}
