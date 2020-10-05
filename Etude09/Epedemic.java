import java.util.*;
import static java.lang.System.out;

/**
 * An application for calculating the amount of sick people and their
 * coordinates within the grid world to get everyone in the gridworld sick as
 * well.
 * 
 * @author Elbert Alcantara
 */
public class Epedemic {

  private char[][] universe;

  /**
   * Constructor for Epedemic.
   * 
   * @param newUniverse Gridworld to calculate.
   */
  public Epedemic(ArrayList<String> newUniverse) {

    universe = new char[newUniverse.size()][newUniverse.get(0).length()];

    for (int i = 0; i < newUniverse.size(); i++) {
      for (int j = 0; j < newUniverse.get(0).length(); j++) {
        universe[i][j] = newUniverse.get(i).charAt(j);
      }
    }

  }

  /**
   * Used for running application A.
   */
  public void runA() {
    countSickened(universe);
    printUni(universe);
  }

  /**
   * Used for running application B.
   */
  public void runB() {
    printUni(universe);
  }

  /**
   * Given a gridworld with sick people, this function will calculate how many
   * other people will become sick.
   * 
   * @param uni
   * @return
   */
  public int countSickened(char[][] uni) {
    int count = 0;
    int sickCount = 0;

    while (true) {
      int countTemp = 0;

      for (int i = 0; i < uni.length; i++) {
        for (int j = 0; j < uni[0].length; j++) {
          if (uni[i][j] == '.') {
            sickCount = 0;
            if (i + 1 < uni.length && uni[i + 1][j] == 'S') {
              sickCount++;
            }
            if (i - 1 >= 0 && uni[i - 1][j] == 'S') {
              sickCount++;
            }
            if (j + 1 < uni[0].length && uni[i][j + 1] == 'S') {
              sickCount++;
            }
            if (j - 1 >= 0 && uni[i][j - 1] == 'S') {
              sickCount++;
            }
            if (sickCount > 1) {
              uni[i][j] = 'S';
              countTemp++;
            }
          }
        }
      }

      if (countTemp == 0) {
        break;
      } else {
        count += countTemp;
      }
    }
    return count;
  }

  public void printUni(char[][] uni) {
    for (char[] c : uni) {
      for (char cc : c) {
        out.print(cc + " ");
      }
      out.println("");
    }
    out.println("");
  }
}
