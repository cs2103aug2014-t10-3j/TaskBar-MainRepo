//@author A0115718E
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
package interpreter;

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
import util.Task;


public class Interpreter {
	public static Task interpretAdd(String command) throws DateTimeException {
		String userInput = command.toLowerCase();
		String userInputOnly = CommandDetails.removeImportanceTagString(userInput);
		String commandDescription = CommandDetails.getDescription(userInputOnly);
		ArrayList<String> commandTag = CommandDetails.getTag(userInput);
		String typeOfTask = CommandDetails.getTypeOfTask(userInput);
		if(typeOfTask == "task"){
			LocalDateTime normalTime = DateTimeLocal.getNormalDateTime(userInputOnly);
			Task task = new Task(commandDescription, commandTag, normalTime);
			return task;
		}else if(typeOfTask == "scheduled task"){
			LocalDateTime scheduledTime = DateTimeLocal.getScheduledDateTime(userInputOnly);
			Task task = new Task(commandDescription, commandTag, scheduledTime);
			return task;
		}else if(typeOfTask == "event"){
			LocalDateTime startTime = DateTimeLocal.getStartDateTime(userInputOnly);
			LocalDateTime endTime = DateTimeLocal.getEndDateTime(userInputOnly);
			Task task = new Task(commandDescription, commandTag, startTime, endTime);
			return task;
		}else if(typeOfTask == "floating"){
			Task task = new Task(commandDescription, commandTag);
			return task;
		}
		return null;
	}
	
	public static String convertTaskToAddCommand(Task task){
		String result = "add";
		result = result + " " + task.getDescription();
		if(task.isDeadLineTask()){			
			LocalDateTime scheduledDateTime = task.getDeadline();
			String formattedScheduleTime = getTimeExpression(scheduledDateTime);								
			result = result + " by " + formattedScheduleTime;
		}
		if(task.isEvent()){		
			LocalDateTime startDateTime = task.getStartTime();			
			LocalDateTime endDateTime = task.getEndTime();
			String formattedStartTime = getTimeExpression(startDateTime); 
			String formattedEndTime = getTimeExpression(endDateTime);			
			result = result + " from " + formattedStartTime + " to " + formattedEndTime;
		}
		int numOfLabels = task.getNumLabels();
		for(int i=0; i<numOfLabels; i++){
			result = result + " #" + task.getLabels().get(i);
		}
		
		return result;
	}
	
	public static ShowCommandType interpretShow(String userInput){
		String showCommandParameter = getParameter(userInput).toLowerCase();
		switch(showCommandParameter){
		case "all":
		case "":
			return ShowCommandType.ALL;
		case "done":
			return ShowCommandType.DONE;
		case "floating":
			return ShowCommandType.FLOATING;
		default:
			if (DateTime.isDayDateFormat(showCommandParameter) ||
					DateTime.isWordDateFormat(showCommandParameter) ||
						DateTime.isNumberDateFormat(showCommandParameter)) {
				return ShowCommandType.DATE;
			}
			
			if(!showCommandParameter.isEmpty() && showCommandParameter.charAt(0) == '#'){
				return ShowCommandType.LABEL;
			}else{
				return ShowCommandType.KEYWORD;
			}
		}
	}
	
	public static String getTimeExpression(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
		DateTimeFormatter formatterWithoutTime = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String expr;
		if (time.getHour()==0 && time.getMinute()==0) {
			expr = time.format(formatterWithoutTime);
		} else {
			expr = time.format(formatter);
		}
		return expr;
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
		//case"a": TODO refactor interpretAdd() to only receive chopped parameter 
		case"delete":
		case"del":
		case"d":
		case"update":
		case"u":
		case"uncomplete":
		case"unc":
		case"complete":
		case"c":
		case"undo":
		case"redo":
		case"show":
			return true;
		default:
			return false;
		}
	}

	public static Command getCommand(String userInput, DisplayData displayData,
			Storage storage, History history, boolean duringUpdate) {
		String[] splitInputTokens = userInput.split(" ", 2);
		String commandKeyword = splitInputTokens[0].toLowerCase();
		switch(commandKeyword){
		case"add":
		//case"a": TODO refactor interpretAdd() to only receive chopped parameter 
			return new Add(displayData, storage, userInput, duringUpdate);
		case"delete":
		case"del":
		case"d":
			return new Delete(displayData, storage, userInput);
		case"update":
		case"u":
			return new Update(displayData, storage, userInput);
		case"complete":
		case"c":
			return new Complete(displayData, storage, userInput);
		case"uncomplete":
		case"unc":
			return new Uncomplete(displayData, storage, userInput);
		case"undo":
			return new Undo(displayData, storage, history);
		case"redo":
			return new Redo(displayData, storage, history);
		default:
			return new Show(displayData, storage, userInput);
		}
	}
	
	public static Command getCommand(KeyEvent event, DisplayData displayData,
			Storage storage, History history) {
		if (event.isControlDown() && event.getCode()==KeyCode.Z) {
			return new Undo(displayData, storage, history);
		} else if (event.isControlDown() && event.getCode()==KeyCode.Y){
			return new Redo(displayData, storage, history);
		}
		return null;
		
	}
}
