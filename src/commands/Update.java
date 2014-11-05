//@author A0116756Y
package commands;

import interpreter.Interpreter;
import storage.Storage;
import util.DisplayData;
import util.Task;

public class Update extends UndoableCommand {
	private Task task;
	
	public Update(DisplayData dd, Storage s, String ui) {
		super(dd, s, ui);
	}

	@Override
	public boolean execute() {
		try {
			// Delete the task, and add again by modifying reverse interpreted
			// text input box.
			int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			task = displayData.getListOfTasks().get(index);
			storage.deleteTask(task);

			setDisplayData(
					Interpreter.convertTaskToAddCommand(task),
					"Please update the task in the input box", null);
		} catch (IndexOutOfBoundsException e) {
			int listSize = displayData.getListOfTasks().size();
			if (listSize == 0) {
				setDisplayData("There is no task in the list");
			} else if (listSize == 1) {
				setDisplayData("There is only 1 task in the list");
			} else {
				setDisplayData("There are only " + listSize
						+ " tasks in the list");
			}
			return false;
		} catch (Exception e) {
			setDisplayData("Invalid update command. format: update <number>");
			return false;
		}
		return true;
	}

	@Override
	public void undo() {
		storage.addTask(task);
		setDisplayData("Undo: Update task \""+ task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
	}

	@Override
	public void redo() {
		storage.deleteTask(task);
		setDisplayData("Redo: Update task \"" + task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
	}
}
