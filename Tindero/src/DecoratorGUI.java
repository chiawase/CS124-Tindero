import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DecoratorGUI extends JFrame {
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JCheckBox chckbxNewCheckBox;
	private JCheckBox chckbxNewCheckBox_1;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DecoratorGUI frame = new DecoratorGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DecoratorGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ButtonGroup group = new ButtonGroup();
		
		JButton btnNewButton = new JButton("Save Skill");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Skill skill = null;
				
				if (rdbtnNewRadioButton.isSelected() == true)
				{
					skill = new Laundry();
				}
				else if(rdbtnNewRadioButton_1.isSelected() == true)
				{
					skill = new HouseKeeper();
				}
				else
				{
					System.out.println("GG");
				}
				
				if(chckbxNewCheckBox.isSelected() == true)
				{
					skill = new WaterThePlants(skill);
				}
				
				if(chckbxNewCheckBox_1.isSelected() == true)
				{
					skill = new FeedThePets(skill);
				}
				
				System.out.println(skill.getDescription());
				
			}
		});
		btnNewButton.setBounds(143, 198, 89, 23);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 184, 167);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Main Skill");
		lblNewLabel.setBounds(10, 11, 81, 14);
		panel.add(lblNewLabel);
		
		rdbtnNewRadioButton = new JRadioButton("Laundry");
		rdbtnNewRadioButton.setBounds(6, 27, 157, 23);
		panel.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Plumber");
		rdbtnNewRadioButton_1.setBounds(6, 53, 157, 23);
		panel.add(rdbtnNewRadioButton_1);
		group.add(rdbtnNewRadioButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(182, 0, 184, 167);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Side Skill");
		lblNewLabel_1.setBounds(10, 11, 119, 14);
		panel_1.add(lblNewLabel_1);
		
		chckbxNewCheckBox = new JCheckBox("Water the Plants");
		chckbxNewCheckBox.setBounds(6, 32, 139, 23);
		panel_1.add(chckbxNewCheckBox);
		
		chckbxNewCheckBox_1 = new JCheckBox("Feed the pets");
		chckbxNewCheckBox_1.setBounds(6, 57, 139, 23);
		panel_1.add(chckbxNewCheckBox_1);
	}
}
