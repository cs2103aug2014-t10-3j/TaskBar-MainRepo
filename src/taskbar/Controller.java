package taskbar;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

	private DisplayData displayData;
	private Storage storage;

	private Logger logger = Logging.getInstance();

	// TODO modify constructor to suit the new architecture.
	public Controller() {
		storage = Storage.getInstance();
		displayData = new DisplayData();
	}

	public DisplayData loadAllTasks() {
		setDisplayData("", storage.getAllNotDoneTasks());
		return displayData;
	}

	public DisplayData handleEnter(String userInput) {
		// TODO to be refactored as processEnterCommandsThatDoNotInvolveSearch
		CommandType currentCommand = Interpreter.getCommandType(userInput);

		switch (currentCommand) {
		case ADD:
			add(userInput);
			break;
		case UNDO:
			// TODO implement UNDO
		case SHOW:
			show(userInput);
			break;
		case DELETE:
			delete(userInput);
			break;
		case UPDATE:
			update(userInput);
			break;
		case COMPLETE:
			complete(userInput);
			break;
		default:
			break;
		}

		return displayData;
	}

	/*
	 * setDisplayData method for setting WITH the need to setting input box to something.
	 */
	private void setDisplayData(String inputText, String prompt,
			ArrayList<Task> listOfTasks) {
		displayData.setNeedToUpdate(true);
		displayData.setNeedToUpdateInputBox(true);
		displayData.setInputText(inputText);
		displayData.setPrompt(prompt);
		displayData.setListOfTasks(listOfTasks);
	}

	/*
	 * setDisplayData method for setting CLEARING the input box.
	 */
	private void setDisplayData(String prompt, ArrayList<Task> listOfTasks) {
		displayData.setNeedToUpdate(true);
		displayData.setNeedToUpdateInputBox(true);
		displayData.setInputText("");
		displayData.setPrompt(prompt);
		displayData.setListOfTasks(listOfTasks);
	}

	/*
	 * setDisplayData method for setting RETAINING the input box text, and
	 * without the need to update the list of tasks
	 */
	private void setDisplayData(String prompt) {
		displayData.setNeedToUpdate(false);
		displayData.setNeedToUpdateInputBox(true);
		displayData.setPrompt(prompt);
	}

	private void add(String userInput) {
		try {
			storage.addTask(Interpreter.interpretAdd(userInput));
			setDisplayData("Task successfully added!",
					storage.getAllNotDoneTasks());
		} catch (DateTimeException e) {
			setDisplayData("Invalid date & time input. Please refer to the User Guide if in doubt.");
		}
	}

	private void show(String userInput) {
		setDisplayData(
				"<delete/update/complete> + <number> to perform action on a task!",
				storage.searchTask(Interpreter.getParameter(userInput)));
	}

	private void delete(String userInput) {
		try {
			int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			Task taskToBeDeleted = displayData.getListOfTasks().get(index);
			storage.deleteTask(taskToBeDeleted);

			setDisplayData("Task deleted successfully",
					storage.getAllNotDoneTasks());

			logger.log(Level.FINE, "Task deleted successfully\n"
					+ taskToBeDeleted.getDescription());
		} catch (Exception e) {
			setDisplayData("Invalid delete command. format: <delete> <number>");
		}

	}

	private void update(String userInput) {
		try {
			// Delete the task, and add again by modifying reverse interpreted
			// text input box.
			int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			Task taskToBeUpdated = displayData.getListOfTasks().get(index);
			storage.deleteTask(taskToBeUpdated);

			setDisplayData(
					Interpreter.convertTaskToAddCommand(taskToBeUpdated),
					"Please update the task in the input box", null);
		} catch (Exception e) {
			setDisplayData("Invalid update command. format: <update> <number>");
		}
	}

	private void complete(String userInput) {
		try {
			int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			Task taskToBeCompleted = displayData.getListOfTasks().get(index);
			taskToBeCompleted.setDone(true);

			setDisplayData("Task successfully marked as completed!", storage.getAllNotDoneTasks());
		} catch (Exception e) {
			setDisplayData("Invalid complete command. format: <complete> <number>");
		}
	}

}
