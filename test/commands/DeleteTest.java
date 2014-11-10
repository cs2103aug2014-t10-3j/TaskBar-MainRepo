package commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.commands.Delete;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.Storage;
import util.DisplayData;
import util.Task;

public class DeleteTest {
	DisplayData displayData;
	Storage storage;
	ArrayList<Task> tasks;
	
	Delete delete;
	@Before
	public void setUp() throws Exception {
		displayData = new DisplayData();
		storage = Storage.getInstance();
		tasks = new ArrayList<Task>();
		tasks.add(new Task());
		storage.addTask(tasks.get(0));
		
		delete = new Delete(displayData, storage, tasks);
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
		delete.execute();
		assertTrue(!storage.getAllNotDoneTasks().contains(tasks.get(0)));
		delete.undo();
		assertTrue(storage.getAllNotDoneTasks().contains(tasks.get(0)));
		delete.redo();
		assertTrue(!storage.getAllNotDoneTasks().contains(tasks.get(0)));
	}

}
