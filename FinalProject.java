import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

class FinalProject extends Frame {
	BufferedImage image;
	ArrayList<Line2D.Double> lines;
	int width;
	int height;
	boolean isOrientationByGradient = true;
	double maxStrokeRadius = 12;
	double minStrokeRadius = 4;
	double maxStrokeLength = 8;
	double minStrokeLength = 1;
	double maxSrokeAngle = 60;
	double minStrokeAngle = 30;
	int pixelInterval = 16;
	Utilities ut = new Utilities();

	public FinalProject() {
		try {
			image = ImageIO.read(new File("image.jpg"));
			/*
			 * // load and display image that the user specifies JFileChooser chooser = new
			 * JFileChooser(); FileNameExtensionFilter filter = new
			 * FileNameExtensionFilter("Images", "jpg", "png");
			 * chooser.setFileFilter(filter); int returnVal = chooser.showOpenDialog(null);
			 * if (returnVal == JFileChooser.APPROVE_OPTION) { image =
			 * ImageIO.read(chooser.getSelectedFile()); }
			 */
			width = image.getWidth();
			height = image.getHeight();
			this.setSize(width, height);
		} catch (Exception e) {
			System.out.println("Cannot load the provided image");
		}
		this.setTitle("Final Project");
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void paint(Graphics g) {
		System.out.println("Calling paint...");
		ArrayList<Line> lines = generateStrokes(image);
		drawLines(lines, g);
		System.out.println("Paint completed.");
	}

	private void drawLines(ArrayList<Line> lines, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (Line line : lines) {
			g2.setColor(line.color);
			g2.setStroke(new BasicStroke((int) ut.randomDoubleBetween(minStrokeRadius, maxStrokeRadius)));
			g2.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
		}
	}

	private float getOrientationForPixel(BufferedImage img, int x, int y) {
		// determine the 'colour distance' of each pixel around the center pixel
		// if p1 is closest = -45
		// if p2 is closest = 0
		// if p3 is closest = 45
		// ... etc
		ArrayList<Integer> pixels = new ArrayList<Integer>(8);
		pixels.add(img.getRGB(x - 1, y - 1));
		pixels.add(img.getRGB(x, y - 1));
		pixels.add(img.getRGB(x + 1, y - 1));
		pixels.add(img.getRGB(x - 1, y));
		// pixels.add(img.getRGB(x, y));
		pixels.add(img.getRGB(x + 1, y));
		pixels.add(img.getRGB(x - 1, y + 1));
		pixels.add(img.getRGB(x, y + 1));
		pixels.add(img.getRGB(x + 1, y + 1));

		// set an initial lowest distance
		double lowestDistance = ut.colorDistance(new Color(img.getRGB(x, y)), new Color(pixels.get(0)));
		int closestPixel = 0;
		for (int pixel : pixels) {
			double distance = ut.colorDistance(new Color(img.getRGB(x, y)), new Color(pixel));
			// if this pixel's distance is smaller than the current lowest distance...
			if (distance < lowestDistance) {
				// keep track of this pixel's location
				closestPixel = pixels.indexOf(pixel);
			}
		}
		System.out.println("Closest pixel: " + closestPixel);
		float angle = 0;
		switch (closestPixel) {
			case 0:
				System.out.println("Closest pixel was top left");
				angle = -45;
				break;
			case 1:
				System.out.println("Closest pixel was top middle");
				angle = 0;
				break;
			case 2:
				System.out.println("Closest pixel was top right");
				angle = 45;
				break;
			case 3:
				System.out.println("Closest pixel was left");
				angle = -90;
				break;
			case 4:
				System.out.println("Closest pixel was right");
				angle = 90;
				break;
			case 5:
				System.out.println("Closest pixel was bottom left");
				angle = -135;
				break;
			case 6:
				System.out.println("Closest pixel was bottom");
				angle = 180;
				break;
			case 7:
				System.out.println("Closest pixel was bottom right");
				angle = 135;
				break;
			default:
				System.out.println("Colour detection failed.");
				break;
		}
		return angle;
	}

	private ArrayList<Line> generateStrokes(BufferedImage img) {
		System.out.println("Generating strokes...");
		ArrayList<Line> lines = new ArrayList<Line>();

		for (int x = 0; x < width; x = x + pixelInterval) {
			for (int y = 0; y < height; y = y + pixelInterval) {
				try {
					double length = ut.randomDoubleBetween(minStrokeLength, maxStrokeLength);
					float angle;
					double angleInDegrees;
					if (isOrientationByGradient) {
						angleInDegrees = getOrientationForPixel(img, x, y);
					} else {
						// 0 degree angle is vertical
						angleInDegrees = ut.randomDoubleBetween(minStrokeAngle, maxSrokeAngle);
					}
					angle = (float) (angleInDegrees * Math.PI / 180);
					int endX = (int) (x + length * Math.sin(angle));
					int endY = (int) (y + length * Math.cos(angle));
					lines.add(new Line(x, y, endX, endY, new Color(image.getRGB(x, y))));
				} catch (Exception e) {
					// print exception
				}
			}
		}
		System.out.println("Strokes generated.");
		return lines;
	}

	public static void main(String[] args) {
		System.out.println("Calling main...");
		new FinalProject();
	}
}