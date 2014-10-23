package taskbar;

import static org.junit.Assert.*;

import org.junit.Test;

public class StorageTest {

	@Test
	public void test() {
		Storage storage = Storage.getInstance();
		Task task1 = new Task();
		task1.setDescription("bed");
		task1.setImportance(5);
		storage.addTask(task1);
		try {
			storage.writeFile();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		fail("Not yet implemented");
	}

}
