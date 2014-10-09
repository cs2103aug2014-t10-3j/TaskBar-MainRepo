package taskbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

public class Controller implements ActionListener {
	private UserView userView;
	private DisplayData displayData;
	static Storage storage = new Storage();

	Controller(UserView uv, DisplayData dd) {
		userView = uv;
		displayData = dd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String userInput = userView.jtf.getText();

		String[] splitInputTokens = userInput.split(" ", 2);

		switch (splitInputTokens[0]) {
		case "view":
			view();
			break;
		case "delete":
			delete(splitInputTokens[1]);
			break;
		case "update":
			update(splitInputTokens[1]);
			break;
		case "complete":
			complete(splitInputTokens[1]);
		default:
			add(userInput);
			break;
		}

		userView.update();
		/*
		 * if (userView.jtf.getText().equals("view")) {
		 * userView.ta.setText(storage.displayTask()); } else { String cmd
		 * =userView.jtf.getText(); Interpreter cmdInterpreter = new
		 * Interpreter(); Task taskToAdd = cmdInterpreter.interpretAdd(cmd);
		 * storage.addTask(taskToAdd);
		 * userView.ta.setText("TASK ADDED SUCCESSFULLY\n\n" +
		 * taskToAdd.getDescription()); }
		 */
	}

	private void view() {
		if (storage.getAllTasks().isEmpty()) {
			displayData.setPrompt("No task to be displayed\n");
		} else {
			displayData.setPrompt("view tasks:\n");
			displayData.setListOfTasks(storage.getAllTasks());
		}
	}

	// TODO add in all kinds of assertion and exception handling.
	private void delete(String parameter) {
		int index = Integer.parseInt(parameter) - 1;
		Task taskToBeDeleted = displayData.getListOfTasks().get(index);
		storage.deleteTask(taskToBeDeleted);

		displayData.setPrompt("Task deleted successfully\n"
				+ taskToBeDeleted.getDescription());
		displayData.setListOfTasks(null);
	}

	private void update(String parameter) {
		int index = Integer.parseInt(parameter) - 1;
		Task taskToBeUpdated = displayData.getListOfTasks().get(index);
		delete(parameter);
		
	}

	private void complete(String parameter) {
		int index = Integer.parseInt(parameter) - 1;
		Task taskToBeCompleted = displayData.getListOfTasks().get(index);
		taskToBeCompleted.setDone(true);

		displayData.setPrompt("Task successfully marked as completed\n"
				+ taskToBeCompleted.getDescription());
		displayData.setListOfTasks(null);
	}

	private void add(String userInput) {
		Task taskToAdd = Interpreter.interpretAdd(userInput);
		storage.addTask(taskToAdd);
		displayData.setPrompt("TASK ADDED SUCCESSFULLY\n"
				+ taskToAdd.getDescription());
		displayData.setListOfTasks(null);
	}

}
