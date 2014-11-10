package interpreter;

import static org.junit.Assert.*;
import logic.interpreter.DateTimeIdentifier;

import org.junit.Test;

public class DateTimeIdentifierTest {

	@Test
	public void testHasEventDate() {
		String str = "add task on sunday from 2pm to 4pm";
		assertEquals(true, DateTimeIdentifier.hasEventDate(str));
	}

	@Test
	public void testIs24hTimeFormat() {
		String str2 = "2359";
		assertEquals(true, DateTimeIdentifier.is24hTimeFormat(str2));
	}

	@Test
	public void testIs12hTimeFormat() {
		String str = "12pm";
		assertEquals(true, DateTimeIdentifier.is12hTimeFormat(str));
	}

	@Test
	public void testIsDayDateFormat() {
		String str = "today";
		assertEquals(true, DateTimeIdentifier.isDayDateFormat(str));
		String str2 = "tmr";
		assertEquals(true, DateTimeIdentifier.isDayDateFormat(str2));
		String str3 = "monday";
		assertEquals(true, DateTimeIdentifier.isDayDateFormat(str3));
		String str4 = "1/12/2014";
		assertEquals(false, DateTimeIdentifier.isDayDateFormat(str4));
	}

}
