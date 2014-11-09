//@author A0116756Y
package commands;

import java.util.ArrayList;

import storage.Storage;
import util.DisplayData;
import util.Task;

public abstract class UndoableCommand extends Command{
	

	public UndoableCommand(DisplayData displayData, Storage storage) {
		super(displayData, storage);
	}

	public abstract void undo();
	public abstract void redo();
	
	public static String getDescriptions (ArrayList<Task> tasks) {
		String str = "";
		for (Task task:tasks) {
			str += "\"" + task.getDescription() + "\", ";
		}
		str = str.substring(0, str.length()-2);
		return str;
	}
}
