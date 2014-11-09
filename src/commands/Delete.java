//@author A0116756Y
package commands;

import java.util.ArrayList;

import storage.Storage;
import util.DisplayData;
import util.Logging;
import util.Task;

public class Delete extends UndoableCommand {
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	public Delete(DisplayData displayData, Storage storage, ArrayList<Task> tasks){
		super(displayData, storage);
		this.tasks = tasks;
	}

	@Override
	public boolean execute() {
		try {
			for (Task task: tasks) {
				storage.deleteTask(task);
			}
			
			if (tasks.size()==1) {
				setDisplayData("Task " + getDescriptions(tasks) + " deleted successfully",
						storage.getAllNotDoneTasks());
			} else {
				setDisplayData("Tasks " + getDescriptions(tasks) + " deleted successfully",
						storage.getAllNotDoneTasks());
			}
		} catch (IndexOutOfBoundsException e) {
			int listSize = displayData.getListOfTasks().size();
			if (listSize==0) {
				setDisplayData("There is no task in the list");
			} else if (listSize==1) {
				setDisplayData("There is only 1 task in the list");
			} else {
				setDisplayData("There are only " + listSize + " tasks in the list");
			}
			return false;
		} catch (Exception e) {
			setDisplayData("Invalid delete command. format: delete <number>.");
			return false;
		}
		Logging.getInstance().info("Tasks " + getDescriptions(tasks) + " deleted");
		return true;
	}
	
	@Override
	public void undo() {
		for (Task task:tasks) {
			storage.addTask(task);			
		}
		if (tasks.size()==1) {
			setDisplayData("Undo: Delete task "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		} else {
			setDisplayData("Undo: Delete tasks "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		}
		Logging.getInstance().info("Undone deleting asks " + getDescriptions(tasks));
	}

	@Override
	public void redo() {
		for (Task task:tasks) {
			storage.deleteTask(task);
		}
		
		if (tasks.size()==1) {
			setDisplayData("Redo: Delete task "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		} else {
			setDisplayData("Redo: Delete tasks "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		}
		Logging.getInstance().info("Redone deleting asks " + getDescriptions(tasks));
	}
}
