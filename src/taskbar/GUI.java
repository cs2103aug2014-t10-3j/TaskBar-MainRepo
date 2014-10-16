package taskbar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI implements ActionListener{

    private static Storage taskList = new Storage();
    
    private JTextField jtf;
    private JTextArea ta;
    
    private DisplayData displayInfo; 
    
    void createAndShowUI() {
        final JFrame frame = new JFrame("TaskBar");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents(frame);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void initComponents(JFrame frame) {
        jtf = new JTextField(40);
        ta = new JTextArea(20,40);

        ta.setEditable(false);
        ta.setText("Welcome to TaskBar!");

        /*jtf.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                ta.setText(jtf.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                ta.setText(jtf.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            //Plain text components don't fire these events.
            }
        });*/
        
        jtf.addActionListener(this);
        

        frame.getContentPane().add(jtf, BorderLayout.NORTH);
        frame.getContentPane().add(ta, BorderLayout.SOUTH);
    }
    
    public void actionPerformed(ActionEvent e) {
    	
	   	String cmd = jtf.getText();
		displayInfo = (new Interpreter()).interpretCmd(cmd,taskList);
        
		showToUser(displayInfo);
        	//ta.setText("TASK ADDED SUCCESSFULLY\n\n" + taskToAdd.getdescription());	    	
    	jtf.selectAll();
    }
    
    private void showToUser(DisplayData info) {
    	ta.setText(info.getPrompt() + "\n\n" + multipleLineList(info.getListOfTasks()));
    }
    
    private String multipleLineList(ArrayList<Task> taskAL) {
    	String str = "";
    	int numOfTasks = taskAL.size();
    	if (numOfTasks==0) {
    		str = "Task list is empty.";
    	} else {
	    	for (int i=0; i<numOfTasks; i++) {
	    		str += (i+1) + ". " + taskAL.get(i).getdescription() + "\n";   
	    	}	    	
    	}
    	return str;
    }
}