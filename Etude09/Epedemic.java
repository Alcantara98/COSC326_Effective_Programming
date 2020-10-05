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
  private char[][] uniSickInitial;

  /**
   * Constructor for Epedemic.
   * 
   * @param newUniverse Gridworld to calculate.
   */
  public Epedemic(ArrayList<String> newUniverse) {

    universe = new char[newUniverse.size()][newUniverse.get(0).length()];
    uniSickInitial = new char[newUniverse.size()][newUniverse.get(0).length()];

    for (int i = 0; i < newUniverse.size(); i++) {
      for (int j = 0; j < newUniverse.get(0).length(); j++) {
        universe[i][j] = newUniverse.get(i).charAt(j);
        uniSickInitial[i][j] = newUniverse.get(i).charAt(j);
      }
    }
  }

  /**
   * Used for running application A.
   */
  public void runA() {
    mustBeSick();
    printUni(uniSickInitial);
    int y = 0;
    int x = 0;
    int bestScore = 1;
    while (bestScore != 0) {

      bestScore = 0;
      y = 0;
      x = 0;

      for (int i = 0; i < universe.length; i++) {
        for (int j = 0; j < universe[0].length; j++) {

          if (universe[i][j] == '.') {

            char[][] uniTemp = cloneUniverse(universe);
            uniTemp[i][j] = 'S';
            int score = countSickened(uniTemp) + 1;

            if (score > bestScore) {
              bestScore = score;
              y = i;
              x = j;
            }
          }
        }
      }

      if (bestScore != 0) {
        universe[y][x] = 'S';
        uniSickInitial[y][x] = 'S';
        countSickened(universe);
      }
    }

    printUni(uniSickInitial);
  }

  /**
   * Used for running application B.
   */
  public void runB() {
    printUni(universe);
  }

  /**
   * Based on certain conditions, it is better to hard pick certain vulnerable
   * people to be sick for best results.This method does that right at the start.
   */
  private void mustBeSick() {
    
    // Condition: Only one vulnerable neighbor.
    for (int i = 0; i < universe.length; i++) {
      for (int j = 0; j < universe[0].length; j++) {

        if (universe[i][j] == '.') {
          int sickCount = 0;
          boolean noSick = true;
          if (i + 1 < universe.length) {
            if (universe[i + 1][j] == '.') {
              sickCount++;
            } else if (universe[i + 1][j] == 'S') {
              noSick = false;
            }
          }
          if (i - 1 >= 0) {
            if (universe[i - 1][j] == '.') {
              sickCount++;
            } else if (universe[i - 1][j] == 'S') {
              noSick = false;
            }
          }
          if (j + 1 < universe[0].length) {
            if (universe[i][j + 1] == '.') {
              sickCount++;
            } else if (universe[i][j + 1] == 'S') {
              noSick = false;
            }
          }
          if (j - 1 >= 0) {
            if (universe[i][j - 1] == '.') {
              sickCount++;
            } else if (universe[i][j - 1] == 'S') {
              noSick = false;
            }
          }
          if (noSick && sickCount < 2) {
            universe[i][j] = 'S';
            uniSickInitial[i][j] = 'S';
          }
        }
      }
    }

    // Condition: Immune people at each corner.
    for (int i = 0; i < universe.length; i++) {
      for (int j = 0; j < universe[0].length; j++) {

        if (universe[i][j] == '.') {
          int sickCount = 0;
          boolean noSick = true;
          if (i + 1 < universe.length) {
            if (j + 1 < universe[0].length) {
              if (universe[i + 1][j + 1] == 'I') {
                sickCount++;
              }
            }
            if (universe[i + 1][j] == 'S') {
              noSick = false;
            }
          }

          if (i - 1 >= 0) {
            if (j - 1 >= 0) {
              if (universe[i - 1][j - 1] == 'I') {
                sickCount++;
              }
            }
            if (universe[i - 1][j] == 'S') {
              noSick = false;
            }
          }

          if (j + 1 < universe[0].length) {
            if (i - 1 >= 0) {
              if (universe[i - 1][j + 1] == 'I') {
                sickCount++;
              }
            }
            if (universe[i][j + 1] == 'S') {
              noSick = false;
            }
          }

          if (j - 1 >= 0) {
            if (i + 1 < universe.length) {
              if (universe[i + 1][j - 1] == 'I') {
                sickCount++;
              }
            }
            if (universe[i][j - 1] == 'S') {
              noSick = false;
            }
          }

          if (noSick && sickCount == 4) {
            universe[i][j] = 'S';
            uniSickInitial[i][j] = 'S';
          }
        }
      }
    }
  }

  public char[][] cloneUniverse(char[][] uni) {
    char[][] newUni = new char[uni.length][uni[0].length];
    for (int i = 0; i < uni.length; i++) {
      newUni[i] = uni[i].clone();
    }

    return newUni;
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

  /**
   * Used to print a universe.
   * 
   * @param uni Universe to print.
   */
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
