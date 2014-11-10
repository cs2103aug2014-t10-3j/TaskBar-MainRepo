//@author A0116756Y
/**
 * UndoableCommand defines undo() and redo() abstract methods for it subclasses.
 * It also provides a getDescriptions() utility method to read descriptions of 
 * multiple Tasks at once. 
 */
package logic.commands;

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
