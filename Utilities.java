import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Utility functions used in the project.
 * 
 * @author Macguire Rintoul
 * @author https://macguire.me
 */
class Utilities {
	Random random = new Random();

	/**
	 * Generate a random double between two values.
	 * 
	 * @param min the minimum value of the returned double
	 * @param max the maximum value of the returned double
	 * @return a double between the provided values
	 */
	public double randomDoubleBetween(double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}

	/**
	 * Shuffle an ArrayList. From
	 * https://www.techiedelight.com/shuffle-randomize-list-java/
	 * 
	 * @param list the ArrayList to shuffle
	 */
	public static <T> void shuffle(ArrayList<T> list) {
		Collections.shuffle(list);
	}

	/**
	 * Computes the 'distance' between one colour and another. Based on Thiadmer
	 * Riemersma's paper at https://www.compuphase.com/cmetric.htm. Implementation
	 * from https://stackoverflow.com/a/6334454/8517613
	 * 
	 * @param c1 one colour to compare
	 * @param c2 the other colour to compare
	 * @return the distance between the colours
	 */
	double colorDistance(Color c1, Color c2) {
		int red1 = c1.getRed();
		int red2 = c2.getRed();
		int rmean = (red1 + red2) >> 1;
		int r = red1 - red2;
		int g = c1.getGreen() - c2.getGreen();
		int b = c1.getBlue() - c2.getBlue();
		return Math.sqrt((((512 + rmean) * r * r) >> 8) + 4 * g * g + (((767 - rmean) * b * b) >> 8));
	}

	/**
	 * Determine the angle that a stroke should have for a given pixel.
	 * 
	 * @param img the image to analyze
	 * @param x   the x coordinate of the pixel to analyze
	 * @param y   the y coordinate of the pixel to analyze
	 * @return the angle that the stroke should be
	 */
	public double getOrientationForPixel(BufferedImage img, int x, int y) {
		ArrayList<Integer> pixels = new ArrayList<Integer>(8);
		pixels.add(img.getRGB(x - 1, y - 1));
		pixels.add(img.getRGB(x, y - 1));
		pixels.add(img.getRGB(x + 1, y - 1));
		pixels.add(img.getRGB(x - 1, y));
		pixels.add(img.getRGB(x + 1, y));
		pixels.add(img.getRGB(x - 1, y + 1));
		pixels.add(img.getRGB(x, y + 1));
		pixels.add(img.getRGB(x + 1, y + 1));

		// set an initial lowest distance
		double lowestDistance = colorDistance(new Color(img.getRGB(x, y)), new Color(pixels.get(0)));
		int closestPixel = 0;

		// for each one of the surrounding pixels,
		// System.out.println("Starting pixel " + x + ", " + y);
		for (int i = 0; i < pixels.size(); i++) {
			double distance = colorDistance(new Color(img.getRGB(x, y)), new Color(pixels.get(i)));
			// System.out.println(i + ": " + distance);
			// if this pixel's distance is smaller than the current lowest distance...
			if (distance < lowestDistance) {
				// set the new record for lowest distance
				lowestDistance = distance;
				// keep track of this pixel's location

				closestPixel = i;
				// System.out.println("Pixel " + closestPixel + " was closer");
			}
		}

		// System.out.println("Closest pixel: " + closestPixel);
		double angle = 0;
		switch (closestPixel) {
			case 0:
				// System.out.println("Closest pixel was top left");
				angle = -135;
				break;
			case 1:
				// System.out.println("Closest pixel was top middle");
				angle = 180;
				break;
			case 2:
				// System.out.println("Closest pixel was top right");
				angle = 135;
				break;
			case 3:
				// System.out.println("Closest pixel was left");
				angle = -90;
				break;
			case 4:
				// System.out.println("Closest pixel was right");
				angle = 90;
				break;
			case 5:
				// System.out.println("Closest pixel was bottom left");
				angle = -45;
				break;
			case 6:
				// System.out.println("Closest pixel was bottom");
				angle = 0;
				break;
			case 7:
				// System.out.println("Closest pixel was bottom right");
				angle = 45;
				break;
			default:
				// System.out.println("Colour detection failed.");
				break;
		}
		// return 30;
		return angle;
	}
}