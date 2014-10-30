package taskbar;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

	private DisplayData displayData;
	private Storage storage;
	private History history;
	//private String input;

	// TODO modify constructor to suit the new architecture.
	public Controller() {
		storage = Storage.getInstance();
		displayData = new DisplayData();
		history = History.getInstance();
	}

	/*
	 * For GUI's initialisation, creates a "all yet-to-be-done task search" through 
	 * an empty input command ("show [empty string]").
	 */
	public DisplayData loadAllTasks() {
		Command command = Interpreter.getCommand("", displayData, storage, history);
		command.execute();
		return displayData;
	}

	public DisplayData handleEnter(String userInput) {
		Command command = Interpreter.getCommand(userInput, displayData, storage, history);
		if(command instanceof UndoableCommand){
			if(command.execute());
			history.addExecutedCommand((UndoableCommand) command);
		}else{
			command.execute();
		}
		return displayData;
	}

}
