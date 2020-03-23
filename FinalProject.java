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

/**
 * The main class of the application.
 * 
 * @author Macguire Rintoul
 * @author https://macguire.me
 */
public class FinalProject extends Frame {
	BufferedImage image; // holds the image loaded for processing
	ArrayList<Line2D.Double> lines; // holds the list of generated 'strokes'
	int width; // width of the loaded image
	int height; // height of the loaded image
	/**
	 * Whether or not the stroke direction is determined by the surrounding colour
	 * gradient
	 */
	boolean isOrientationByGradient = true;
	boolean isDebugMode = false; // enable for debugging features
	Utilities ut = new Utilities(); // utility functions

	/*
	 * EXPOSED PARAMETERS
	 */
	double maxStrokeRadius = 12; // maximum stroke radius
	double minStrokeRadius = 4; // minimum stroke radius
	double maxStrokeLength = 20; // maximum stroke length
	double minStrokeLength = 4; // minimum stroke length
	double maxSrokeAngle = 60; // maximum stroke angle (if isOrientationByGradient is false)
	double minStrokeAngle = 30; // minimum stroke angle (if isOrientationByGradient is false)
	int pixelInterval = 4; // how many pixels between each stroke (e.g. 4 = 0,0 -> 4,0)

	/**
	 * Creates the FinalProject class.
	 * 
	 * @return FinalProject
	 */
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
		UIControlWindow uiControls = new UIControlWindow(this);
		uiControls.display();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Sets the allowed range of values for a given application parameter (e.g.
	 * radius)
	 * 
	 * @param param one of the app parameters
	 * @param min   the minimum allowed value for the parameter
	 * @param max   the maximum allowed value for the parameter
	 */
	public void setRange(Parameter param, int min, int max) {
		switch (param) {
			case radius:
				this.minStrokeRadius = min;
				this.maxStrokeRadius = max;
				break;
			case length:
				this.minStrokeLength = min;
				this.maxStrokeLength = max;
				break;
			default:
				break;
		}
		repaint();
	}

	public void paint(final Graphics g) {
		System.out.println("Calling paint...");

		final ArrayList<Line> lines = generateStrokes(image);
		Utilities.shuffle(lines);
		drawLines(lines, g);

		System.out.println("Paint completed.");
	}

	/**
	 * Render the given lines in the application.
	 * 
	 * @param lines the generated strokes to be drawn
	 * @param g
	 */
	private void drawLines(final ArrayList<Line> lines, final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		for (final Line line : lines) {
			g2.setColor(line.color);
			g2.setStroke(new BasicStroke((int) ut.randomDoubleBetween(minStrokeRadius, maxStrokeRadius),
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);

			if (isDebugMode) {
				// draw the origin of the line
				g2.setColor(Color.red);
				g2.setStroke(new BasicStroke(1));
				g2.drawLine((int) line.x1, (int) line.y1, (int) line.x1, (int) line.y1);
			}
		}
	}

	/**
	 * Generate strokes based on the given image
	 * 
	 * @param img the image to generate strokes for
	 * @return the list of generated lines that will later be drawn
	 */
	private ArrayList<Line> generateStrokes(final BufferedImage img) {
		System.out.println("Generating strokes...");
		final ArrayList<Line> lines = new ArrayList<Line>();

		for (int x = 0; x < width; x = x + pixelInterval) {
			for (int y = 0; y < height; y = y + pixelInterval) {
				try {
					final double length = ut.randomDoubleBetween(minStrokeLength, maxStrokeLength);
					float angle;
					double angleInDegrees;
					if (isOrientationByGradient) {
						angleInDegrees = ut.getOrientationForPixel(img, x, y);
					} else {
						// 0 degree angle is vertical
						angleInDegrees = ut.randomDoubleBetween(minStrokeAngle, maxSrokeAngle);
					}
					angle = (float) Math.toRadians(angleInDegrees);

					final int endX = (int) (x + length * Math.sin(angle));
					final int endY = (int) (y + length * Math.cos(angle));
					lines.add(new Line(x, y, endX, endY, new Color(image.getRGB(x, y))));
				} catch (final Exception e) {
					System.out.println(e);
				}
			}
		}
		System.out.println("Strokes generated.");
		return lines;
	}

	public static void main(final String[] args) {
		System.out.println("Calling main...");
		new FinalProject();
	}
}