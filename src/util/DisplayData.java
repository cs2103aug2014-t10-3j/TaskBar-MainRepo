//@author A0116756Y
/**
 * DisplayData is extensively used by both Logic and GUI as a intermediate data package
 *  for information that is going to be displayed to the user. 
 */
package util;

import java.util.ArrayList;

public class DisplayData {
	private String inputText;
	private String prompt;
	private ArrayList<Task> listOfTasks;

	private boolean needToUpdate;

	private boolean needToUpdateInputBox;

	public DisplayData() {
		super();
	}

	public boolean needToUpdate() {
		return needToUpdate;
	}

	public void setNeedToUpdate(boolean needToUpdate) {
		this.needToUpdate = needToUpdate;
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

	public boolean needToUpdateInputBox() {
		return needToUpdateInputBox;
	}

	public void setNeedToUpdateInputBox(boolean needToUpdateInputBox) {
		this.needToUpdateInputBox = needToUpdateInputBox;
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
