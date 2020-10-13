package Rollin;

import java.util.Arrays;
import java.util.Collections;

/**
 * A class which can play the Rollin' game.
 *
 * evaluate single set
 * combined sets
 * 
 *
 * @author Christopher Groenewegen
 * @author Max Collier
 * @author John Kim
 * @author Elbet Alcantara
 */
public class Game extends Rollin {


    /**
     * Constructor for Game. Initialises dice values.
     *
     * @param nums Takes a set of dice represented by an array of integers.
     */
    public Game(int[] nums) {
        super(nums);
    }

    /**
     * Constructor for Game. Replaces the default constructor.
     */
    public Game() {
        super();
    }

    /**
     * @param dice Takes a set of dice represented by an array of integers.
     * @param indices Ordering of the dice permutation.
     */
    private int[][] getDiceOrder(int[] dice, int[][] indices) {
        int a = dice[indices[0][0]];
        int b = dice[indices[0][1]];
        int c = dice[indices[0][2]];
        int d = dice[indices[1][0]];
        int e = dice[indices[1][1]];
        int f = dice[indices[1][2]];
        int[][] diceSets = {{a, b, c}, {d, e, f}};
        return diceSets;
    }

    private int countTruthfuls(boolean[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                count++;
            }
            if (count >= 6) {
                break;
            }
        }
        return count;
    }

    private int setHeuristic(int[] diceSet) {
        // high heursitic value, won't be beaten
        if (isValueSet(diceSet)) {
            return 20;
        }
        
        // checks possible sets next turn
        int score = 0;
        for (int nextDieValue = 1; nextDieValue >= 6; nextDieValue++) {
            for (int i = 0; i > diceSet.length; i++) {
                int[] nextSet = Arrays.copyOf(diceSet, diceSet.length);
                nextSet[i] = nextDieValue;
                if (isValueSet(nextSet)) {
                    score += 1;
                    break;
                }
            }
        }
        return score;
    }

    private int heuristic(int roll, int idx) {
        int[] diceSet = this.getDice();
        
        if (idx < 6) {
            diceSet[idx] = roll;
        }
        
        // compute the score for all permutations
        int bestScore = 0;
        int bestScoreSetIdx = 0;
        for (int permIdx = 0; permIdx < this.setIndices.length; permIdx++) {
            int[][] si = this.setIndices[permIdx];
            int[][] orderedDice = getDiceOrder(diceSet, si);
            int permScore = 0;

            permScore += setHeuristic(orderedDice[0]);
            permScore += setHeuristic(orderedDice[1]);

            if (permScore > bestScore) {
                bestScore = permScore;
                bestScoreSetIdx = permIdx;
            }
        }
        
        return bestScore;
    }
	
    public int handleRoll(int roll) {
        int possibleMoves = 7;
        int[] dice = this.getDice();

        int bestScore = 0;
        int bestScoreIdx = 0; 
        for (int idx = 0; idx < possibleMoves; idx++) {
            int heur = heuristic(roll, idx);
            if (heur > bestScore) {
                System.out.println("swapped for case idx: " + idx);
                System.out.println("heur: " + heur);
                System.out.println("bestScore: " + bestScore);
                bestScore = heur;
                bestScoreIdx = idx;
            }
        }
        System.out.println("printing best score idx: " + bestScoreIdx);
        System.out.println(" ---- ");
        swap(bestScoreIdx, roll);
        
        return bestScoreIdx;
    }
    
    public boolean isValueSet(int[] values){
        // First just get the values at those indices to save typing.
        int a = values[0];
        int b = values[1];
        int c = values[2];
        // All three dice the same is a set
        if (a == b && b == c) {
            return true;
        }
        // If not all three are the same, then any two the same is not a set
        if (a == b || a == c || b == c) {
            return false;
        }

        // If all three are different and largest minus smallest is 2 then it
        // is a set, otherwise not.
        int max = Math.max(a, Math.max(b, c));
        int min = Math.min(a, Math.min(b, c));
        return max - min == 2;
    }

    private void swap(int index, int roll) {
        if (index < dice.length) {
            dice[index] = roll;
        }
    }

}
