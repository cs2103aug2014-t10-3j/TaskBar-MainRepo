package interpreter;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Task;

public class InterpreterTest {

	@Test
	public void testConvertTaskToAddCommand() {
		String str = "add complete assignment by 1/12/2014 2359 #school";
		Task task = new Task();
		task = Interpreter.interpretAdd(str);
		
		assertEquals("add complete assignment by 01/12/2014 2359 #school", Interpreter.convertTaskToAddCommand(task));
	}

}
