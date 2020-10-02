package etude06;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * A Class that draws toothpicks/lines perpendicular and at the ends of those
 * already drawn. It extends JComponent Class and needs a JFrame to be placed
 * upon.
 * 
 * @author Elbert Alcantara
 *
 */
public class toothPickDrawer extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int iterations;
	private float sizeCoeff;
	private float windowLength;
	private float windowWidth;
	private float firstPickSize;

	// Each array will contain 6 values describing a pick.
	// 0: x coordinate of left/top edge 1: y coordinate of left/top edge
	// 2: x for right/bottom 3: y for right/bottom
	// 4: length 5: 0 if horizontal, and 1 if vertical
	ArrayList<float[]> Picks = new ArrayList<float[]>();
	float[] firstPick;

	/**
	 * Constructor to initialize data fields iterations and sizeCoeff.
	 * 
	 * @param iterations    How many iterations to draw toothPicks at
	 *                      currently(current iteration) unoccupied ends.
	 * @param sizeCoeff     Length of tooth picks will be tooth pick length of the
	 *                      preceding iteration multiplied by sizeCoeff.
	 * @param windowLength
	 * @param windowWidth
	 * @param firstPickSize The larger it is, the smaller the pick.
	 */
	public toothPickDrawer(int iterations, float sizeCoeff, float windowLength, float windowWidth,
			float firstPickSize) {
		this.iterations = iterations;
		this.sizeCoeff = sizeCoeff;
		this.windowLength = windowLength;
		this.windowWidth = windowWidth;
		this.firstPickSize = firstPickSize;
	}

	/**
	 * This function will use a for loop that will go through 2^(iterations) loops
	 * which equals the number of picks that will be drawn. The first one has to be
	 * added and drawn manually to start of the process. Each loop, two picks are
	 * added to the array list of picks.
	 * 
	 * @param g Graphics2D object to draw picks on.
	 * @return g where the tooth picks have been drawn on.
	 */
	public Graphics2D drawPicks(Graphics2D g) {
		/*
		 * This section ensures that no matter the size coefficient, the diagram will
		 * fit within the boundaries of the window.
		 */
		float projectedLengthX = firstPickSize;
		float projectedLengthY = 0f;
		float currentLength = firstPickSize;
		for (int i = 0; i < iterations; i++) {
			if (i % 2 == 0) {
				currentLength *= sizeCoeff;
				projectedLengthY += currentLength;
			} else {
				currentLength *= sizeCoeff;
				projectedLengthX += currentLength;
			}
		}
		if ((float) windowLength / (float) windowWidth < projectedLengthX / projectedLengthY) {
			firstPickSize *= (windowLength - 150) / projectedLengthX;
		} else {
			firstPickSize *= (windowWidth - 150) / projectedLengthY;
		}

		/*
		 * This section creates the initial toothpick and adds it the array, all other
		 * toothpicks will be derived from this first one.
		 */
		float centreX = windowLength / 2;
		float centreY = windowWidth / 2;
		float Length = firstPickSize;
		float[] first = { (centreX * 0.965f) - (Length / 2), centreY * 0.958f, (centreX * 0.965f) + (Length / 2),
				centreY * 0.958f, Length, 0 };
		firstPick = first;
		Picks.add(firstPick);
		g.drawLine((int) Picks.get(0)[0], (int) Picks.get(0)[1], (int) Picks.get(0)[2], (int) Picks.get(0)[3]);

		/*
		 * This is the for loop that will derive all other picks and draw them on the
		 * Panel from the initial toothpick.
		 */
		for (int i = 0; i < Math.pow(2, iterations) - 1; i++) {
			float[] newPickOne = new float[6];
			float[] newPickTwo = new float[6];

			// Calculates the new length of the picks
			newPickOne[4] = Picks.get(i)[4] * sizeCoeff;
			newPickTwo[4] = newPickOne[4];

			if (Picks.get(i)[5] == 0) {// 0 means the parent pick is horizontal.
				// Since horizontal, the two new picks must be vertical.
				newPickOne[5] = 1;
				newPickTwo[5] = 1;

				// X values are equal for both ends
				newPickOne[0] = Picks.get(i)[0];
				newPickOne[2] = Picks.get(i)[0];
				newPickTwo[0] = Picks.get(i)[2];
				newPickTwo[2] = Picks.get(i)[2];

				// initializing Y values
				newPickOne[1] = Picks.get(i)[1] + newPickOne[4] / 2;
				newPickOne[3] = Picks.get(i)[1] - newPickOne[4] / 2;
				newPickTwo[1] = Picks.get(i)[3] + newPickTwo[4] / 2;
				newPickTwo[3] = Picks.get(i)[3] - newPickTwo[4] / 2;

			} else {// If not horizontal, it has to be vertical.
				// Since vertical, the two new picks must be horizontal.
				newPickOne[5] = 0;
				newPickTwo[5] = 0;

				// Y values are equal for both ends
				newPickOne[1] = Picks.get(i)[1];
				newPickOne[3] = Picks.get(i)[1];
				newPickTwo[1] = Picks.get(i)[3];
				newPickTwo[3] = Picks.get(i)[3];

				// initializing X values
				newPickOne[0] = Picks.get(i)[0] + newPickOne[4] / 2;
				newPickOne[2] = Picks.get(i)[0] - newPickOne[4] / 2;
				newPickTwo[0] = Picks.get(i)[2] + newPickTwo[4] / 2;
				newPickTwo[2] = Picks.get(i)[2] - newPickTwo[4] / 2;
			}

			// We add the two new picks to the array list of picks.
			Picks.add(newPickOne);
			Picks.add(newPickTwo);

			// We draw both new picks into g.
			g.drawLine((int) Math.round(newPickOne[0]), (int) Math.round(newPickOne[1]),
					(int) Math.round(newPickOne[2]), (int) Math.round(newPickOne[3]));
			g.drawLine((int) Math.round(newPickTwo[0]), (int) Math.round(newPickTwo[1]),
					(int) Math.round(newPickTwo[2]), (int) Math.round(newPickTwo[3]));
		}
		return g;
	}

	/**
	 * paintComponent for drawing graphics.
	 */
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g = drawPicks(g);
	}
}
