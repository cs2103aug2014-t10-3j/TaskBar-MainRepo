package taskbar;

import java.util.ArrayList;

public class DisplayData {
	private String prompt;
	private ArrayList<Task> listOfTasks;
	
	public DisplayData(){
		super();
	}
	
	public boolean promptIsNull(){
		return prompt == null;
	}
	
	public boolean listOfTasksIsNull(){
		return listOfTasks == null;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public ArrayList<Task> getListOfTasks() {
		return listOfTasks;
	}

	public void setListOfTasks(ArrayList<Task> listOfTasks) {
		this.listOfTasks = listOfTasks;
	};
}
