import java.util.Arrays;

public class Words {
    private char[] word;
    private int size;
    private String originalWord;

    public Words(String originalWord) {
        this.originalWord = originalWord;
        this.word = originalWord.toCharArray();
        Arrays.sort(this.word);
        size = originalWord.length();
    }

    public char[] getWord() {
        return this.word;
    }

    public String getOrigWord() {
        return this.originalWord;
    }

    public int length() {
        return this.size;
    }

    public char charAt(int index) {
        return this.word[index];
    }

    public char getOrigChar(int index) {
        return this.originalWord.charAt(index);
    }


}