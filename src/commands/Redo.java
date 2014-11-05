//@author A0116756Y
package commands;

import logic.History;
import storage.Storage;
import util.DisplayData;

public class Redo extends Command {
	private History history;

	public Redo(DisplayData dd, Storage s, History h) {
		super(dd, s, null);
		displayData = dd;
		storage = s;
		history = h;
	}

	@Override
	public boolean execute() {
		boolean isSuccessful = history.redoOneStep();
		if(!isSuccessful){
			setDisplayData("No more operation to redo.",
					storage.getAllNotDoneTasks());
		}
		return true;
	}
}
