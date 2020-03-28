import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class UIControlWindow extends JPanel implements ActionListener {
	private FinalProject finalProject;
	private RangeSlider radiusSlider = new RangeSlider();
	private RangeSlider lengthSlider = new RangeSlider();

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
	private JButton export = new JButton("Export");

	public UIControlWindow(FinalProject finalProject) {
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		setLayout(new GridBagLayout());
		this.finalProject = finalProject;

		/* create radius slider */
		radiusSliderTitle.setText("Stroke radius");
		radiusSliderLabel1.setText("Lower value:");
		radiusSliderLabel2.setText("Upper value:");
		radiusSlider.setPreferredSize(new Dimension(240, radiusSlider.getPreferredSize().height));
		radiusSlider.setMinimum(0);
		radiusSlider.setMaximum(10);
		radiusSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				radiusSliderValue1.setText(String.valueOf(slider.getValue()));
				radiusSliderValue2.setText(String.valueOf(slider.getUpperValue()));
				System.out.println("RADIUS SLIDER CHANGED");
				finalProject.setRange(Parameter.radius, slider.getValue(), slider.getUpperValue());
			}
		});
		add(radiusSliderTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(radiusSliderLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(radiusSliderValue1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
		add(radiusSliderLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(radiusSliderValue2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));
		add(radiusSlider, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// create length slider
		lengthSliderTitle.setText("Stroke length");
		lengthSliderLabel1.setText("Lower value:");
		lengthSliderLabel2.setText("Upper value:");
		lengthSlider.setPreferredSize(new Dimension(240, lengthSlider.getPreferredSize().height));
		lengthSlider.setMinimum(0);
		lengthSlider.setMaximum(10);
		lengthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				lengthSliderValue1.setText(String.valueOf(slider.getValue()));
				lengthSliderValue2.setText(String.valueOf(slider.getUpperValue()));
				System.out.println("LENGTH SLIDER CHANGED");
				finalProject.setRange(Parameter.length, slider.getValue(), slider.getUpperValue());
			}
		});
		JLabel divider = new JLabel();
		divider.setText("--------------");

		add(divider, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(0, 0, 3, 3), 0, 0));

		add(lengthSliderTitle, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(lengthSliderLabel1, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(lengthSliderValue1, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
		add(lengthSliderLabel2, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(lengthSliderValue2, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));
		add(lengthSlider, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// setup export button
		add(export, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(0, 0, 3, 3), 0, 0));
		export.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton clickedButton = (JButton) event.getSource();
		if (clickedButton == export) {
			// capture the button
			finalProject.captureComponent(finalProject);
		}
	}

	/** Display the UI control window with some default settings. */
	public void display() {
		// Initialize values.
		radiusSlider.setValue(3);
		radiusSlider.setUpperValue(7);

		lengthSlider.setValue(3);
		lengthSlider.setUpperValue(7);

		// Initialize value display.
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
