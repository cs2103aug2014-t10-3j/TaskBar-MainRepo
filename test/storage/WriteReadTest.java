//@author A0111499B
package storage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Test;

import storage.Storage;
import util.Task;

public class WriteReadTest {
	/**
	 * Methods tested:
	 * readFile()
	 * writeFile()
	 * addTask(Task)
	 * deleteTask(Task)
	 */
	@Test
	public void testWrite() throws IOException {

		// time task. DATE with HH:MM to HH::MM
		// 2 labels #sleep, #wake up
		Storage storage = Storage.getInstance();
		Task task1 = new Task();
		task1.setDescription("testing1");
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("testlabel1");
		labels.add("test label2");
		task1.setLabels(labels);
		String str = "1986-04-08 09:30";
		String str1 = "1986-04-08 12:59";
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime1 = LocalDateTime.parse(str, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(str1, formatter);
		task1.setStartTime(dateTime1);
		task1.setEndTime(dateTime2);
		storage.addTask(task1);
		
		// Floating Task
		Task task2 = new Task();
		task2.setDescription("testing2");
		storage.addTask(task2);
		
		// Tasks should be added in correctly into the XML file
	}

	@Test
	public void testRead() throws IOException {
		Storage storage = Storage.getInstance();
		ArrayList<Task> list = storage.getAllNotDoneTasks();
		Task task1 = list.get(list.size() - 2);
		Task task2 = list.get(list.size() - 1);

		// Task1
		Task testtask1 = new Task();
		testtask1.setDescription("testing1");
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("testlabel1");
		labels.add("test label2");
		testtask1.setLabels(labels);
		String str = "1986-04-08 09:30";
		String str1 = "1986-04-08 12:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime1 = LocalDateTime.parse(str, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(str1, formatter);
		testtask1.setStartTime(dateTime1);
		testtask1.setEndTime(dateTime2);

		// Task2
		Task testtask2 = new Task();
		testtask2.setDescription("testing2");
		
		// Deletes the task that we added in for testing
		// If passes test, means it is written in and read out correctly such
		// that it is able to detect and delete
		// the task added.
		assertEquals("Check whether Task1 is written and read in correctly",storage.testdeleteTask(task1).toString(), testtask1.toString());
		assertEquals("Check whether Task2 is written and read in correctly",storage.testdeleteTask(task2).toString(), testtask2.toString());
	}
}
