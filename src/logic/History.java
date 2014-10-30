package logic;

import java.util.Stack;

import commands.Add;
import commands.UndoableCommand;
import commands.Update;

public class History {
	private static History history;
	private Stack<UndoableCommand> canBeUndoneCommands, undoneCommands;

	private History() {
		canBeUndoneCommands = new Stack<UndoableCommand>();
		undoneCommands = new Stack<UndoableCommand>();
	}

	public static History getInstance() {
		if (history == null) {
			history = new History();
		}
		return history;
	}

	public void addExecutedCommand(UndoableCommand command) {
		canBeUndoneCommands.push(command);
		undoneCommands.clear();
	}

	/**
	 * 
	 * @return true when the command is successfully undone; false when there is no more commands to undo.
	 */
	public boolean undoOneStep(){
		if(canBeUndoneCommands.empty()){
			return false;
		}
		UndoableCommand command = canBeUndoneCommands.pop();
		command.undo();
		undoneCommands.push(command);
		
		if ((command instanceof Update) && ((Add) command).isDuringUpdate()) {
			undoOneStep();
		}
		return true;
	}
	
	/**
	 * 
	 * @return true when the command is successfully undone; false when there is no more commands to undo.
	 */
	public boolean redoOneStep(){
		if(undoneCommands.empty()){
			return false;
		}
		UndoableCommand command = undoneCommands.pop();
		
		command.redo();
		canBeUndoneCommands.push(command);
		if (command instanceof Update) {
			redoOneStep();
		}
		
		return true;
	}
}
