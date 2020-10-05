import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for taking input and running Social Distancing Application.
 * 
 * @author Elbert Alcantara
 * @author Christopher Groenewegen
 * @author John KJ Kim
 */
public class Social {

  /**
   * Main method.
   * 
   * @param args
   */
  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    String[] first = new String[2];
    int gridWidth = 0;
    int gridLength = 0;
    ArrayList<int[]> seatedPeople = new ArrayList<>();
    boolean isFirstLine = true;

    while (scan.hasNextLine()) {

      String currLine = scan.nextLine();
      if (isFirstLine) {
        first = currLine.split(" ");
        isFirstLine = false;
        gridWidth = Integer.parseInt(first[1]);
        gridLength = Integer.parseInt(first[0]);
      } else if (currLine.compareTo("") != 0) {
        String[] coordinates = currLine.split(" ");
        int[] seatPositions = new int[2];
        for (int i = 0; i < 2; i++) {
          seatPositions[i] = Integer.parseInt(coordinates[i]);
        }
        seatedPeople.add(seatPositions);
      } 
      if (currLine.equals("") || !scan.hasNextLine()) {
        Distance dist = new Distance(gridWidth, gridLength, seatedPeople);
        dist.run();
        isFirstLine = true;
        first = new String[2];
        gridWidth = 0;
        gridLength = 0;
        seatedPeople = new ArrayList<>();
      }
    }
    scan.close();
  }
}
