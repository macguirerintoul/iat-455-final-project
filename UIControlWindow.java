import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Demo application panel to display a range slider.
 */
public class UIControlWindow extends JPanel {

	private JLabel rangeSliderLabel1 = new JLabel();
	private JLabel rangeSliderValue1 = new JLabel();
	private JLabel rangeSliderLabel2 = new JLabel();
	private JLabel rangeSliderValue2 = new JLabel();
	private RangeSlider radiusSlider = new RangeSlider();
	private RangeSlider lengthSlider = new RangeSlider();

	public UIControlWindow() {
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		setLayout(new GridBagLayout());

		// RADIUS SLIDER
		rangeSliderLabel1.setText("Lower value:");
		rangeSliderLabel2.setText("Upper value:");
		rangeSliderValue1.setHorizontalAlignment(JLabel.LEFT);
		rangeSliderValue2.setHorizontalAlignment(JLabel.LEFT);
		radiusSlider.setPreferredSize(new Dimension(240, radiusSlider.getPreferredSize().height));
		radiusSlider.setMinimum(0);
		radiusSlider.setMaximum(10);
		radiusSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				rangeSliderValue1.setText(String.valueOf(slider.getValue()));
				rangeSliderValue2.setText(String.valueOf(slider.getUpperValue()));
				System.out.println("RADIUS SLIDER CHANGED");
			}
		});

		add(rangeSliderLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(rangeSliderValue1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
		add(rangeSliderLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(rangeSliderValue2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));
		add(radiusSlider, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// LENGTH SLIDER
		rangeSliderLabel1.setText("Lower value:");
		rangeSliderLabel2.setText("Upper value:");
		rangeSliderValue1.setHorizontalAlignment(JLabel.LEFT);
		rangeSliderValue2.setHorizontalAlignment(JLabel.LEFT);
		lengthSlider.setPreferredSize(new Dimension(240, lengthSlider.getPreferredSize().height));
		lengthSlider.setMinimum(0);
		lengthSlider.setMaximum(10);
		lengthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				rangeSliderValue1.setText(String.valueOf(slider.getValue()));
				rangeSliderValue2.setText(String.valueOf(slider.getUpperValue()));
				System.out.println("LENGTH SLIDER CHANGED");
			}
		});

		add(lengthSlider, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

	}

	public void display() {
		// Initialize values.
		radiusSlider.setValue(3);
		radiusSlider.setUpperValue(7);

		lengthSlider.setValue(3);
		lengthSlider.setUpperValue(7);

		// Initialize value display.
		rangeSliderValue1.setText(String.valueOf(radiusSlider.getValue()));
		rangeSliderValue2.setText(String.valueOf(radiusSlider.getUpperValue()));

		// Create window frame.
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Range Slider Demo");

		// Set window content and validate.
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.pack();

		// Set window location and display.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Main application method.
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new UIControlWindow().display();
			}
		});
	}
}
