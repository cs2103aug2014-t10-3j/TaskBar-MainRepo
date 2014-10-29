package taskbar;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Test;

public class StorageTest {

	@Test
	public void testWrite() {
		Storage storage = Storage.getInstance();
		Task task1 = new Task();
		task1.setDescription("bed");
		task1.setImportance(5);
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("sleep");
		labels.add("wake up");
		task1.setLabels(labels);
		String str = "1986-04-08 09:30";
		String str1 = "1986-04-08 12:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime1 = LocalDateTime.parse(str, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(str1,formatter);
		task1.setStartTime(dateTime1);
		task1.setEndTime(dateTime2);
		storage.addTask(task1);
				
		
		Task task2 = new Task();
		task2.setDescription("hello");
		task2.setImportance(2);
		storage.addTask(task2);
		try {
			storage.writeFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRead() {
		Storage storage = Storage.getInstance();
		for (int i = 0; i < storage.getAllTasks().size(); i++) {
			System.out.println(storage.getAllTasks().get(i).toString());
		}
	}

	@Test
	public void deleteTask() {
		Storage storage = Storage.getInstance();
		Task deleteTask = storage.getAllTasks().get(0);
		storage.deleteTask(deleteTask);
		for (int i = 0; i < storage.getAllTasks().size(); i++) {
			System.out.println(storage.getAllTasks().get(i).toString());
		}
	}



}
