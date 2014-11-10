//@author A0116756Y
package logic.commands;

import java.time.DateTimeException;
import java.time.LocalDate;

import storage.Storage;
import util.DisplayData;
import util.Logging;

public class Show extends Command {
	ShowCommandType type;
	String searchKey;
	LocalDate date;
	
	/**
	 * Constructor for Show commands of type ALL, DONE, FLOATING
	 * @param displayData
	 * @param storage
	 * @param type
	 */
	public Show(DisplayData displayData, Storage storage, ShowCommandType type){
		super(displayData, storage);
		this.type = type;
	}
	
	/**
	 * Constructor for Show commands of type KEYWORD, LABEL
	 * @param displayData
	 * @param storage
	 * @param type
	 * @param key the keyword for search.
	 */
	public Show(DisplayData displayData, Storage storage, ShowCommandType type, String key){
		super(displayData, storage);
		this.type = type;
		searchKey = key;
	}
	
	/**
	 * Constructor for Show commands of type DATE
	 * @param displayData
	 * @param storage
	 * @param type
	 * @param date
	 */
	public Show(DisplayData displayData, Storage storage, ShowCommandType type, LocalDate date){
		super(displayData, storage);
		this.type = type;
		this.date = date;
	}

	public enum ShowCommandType {
		ALL, DONE, KEYWORD, LABEL, DATE, FLOATING;
	}

	@Override
	public boolean execute() {
		try {
			switch(type){
			case ALL:
				setDisplayData("Showing all undone tasks.", storage.getAllNotDoneTasks());
				break;
			case DONE:
				setDisplayData("Showing all completed tasks.", storage.getAllDoneTasks());
				break;
			case FLOATING:
				setDisplayData("Showing all floating tasks", storage.getFloatingTasks());
				break;
			case KEYWORD:
				setDisplayData("<delete/update/complete> + <number> to perform action on a task in the list.", 
						storage.getTaskByKeyword(searchKey));
				break;
			case LABEL:
				setDisplayData("<delete/update/complete> + <number> to perform action on a task in the list.",
						storage.getTaskByLabel(searchKey));
				break;
			case DATE:
				setDisplayData("<delete/update/complete> + <number> to perform action on a task in the list.", 
						storage.getTasksOnADate(date));
			}
		} catch (DateTimeException e) {
			setDisplayData("Invalid date/time input!");
			return false;
		}
		Logging.getInstance().info("A show command of type " + type + " is executed");
		return true;
	}
}
