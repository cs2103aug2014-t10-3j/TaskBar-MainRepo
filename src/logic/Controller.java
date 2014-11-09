package logic;

import java.io.IOException;
import java.time.DateTimeException;

import interpreter.Interpreter;
import javafx.scene.input.KeyEvent;
import commands.Command;
import commands.UndoableCommand;
import commands.Update;
import storage.Storage;
import util.DisplayData;
import util.Logging;

public class Controller {

	private DisplayData displayData;
	private Storage storage;
	private History history;
	private boolean duringUpdate = false;

	// private String input;

	public Controller() {

		displayData = new DisplayData();
		history = History.getInstance();
	}

	/*
	 * For GUI's initialisation, creates a "all yet-to-be-done task" search
	 */
	public DisplayData loadAllTasks() {
		try {
			storage = Storage.getInstance();
		} catch (IOException e) {
			displayData.setNeedToUpdate(true);
			displayData
					.setPrompt("ETtasks.xml might have been corrupted. Please repair or delete it. "
							+ "Consult User Guide for more.");
		}
		Command command = Interpreter.getCommand("all", displayData, storage,
				history, duringUpdate);
		command.execute();
		return displayData;
	}

	public DisplayData handleEnter(String userInput) {
		try {
			Command command = Interpreter.getCommand(userInput, displayData,
					storage, history, duringUpdate);

			if (command instanceof UndoableCommand) {
				if (command.execute()) {
					history.addExecutedCommand((UndoableCommand) command);
					Logging.getInstance().info(
							"undoable command successfully executed.");
				}
			} else {
				command.execute();
				Logging.getInstance().info(
						"non-undoable command successfully executed.");
			}

			if (command instanceof Update) {
				duringUpdate = true;
			} else {
				duringUpdate = false;
			}
		} catch (DateTimeException e) {
			displayData.setNeedToUpdate(false);
			displayData.setNeedToUpdateInputBox(false);
			displayData.setPrompt("Invalid time/date format. Pls refer to User Guide if in doubt.");
			Logging.getInstance().warning(
					"Invalid input of time/date format received.");
		}catch (IndexOutOfBoundsException e) {
			displayData.setNeedToUpdate(false);
			displayData.setNeedToUpdateInputBox(false);
			int listSize = displayData.getListOfTasks().size();
			if (listSize==0) {
				displayData.setPrompt("There is no task in the list");
			} else if (listSize==1) {
				displayData.setPrompt("There is only 1 task in the list");
			} else {
				displayData.setPrompt("There are only " + listSize + " tasks in the list");
			}
			Logging.getInstance().warning("Index No. specified by user is not found.");
		} catch (Exception e){
			displayData.setNeedToUpdate(false);
			displayData.setNeedToUpdateInputBox(false);
			displayData.setPrompt("Invalid command input. Press ctrl+H to see help or check User Guide.");
			Logging.getInstance().warning("Invalid input from use received.");
		}

		return displayData;
	}

	public DisplayData handleHotkey(KeyEvent event) {
		Command command = Interpreter.getCommand(event, displayData, storage,
				history);
		if (command != null) {
			command.execute();
		}
		return displayData;
	}

}
