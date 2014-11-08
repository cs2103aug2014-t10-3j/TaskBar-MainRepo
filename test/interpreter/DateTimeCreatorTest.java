package interpreter;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

public class DateTimeCreatorTest {
	
	String dateTime = "monday 8am";

	@Test
	public void testGetDateTime() {
		LocalDateTime tmp = LocalDateTime.of(2014, 11, 10, 8, 0, 0, 0);
		assertEquals(tmp, DateTimeCreator.getDateTime(dateTime).withSecond(0).withNano(0));
		
	}

}
