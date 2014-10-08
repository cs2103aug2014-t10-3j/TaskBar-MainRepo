package taskbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

public class Controller implements ActionListener {
	private UserView userView;
	static Storage storage = new Storage();
	
	Controller(UserView uv){
		userView = uv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	if (userView.jtf.getText().equals("view")) {
    		userView.ta.setText(storage.displayTask());
    	} else {
    		String cmd =userView.jtf.getText();
    		Interpreter cmdInterpreter = new Interpreter();
    		Task taskToAdd = cmdInterpreter.interpretAdd(cmd);
    		storage.addTask(taskToAdd);
    		userView.ta.setText("TASK ADDED SUCCESSFULLY\n\n" + taskToAdd.getDiscription());	
    	}
    	userView.jtf.selectAll();
	}

}
