//@author A0115718E
/**
 * This class handles the input from user and translate the raw input into a task object.
 *
 */
package interpreter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import logic.History;
import commands.Add;
import commands.Command;
import commands.Complete;
import commands.Delete;
import commands.Redo;
import commands.Show;
import commands.Uncomplete;
import commands.Undo;
import commands.Update;
import commands.Show.ShowCommandType;
import storage.Storage;
import util.DisplayData;
import util.Logging;
import util.Task;

public class Interpreter {
	public static Task interpretAdd(String command) throws DateTimeException {
		String userInput = command.toLowerCase();
		String userInputOnly = CommandDetails.removeTagString(command);
		String userInputNoQuotes = CommandDetails.removeDescriptionString(userInputOnly);
		String commandDescription = CommandDetails
				.getDescription(userInputOnly);
		ArrayList<String> commandTag = CommandDetails.getTag(userInput);
		String typeOfTask = CommandDetails.getTypeOfTask(userInputNoQuotes);
		if (typeOfTask.equals("task")) {
			LocalDateTime normalTime = DateTimeGetter
					.getNormalDateTime(userInputNoQuotes.toLowerCase());
			Task task = new Task(commandDescription, commandTag, normalTime);
			Logging.getInstance().info("Scheduled task is successfuly identified and created.");
			return task;
		} else if (typeOfTask.equals("scheduled task")) {
			LocalDateTime scheduledTime = DateTimeGetter
					.getScheduledDateTime(userInputNoQuotes.toLowerCase());
			Task task = new Task(commandDescription, commandTag, scheduledTime);
			Logging.getInstance().info("Scheduled task is successfuly identified and created.");
			return task;
		} else if (typeOfTask.equals("event")) {
			LocalDateTime startTime = DateTimeGetter
					.getStartDateTime(userInputNoQuotes.toLowerCase());
			LocalDateTime endTime = DateTimeGetter.getEndDateTime(userInputNoQuotes
					.toLowerCase());
			Task task = new Task(commandDescription, commandTag, startTime,
					endTime);
			Logging.getInstance().info("Event task is successfuly identified and created.");
			return task;
		} else if (typeOfTask.equals("floating")) {
			Task task = new Task(commandDescription, commandTag);
			Logging.getInstance().info("Floating task is successfuly identified and created.");
			return task;
		}
		return null;
	}

	public static String convertTaskToAddCommand(Task task) {
		String result = "add";
		result = result + " " + task.getDescription();
		if (task.isDeadLineTask()) {
			LocalDateTime scheduledDateTime = task.getDeadline();
			String formattedScheduleTime = getTimeExpression(scheduledDateTime);
			result = result + " by " + formattedScheduleTime;
		}
		if (task.isEvent()) {
			LocalDateTime startDateTime = task.getStartTime();
			LocalDateTime endDateTime = task.getEndTime();
			String formattedStartTime = getTimeExpression(startDateTime);
			String formattedEndTime = getTimeExpression(endDateTime);
			result = result + " from " + formattedStartTime + " to "
					+ formattedEndTime;
		}
		int numOfLabels = task.getNumLabels();
		for (int i = 0; i < numOfLabels; i++) {
			result = result + " #" + task.getLabels().get(i);
		}

		return result;
	}

	public static String getTimeExpression(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HHmm");
		DateTimeFormatter formatterWithoutTime = DateTimeFormatter
				.ofPattern("dd/MM/yyyy");
		String expr;
		if (time.getHour() == 0 && time.getMinute() == 0) {
			expr = time.format(formatterWithoutTime);
		} else {
			expr = time.format(formatter);
		}
		return expr;
	}

	public static Command getCommand(KeyEvent event, DisplayData displayData,
			Storage storage, History history) {
		if (event.isControlDown() && event.getCode() == KeyCode.Z) {
			return new Undo(displayData, storage, history);
		} else if (event.isControlDown() && event.getCode() == KeyCode.Y) {
			return new Redo(displayData, storage, history);
		}
		return null;

	}

	public static Command getCommand(String userInput, DisplayData displayData,
			Storage storage, History history, boolean duringUpdate) {
		String[] splitInputTokens = userInput.split(" ", 2);
		String commandKeyword = splitInputTokens[0].toLowerCase();
		switch (commandKeyword) {
		case "add":
			return new Add(displayData, storage, interpretAdd(userInput),
					duringUpdate);
		case "delete":
		case "del":
		case "d":
			return new Delete(displayData, storage, getMassCommandTaskList(displayData, userInput));
		case "update":
		case "u":
			return new Update(displayData, storage, 
					displayData.getListOfTasks().get(Integer.parseInt(getParameter(userInput)) - 1));
		case "complete":
		case "c":
			return new Complete(displayData, storage, getMassCommandTaskList(displayData, userInput));
		case "uncomplete":
		case "unc":
			return new Uncomplete(displayData, storage, getMassCommandTaskList(displayData, userInput));
		case "undo":
			return new Undo(displayData, storage, history);
		case "redo":
			return new Redo(displayData, storage, history);
		default:
			return createShow(displayData, storage, userInput);
		}
	}

	public static Show createShow(DisplayData displayData, Storage storage,
			String userInput) {
		ShowCommandType type = interpretShow(userInput);
		switch (type) {
		case ALL:
			return new Show(displayData, storage, type);
		case DONE:
			return new Show(displayData, storage, type);
		case FLOATING:
			return new Show(displayData, storage, type);
		case DATE:
			LocalDate date = DateTimeCreator.getDateTime(
					Interpreter.getParameter(userInput)).toLocalDate();
			return new Show(displayData, storage, type, date);
		case LABEL:
			return new Show(displayData, storage, type, getParameter(userInput)
					.substring(1));
		case KEYWORD:
		default:
			return new Show(displayData, storage, type, getParameter(userInput));

		}
	}

	public static ShowCommandType interpretShow(String userInput) {
		String showCommandParameter = getParameter(userInput).toLowerCase();
		switch (showCommandParameter) {
		case "all":
		case "":
			return ShowCommandType.ALL;
		case "done":
			return ShowCommandType.DONE;
		case "floating":
			return ShowCommandType.FLOATING;
		default:
			if (DateTimeIdentifier.isDayDateFormat(showCommandParameter)
					|| DateTimeIdentifier
							.isWordDateFormat(showCommandParameter)
					|| DateTimeIdentifier
							.isNumberDateFormat(showCommandParameter)) {
				return ShowCommandType.DATE;
			}

			if (!showCommandParameter.isEmpty()
					&& showCommandParameter.charAt(0) == '#') {
				return ShowCommandType.LABEL;
			} else {
				return ShowCommandType.KEYWORD;
			}
		}
	}

	public static String getParameter(String userInput) {
		if (hasCommandKeyword(userInput)) {
			String[] splitInputTokens = userInput.split(" ", 2);
			return splitInputTokens[1];
		}
		return userInput;

	}

	public static ArrayList<Task> getMassCommandTaskList(
			DisplayData displayData, String userInput) {
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		String indexString = Interpreter.getParameter(userInput);
		String[] splitIndex = indexString.split(" ");
		for (String indexGroup : splitIndex) {
			try {
				int index = Integer.parseInt(indexGroup) - 1;
				if (!indexList.contains(index)) {
					indexList.add(index);
				}
			} catch (NumberFormatException nfe) {
				String[] rangeIndex = indexGroup.split("-", 2);
				for (int i = Integer.parseInt(rangeIndex[0]) - 1; i <= Integer
						.parseInt(rangeIndex[1]) - 1; i++) {
					if (!indexList.contains(i)) {
						indexList.add(i);
					}
				}
			}
		}
		ArrayList<Task> tasks = new ArrayList<Task>();
		for (int index : indexList) {
			Task task = displayData.getListOfTasks().get(index);
			tasks.add(task);
		}
		return tasks;
	}

	private static boolean hasCommandKeyword(String userInput) {
		String[] splitInputTokens = userInput.split(" ", 2);
		String commandKeyword = splitInputTokens[0].toLowerCase();
		switch (commandKeyword) {
		case "add":
		case "delete":
		case "del":
		case "d":
		case "update":
		case "u":
		case "uncomplete":
		case "unc":
		case "complete":
		case "c":
		case "undo":
		case "redo":
		case "show":
			return true;
		default:
			return false;
		}
	}
}
