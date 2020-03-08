import java.util.Random;
import java.awt.Color;

class Utilities {
	Random random = new Random();

	public double randomDoubleBetween(double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}

	double colorDistance(Color c1, Color c2) {
		// from https://stackoverflow.com/a/6334454/8517613
		int red1 = c1.getRed();
		int red2 = c2.getRed();
		int rmean = (red1 + red2) >> 1;
		int r = red1 - red2;
		int g = c1.getGreen() - c2.getGreen();
		int b = c1.getBlue() - c2.getBlue();
		return Math.sqrt((((512 + rmean) * r * r) >> 8) + 4 * g * g + (((767 - rmean) * b * b) >> 8));
	}
}