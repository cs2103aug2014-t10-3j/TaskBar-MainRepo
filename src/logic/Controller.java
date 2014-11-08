package logic;

import interpreter.Interpreter;

import javafx.scene.input.KeyEvent;
import commands.Command;
import commands.UndoableCommand;
import commands.Update;
import storage.Storage;
import util.DisplayData;

public class Controller {

	private DisplayData displayData;
	private Storage storage;
	private History history;
	private boolean duringUpdate = false;
	//private String input;

	public Controller() {
		storage = Storage.getInstance();
		displayData = new DisplayData();
		history = History.getInstance();
	}

	/*
	 * For GUI's initialisation, creates a "all yet-to-be-done task" search 
	 */
	public DisplayData loadAllTasks() {
		Command command = Interpreter.getCommand("all", displayData, storage, history, duringUpdate);
		command.execute();
		return displayData;
	}

	public DisplayData handleEnter(String userInput) {
		Command command = Interpreter.getCommand(userInput, displayData, storage, history, duringUpdate);
		if(command instanceof UndoableCommand){
			if(command.execute()) {
				history.addExecutedCommand((UndoableCommand) command);
				Logging.getInstance().info("Controller: undoable command successfully executed.");
			}
		}else{
			command.execute();
			Logging.getInstance().info("Controller: non-undoable command successfully executed.");
		}
		
		if (command instanceof Update) {
			duringUpdate = true;
		} else {
			duringUpdate = false;
		}
		
		return displayData;
	}
	
	public DisplayData handleHotkey(KeyEvent event) {
		Command command = Interpreter.getCommand(event, displayData, storage, history);
		if (command!=null) {
			command.execute();
		}
		return displayData;
	}

}
