package commands;
import static org.junit.Assert.*;
import logic.commands.Add;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.Storage;
import util.DisplayData;
import util.Task;

public class AddTest {
	DisplayData displayData;
	Storage storage;
	Task task;
	boolean isUpdating;
	
	Add add;
	
	@Before
	public void setUp() throws Exception {
		displayData = new DisplayData();
		storage = Storage.getInstance();
		task = new Task();
		isUpdating = false;
		add = new Add(displayData, storage, task, isUpdating);
	}

	@Test
	public void testAdd() {
		add.execute();
		assertTrue(storage.getAllNotDoneTasks().contains(task));
		add.undo();
		assertTrue(!storage.getAllNotDoneTasks().contains(task));
		add.redo();
		assertTrue(storage.getAllNotDoneTasks().contains(task));
	}
	
	@After
	public void tearDown() throws Exception{
		if (storage.getAllDoneTasks().contains(task)
				|| storage.getAllNotDoneTasks().contains(task)){
			storage.deleteTask(task);
		}
	}

}
