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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Interpreter {
	public static Task interpretAdd(String string) {
		String unsplitString = string;
		String[] itemsOfTask = unsplitString.split("\\\\");
		String description = itemsOfTask[1];
		ArrayList<String> labels = new ArrayList<String>();
		labels.add(itemsOfTask[2]);
		int importance = Integer.parseInt(itemsOfTask[3]);
		
		if(itemsOfTask.length == 4){
			
			Task task = new Task(description, labels, importance);
		
			return task;
			
		} else if (itemsOfTask.length == 5){
			
			String date = itemsOfTask[4];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			LocalDateTime deadline = LocalDateTime.parse(date, formatter);
			
			Task deadlineTask = new Task(description, labels, importance, deadline);						
			
			return deadlineTask;
			
		} else if (itemsOfTask.length == 6) {
			
			String start = itemsOfTask[4];
			String end = itemsOfTask[5];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			LocalDateTime startTime = LocalDateTime.parse(start, formatter);
			LocalDateTime endTime = LocalDateTime.parse(end, formatter);
			
			Task event = new Task(description, labels, importance, startTime, endTime);
			
			return event;			
		} else return null;		
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
}
