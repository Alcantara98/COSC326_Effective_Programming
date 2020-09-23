import java.util.ArrayList;

public class Distance {
    private int rows, cols;
    private boolean[][] grid;
    private ArrayList<int[]> seatedPeople;

    public Distance(int x, int y, ArrayList<int[]> seatedArray) {
        rows = x;
        cols = y;
        grid = new boolean[rows][cols];
        seatedPeople = new ArrayList<int[]>(seatedArray);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = true;
            }
        }

    }

    public void run() {

    }

    public boolean checkGrid(boolean[][] thing) {
    	rows = 9; 
        return false;
    }
}
