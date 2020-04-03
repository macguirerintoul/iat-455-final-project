import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * UI controls to interact with the FinalProject class.
 * 
 * @author Ernie Yu
 * @author https://github.com/ernieyu
 * 
 * @author Macguire Rintoul
 * @author https://macguire.me
 */
public class UIControlWindow extends JPanel implements ActionListener, ChangeListener, ItemListener {
	private FinalProject finalProject; // reference to the finalProject
	private RangeSlider radiusSlider = new RangeSlider(); // double-input slider to control stroke radius
	private RangeSlider lengthSlider = new RangeSlider(); // double-input slider to control stroke length
	private RangeSlider angleSlider = new RangeSlider(); // double-input slider to control stroke length
	private JSlider pixelIntervalSlider; // single-input slider to control pixel interval

	// labels for sliders and values
	private JLabel radiusSliderTitle = new JLabel();
	private JLabel radiusSliderLabel1 = new JLabel();
	private JLabel radiusSliderValue1 = new JLabel();
	private JLabel radiusSliderLabel2 = new JLabel();
	private JLabel radiusSliderValue2 = new JLabel();

	private JLabel lengthSliderTitle = new JLabel();
	private JLabel lengthSliderLabel1 = new JLabel();
	private JLabel lengthSliderValue1 = new JLabel();
	private JLabel lengthSliderLabel2 = new JLabel();
	private JLabel lengthSliderValue2 = new JLabel();

	private JLabel angleSliderTitle = new JLabel();
	private JLabel angleSliderLabel1 = new JLabel();
	private JLabel angleSliderValue1 = new JLabel();
	private JLabel angleSliderLabel2 = new JLabel();
	private JLabel angleSliderValue2 = new JLabel();

	private JLabel pixelIntervalSliderTitle = new JLabel();

	// control whether strokes are shuffled before drawing
	private Checkbox shuffleStrokes = new Checkbox("Shuffle strokes", true);
	// control whether strokes follow constant colour
	private Checkbox constantColour = new Checkbox("Strokes follow constant colour", true);

	private JButton applyButton = new JButton("Apply"); // apply the current parameters
	private JButton exportButton = new JButton("Export"); // export the painting to a file

	/**
	 * Create the UI control window.
	 * 
	 * @param finalProject the finalProject instance
	 */
	public UIControlWindow(FinalProject finalProject) {
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		setLayout(new GridBagLayout());
		this.finalProject = finalProject;
		pixelIntervalSlider = new JSlider(JSlider.HORIZONTAL, finalProject.minAllowedPixelInterval,
				finalProject.maxAllowedPixelInterval, finalProject.pixelInterval);

		// add change listeners to sliders
		pixelIntervalSlider.addChangeListener(this);
		lengthSlider.addChangeListener(this);
		radiusSlider.addChangeListener(this);
		angleSlider.addChangeListener(this);

		// create pixel interval slider
		pixelIntervalSliderTitle.setText("Pixel interval");
		pixelIntervalSlider.setPreferredSize(new Dimension(240, 40));
		pixelIntervalSlider.setMajorTickSpacing(1);
		pixelIntervalSlider.setPaintTicks(true);
		pixelIntervalSlider.setPaintLabels(true);
		pixelIntervalSlider.setSnapToTicks(true);

		// create radius slider
		radiusSliderTitle.setText("Stroke radius");
		radiusSliderLabel1.setText("Lower value:");
		radiusSliderLabel2.setText("Upper value:");
		radiusSlider.setPreferredSize(new Dimension(240, radiusSlider.getPreferredSize().height));
		radiusSlider.setMinimum(finalProject.minAllowedStrokeRadius);
		radiusSlider.setMaximum(finalProject.maxAllowedStrokeRadius);
		radiusSlider.setValue(finalProject.minStrokeRadius);
		radiusSlider.setUpperValue(finalProject.maxStrokeRadius);

		// create length slider
		lengthSliderTitle.setText("Stroke length");
		lengthSliderLabel1.setText("Lower value:");
		lengthSliderLabel2.setText("Upper value:");
		lengthSlider.setPreferredSize(new Dimension(240, lengthSlider.getPreferredSize().height));
		lengthSlider.setMinimum(finalProject.minAllowedStrokeLength);
		lengthSlider.setMaximum(finalProject.maxAllowedStrokeLength);
		lengthSlider.setValue(finalProject.minStrokeLength);
		lengthSlider.setUpperValue(finalProject.maxStrokeLength);

		// create angle slider
		angleSliderTitle.setText("Stroke angle (only used when above is unchecked)");
		angleSliderLabel1.setText("Lower value:");
		angleSliderLabel2.setText("Upper value:");
		angleSlider.setPreferredSize(new Dimension(240, angleSlider.getPreferredSize().height));
		angleSlider.setMinimum(finalProject.minAllowedStrokeAngle);
		angleSlider.setMaximum(finalProject.maxAllowedStrokeAngle);
		angleSlider.setValue(finalProject.minStrokeAngle);
		angleSlider.setUpperValue(finalProject.maxStrokeAngle);

		// Add action listeners to buttons
		applyButton.addActionListener(this);
		exportButton.addActionListener(this);

		// Add item listeners to checkboxes
		constantColour.addItemListener(this);
		shuffleStrokes.addItemListener(this);

		Insets noInsets = new Insets(0, 0, 0, 0);
		Insets marginTop = new Insets(16, 0, 4, 0);

		/*
		 * PIXEL INTERVAL SLIDER
		 */

		// Row 0
		add(pixelIntervalSliderTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 1
		add(pixelIntervalSlider, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		/*
		 * RADIUS SLIDER
		 */

		// Row 2
		add(radiusSliderTitle, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 3
		add(radiusSliderLabel1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(radiusSliderValue1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 4
		add(radiusSliderLabel2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(radiusSliderValue2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 5
		add(radiusSlider, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		/*
		 * LENGTH SLIDER
		 */

		// Row 6
		add(lengthSliderTitle, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 7
		add(lengthSliderLabel1, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(lengthSliderValue1, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 8
		add(lengthSliderLabel2, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(lengthSliderValue2, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 9
		add(lengthSlider, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		/*
		 * CHECKBOXES
		 */

		// Row 10
		add(constantColour, new GridBagConstraints(0, 10, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 11
		add(angleSliderTitle, new GridBagConstraints(0, 11, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 12
		add(angleSliderLabel1, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(angleSliderValue1, new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 13
		add(angleSliderLabel2, new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(angleSliderValue2, new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 14
		add(angleSlider, new GridBagConstraints(0, 14, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 15
		add(shuffleStrokes, new GridBagConstraints(0, 15, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		/*
		 * BUTTONS
		 */

		// Row 16
		add(applyButton, new GridBagConstraints(0, 16, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 17
		add(exportButton, new GridBagConstraints(0, 17, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

	}

	/**
	 * Called when a checkbox state is changed.
	 * 
	 * @param e the event associated with the state change
	 */
	public void itemStateChanged(ItemEvent e) {
		Checkbox source = (Checkbox) e.getSource();
		if (source == constantColour) {
			System.out.println("Constant colour toggled");
		} else if (source == shuffleStrokes) {
			System.out.println("Shuffle strokes toggled");
		}
	}

	/**
	 * Called when a slider is changed.
	 * 
	 * @param e the event associated with the change
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == lengthSlider) {
			lengthSliderValue1.setText(String.valueOf(lengthSlider.getValue()));
			lengthSliderValue2.setText(String.valueOf(lengthSlider.getUpperValue()));
			System.out.println("LENGTH SLIDER CHANGED");
		} else if (e.getSource() == radiusSlider) {
			radiusSliderValue1.setText(String.valueOf(radiusSlider.getValue()));
			radiusSliderValue2.setText(String.valueOf(radiusSlider.getUpperValue()));
			System.out.println("RADIUS SLIDER CHANGED");
		} else if (e.getSource() == angleSlider) {
			angleSliderValue1.setText(String.valueOf(angleSlider.getValue()));
			angleSliderValue2.setText(String.valueOf(angleSlider.getUpperValue()));
			System.out.println("angle SLIDER CHANGED");
		} else if (e.getSource() == pixelIntervalSlider) {
			if (!pixelIntervalSlider.getValueIsAdjusting()) {
				System.out.println("pixel interval slider changed");
			}
		}
	}

	/**
	 * Called when a button is clicked.
	 * 
	 * @param event the event associated with the click
	 */
	public void actionPerformed(ActionEvent event) {
		JButton clickedButton = (JButton) event.getSource();
		if (clickedButton == exportButton) {
			// Export the finalProject window to an image
			if (finalProject.captureComponent(finalProject)) {
				// Show a dialog with a success message
				JOptionPane.showMessageDialog(this, "Image saved to the project folder successfully.");
			}
		} else if (clickedButton == applyButton) {
			// Apply the current control values to finalProject
			apply();
		}
	}

	/** Apply the slider values to the finalProject class. */
	private void apply() {
		finalProject.pixelInterval = pixelIntervalSlider.getValue();
		finalProject.setRange(Parameter.radius, radiusSlider.getValue(), radiusSlider.getUpperValue());
		finalProject.setRange(Parameter.length, lengthSlider.getValue(), lengthSlider.getUpperValue());
		finalProject.setRange(Parameter.angle, angleSlider.getValue(), angleSlider.getUpperValue());
		finalProject.isOrientationByGradient = constantColour.getState();
		finalProject.isShuffleStrokes = shuffleStrokes.getState();
	}

	/** Display the UI control window. */
	public void display() {
		// Display the current values of the sliders
		radiusSliderValue1.setText(String.valueOf(radiusSlider.getValue()));
		radiusSliderValue2.setText(String.valueOf(radiusSlider.getUpperValue()));
		lengthSliderValue1.setText(String.valueOf(lengthSlider.getValue()));
		lengthSliderValue2.setText(String.valueOf(lengthSlider.getUpperValue()));

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("UI Controls");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
