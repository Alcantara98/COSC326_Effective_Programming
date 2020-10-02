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
        ArrayList<String> inputDictionary = new ArrayList<String>();

        // Contains all words from input.
        ArrayList<String> words = new ArrayList<String>();

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
                    words.add(inputLine);
                } else {
                    inputDictionary.add(inputLine);
                }
            }
        }
        sc.close();

        Anagram myAnagram = new Anagram(inputDictionary, words);
        myAnagram.run();
    }
}
