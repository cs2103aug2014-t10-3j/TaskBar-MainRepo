package commands;

import logic.History;
import storage.Storage;
import util.DisplayData;

public class Undo extends Command {
	private History history;
	
	public Undo(DisplayData dd, Storage s, History h) {
		super(dd, s, null);
		displayData = dd;
		storage = s;
		history = h;
	}


	@Override
	public boolean execute() {
		boolean isSuccessful = history.undoOneStep();
		if(!isSuccessful){
			setDisplayData("No more operation to undo.",
					storage.getAllNotDoneTasks());
		}
		return true;
	}

}
