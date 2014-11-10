//@author A0116756Y
package logic.commands;

import logic.History;
import storage.Storage;
import util.DisplayData;
import util.Logging;

public class Redo extends Command {
	private History history;

	public Redo(DisplayData displayData, Storage storage, History history) {
		super(displayData, storage);
		this.history = history;
	}

	@Override
	public boolean execute() {
		boolean isSuccessful = history.redoOneStep();
		if(!isSuccessful){
			setDisplayData("No more operation to redo.",
					storage.getAllNotDoneTasks());
		}
		Logging.getInstance().info("Redo is executed");
		return true;
	}
}
