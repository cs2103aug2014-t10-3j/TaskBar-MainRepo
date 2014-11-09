package commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.Storage;
import util.DisplayData;
import util.Task;

public class UpdateTest {
	DisplayData displayData;
	Storage storage;
	Task task;
	
	Update update;
	
	@Before
	public void setUp() throws Exception {
		displayData = new DisplayData();
		storage = Storage.getInstance();
		task = new Task();
		storage.addTask(task);

		update = new Update(displayData, storage, task);
	}

	@After
	public void tearDown() throws Exception {
		if (storage.getAllDoneTasks().contains(task)
				|| storage.getAllNotDoneTasks().contains(task)){
			storage.deleteTask(task);
		}
	}

	@Test
	public void test() {
		update.execute();
		assertTrue(!storage.getAllNotDoneTasks().contains(task));
		update.undo();
		assertTrue(storage.getAllNotDoneTasks().contains(task));
		update.redo();
		assertTrue(!storage.getAllNotDoneTasks().contains(task));
	}

}
