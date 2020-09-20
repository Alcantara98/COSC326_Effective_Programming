import java.util.ArrayList;

public class SocialDistance {
	// We will be working within these bounds.
	private final static int MAXGRIDWIDTH = 100, MAXGRIDHEIGHT = 100, MAXSEATED = 500;

	// grid dimensions
	private int gridWidth, gridHeight;

	// An array of int pairs which are meant to represent seated people.
	private ArrayList<int[]> seatedPoints;

	// Contains the distance of the closest point from current path to the
	// corresponding seated point.
	private ArrayList<Integer> closestDistance = new ArrayList<Integer>();

	/*
	 * Keeps track of which tiles we have visited. 0 = not visited 1 = visited
	 */
	private int[][] visitedPoints;

	private ArrayList<int[]> path = new ArrayList<int[]>();

	private int currentX = 0;
	private int currentY = 0;
	private int currentDistance = 0;

	public SocialDistance(int gridWidth, int gridHeight, ArrayList<int[]> seatedPoints) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.seatedPoints = seatedPoints;

		for (int[] a : visitedPoints) {
			for (int b : a) {
				b = 0;
			}
		}
		int[] initialCoordinates = { 0, 0 };
		path.add(initialCoordinates);
	}

	public int distanceFromPath(int x1, int y1, int x2, int y2) {
		int x3 = Math.abs(x1 - x2);
		int y3 = Math.abs(y1 - y2);
		return x3 + y3;

	}

	public double absDistance(int x1, int y1, int x2, int y2) {
		int x3 = Math.abs(x1 - x2);
		int y3 = Math.abs(y1 - y2);
		return Math.sqrt(Math.pow((double) x3, 2) + Math.pow((double) y3, 2));
	}

	public void compute() {
		int shortestPathFound = 0;
		int east = 0;
		int west = 0;
		int north = 0;
		int south = 0;

		while (currentX != MAXGRIDWIDTH && currentY != MAXGRIDHEIGHT) {
			// We check all four possible directions and find the total distance from all
			// seated points.

			// If forwardPath remains false, we calculate either north or west which
			// leads us further from our destination.
			boolean forwardPath = false;
			if (currentY + 1 >= 0 && visitedPoints[currentX][currentY + 1] == 0) {
				for (int i = 0; i < seatedPoints.size(); i++) {
					int[] seatCoord = seatedPoints.get(i);
					south += distanceFromPath(seatCoord[0], seatCoord[1], currentX, currentY + 1);
					forwardPath = true;
				}
			}
			if (currentX + 1 >= 0 && visitedPoints[currentX + 1][currentY] == 0) {
				for (int i = 0; i < seatedPoints.size(); i++) {
					int[] seatCoord = seatedPoints.get(i);
					east += distanceFromPath(seatCoord[0], seatCoord[1], currentX + 1, currentY);
				}
				forwardPath = true;
			}
			// No path closer to destination found.
			if (!forwardPath) {
				if (currentX - 1 >= 0 && visitedPoints[currentX - 1][currentY] == 0) {
					for (int i = 0; i < seatedPoints.size(); i++) {
						int[] seatCoord = seatedPoints.get(i);
						west += distanceFromPath(seatCoord[0], seatCoord[1], currentX - 1, currentX);
					}
				}
				if (currentY - 1 >= 0 && visitedPoints[currentX][currentY - 1] == 0) {
					for (int i = 0; i < seatedPoints.size(); i++) {
						int[] seatCoord = seatedPoints.get(i);
						north += distanceFromPath(seatCoord[0], seatCoord[1], currentX, currentY - 1);
					}
				}
			}
			// If all are 0, then there is no path we have not visited hence we have to
			// backtrack to find a new path.
			if (east == 0 && west == 0 && north == 0 && south == 0) {
				path.remove(path.size() - 1);
				int[] newCoordinates = path.get(path.size() - 1);
				currentX = newCoordinates[0];
				currentY = newCoordinates[1];
			} else {
				if (forwardPath) {
					if (south > east) {
						currentY += 1;
					} else {
						currentX += 1;
					}
					int[] newCoordinates = { currentX, currentY };
					path.add(newCoordinates);
				} else {
					if (north > west) {
						currentY -= 1;
					} else {
						currentX -= 1;
					}
					int[] newCoordinates = { currentX, currentY };
					path.add(newCoordinates);
				}
			}
		}
	}
}