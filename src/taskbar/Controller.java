package taskbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

public class Controller implements ActionListener {
	private UserView userView;
	private DisplayData displayData;
	static Storage storage = new Storage();
	
	Controller(UserView uv, DisplayData dd){
		userView = uv;
		displayData = dd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	String userInput = userView.jtf.getText();
    	
    	String command = userInput.split(" ", 2)[0];
    	
    	switch(command){
    	case "view":
    	case "delete":
    		break;
    	case "update":
    	case "
    	}
		
		if (userView.jtf.getText().equals("view")) {
    		userView.ta.setText(storage.displayTask());
    	} else {
    		String cmd =userView.jtf.getText();
    		Interpreter cmdInterpreter = new Interpreter();
    		Task taskToAdd = cmdInterpreter.interpretAdd(cmd);
    		storage.addTask(taskToAdd);
    		userView.ta.setText("TASK ADDED SUCCESSFULLY\n\n" + taskToAdd.getDescription());	
    	}
	}

}
