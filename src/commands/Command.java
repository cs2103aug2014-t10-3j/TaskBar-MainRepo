//@author A0116756Y
/**
 * Abstract Class Command provides the template for all command objects
 * used in ET. Besides the abstract execute() method, it provides a few 
 * variations of setDisplayData() for more efficient configuring of DisplayData.
 */
package commands;

import java.util.ArrayList;

import storage.Storage;
import util.DisplayData;
import util.Task;

public abstract class Command {
	public DisplayData displayData;
	public Storage storage;
	
	public Command(DisplayData displayData, Storage storage){
		this.displayData = displayData;
		this.storage = storage;
	}
	
	/**
	 * 
	 * @return true when the command is successfully executed; false otherwise.
	 */
	public abstract boolean execute();
	
	/**
	 * setDisplayData method for setting WITH the need to setting input box to something.
	 */
	public void setDisplayData(String inputText, String prompt,
			ArrayList<Task> listOfTasks) {
		displayData.setNeedToUpdate(true);
		displayData.setNeedToUpdateInputBox(true);
		displayData.setInputText(inputText);
		displayData.setPrompt(prompt);
		displayData.setListOfTasks(listOfTasks);
	}

	/**
	 * setDisplayData method for setting CLEARING the input box.
	 */
	public void setDisplayData(String prompt, ArrayList<Task> listOfTasks) {
		displayData.setNeedToUpdate(true);
		displayData.setNeedToUpdateInputBox(true);
		displayData.setInputText("");
		displayData.setPrompt(prompt);
		displayData.setListOfTasks(listOfTasks);
	}

	/**
	 * setDisplayData method for setting RETAINING the input box text, and
	 * without the need to update the list of tasks
	 */
	public void setDisplayData(String prompt) {
		displayData.setNeedToUpdate(false);
		displayData.setNeedToUpdateInputBox(false);
		displayData.setPrompt(prompt);
	}

}
