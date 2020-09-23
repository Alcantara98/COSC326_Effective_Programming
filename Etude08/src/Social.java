import java.util.Scanner;
import java.util.ArrayList;

public class Social {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String firstLine = scan.nextLine();
        String[] first = firstLine.split(" ");
        int gridWidth = Integer.parseInt(first[1]);
        int gridLength = Integer.parseInt(first[0]);

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
               Distance dist = new Distance(gridWidth, gridLength, seatedPeople);
            }
        }
        
        System.out.println(seatedPeople.get(0)[0]);
    }


}

