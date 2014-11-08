package interpreter;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeGetter {

	public static LocalDateTime getStartDateTime(String userInput) throws DateTimeException{
		//return start time from user input
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

	public static LocalDateTime getEndDateTime(String userInput) throws DateTimeException{
		//return end time from user input
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

	public static LocalDateTime getScheduledDateTime(String userInput) throws DateTimeException{
		//return scheduled time from user input
		String scheduledDateTime = null;
		scheduledDateTime = userInput.substring(userInput.lastIndexOf(" by ")+4, userInput.length());
		LocalDateTime scheduledTime = DateTimeCreator.getDateTime(scheduledDateTime);
		return scheduledTime;
	}

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
