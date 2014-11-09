package commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.Storage;
import util.DisplayData;
import util.Task;

public class UncompleteTest {
	DisplayData displayData;
	Storage storage;
	ArrayList<Task> tasks;

	Uncomplete uncomplete;
	@Before
	public void setUp() throws Exception {
		displayData = new DisplayData();
		storage = Storage.getInstance();
		tasks = new ArrayList<Task>();
		tasks.add(new Task());
		tasks.get(0).setDone(true);
		storage.addTask(tasks.get(0));

		uncomplete = new Uncomplete(displayData, storage, tasks);
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
		uncomplete.execute();
		assertTrue(storage.getAllNotDoneTasks().contains(tasks.get(0)));
		uncomplete.undo();
		assertTrue(!storage.getAllNotDoneTasks().contains(tasks.get(0)));
		uncomplete.redo();
		assertTrue(storage.getAllNotDoneTasks().contains(tasks.get(0)));
	}

}
