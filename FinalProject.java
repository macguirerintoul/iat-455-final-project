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
import java.util.Random;
import javax.imageio.ImageIO;

class FinalProject extends Frame {
	BufferedImage image;
	ArrayList<Line2D.Double> lines;
	Random random = new Random();
	int width;
	int height;
	double maxStrokeRadius = 8;
	double minStrokeRadius = 4;
	double maxSrokeAngle = 90;
	double minStrokeAngle = 0;
	int pixelInterval = 4;

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

	public double randomDoubleBetween(double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}

	private void drawLines(ArrayList<Line> lines, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (Line line : lines) {
			g2.setColor(line.color);
			g2.setStroke(new BasicStroke((int) randomDoubleBetween(minStrokeRadius, maxStrokeRadius)));
			g2.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
		}
	}

	private ArrayList<Line> generateStrokes(BufferedImage img) {
		System.out.println("Generating strokes...");
		ArrayList<Line> lines = new ArrayList<Line>();

		for (int x = 0; x < width; x = x + pixelInterval) {
			for (int y = 0; y < height; y = y + pixelInterval) {
				try {
					// 0 degree angle is vertical
					int angleInDegrees = 60 - random.nextInt(30);
					int length = 10;
					float angle = (float) ((360 - angleInDegrees) * Math.PI / 180);
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