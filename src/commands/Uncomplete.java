package commands;

import java.util.ArrayList;

import interpreter.Interpreter;
import storage.Storage;
import util.DisplayData;
import util.Task;

public class Uncomplete extends UndoableCommand {
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	public Uncomplete(DisplayData dd, Storage s, String ui) {
		super(dd, s, ui);
	}

	@Override
	public boolean execute() {
		try {
			ArrayList<Integer> indexList = massCommandIndex(userInput);
			for (int index:indexList) {
				Task task = displayData.getListOfTasks().get(index);
				if (task.isDone()) {
					tasks.add(task);
				}
			}
			
			for (Task task: tasks) {
				storage.uncompleteTask(task);
			}

			if (tasks.size()==1) {
				setDisplayData("Task " + getDescriptions(tasks) + " marked as undone",
						storage.getAllNotDoneTasks());
			} else {
				setDisplayData("Tasks " + getDescriptions(tasks) + " marked as undone",
						storage.getAllNotDoneTasks());
			}
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
			setDisplayData("Invalid uncomplete command. format: uncomplete <number>");
			return false;
		}
		return true;
	}

	@Override
	public void undo() {
		for (Task task:tasks) {
			storage.completeTask(task);
		}
		if (tasks.size()==1) {
			setDisplayData("Undo: Mark undone "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		} else {
			setDisplayData("Undo: Mark undone "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		}
	}

	@Override
	public void redo() {
		for (Task task:tasks) {
			storage.completeTask(task);
		}
		if (tasks.size()==1) {
			setDisplayData("Redo: Mark undone "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		} else {
			setDisplayData("Redo: Mark undone "+ getDescriptions(tasks),
					storage.getAllNotDoneTasks());
		}
	}
}
