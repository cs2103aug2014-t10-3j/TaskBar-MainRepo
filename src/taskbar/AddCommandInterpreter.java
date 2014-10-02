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


public class AddCommandInterpreter {
	
	private String description; //description of task
	private ArrayList<String> labels; //category of task
	private int importance; //importance of task, represented by integers
	
	//interpret the add command and return a Task or DeadlineTask or Event
	public AddCommandInterpreter() {
		labels = new ArrayList<String>();
	}
	
	public Task interpretAdd(String string) {
		String unsplitString = string;
		String[] itemsOfTask = unsplitString.split("\\\\");
		description = itemsOfTask[1];
		labels.add(itemsOfTask[2]);
		importance = Integer.parseInt(itemsOfTask[3]);
		
		if(itemsOfTask.length == 4){
			
			Task task = new Task(description, labels, importance);
		
			return task;
			
		} else if (itemsOfTask.length == 5){
			
			String date = itemsOfTask[4];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			LocalDateTime deadline = LocalDateTime.parse(date, formatter);
			
			DeadlineTask deadlineTask = new DeadlineTask(description, labels, importance, deadline);						
			
			return deadlineTask;
			
		} else if (itemsOfTask.length == 6) {
			
			String start = itemsOfTask[4];
			String end = itemsOfTask[5];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			LocalDateTime startTime = LocalDateTime.parse(start, formatter);
			LocalDateTime endTime = LocalDateTime.parse(end, formatter);
			
			Event event = new Event(description, labels, importance, startTime, endTime);
			
			return event;			
		} else return null;		
	}
}
