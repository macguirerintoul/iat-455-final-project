import java.awt.Color;

/**
 * Represents a stroke in the 'painting' of the image.
 * 
 * @author Macguire Rintoul
 * @author https://macguire.me
 */
class Line {
	final int x1; // the x origin of the line
	final int y1; // the y origin of the line
	final int x2; // the x destination of the line
	final int y2; // the y destination of the line
	final Color color; // the color of the line

	/** Creates a line. */
	public Line(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
}