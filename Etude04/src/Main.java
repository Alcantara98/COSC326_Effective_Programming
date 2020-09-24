import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * Main function of application.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Contains all the words from the input dictionary.
        ArrayList<Words> wholeDictionary = new ArrayList<Words>();

        // Contains all words from input.
        ArrayList<Words> words = new ArrayList<Words>();

        boolean flag = false;
        // int inputType = 0;

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
                    Words newWord = new Words(currentWord);
                    words.add(newWord);
                } else {
                    Words newWord = new Words(inputLine);
                    wholeDictionary.add(newWord);
                }
            }
        }

        Anagram myAnagram = new Anagram(wholeDictionary, words);
        myAnagram.run();
    }
}
