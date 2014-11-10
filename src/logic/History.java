//@author A0116756Y
/**
 * History maintains a record of UndoableCommands that have already been executed,
 * provides undo and redo functionality.
 */
package logic;

import java.util.Stack;

import logic.commands.Add;
import logic.commands.UndoableCommand;
import logic.commands.Update;

public class History {
	private static History history;
	private Stack<UndoableCommand> canBeUndoneCommands, canBeRedoneCommands;

	private History() {
		canBeUndoneCommands = new Stack<UndoableCommand>();
		canBeRedoneCommands = new Stack<UndoableCommand>();
	}
	/**
	 * History implements Singleton Pattern. This method allows other class to
	 * get the reference to the History instance in the current runtime.
	 * @return the singleton instance of History in the current runtime.
	 */
	public static History getInstance() {
		if (history == null) {
			history = new History();
		}
		return history;
	}

	public void addExecutedCommand(UndoableCommand command) {
		canBeUndoneCommands.push(command);
		canBeRedoneCommands.clear();
	}

	/**
	 * @return true when the command is successfully undone; false when there is no more commands to undo.
	 */
	public boolean undoOneStep(){
		if(canBeUndoneCommands.empty()){
			return false;
		}
		UndoableCommand command = canBeUndoneCommands.pop();
		command.undo();
		canBeRedoneCommands.push(command);
		
		//Allows the coupled undo for Add&Update during an update.
		//See isDuringUpdate() method in Add for more details.
		if ((command instanceof Add) && ((Add) command).isDuringUpdate()) {
			undoOneStep();
		}
		return true;
	}
	
	/**
	 * @return true when the command is successfully undone; false when there is no more commands to undo.
	 */
	public boolean redoOneStep(){
		if(canBeRedoneCommands.empty()){
			return false;
		}
		UndoableCommand command = canBeRedoneCommands.pop();
		
		command.redo();
		canBeUndoneCommands.push(command);
		
		//Allows the coupled undo for Add&Update during an update.
		//See isDuringUpdate() method in Add for more details.
		if (command instanceof Update) {
			redoOneStep();
		}
		
		return true;
	}
}
