public class Threes2 {
	public static void main(String[] args) {
		int x = 3;
		int y = 4;
		int z = 2;

		long startTime = System.nanoTime();
		for (int i = 1; i < 71;) {
			while (true) {
				double yDouble = Math.sqrt(Math.pow(z, 3) + 1 - Math.pow(x, 2));
				if (yDouble < x) {
					break;
				}
				if (yDouble % 1 == 0) {
					y = (int) yDouble;
					if (!((x % 2 == 0 && (y % 2 == 0 || z % 2 == 0)) || (y % 2 == 0 && (x % 2 == 0 || z % 2 == 0)))
							&& isCoPrime(y, x) && isCoPrime(y, z) && isCoPrime(x, z)) {
						System.out.printf("%d %d %d %d", i, x, y, z);
						System.out.println("");
						i += 1;
					}
				}
				z -= 1;
			}
			z = x;
			x += 1;
		}
		long endTime = System.nanoTime();

		long timeElapsed = endTime - startTime;
		System.out.println(timeElapsed / (double) 1000000000);

		System.out.println("");
		x = 3;
		y = 4;
		z = 2;
		long startTimeTwo = System.nanoTime();
		for (int i = 1; i < 71;) {
			while (true) {
				double yDouble = Math.sqrt(Math.pow(z, 3) + 1 - Math.pow(x, 2));
				if (yDouble < x) {
					break;
				}
				if (yDouble % 1 == 0 && yDouble > x) {
					y = (int) yDouble;
					if (!((x % 2 == 0 && (y % 2 == 0 || z % 2 == 0)) || (y % 2 == 0 && (x % 2 == 0 || z % 2 == 0)))
							&& isCoPrime(y, x) && isCoPrime(y, z) && isCoPrime(x, z)) {
						System.out.printf("%d %d %d %d", i, x, y, z);
						System.out.println("");
						i += 1;
						if (i == 71) {
							break;
						}
					}
				}
				x += 1;
			}
			x = z;
			z += 1;
		}
		long endTimeTwo = System.nanoTime();

		long timeElapsedTwo = endTimeTwo - startTimeTwo;
		System.out.println(timeElapsedTwo / (double) 1000000000);

	}

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
