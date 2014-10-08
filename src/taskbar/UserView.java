package taskbar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserView {
	private Controller controller;
	private DisplayData displayData;
	JTextField jtf;
	JTextArea ta;

	private void createAndShowUI() {
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
		displayData = new DisplayData(); 
		controller = new Controller(this, displayData);
		
		jtf = new JTextField(40);
		ta = new JTextArea(20, 40);

		ta.setEditable(false);
		ta.setText("Welcome to TaskBar!");

		jtf.addActionListener(controller);

		frame.getContentPane().add(jtf, BorderLayout.NORTH);
		frame.getContentPane().add(ta, BorderLayout.SOUTH);
	}
	
	/*
	 * This method is called by Controller to update UserView from the 
	 * referenced DisplayData instance.
	 * TODO refactor to display the information more elegantly
	 */
	void update(){
		String toBeDisplayed = "";
		if(!displayData.listOfTasksIsNull()){
			toBeDisplayed += displayData.getPrompt() + "\n";
		}
		if(!displayData.promptIsNull()){
			int counter = 1; //TODO refactor the listOfTasks to incoorperate 
					//serial number, decouple this hidden coupling between 
					//UserView and Controller command interpretation.
			for(Task t : displayData.getListOfTasks()){
				toBeDisplayed += counter + ". " + t.getDescription() + "\n";
				counter ++;
			}
		}
		
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