//@author A0116756Y
package commands;

import interpreter.Interpreter;
import storage.Storage;
import util.DisplayData;
import util.Logging;
import util.Task;

public class Update extends UndoableCommand {
	private Task task;
	
	public Update(DisplayData displayData, Storage storage, Task task){
		super(displayData, storage);
		this.task = task;
	}

	@Override
	public boolean execute() {
		try {
			// Delete the task, and add again by modifying reverse interpreted
			// text input box.
			/*int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			task = displayData.getListOfTasks().get(index);*/
			storage.deleteTask(task);

			setDisplayData(
					Interpreter.convertTaskToAddCommand(task),
					"Please update the task in the input box", null);
		} catch (Exception e) {
			setDisplayData("Invalid update command. format: update <number>");
			return false;
		}
		Logging.getInstance().info("Task " + task.getDescription() + "is updated");
		return true;
	}

	@Override
	public void undo() {
		storage.addTask(task);
		setDisplayData("Undo: Update task \""+ task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
		Logging.getInstance().info("Undone updating task " + task.getDescription());
	}

	@Override
	public void redo() {
		storage.deleteTask(task);
		setDisplayData("Redo: Update task \"" + task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
		Logging.getInstance().info("Redone updating task " + task.getDescription());
	}
}
