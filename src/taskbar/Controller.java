package taskbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller{

	private DisplayData displayData;
	private Storage storage;
	private boolean isResultLocked;

	private Logger logger = Logging.getInstance();

	// TODO modify constructor to suit the new architecture.
	Controller() {
		storage = Storage.getInstance();
		isResultLocked = false;
	}

	public DisplayData handleKeyTyped(String userInput) {
		if (isResultLocked) {
			displayData.setNeedToUpdate(false);
		} else {
			processUnlockedKeyTyped(userInput);
		}
		return displayData;
	}

	public DisplayData handleEnter(String userInput) {
		// TODO to be refactored as processEnterCommandsThatDoNotInvolveSearch
		CommandType currentCommand = Interpreter.getCommand(userInput);
		if (currentCommand == CommandType.ADD) {
			storage.addTask(Interpreter.interpretAdd(userInput));
			setDisplayData("Task successfully added!", null,
					isResultLocked = false);
		} else if (currentCommand == CommandType.UNDO) {
			// TODO implement UNDO
		} else if (!isResultLocked) {
			// TODO to be refactored as processUnlockedEnter
			// TODO add in handling for arrow-key selection here, delete assert
			assert Interpreter.getCommand(userInput) == CommandType.SHOW;
			setDisplayData(
					"<delete/update/complete> + <number> to perform action on a task!",
					isResultLocked = true);
		} else {
			// when the command is not ADD or UNDO, and result is locked:
			processLockedEnter(userInput);
		}

		return displayData;
	}

	/*
	 * setDisplayData method for setting WITH the need to update input box.
	 */
	private void setDisplayData(String inputText, String prompt,
			ArrayList<Task> listOfTasks, boolean isResultLocked) {
		displayData.setNeedToUpdate(true);
		displayData.setNeedToUpdateInputBox(true);
		displayData.setResultLocked(isResultLocked);
		displayData.setInputText(inputText);
		displayData.setPrompt(prompt);
		displayData.setListOfTasks(listOfTasks);
	}

	/*
	 * setDisplayData method for setting withOUT the need to update input box.
	 */
	private void setDisplayData(String prompt, ArrayList<Task> listOfTasks,
			boolean isResultLocked) {
		displayData.setNeedToUpdate(true);
		displayData.setNeedToUpdateInputBox(false);
		displayData.setResultLocked(isResultLocked);
		displayData.setPrompt(prompt);
		displayData.setListOfTasks(listOfTasks);
	}

	/*
	 * setDisplayData method for setting without the need to update input box,
	 * and maintaining the list of task as the previous task.
	 */
	private void setDisplayData(String prompt, boolean isResultLocked) {
		displayData.setNeedToUpdate(true);
		displayData.setNeedToUpdateInputBox(false);
		displayData.setResultLocked(isResultLocked);
		displayData.setPrompt(prompt);
	}

	private void processUnlockedKeyTyped(String userInput) {
		CommandType currentCommand = Interpreter.getCommand(userInput);
		switch (currentCommand) {
		case ADD:
		case UNDO:
			break;
		default: // NONE, SHOW, DELETE, UPDATE, COMPLETE
			displayData.setListOfTasks( // TODO rethink how to update DD
										// properly, to avoid the proprobme of
										// previously set boolean.
					storage.searchTask(Interpreter.getParameter(userInput)));
			break;
		}
	}

	private void processLockedEnter(String userInput) {
		CommandType currentCommand = Interpreter.getCommand(userInput);
		assert currentCommand != CommandType.ADD
				&& currentCommand != CommandType.UNDO : "improperly reached processLockedEnter";
		switch (currentCommand) {
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
	}

	private void show(String userInput) {
		setDisplayData(
				"<delete/update/complete> + <number> to perform action on a task!",
				storage.searchTask(Interpreter.getParameter(userInput)),
				isResultLocked = true);
	}

	private void delete(String userInput) {
		try {
			int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			Task taskToBeDeleted = displayData.getListOfTasks().get(index);
			storage.deleteTask(taskToBeDeleted);

			setDisplayData("Task deleted successfully", null,
					isResultLocked = false);

			logger.log(Level.FINE, "Task deleted successfully\n"
					+ taskToBeDeleted.getDescription());
		} catch (Exception e) {
			setDisplayData("Invalid delete command. format: <delete> <number>",
					isResultLocked = true);
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
					"Please update the task in the input box", null,
					isResultLocked = false);
		} catch (Exception e) {
			setDisplayData("Invalid update command. format: <delete> <number>",
					isResultLocked = true);
		}
	}

	private void complete(String userInput) {
		try {
			int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			Task taskToBeCompleted = displayData.getListOfTasks().get(index);
			taskToBeCompleted.setDone(true);
			
			setDisplayData("Task successfully marked as completed!", null, 
					isResultLocked = false);
		} catch (Exception e) {
			setDisplayData("Invalid complete command. format: <delete> <number>",
					isResultLocked = true);
		}
	}

}
