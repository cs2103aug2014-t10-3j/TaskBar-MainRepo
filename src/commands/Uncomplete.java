package commands;

import interpreter.Interpreter;
import storage.Storage;
import util.DisplayData;
import util.Task;

public class Uncomplete extends UndoableCommand {
	private Task task;
	
	public Uncomplete(DisplayData dd, Storage s, String ui) {
		super(dd, s, ui);
	}

	@Override
	public boolean execute() {
		try {
			int index = Integer.parseInt(Interpreter.getParameter(userInput)) - 1;
			assert index >= 0 : "Invalid task index number.";
			task = displayData.getListOfTasks().get(index);
			if(task.isDone() ==  false){
				//an exception is thrown and caught locally if the task specified is not yet done.
				throw new UnsupportedOperationException();
			}
			storage.completeTask(task);

			setDisplayData("Task successfully marked as Not Yet Done. Showing all not yet done tasks.",
					storage.getAllNotDoneTasks());
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
		} catch (UnsupportedOperationException e){
			setDisplayData("The task is not marked as done yet!");
			return false;
		}
		catch (Exception e) {
			setDisplayData("Invalid complete command. format: uncomplete <number>");
			return false;
		}
		return true;
	}

	@Override
	public void undo() {
		storage.completeTask(task);
		setDisplayData("Undone \"uncomplete task "+ task.getDescription() + "\"",
				storage.getAllNotDoneTasks());
	}
}
