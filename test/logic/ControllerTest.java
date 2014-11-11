//@author A0116756Y
/**
 * This is a component test for the Logic component of ET.
 * It simulates two user operations in sequence:
 * 1. an "add" command is input by the user. 
 * 		-- then verifies that the task with the correct description has been add.
 * 2. an "undo" command is input by the user. 
 * 		-- then verifies that the task has been removed.
 */
package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.DisplayData;

public class ControllerTest {
	Controller controller;
	DisplayData displayData;
	String taskDescription = "testing task";
	String undoInput = "undo";
	
	@Before
	public void setUp() throws Exception {
		controller = new Controller();
		controller.loadAllTasks();
	}

	@Test
	public void test() {
		displayData = controller.handleEnter("add " + taskDescription);
		assertTrue(displayData.getPrompt().equals("Task successfully added!"));
		displayData = controller.handleEnter(undoInput);
		assertTrue(displayData.getPrompt().equals("Undo: Add task \"" + taskDescription + "\""));
	}

}
