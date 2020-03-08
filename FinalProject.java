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
	boolean isDebugMode = false;
	double maxStrokeRadius = 10;
	double minStrokeRadius = 5;
	double maxStrokeLength = 12;
	double minStrokeLength = 4;
	double maxSrokeAngle = 60;
	double minStrokeAngle = 30;
	int pixelInterval = 4;
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
		Utilities.shuffle(lines);
		drawLines(lines, g);
		System.out.println("Paint completed.");
	}

	private void drawLines(ArrayList<Line> lines, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (Line line : lines) {
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
						angleInDegrees = ut.getOrientationForPixel(img, x, y);
					} else {
						// 0 degree angle is vertical
						angleInDegrees = ut.randomDoubleBetween(minStrokeAngle, maxSrokeAngle);
					}
					angle = (float) Math.toRadians(angleInDegrees);

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