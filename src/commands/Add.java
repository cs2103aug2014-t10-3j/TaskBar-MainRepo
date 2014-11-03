package commands;

import interpreter.Interpreter;

import java.time.DateTimeException;

import logic.History;
import storage.Storage;
import util.DisplayData;
import util.Task;

public class Add extends UndoableCommand {
	private Task task;
	private boolean duringUpdate;
	
	public Add(DisplayData dd, Storage s, String ui){
		super(dd, s, ui);
		duringUpdate = false;
	}
	
	public Add(DisplayData dd, Storage s, String ui, boolean updating) {
		super(dd, s, ui);
		duringUpdate = updating;
	}
	
	@Override
	public boolean execute() throws DateTimeException{
		try {
			task = Interpreter.interpretAdd(userInput);
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
	}

	@Override
	public void redo() {
		storage.addTask(task);
		setDisplayData("Redo: Add task \"" + task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
	}
	
	public boolean isDuringUpdate(){
		return duringUpdate;
	}
	
	
}
