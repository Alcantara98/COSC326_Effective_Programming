import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used for taking input from stdin to run Anagram application.
 * 
 * @author Elbert Alcantara
 * @author Minh Tran
 */
public class Main {

    /**
     * Main function of application.
     * 
     * @param args
     * @throws IOException
     */ 
    public static void main(String[] args) throws IOException {
        // Contains all the words from the input dictionary.
        ArrayList<char[]> inputDictionary = new ArrayList<char[]>();

        // Contains all words from input.
        ArrayList<char[]> words = new ArrayList<char[]>();

        boolean flag = false;

        // Takes input from stdin.
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String inputLine = sc.nextLine();
            if (inputLine.isEmpty()) {
                flag = true;
            } else {
                inputLine = inputLine.toLowerCase();
                String currentWord = "";
                for (int i = 0; i < inputLine.length(); i++) {
                    char c = inputLine.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        currentWord = currentWord + c;
                    }
                }

                if (!flag) {
                    words.add(inputLine.toCharArray());
                } else {
                    inputDictionary.add(inputLine.toCharArray());
                }
            }
        }
        sc.close();

        Anagram myAnagram = new Anagram(inputDictionary, words);
        myAnagram.run();
    }
}
