//@author A0116756Y
package commands;

import java.time.DateTimeException;
import java.time.LocalDate;

import interpreter.DateTimeLocal;
import interpreter.Interpreter;
import storage.Storage;
import util.DisplayData;

public class Show extends Command {

	public Show(DisplayData dd, Storage s, String ui) {
		super(dd, s, ui);
	}

	public enum ShowCommandType {
		ALL, DONE, KEYWORD, LABEL, DATE, PERIOD, FLOATING;
	}

	@Override
	public boolean execute() {
		try {
			switch(Interpreter.interpretShow(userInput)){
			//TODO implement searching for a duration (month, week, ...)
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
						storage.getTaskByKeyword(Interpreter.getParameter(userInput)));
				break;
			case LABEL:
				setDisplayData("<delete/update/complete> + <number> to perform action on a task in the list.",
						storage.getTaskByLabel(Interpreter.getParameter(userInput).substring(1)));
				break;
			case DATE:
				LocalDate date = DateTimeLocal.getDateTime(Interpreter.getParameter(userInput)).toLocalDate();
				setDisplayData("<delete/update/complete> + <number> to perform action on a task in the list.", 
						storage.getTasksOnADate(date));
			}
		} catch (DateTimeException e) {
			setDisplayData("Invalid date/time input!");
			return false;
		}
		return true;
	}
}
