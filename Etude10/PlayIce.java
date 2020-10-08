/**
 * PlayIce.java
 * Chris G, 1 October 2020
 * COSC326, etude 10
 *
 * Plays the Ice Game, based of the 'i' before 'e' except after 'c' rule.
 */

package playice;

import java.util.*;

/**
 * Given valid input plays the Ice Game.
 * @author Christopher Groenewegen
 */
public class PlayIce {
    private static final boolean VERBOSE = false;

    /**
     * Main method. Scans input and plays the Ice Game with that input.
     * Input is given the following format:
     * A single line containing the alphabet for the Ice Game.
     * Zero or more lines consisting of forbidden substrings followed by zero or
     * more exceptions.
     * A blank line.
     * Zero or or more lines consisting of either letters (a to z) or a positive
     * integer, which line each corrisponds to an instance if the Ice Game.
     * Prints the result of each game on a seperate line.
     * @param args Standard commandline arguments.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String alphabet = scan.nextLine();
        ArrayList<String> rules = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        boolean seenBlank = false;

        while (scan.hasNext()) {
            String line = scan.nextLine();

            if (line.compareTo("") == 0) {
                seenBlank = true;
            } else {
                if (seenBlank) {
                    words.add(line);
                } else {
                    rules.add(line);
                }
            }
        }
        
        if (VERBOSE) {
            System.out.println(alphabet);
            for (String rule : rules) {
                System.out.println(rule);
            }
            System.out.println();
            for (String word : words) {
                System.out.println(word);
            }
        }  

        IceGame icegame = new IceGame(alphabet, rules);

        for (String word : words) {
            if (word.matches("\\d+")) {
                // find out if word is a positive integer
                long num = Long.parseLong(word);
                if (num > 0) {
                    icegame.game(num);
                }
            } else {
                // otherwise assume word consists of letters.
                icegame.game(word);
            }
        }
    } // end main
}
