//@author A0116756Y
package commands;

import java.time.DateTimeException;

import storage.Storage;
import util.DisplayData;
import util.Logging;
import util.Task;

public class Add extends UndoableCommand {
	private Task task;
	private boolean duringUpdate;
	
	public Add(DisplayData displayData, Storage storage, Task task, boolean updating){
		super(displayData, storage);
		this.task = task;
		this.duringUpdate = updating;
	}

	
	@Override
	public boolean execute(){
		try {
			storage.addTask(task);
			if (duringUpdate) {
				setDisplayData("Task successfully updated!",
						storage.getAllNotDoneTasks());
			}else {
				setDisplayData("Task successfully added!",
						storage.getAllNotDoneTasks());
			}
		} catch (DateTimeException e) {
			setDisplayData("Invalid date/time input. Please refer to the User Guide if in doubt.");
			return false;
		}
		Logging.getInstance().info("Task \""+task.getDescription() + "\" is added.");
		return true;
	}

	@Override
	public void undo() {
		if (duringUpdate) {
			storage.deleteTask(task);
			setDisplayData("Undo: Update task to \"" + task.getDescription() + "\"",
					storage.getAllNotDoneTasks());
		} else {
			storage.deleteTask(task);
			setDisplayData("Undo: Add task \"" + task.getDescription() + "\"",
					storage.getAllNotDoneTasks());
		}
		Logging.getInstance().info("Undone adding task \""+task.getDescription() + "\"");
	}

	@Override
	public void redo() {
		storage.addTask(task);
		setDisplayData("Redo: Add task \"" + task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
		Logging.getInstance().info("Redone adding task \""+task.getDescription() + "\"");
	}
	
	public boolean isDuringUpdate(){
		return duringUpdate;
	}
	
	
}
