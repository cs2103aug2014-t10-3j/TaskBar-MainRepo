package interpreter;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import logic.interpreter.DateTimeCreator;

import org.junit.Test;

public class DateTimeCreatorTest {
	
	String dateTime1 = "1 dec 830am";
	String dateTime2 = "1/12/2014 0830";
	String dateTime3 = "2 december 2014 1159pm";
	String dateTime4 = "2/12 2359";
	String dateTime5 = "1 dec 2014 12am"; //borderline case, since 12am = 00:00
	LocalDateTime time1 = LocalDateTime.of(2014, 12, 1, 8, 30);
	LocalDateTime time2 = LocalDateTime.of(2014, 12, 2, 23, 59);
	LocalDateTime time3 = LocalDateTime.of(2014, 12, 1, 0, 0);

	@Test
	public void testGetDateTime() {
		assertEquals(time1, DateTimeCreator.getDateTime(dateTime1).withSecond(0).withNano(0));
		assertEquals(time1, DateTimeCreator.getDateTime(dateTime2).withSecond(0).withNano(0));
		assertEquals(time2, DateTimeCreator.getDateTime(dateTime3).withSecond(0).withNano(0));
		assertEquals(time2, DateTimeCreator.getDateTime(dateTime4).withSecond(0).withNano(0));
		assertEquals(time3, DateTimeCreator.getDateTime(dateTime5).withSecond(0).withNano(0));
		
	}

}
