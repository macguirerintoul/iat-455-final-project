
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
	private FinalProject finalProject;
	private RangeSlider radiusSlider = new RangeSlider();
	private RangeSlider lengthSlider = new RangeSlider();
	private JSlider pixelIntervalSlider;

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
	private JLabel pixelIntervalSliderTitle = new JLabel();

	private Checkbox constantColour = new Checkbox("Strokes follow constant colour", true);

	private JButton exportButton = new JButton("Export");
	private JButton applyButton = new JButton("Apply");

	public UIControlWindow(FinalProject finalProject) {
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		setLayout(new GridBagLayout());
		this.finalProject = finalProject;

		pixelIntervalSliderTitle.setText("Pixel interval");
		pixelIntervalSlider = new JSlider(JSlider.HORIZONTAL, finalProject.minAllowedPixelInterval,
				finalProject.maxAllowedPixelInterval, finalProject.pixelInterval);
		pixelIntervalSlider.setMajorTickSpacing(1);
		pixelIntervalSlider.addChangeListener(this);
		pixelIntervalSlider.setPaintTicks(true);
		pixelIntervalSlider.setPaintLabels(true);
		pixelIntervalSlider.setSnapToTicks(true);

		/* create radius slider */
		radiusSliderTitle.setText("Stroke radius");
		radiusSliderLabel1.setText("Lower value:");
		radiusSliderLabel2.setText("Upper value:");
		radiusSlider.setPreferredSize(new Dimension(240, radiusSlider.getPreferredSize().height));
		radiusSlider.setMinimum((int) finalProject.minAllowedStrokeRadius);
		radiusSlider.setMaximum((int) finalProject.maxAllowedStrokeRadius);
		radiusSlider.setValue((int) finalProject.minStrokeRadius);
		radiusSlider.setUpperValue((int) finalProject.maxStrokeRadius);
		radiusSlider.addChangeListener(this);

		// create length slider
		lengthSliderTitle.setText("Stroke length");
		lengthSliderLabel1.setText("Lower value:");
		lengthSliderLabel2.setText("Upper value:");
		lengthSlider.setPreferredSize(new Dimension(240, lengthSlider.getPreferredSize().height));
		lengthSlider.setMinimum((int) finalProject.minAllowedStrokeLength);
		lengthSlider.setMaximum((int) finalProject.maxAllowedStrokeLength);
		lengthSlider.addChangeListener(this);
		lengthSlider.setValue((int) finalProject.minStrokeLength);
		lengthSlider.setUpperValue((int) finalProject.maxStrokeLength);

		// Add action listeners
		applyButton.addActionListener(this);
		exportButton.addActionListener(this);
		constantColour.addItemListener(this);

		Insets insets = new Insets(0, 0, 3, 3);

		// Add UI elements to window
		// Row 0
		add(radiusSliderTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));

		// Row 1
		add(radiusSliderLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(radiusSliderValue1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));

		// Row 2
		add(radiusSliderLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(radiusSliderValue2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));

		// Row 3
		add(radiusSlider, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// Row 4

		// Row 5
		add(lengthSliderTitle, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));

		// Row 6
		add(lengthSliderLabel1, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(lengthSliderValue1, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));

		// Row 7
		add(lengthSliderLabel2, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(lengthSliderValue2, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));

		// Row 8
		add(lengthSlider, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// Row 9
		add(constantColour, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// Row 10
		add(applyButton, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));

		// Row 11
		add(exportButton, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));

		// Row 12
		add(pixelIntervalSliderTitle, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));

		// Row 13
		add(pixelIntervalSlider, new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, insets, 0, 0));
	}

	/**
	 * Called when a checkbox state is changed.
	 * 
	 * @param e the event associated with the state change
	 */
	public void itemStateChanged(ItemEvent e) {
		System.out.println(e.getStateChange() == 1 ? "checked" : "unchecked");
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
		} else if (e.getSource() == pixelIntervalSlider) {
			if (!pixelIntervalSlider.getValueIsAdjusting()) {
				System.out.println("pixel interval slider changed");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton clickedButton = (JButton) event.getSource();
		if (clickedButton == exportButton) {
			// capture the button
			finalProject.captureComponent(finalProject);
		} else if (clickedButton == applyButton) {
			/* Apply the new values to finalProject */
			apply();
		}
	}

	/** Apply the slider values to the finalProject class. */
	private void apply() {
		finalProject.pixelInterval = pixelIntervalSlider.getValue();
		finalProject.setRange(Parameter.radius, radiusSlider.getValue(), radiusSlider.getUpperValue());
		finalProject.setRange(Parameter.length, lengthSlider.getValue(), lengthSlider.getUpperValue());
		finalProject.isOrientationByGradient = constantColour.getState();
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
