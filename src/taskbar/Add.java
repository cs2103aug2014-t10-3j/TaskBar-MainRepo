package taskbar;

import java.time.DateTimeException;

public class Add extends UndoableCommand {
	private Task task;
	
	public Add(DisplayData dd, Storage s, String ui){
		super(dd, s, ui);
	}
	
	@Override
	public boolean execute() {
		try {
			task = Interpreter.interpretAdd(userInput);
			storage.addTask(task);
			setDisplayData("Task successfully added!",
					storage.getAllNotDoneTasks());
		} catch (DateTimeException e) {
			setDisplayData("Invalid date/time input. Please refer to the User Guide if in doubt.");
			return false;
		}
		return true;
	}

	@Override
	public void undo() {
		storage.deleteTask(task);
		setDisplayData("Undone \"add task "+ task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
	}
}
