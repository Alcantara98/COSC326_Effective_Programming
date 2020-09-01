package etude06;

import javax.swing.JFrame;;

/**
 * Main Class to run toothPickDrawer. Also used to initialize window size,
 * iterations to reach when drawing the picks and size coefficient as we go
 * through each iteration.
 * 
 * @author Elbert Alcantara
 *
 */
public class mainClass {
	/**
	 * Will either take 1, 2, or 4 command line arguments: 1: iterations to reach 2:
	 * size change coefficient 3: windowLength 4: windowWidth 5: size of first pick
	 * = windowLength / firstPickSize
	 * 
	 * Default will draw the diagram provided in the PDF for ToothPicks.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int iterations = 15;
		float sizeCoeff = 0.76f;
		int windowLength = 1600;
		int windowWidth = 900;
		float firstPickSize = 4f;

		// Checks if there are command line arguments
		if (args.length == 1) {
			iterations = Integer.parseInt(args[0]);
		} else if (args.length == 2) {
			iterations = Integer.parseInt(args[0]);
			sizeCoeff = Float.parseFloat(args[1]);
		}

		// Creates a new JFrame to place our JComponent on.
		JFrame window = new JFrame();
		window.setSize(windowLength, windowWidth);
		window.setTitle("Tooth Picks Baby!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

		toothPickDrawer comp = new toothPickDrawer(iterations, sizeCoeff, (float) windowLength, (float) windowWidth,
				firstPickSize);
		window.add(comp);
	}
}
