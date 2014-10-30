package commands;

import storage.Storage;
import util.DisplayData;

public abstract class UndoableCommand extends Command{
	
	public UndoableCommand(DisplayData dd, Storage s, String ui) {
		super(dd, s, ui);
		// TODO Auto-generated constructor stub
	}

	public abstract void undo();
}
