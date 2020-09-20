package Rollin;

import java.util.Random;

/**
 * A main method used for testing code for the Rollin' etude.
 *
 * @author Christopher Groenewegen, Max Collier, John Kim
 */
public class Main {
	
    public static void main(String[] args) {
        Random generator = new Random();
        int[] initial = new int[6];
        for (int i = 0; i<6; i++) {
            initial[i] = generator.nextInt(6) + 1;
        }
        int[] dice = new int[6];
        Rollin game = new Game(initial);
        
        int count = 0; // Used for counting the interations of the while loop.
        int roll;
        System.out.println(game.isComplete());
        while (!game.isComplete()) {
            count++;
            int high = 6;
            int low = 1;
            roll = generator.nextInt(high - low) + low;
            System.out.println("roll: " + roll);
            game.handleRoll(roll);
            dice = game.getDice();
            for (int die : dice) {
                System.out.print(die + " ");
            }
            System.out.println("");
        }
        // Print statement for testing purposes
        System.out.println("this is the end of the program: " + count);
    }
}
