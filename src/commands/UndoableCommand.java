//@author A0116756Y
package commands;

import interpreter.Interpreter;

import java.util.ArrayList;

import storage.Storage;
import util.DisplayData;
import util.Task;

public abstract class UndoableCommand extends Command{
	
	public UndoableCommand(DisplayData dd, Storage s, String ui) {
		super(dd, s, ui);
		// TODO Auto-generated constructor stub
	}

	public abstract void undo();
	public abstract void redo();
	
	public static ArrayList<Integer> massCommandIndex (String userInput) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		String indexString = Interpreter.getParameter(userInput);
		String[] splitIndex = indexString.split(" ");
		for (String indexGroup: splitIndex) {
			try {
				int index = Integer.parseInt(indexGroup)-1;
				if (!arr.contains(index)) {
					arr.add(index);
				}
			} catch (NumberFormatException nfe) {
				String[] rangeIndex = indexGroup.split("-",2);
				for (int i = Integer.parseInt(rangeIndex[0])-1; i <= Integer.parseInt(rangeIndex[1])-1; i++) {
					if (!arr.contains(i)) {
						arr.add(i);
					}
				}
			}
		}
		return arr;
	}
	
	public static String getDescriptions (ArrayList<Task> tasks) {
		String str = "";
		for (Task task:tasks) {
			str += "\"" + task.getDescription() + "\", ";
		}
		str = str.substring(0, str.length()-2);
		return str;
	}
}
