package taskbar;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

	private DisplayData displayData;
	private Storage storage;
	private History history;
	//private String input;

	private Logger logger = Logging.getInstance();

	// TODO modify constructor to suit the new architecture.
	public Controller() {
		storage = Storage.getInstance();
		displayData = new DisplayData();
		history = History.getInstance();
	}

	public DisplayData loadAllTasks() {
		setDisplayData("", storage.getAllNotDoneTasks());
		return displayData;
	}

	public DisplayData handleEnter(String userInput) {
		Command command = Interpreter.getCommand(userInput, displayData, storage, history);
		if(command instanceof UndoableCommand){
			if(command.execute());
			history.addExecutedCommand((UndoableCommand) command);
		}else{
			command.execute();
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
		displayData.setNeedToUpdateInputBox(false);
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
				storage.getTaskByKeyword(Interpreter.getParameter(userInput)));
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
		} catch (IndexOutOfBoundsException e) {
			int listSize = displayData.getListOfTasks().size();
			if (listSize==0) {
				setDisplayData("There is no task in the list");
			} else if (listSize==1) {
				setDisplayData("There is only 1 task in the list");
			} else {
				setDisplayData("There are only " + listSize + " tasks in the list");
			}
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
		} catch (IndexOutOfBoundsException e) {
			int listSize = displayData.getListOfTasks().size();
			if (listSize==0) {
				setDisplayData("There is no task in the list");
			} else if (listSize==1) {
				setDisplayData("There is only 1 task in the list");
			} else {
				setDisplayData("There are only " + listSize + " tasks in the list");
			}
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
		} catch (IndexOutOfBoundsException e) {
			int listSize = displayData.getListOfTasks().size();
			if (listSize==0) {
				setDisplayData("There is no task in the list");
			} else if (listSize==1) {
				setDisplayData("There is only 1 task in the list");
			} else {
				setDisplayData("There are only " + listSize + " tasks in the list");
			}
		} catch (Exception e) {
			setDisplayData("Invalid complete command. format: <complete> <number>");
		}
	}

}
