package taskbar;

import java.util.ArrayList;

public class DisplayData {
	private String inputText;
	private String prompt;
	private ArrayList<Task> listOfTasks;
	
	private boolean needToUpdateInputBox;
	private boolean isResultLocked;

	public DisplayData() {
		super();
	}
	
	public boolean inputTextIsEmpty() {
		return inputText == null || inputText.equals("");
	}
	public boolean promptIsEmpty() {
		return prompt == null || prompt.equals("");
	}

	public boolean listOfTasksIsEmpty() {
		return listOfTasks == null || listOfTasks.isEmpty();
	}

	public boolean needToUpdateInputBox(){
		return needToUpdateInputBox;
	}
	
	public void setNeedToUpdateInputBox(boolean needToUpdateInputBox) {
		this.needToUpdateInputBox = needToUpdateInputBox;
	}

	public void setResultLocked(boolean isResultLocked) {
		this.isResultLocked = isResultLocked;
	}

	public boolean isResultLocked(){
		return isResultLocked;
	}
	
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inuptText) {
		this.inputText = inuptText;
	};

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
	}
}
