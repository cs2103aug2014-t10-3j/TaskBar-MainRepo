//@author A0116756Y
package commands;

import logic.History;
import storage.Storage;
import util.DisplayData;
import util.Logging;

public class Undo extends Command {
	private History history;
	
	public Undo(DisplayData displayData, Storage storage, History history) {
		super(displayData, storage);
		this.history = history;
	}


	@Override
	public boolean execute() {
		boolean isSuccessful = history.undoOneStep();
		if(!isSuccessful){
			setDisplayData("No more operation to undo.",
					storage.getAllNotDoneTasks());
		}
		Logging.getInstance().info("Undo is executed");
		return true;
	}

}
