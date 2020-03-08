import java.util.Random;

class Utilities {
	Random random = new Random();

	public double randomDoubleBetween(double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}
}