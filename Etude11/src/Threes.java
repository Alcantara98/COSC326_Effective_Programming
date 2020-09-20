/**
 * Class for finding the first 70 sets of x, y, z where x, y, z must be positive
 * integers, z < x < y, and each set must be relatively CoPrime. Date Completed:
 * 15/08/20 ID: 4435223
 * 
 * @author Elbert Alcantara
 *
 */
public class Threes {
	/**
	 * Runs the algorithm for finding the 70 sets when raising x and z
	 * incrementally.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int x = 3;
		int y = 5;
		int z = 1;

		// The first for loop finds the first 70 sets while raising x incrementally.
		for (int i = 1; i < 71;) {
			while (true) {
				double yDouble = Math.sqrt(Math.pow(z, 3) + 1 - Math.pow(x, 2));
				if (yDouble < x || z < 0) {
					break;
				}
				if (yDouble % 1 == 0) {
					y = (int) yDouble;
					if (isCoPrime(y, x) && isCoPrime(y, z) && isCoPrime(x, z)) {
						System.out.printf("%d %d %d %d", i, x, y, z);
						System.out.println("");
						i += 1;
					}
				}
				z -= 2;
			}
			z = x;
			x += 2;
		}

		System.out.println("");
		x = 3;
		y = 5;
		z = 1;

		// The second for loop finds the first 70 sets while raising z incrementally
		for (int i = 1; i < 71;) {
			while (true) {

				double yDouble = Math.sqrt(Math.pow(z, 3) + 1 - Math.pow(x, 2));
				if (yDouble < x || Double.isNaN(yDouble)) {
					break;
				}
				if (yDouble % 1 == 0 && yDouble > x) {
					y = (int) yDouble;
					if (isCoPrime(y, x) && isCoPrime(y, z) && isCoPrime(x, z)) {
						System.out.printf("%d %d %d %d", i, x, y, z);
						System.out.println("");
						i += 1;
						if (i == 71) {
							break;
						}
					}
				}
				x += 2;
			}
			x = z;
			z += 2;
		}
	}

	/**
	 * This function determines whether two numbers are CoPrime.
	 * 
	 * @param candidateOne The function assumes candidateOne to be larger or equal
	 *                     to candidateTwo.
	 * @param candidateTwo Has to be smaller or equal to candidateOne.
	 * @return boolean, If greatest common factor is 1, it returns true, else false.
	 */
	public static boolean isCoPrime(int candidateOne, int candidateTwo) {
		int remainder = candidateOne % candidateTwo;
		while (remainder != 0) {
			candidateOne = candidateTwo;
			candidateTwo = remainder;
			remainder = candidateOne % candidateTwo;
		}
		if (remainder == 0) {
			if (candidateTwo == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
