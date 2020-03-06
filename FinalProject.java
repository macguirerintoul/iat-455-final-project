import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

class FinalProject extends Frame {
	BufferedImage image;

	static int width = 1280;
	static int height = 800;

	public FinalProject() {
		try {
			image = ImageIO.read(new File("bird1.jpg"));
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

	public void paint() {
		System.out.println("Calling paint...");
		ArrayList<Line> lines = generateStrokes(image);
		for (Line line : lines) {
			System.out.println(line);
		}
		System.out.println("Paint completed.");
	}

	private ArrayList<Line> generateStrokes(BufferedImage img) {
		System.out.println("Generating strokes...");
		ArrayList<Line> lines = new ArrayList<Line>(25);
		for (int x = 0; x < 5; x++) {
			for (int y = 0; x < 5; y++) {
				lines.add(new Line(x, y, x + 5, y + 5, new Color(100, 100, 100)));
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
		finalProject.paint();
	}
}