import java.util.ArrayList;

public class Distance {
  private int rows, cols;

  // Keeps track of the grid world map.
  private boolean[][] grid;

  // Coordinates of seated people.
  private ArrayList<int[]> seatedPeople;

  // Coordinates of seated people.
  private ArrayList<Integer> seatedExpansion;

  // Keeps track of the current seated people for testing that combined would
  // block.
  private ArrayList<Integer> testPeople;

  private boolean foundBlocker;

  private int minSeatedDist;

  private boolean minSeatedDistZero;

  private int totalDistance;

  private final boolean DEBUGGING = true;

  /**
   * Constructor for Distance.
   * 
   * @param x           Size of grid world horizontally.
   * @param y           Size of grid world vertically.
   * @param seatedArray Array List of the index of seated people.
   */
  public Distance(int x, int y, ArrayList<int[]> seatedArray) {
    rows = y;
    cols = x;

    grid = new boolean[rows][cols];

    seatedPeople = new ArrayList<int[]>(seatedArray);

    seatedExpansion = new ArrayList<Integer>();

    testPeople = new ArrayList<Integer>();

    foundBlocker = false;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid[i][j] = true;
      }
    }

    for (int[] seated : seatedPeople) {
      grid[seated[0]][seated[1]] = false;
    }
    if (!checkGrid(grid)) {
      minSeatedDistZero = true;
    } else {
      minSeatedDistZero = false;
    }
    for (int[] seated : seatedPeople) {
      grid[seated[0]][seated[1]] = true;
    }

    for (int i = 0; i < seatedPeople.size(); i++) {
      seatedExpansion.add(0);
    }
    minSeatedDist = 0;
    totalDistance = 0;
  }

  /**
   * Runs the Social Distancing Application;
   */
  public void run() {
    if (!minSeatedDistZero) {
      minSeatedDist = expandCount();
    }
    while (seatedPeople.size() != 0) {
      int expand = expandCount();
      expandAllBy(expand);
      //printGrid(grid);
      //System.out.println(expand);

      for (int i = 1; i < seatedPeople.size() + 1 && !foundBlocker; i++) {
        combinations(0, i, 0);
      }

      foundBlocker = false;
      testPeople.clear();
    }
    
    printGrid(grid);
    System.out.println("min " + minSeatedDist + ", total " + totalDistance);
  }

  /**
   * Does a depth first search to a certain depth to find combinations of 'depth'
   * amount of seated people to test if the group is responsible for blocking the
   * next expansion.
   * 
   * @param depth
   * @param maxDepth
   * @param pool
   * @param index
   */
  private void combinations(int depth, int maxDepth, int index) {
    if (depth == maxDepth) {
      boolean[][] gridCopy = new boolean[rows][cols];
      for (int i = 0; i < rows; i++) {
        gridCopy[i] = grid[i].clone();
      }

      for (int i = 0; i < testPeople.size(); i++) {
        int x = seatedPeople.get(testPeople.get(i))[0];
        int y = seatedPeople.get(testPeople.get(i))[1];
        int distance = seatedExpansion.get(testPeople.get(i));
        removeNodes(x, y, distance + 1, gridCopy);
      }

      if (!checkGrid(gridCopy)) {
        for (int i = 0; i < testPeople.size(); i++) {
          int indexToRemove = testPeople.get(i) - i;
          seatedPeople.remove(indexToRemove);
          totalDistance += seatedExpansion.get(indexToRemove);
          seatedExpansion.remove(indexToRemove);
        }
        foundBlocker = true;
      }
      return;
    }

    if (foundBlocker) {
      return;
    }

    for (; index < seatedPeople.size() && !foundBlocker; index++) {
      testPeople.add(index);
      combinations(depth + 1, maxDepth, index + 1);
      testPeople.remove(testPeople.size() - 1);
    }
    return;
  }

  /**
   * 
   * @param expanAmount
   */
  private void expandAllBy(int expanAmount) {
    if (expanAmount != 0) {
      for (int i = 0; i <= expanAmount; i++) {
        for (int j = 0; j < seatedPeople.size(); j++) {
          removeNodes(seatedPeople.get(j)[0], seatedPeople.get(j)[1], seatedExpansion.get(j) + i, grid);
        }
      }
      for (int i = 0; i < seatedExpansion.size(); i++) {
        seatedExpansion.set(i, seatedExpansion.get(i) + expanAmount);
      }
    }
  }

  /**
   * calculates and returns the minimum seated distance.
   * 
   * @return min distance
   */
  private int expandCount() {
    boolean[][] gridCopy = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) {
      gridCopy[i] = grid[i].clone();
    }

    int count = 0;
    while (true) {
      for (int i = 0; i < seatedPeople.size(); i++) {
        int x = seatedPeople.get(i)[0];
        int y = seatedPeople.get(i)[1];
        int distance = seatedExpansion.get(i);
        removeNodes(x, y, distance + count + 1, gridCopy);
        //printGrid(gridCopy);
      }
      if (checkGrid(gridCopy)) {
        count++;
      } else {
        break;
      }
    }
    return count;
  }

  /**
   * Prints the Grid for debugging.
   */
  public void printGrid(boolean[][] gridToPrint) {
    if (DEBUGGING) {
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          System.out.print((gridToPrint[i][j] ? 1 : 0) + "  ");
        }
        System.out.println("");
      }
      System.out.println("");
    }
  }

  /**
   * Changes seats in a diamond shape around node at (x, y). Only outer seats are
   * changed since we are incrementally increasing border around each seated
   * person. We can also decrease perimeter aroud the seat by having state = true.
   * 
   * @param y     index
   * @param x     index
   * @param dist  perimeter
   * @param state change seat to this state (true or false)
   */
  private void removeNodes(int x, int y, int dist, boolean[][] gridToChange) {
    int offset = 1;
    if (dist != 0) {
      dist -= 1;

      try {
        gridToChange[x][y - dist] = false;
      } catch (Exception e) {
      }
      try {
        gridToChange[x][y + dist] = false;
      } catch (Exception e) {
      }
      try {
        gridToChange[x - dist][y] = false;
      } catch (Exception e) {
      }
      try {
        gridToChange[x + dist][y] = false;
      } catch (Exception e) {
      }

      for (int i = y - dist + 1; i < y; i++, offset++) {
        try {
          gridToChange[x - offset][i] = false;
        } catch (Exception e) {
        }
        try {
          gridToChange[x + offset][i] = false;
        } catch (Exception e) {
        }
        try {
          gridToChange[x - offset][y + dist - offset] = false;
        } catch (Exception e) {
        }
        try {
          gridToChange[x + offset][y + dist - offset] = false;
        } catch (Exception e) {
        }
      }
    }
  }

  /**
   * This function tests if a path could be found with the current grid. We use a
   * Variation of Dijkstra's Algorithm, but instead of keeping track of current
   * path length, we calculate each new nodes distance from the destination
   * because it is not very costly to do so since each node's distance to each
   * adjacent node is the same, and it is a grid world.
   * 
   * @return true if a path could be found, else false
   */
  public boolean checkGrid(boolean[][] gridToCheck) {

    boolean[][] gridCopy = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) {
      gridCopy[i] = gridToCheck[i].clone();
    }

    ArrayList<int[]> openNodes = new ArrayList<int[]>();
    ArrayList<Integer> nodeValue = new ArrayList<Integer>();

    int x = 0;
    int y = 0;

    // Checks if starting point is available.
    if (!gridCopy[y][x]) {
      return false;
    }
    // Initializes root as first available node.
    int[] root = { y, x };
    openNodes.add(root);
    nodeValue.add(rows + cols);

    do {
      // Retrive best node.
      int[] currentIndex = openNodes.get(openNodes.size() - 1).clone();

      // If current Node is the end node, return true
      // 0 = y axis value, 1 = x axis value
      if (currentIndex[0] == rows - 1 && currentIndex[1] == cols - 1) {
        return true;
      }

      // Remove node, and mark coordinate false in gridCopy so we don't visit it
      // again.
      openNodes.remove(openNodes.size() - 1);
      nodeValue.remove(nodeValue.size() - 1);
      gridCopy[currentIndex[0]][currentIndex[1]] = false;

      // Checks if we can add each 4 adjacent nodes of current node to openNodes.
      if (// North Node
      currentIndex[0] - 1 > 0 && gridCopy[currentIndex[0] - 1][currentIndex[1]]) {

        // Add new node to openNodes and its value to nodeValues.
        int[] newNode = { currentIndex[0] - 1, currentIndex[1] };
        openNodes.add(newNode);
        nodeValue.add(rows - currentIndex[0] - 1 + cols - currentIndex[1]);
        // Place node in proper place, so that node closest to destination is always on
        // top (highest index) - Bubble Sort Algorithm
        for (int i = nodeValue.size() - 1; i > 0; i--) {
          if (nodeValue.get(i - 1) < nodeValue.get(i)) {
            int[] tempNode = openNodes.get(i).clone();
            int tempValue = nodeValue.get(i);

            openNodes.set(i, openNodes.get(i - 1).clone());
            nodeValue.set(i, nodeValue.get(i - 1));

            openNodes.set(i - 1, tempNode);
            nodeValue.set(i - 1, tempValue);
          }
        }
      }
      if (// East Node
      currentIndex[1] + 1 < cols && gridCopy[currentIndex[0]][currentIndex[1] + 1]) {

        // Add new node to openNodes and its value to nodeValues.
        int[] newNode = { currentIndex[0], currentIndex[1] + 1 };
        openNodes.add(newNode);
        nodeValue.add(rows - currentIndex[0] + cols - currentIndex[1] + 1);

        // Place node in proper place, so that node closest to destination is always on
        // top (highest index) - Bubble Sort Algorithm
        for (int i = nodeValue.size() - 1; i > 0; i--) {
          if (nodeValue.get(i - 1) < nodeValue.get(i)) {
            int[] tempNode = openNodes.get(i).clone();
            int tempValue = nodeValue.get(i);

            openNodes.set(i, openNodes.get(i - 1).clone());
            nodeValue.set(i, nodeValue.get(i - 1));

            openNodes.set(i - 1, tempNode);
            nodeValue.set(i - 1, tempValue);
          }
        }
      }
      if (// South Node
      currentIndex[0] + 1 < rows && gridCopy[currentIndex[0] + 1][currentIndex[1]]) {

        // Add new node to openNodes and its value to nodeValues.
        int[] newNode = { currentIndex[0] + 1, currentIndex[1] };
        openNodes.add(newNode);
        nodeValue.add(rows - currentIndex[0] + 1 + cols - currentIndex[1]);

        // Place node in proper place, so that node closest to destination is always on
        // top (highest index) - Bubble Sort Algorithm
        for (int i = nodeValue.size() - 1; i > 0; i--) {
          if (nodeValue.get(i - 1) < nodeValue.get(i)) {
            int[] tempNode = openNodes.get(i).clone();
            int tempValue = nodeValue.get(i);

            openNodes.set(i, openNodes.get(i - 1).clone());
            nodeValue.set(i, nodeValue.get(i - 1));

            openNodes.set(i - 1, tempNode);
            nodeValue.set(i - 1, tempValue);
          }
        }
      }
      if (// West Node
      currentIndex[1] - 1 > 0 && gridCopy[currentIndex[0]][currentIndex[1] - 1]) {

        // Add new node to openNodes and its value to nodeValues.
        int[] newNode = { currentIndex[0], currentIndex[1] - 1 };
        openNodes.add(newNode);
        nodeValue.add(rows - currentIndex[0] + cols - currentIndex[1] - 1);

        // Place node in proper place, so that node closest to destination is always on
        // top (highest index) - Bubble Sort Algorithm
        for (int i = nodeValue.size() - 1; i < 0; i--) {
          if (nodeValue.get(i - 1) > nodeValue.get(i)) {
            int[] tempNode = openNodes.get(i).clone();
            int tempValue = nodeValue.get(i);

            openNodes.set(i, openNodes.get(i - 1).clone());
            nodeValue.set(i, nodeValue.get(i - 1));

            openNodes.set(i - 1, tempNode);
            nodeValue.set(i - 1, tempValue);
          }
        }
      }
    } while (openNodes.size() > 0);

    return false;
  }
}
