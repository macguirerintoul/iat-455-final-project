import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

class FinalProject extends Frame {
	BufferedImage image;

	public FinalProject() {
		try {
			// load and display image that the user specifies
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				image = ImageIO.read(chooser.getSelectedFile());
			}
		} catch (Exception e) {
			System.out.println("Cannot load the provided image");
		}
		this.setTitle("Week 3 workshop - Basic image manipulation");
		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void paint(Graphics g) {
		int w = 1280;
		int h = 800;
		this.setSize(w, h);

		g.drawImage(image, w / 4, h / 4, w / 2, h / 2, this);
	}

	public static void main(String[] args) {
		FinalProject finalProject = new FinalProject();
	}
}