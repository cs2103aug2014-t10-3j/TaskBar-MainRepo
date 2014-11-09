//@author A0115718E
/**
 * This class returns the suitable LocalDateTime objects to Interpreter according to the task type;
 */

package interpreter;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeGetter {

	/**
	 * 
	 * @param userInput
	 * @return start time of an event
	 * @throws DateTimeException
	 */
	public static LocalDateTime getStartDateTime(String userInput) throws DateTimeException{
		String startDateTime = null;
		startDateTime = userInput.substring(userInput.lastIndexOf(" from ")+6, userInput.lastIndexOf(" to "));
		LocalDateTime startTime = DateTimeCreator.getDateTime(startDateTime);
		if(DateTimeIdentifier.hasEventDate(userInput)){
			LocalDateTime eventDate = getEventDate(userInput);
			LocalDateTime timeStart = LocalDateTime.of(eventDate.getYear(), eventDate.getMonthValue(), eventDate.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
			return timeStart;
		}
		return startTime;
	}

	/**
	 * 
	 * @param userInput
	 * @return end time of an event
	 * @throws DateTimeException
	 */
	public static LocalDateTime getEndDateTime(String userInput) throws DateTimeException{
		String endDateTime = null;
		endDateTime = userInput.substring(userInput.lastIndexOf(" to ")+4, userInput.length());
		LocalDateTime endTime = DateTimeCreator.getDateTime(endDateTime);
		if(DateTimeIdentifier.hasEventDate(userInput)){
			LocalDateTime eventDate = getEventDate(userInput);
			LocalDateTime timeEnd = LocalDateTime.of(eventDate.getYear(), eventDate.getMonthValue(), eventDate.getDayOfMonth(), endTime.getHour(), endTime.getMinute());
			return timeEnd;
		}
		if(endTime.isBefore(getStartDateTime(userInput))){
			return endTime.withDayOfMonth(getStartDateTime(userInput).getDayOfMonth());
		}
		return endTime;
	}
	
	/**
	 * 
	 * @param userInput
	 * @return a LocalDateTime object that has an event date separated from time e.g "on sunday from 2pm to 4pm";
	 * @throws DateTimeException
	 */
	public static LocalDateTime getEventDate(String userInput) throws DateTimeException{
		String date = null;
		Pattern pattern = Pattern.compile("on(\\s.*?\\s)from\\s");
		Matcher matcher = pattern.matcher(userInput);
		while(matcher.find()){
			date = matcher.group(1);
		}
		LocalDateTime eventDate = DateTimeCreator.getDateTime(date);
		return eventDate;
	}

	/**
	 * 
	 * @param userInput
	 * @return a LocalDateTime object
	 * @throws DateTimeException
	 */
	public static LocalDateTime getScheduledDateTime(String userInput) throws DateTimeException{
		//return scheduled time from user input
		String scheduledDateTime = null;
		scheduledDateTime = userInput.substring(userInput.lastIndexOf(" by ")+4, userInput.length());
		LocalDateTime scheduledTime = DateTimeCreator.getDateTime(scheduledDateTime);
		return scheduledTime;
	}

	/**
	 * 
	 * @param userInput
	 * @return return a LocalDateTime object
	 * @throws DateTimeException
	 */
	public static LocalDateTime getNormalDateTime(String userInput) throws DateTimeException{
		//return normal time from user input
		String normalDateTime = null;
		LocalDateTime normalTime = null;
		if(userInput.contains("on")){
			normalDateTime = userInput.substring(userInput.lastIndexOf(" on ")+4, userInput.length());
			normalTime = DateTimeCreator.getDateTime(normalDateTime);
		}else if(userInput.contains("at")){
			normalDateTime = userInput.substring(userInput.lastIndexOf(" at ")+4, userInput.length());
			normalTime = DateTimeCreator.getDateTime(normalDateTime);
		}
		return normalTime;
	}
}
