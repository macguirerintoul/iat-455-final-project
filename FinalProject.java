import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
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

	static int width = 1280;
	static int height = 800;

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
		for (Line line : lines) {
			g.setColor(line.color);
			g.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
		}
	}

	private ArrayList<Line> generateStrokes(BufferedImage img) {
		System.out.println("Generating strokes...");
		ArrayList<Line> lines = new ArrayList<Line>();
		for (int x = 0; x < width; x = x + 4) {
			for (int y = 0; y < height; y = y + 4) {
				try {
					lines.add(new Line(x, y, x + 4, y + 4, new Color(image.getRGB(x, y))));
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
		FinalProject finalProject = new FinalProject();
		finalProject.setSize(width, height);
		System.out.println("hello");
		finalProject.repaint();
	}
}