package taskbar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI implements ActionListener{

    private static Storage taskList = new Storage();
    
    private JTextField jtf;
    private JTextArea ta;
    
    
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GUI().createAndShowUI();
            }
        });
    }

    private void createAndShowUI() {
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
    	
    	if (jtf.getText().equals("view")) {
    		ta.setText(taskList.displayTask());
    	} else {
    		String cmd =jtf.getText();
    		Interpreter cmdInterpreter = new Interpreter();
    		Task taskToAdd = cmdInterpreter.interpretAdd(cmd);
        	taskList.addTask(taskToAdd);
        	ta.setText("TASK ADDED SUCCESSFULLY\n\n" + taskToAdd.getDiscription());	
    	}
    	jtf.selectAll();
    }
}