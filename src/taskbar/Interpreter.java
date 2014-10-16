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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Interpreter {
	
	private String description; //description of task
	private ArrayList<String> labels; //category of task
	private int importance; //importance of task, represented by integers
	
	//interpret the add command and return a Task or DeadlineTask or Event
	public Interpreter() {
		labels = new ArrayList<String>();
	}
	
	public DisplayData interpretCmd(String cmd, Storage taskList) {
		String[] cmdComponents = cmd.split("\\\\");
		switch (cmdComponents[0]) {
		case "add":
			Task taskToAdd = interpretAdd(cmd, taskList);
			return new DisplayData("TASK ADDED SUCCESSFULLY",taskList.getTasks());
		case "delete": {
			Task taskToDel = interpretDelete(cmd, taskList);
			if (taskToDel!=null) {
				return new DisplayData("TASK DELETED SUCCESSFULLY",taskList.getTasks());
			} else {
				return new DisplayData("NO SUCH TASK TO DELETE", taskList.getTasks());
			}
		}
		
		case "view" :
			return new DisplayData("", taskList.getTasks());
				
		case "update":
			Task newTask = interpretUpdate(cmd, taskList);
			if (newTask!=null) {
				return new DisplayData("TASK UPDATED SUCCESSFULLY",taskList.getTasks());
			} else {
				return new DisplayData("NO SUCH TASK TO UPDATE", taskList.getTasks());
			}
		}
		return null;
	}
	
	public Task interpretAdd(String string, Storage taskList) {
		String unsplitString = string;
		String[] itemsOfTask = unsplitString.split("\\\\");
		description = itemsOfTask[1];
		labels.add(itemsOfTask[2]);
		importance = Integer.parseInt(itemsOfTask[3]);
		
		if(itemsOfTask.length == 4){
			
			Task task = new Task(description, labels, importance);
			taskList.addTask(task);
			return task;
			
		} else if (itemsOfTask.length == 5){
			
			String date = itemsOfTask[4];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			LocalDateTime deadline = LocalDateTime.parse(date, formatter);
			
			DeadlineTask deadlineTask = new DeadlineTask(description, labels, importance, deadline);
			taskList.addTask(deadlineTask);
			
			return deadlineTask;
			
		} else if (itemsOfTask.length == 6) {
			
			String start = itemsOfTask[4];
			String end = itemsOfTask[5];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			LocalDateTime startTime = LocalDateTime.parse(start, formatter);
			LocalDateTime endTime = LocalDateTime.parse(end, formatter);
			
			Event event = new Event(description, labels, importance, startTime, endTime);
			taskList.addTask(event);
			
			return event;			
		} else return null;		
	}
	
	public Task interpretDelete(String cmd, Storage taskList) {
		String unsplitString = cmd;
		String[] cmdComponents = unsplitString.split("\\\\");
		int pos = Integer.parseInt(cmdComponents[1]) - 1;
		
		if (pos>(taskList.getTasks().size()) || pos<0) {
			return null;
		} else {
			Task taskToDelete = taskList.getTasks().get(pos);
			taskList.deleteTask(taskToDelete);			
			return taskToDelete;
		}		
	}
	
	public Task interpretUpdate(String cmd, Storage taskList) {
		String unsplitString = cmd;
		String[] cmdComponents = unsplitString.split("\\\\");
		int pos = Integer.parseInt(cmdComponents[1]) - 1;
		Task oldTask = taskList.getTasks().get(pos);
		
		if (pos>(taskList.getTasks().size()) || pos<0) {
			return null;
		} else {
			JTextField descField = new JTextField(20);
		    JTextField lblField = new JTextField(20);
		    JTextField imptField = new JTextField(5);
		    	
		    JPanel myPanel = new JPanel();
		    myPanel.add(new JLabel("Description: "));
		    myPanel.add(descField);
		    descField.setText(oldTask.getdescription());
		    myPanel.add(new JLabel("Label:"));
		    myPanel.add(lblField);
		    lblField.setText(oldTask.getLabels().get(0));
		    myPanel.add(new JLabel("Importance:"));
		    myPanel.add(imptField);
		    imptField.setText("" + oldTask.getImportance());
		    int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please provide update information", JOptionPane.OK_CANCEL_OPTION);
		    if (result == JOptionPane.OK_OPTION) {
		    	description = descField.getText();
		    	labels.clear();
		    	labels.add(lblField.getText());
		    	importance = Integer.parseInt(imptField.getText());
		    }
		    
			taskList.deleteTask(oldTask);
		    Task newTask = new Task(description, labels, importance);
			taskList.getTasks().add(pos, newTask);;
			return newTask;
		}		
	}	
}
