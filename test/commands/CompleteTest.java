//@author A0116756Y
package commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.commands.Complete;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.Storage;
import util.DisplayData;
import util.Task;

public class CompleteTest {
	DisplayData displayData;
	Storage storage;
	ArrayList<Task> tasks;

	Complete complete;

	@Before
	public void setUp() throws Exception {
		displayData = new DisplayData();
		storage = Storage.getInstance();
		tasks = new ArrayList<Task>();
		tasks.add(new Task());
		storage.addTask(tasks.get(0));

		complete = new Complete(displayData, storage, tasks);
	}

	@After
	public void tearDown() throws Exception {
		if (storage.getAllDoneTasks().contains(tasks.get(0))
				|| storage.getAllNotDoneTasks().contains(tasks.get(0))){
			storage.deleteTask(tasks.get(0));
		}
	}

	@Test
	public void test() {
		complete.execute();
		assertTrue(storage.getAllDoneTasks().contains(tasks.get(0)));
		complete.undo();
		assertTrue(!storage.getAllDoneTasks().contains(tasks.get(0)));
		complete.redo();
		assertTrue(storage.getAllDoneTasks().contains(tasks.get(0)));
	}

}
