package taskbar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserView {
	private Controller controller;
	JTextField jtf;
	JTextArea ta;

	void createAndShowUI() {
		final JFrame frame = new JFrame("TaskBar");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initComponents(frame);

		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	private void initComponents(JFrame frame) {
		//initiate a controller instance, the UserView and Controller hold 
		//references to each other.
		//TODO refactor the initiation of UserView into Controller
		controller = new Controller(this);
		
		jtf = new JTextField(40);
		ta = new JTextArea(20, 40);

		ta.setEditable(false);
		ta.setText("Welcome to TaskBar!");

		jtf.addActionListener(controller);

		frame.getContentPane().add(jtf, BorderLayout.NORTH);
		frame.getContentPane().add(ta, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new UserView().createAndShowUI();
			}
		});
		
	}
}