/**
 * This class handles the input from user and translate the raw input into a task object.
 * One backslash is used to separate the information
 * CURRENTLY FOR LABELS: USING ONE LABEL FOR EACH TASK, WILL CONTINUE TO WORK ON STRING ARRAYS FOR LABELS
 * TASK: Input format for user: add\description\labels\importance
 * DEADLINETASK: Input format for user: add\description\labels\importance\dd.MM.yyyy HH.mm[deadline]
 * EVENT: Input format for user: add\description\labels\importance\dd.MM.yyyy HH:mm[start time]\dd.MM.yyyy HH:mm[end time]
 * @author Xiaofan
 *
 */
package taskbar;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Interpreter {
	public static Task interpretAdd(String command) {
		String userInput = command.toLowerCase();
		String userInputOnly = Command.removeImportanceTagString(command);
		String commandDescription = Command.getDescription(userInput);
		int commandImportance = Command.getImportance(userInput);
		ArrayList<String> commandTag = Command.getTag(userInput);
		String typeOfTask = Command.getTypeOfTask(userInput);
		if(typeOfTask == "task"){
			LocalDateTime normalTime = DateTimeLocal.getNormalDateTime(userInputOnly);
			Task task = new Task(commandDescription, commandTag, commandImportance, normalTime);
			return task;
		}else if(typeOfTask == "scheduled task"){
			LocalDateTime scheduledTime = DateTimeLocal.getScheduledDateTime(userInputOnly);
			Task task = new Task(commandDescription, commandTag, commandImportance, scheduledTime);
			return task;
		}else if(typeOfTask == "event"){
			LocalDateTime startTime = DateTimeLocal.getStartDateTime(userInputOnly);
			LocalDateTime endTime = DateTimeLocal.getEndDateTime(userInputOnly);
			Task task = new Task(commandDescription, commandTag, commandImportance, startTime, endTime);
			return task;
		}else if(typeOfTask == "floating"){
			Task task = new Task(commandDescription, commandTag, commandImportance);
			return task;
		}
		return null;
	}
	
	public static String convertTaskToAddCommand(Task task){
		String result = "add";
		result += "\\" + task.getDescription() 
		+ "\\" + task.getLabels().get(0)
		+ "\\" + task.getImportance(); 
		
		/*TODO to be implemented
		if(task.isDeadLineTask()){
			
		}*/
		
		return result;
	}
	
	public static String getParameter(String userInput){
		if(hasCommandKeyword(userInput)){
			String[] splitInputTokens = userInput.split(" ", 2);
			return splitInputTokens[1];
		}
		return userInput;
		
	}
	
	private static boolean hasCommandKeyword(String userInput){
		String[] splitInputTokens = userInput.split(" ", 2);
		String commandKeyword = splitInputTokens[0].toLowerCase();
		switch(commandKeyword){
		case"add":
		case"delete":
		case"update":
		case"complete":
		case"undo":
		case"show":
			return true;
		default:
			return false;
		}
	}
	
	public static CommandType getCommandType(String userInput){
		String[] splitInputTokens = userInput.split(" ", 2);
		String commandKeyword = splitInputTokens[0].toLowerCase();
		switch(commandKeyword){
		case"add":
			return CommandType.ADD;
		case"delete":
			return CommandType.DELETE;
		case"update":
			return CommandType.UPDATE;
		case"complete":
			return CommandType.COMPLETE;
		case"undo":
			return CommandType.UNDO;
		default:
			return CommandType.SHOW;
		}
	}
}
