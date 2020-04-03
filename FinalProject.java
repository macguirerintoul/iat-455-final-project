import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The main class of the application.
 * 
 * @author Macguire Rintoul
 * @author https://macguire.me
 */
public class FinalProject extends Frame {
	BufferedImage image; // holds the image loaded for processing
	BufferedImage textureImage; // holds the image loaded for processing
	ArrayList<Line2D.Double> lines; // holds the list of generated 'strokes'
	int width; // width of the loaded image
	int height; // height of the loaded image
	boolean isDebugMode = true; // enable for debugging features
	Utilities ut = new Utilities(); // utility functions

	int minAllowedPixelInterval = 1; // minimum allowed pixels between strokes
	int maxAllowedPixelInterval = 12; // maximum allowed pixels between strokes
	int minAllowedStrokeRadius = 1; // minimum allowed stroke radius
	int maxAllowedStrokeRadius = 16; // maximum allowed stroke radius
	int maxAllowedStrokeLength = 20; // maximum allowed stroke length
	int minAllowedStrokeLength = 1; // minimum allowed stroke length

	/*
	 * EXPOSED PARAMETERS
	 */
	boolean isOrientationByGradient = true; // stroke angle determined by surrounding gradient? T/F
	boolean isShuffleStrokes = true; // shuffle strokes before drawing? T/F
	int maxStrokeRadius = 12; // maximum stroke radius
	int minStrokeRadius = 4; // minimum stroke radius
	int maxStrokeLength = 20; // maximum stroke length
	int minStrokeLength = 4; // minimum stroke length
	int maxSrokeAngle = 60; // maximum stroke angle (if isOrientationByGradient is false)
	int minStrokeAngle = 30; // minimum stroke angle (if isOrientationByGradient is false)
	int pixelInterval = 4; // how many pixels between each stroke (e.g. 4 = 0,0 -> 4,0)

	/**
	 * Create the FinalProject class.
	 * 
	 * @return FinalProject
	 */
	public FinalProject() {
		loadImage(); // Allow the user to load an image into the app
		this.setTitle("Final Project"); // Set the title of the painting window
		this.setVisible(true); // Show the painting window
		UIControlWindow uiControls = new UIControlWindow(this); // Create the UI control window
		uiControls.display(); // Show the UI control window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Allow the user to load an image into the app.
	 * 
	 */
	private void loadImage() {
		try {
			if (isDebugMode) {
				image = ImageIO.read(new File("LargeImage.jpg"));
			} else {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					image = ImageIO.read(chooser.getSelectedFile());
				}
			}

			// if the image is larger than the screen, scale the image down
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			int screenWidth = gd.getDisplayMode().getWidth();
			int screenHeight = gd.getDisplayMode().getHeight();

			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();

			// If the image is larger than the screen in either orientation...
			if (imageWidth > screenWidth || imageHeight > screenHeight) {
				int newWidth = 400;
				int newHeight = 400;

				// If the image is in landscape orientation...
				if (imageWidth > imageHeight) {
					// The new width should be the width of the screen
					newWidth = screenWidth;
					// The new height should be...
					newHeight = (int) (imageHeight * (float) screenHeight / imageHeight);
				} else {
					// TODO
					// Image is in portrait; new height should be the height of the screen
				}
				image = resizeImage(image, newWidth, newHeight);
				width = image.getWidth();
				height = image.getHeight();
			} else {
				width = imageWidth;
				height = imageHeight;
			}
			this.setSize(width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Capture a screenshot of a component and save it to a file.
	 * 
	 * @param component the component to capture
	 * @return true if image saved successfully
	 */
	public boolean captureComponent(Component component) {
		Rectangle rect = component.getBounds();

		try {
			String format = "png";
			String fileName = "YourPainting." + format;
			BufferedImage captureImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
			component.paint(captureImage.getGraphics());
			ImageIO.write(captureImage, format, new File(fileName));
			System.out.printf("The screenshot was saved.");
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Resize an image.
	 * 
	 * @param originalImage the image to resize
	 * @param width         the width to resize to
	 * @param height        the height to resize to
	 * @return the resized image
	 */
	private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	/**
	 * Set the range of values for a given application parameter (e.g. stroke
	 * radius)
	 * 
	 * @param param one of the app parameters
	 * @param min   the minimum value for the parameter
	 * @param max   the maximum value for the parameter
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

	/**
	 * Paint the app window.
	 * 
	 * @param g
	 */
	public void paint(final Graphics g) {
		System.out.println("Calling paint...");
		final ArrayList<Line> lines = generateStrokes(image);
		if (isShuffleStrokes) {
			Utilities.shuffle(lines);
		}
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

	/**
	 * Start the app.
	 * 
	 * @param args arguments
	 */
	public static void main(final String[] args) {
		System.out.println("Calling main...");
		new FinalProject();
	}
}