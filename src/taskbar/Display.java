package taskbar;

import java.util.ArrayList;

public class Display {
	private String prompt;
	private ArrayList<Task> listOfTasks;
	
	public Display(String prompt, ArrayList<Task> listOfTasks){
		this.prompt = prompt;
		this.listOfTasks = listOfTasks;
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
