import java.util.ArrayList;
import java.util.Scanner;

/**
 * Etude08
 */
public class Etude08 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String firstLine = scan.nextLine();
        String[] first = firstLine.split(" ");
        int gridWidth = Integer.parseInt(first[0]);
        int gridLength = Integer.parseInt(first[1]);

        ArrayList<int[]> seatedPeople = new ArrayList<>();

        while (scan.hasNextLine()) {
            String currLine = scan.nextLine();
            if (currLine.compareTo("") != 0) {
				String[] coordinates = currLine.split(" ");
                int[] seatPositions = new int[2];
                for (int i = 0; i < 2; i++) {
					seatPositions[i] = Integer.parseInt(coordinates[i]);
                }
                seatedPeople.add(seatPositions);
            } else {
                // compute();
            }
        }
        /*
         * System.out.println(seatedPeople.get(0)[0]);
         * System.out.println(seatedPeople.get(0)[1]);
         * System.out.println(seatedPeople.get(1)[0]);
         * System.out.println(seatedPeople.get(1)[1]);
         * System.out.println(seatedPeople.get(2)[0]);
         * System.out.println(seatedPeople.get(2)[1]);
         * System.out.println(seatedPeople.get(3)[0]);
         * System.out.println(seatedPeople.get(3)[1]);
         * System.out.println(gridWidth);
         * System.out.println(gridLength);
         */

    }

    public void printFinal() {

    }

}