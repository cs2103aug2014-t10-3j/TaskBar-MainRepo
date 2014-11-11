//@author A0115718E
package interpreter;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import logic.interpreter.CommandDetails;

public class CommandDetailsTest {
	
	String input = "add extra lesson on monday 8am #mondaybluz #school";
	

	@Test
	public void testGetDescription() {
		assertEquals("extra lesson", CommandDetails.getDescription(input));
	}

	@Test
	public void testRemoveImportanceTagString() {
		assertEquals("add extra lesson on monday 8am", CommandDetails.removeTagString(input));
	}

	@Test
	public void testGetTag() {
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("mondaybluz");
		tags.add("school");
		assertEquals(tags, CommandDetails.getTag(input));
	}

	@Test
	public void testGetTypeOfTask() {
		assertEquals("task", CommandDetails.getTypeOfTask(input));
	}

}
