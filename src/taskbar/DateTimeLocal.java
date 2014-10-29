package taskbar;
/**
 * @author Xiaofan
 */


import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateTimeLocal {

	public static LocalDateTime getStartDateTime(String userInput) throws DateTimeException{
		//return start time from user input
		String startDateTime = null;
		startDateTime = userInput.substring(userInput.lastIndexOf(" from ")+6, userInput.lastIndexOf(" to "));
		LocalDateTime startTime = getDateTime(startDateTime);
		if(DateTime.hasEventDate(userInput)){
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
		LocalDateTime endTime = getDateTime(endDateTime);
		if(DateTime.hasEventDate(userInput)){
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
		Pattern pattern = Pattern.compile("on(.*?\\s)from\\s");
		Matcher matcher = pattern.matcher(userInput);
		while(matcher.find()){
			date = matcher.group(1);
		}
		LocalDateTime eventDate = getDateTime(date);
		return eventDate;
	}

	public static LocalDateTime getScheduledDateTime(String userInput) throws DateTimeException{
		//return scheduled time from user input
		String scheduledDateTime = null;
		scheduledDateTime = userInput.substring(userInput.lastIndexOf(" by ")+4, userInput.length());
		LocalDateTime scheduledTime = getDateTime(scheduledDateTime);
		return scheduledTime;
	}

	public static LocalDateTime getNormalDateTime(String userInput) throws DateTimeException{
		//return normal time from user input
		String normalDateTime = null;
		LocalDateTime normalTime = null;
		if(userInput.contains("on")){
			normalDateTime = userInput.substring(userInput.lastIndexOf(" on ")+4, userInput.length());
			normalTime = getDateTime(normalDateTime);
		}else if(userInput.contains("at")){
			normalDateTime = userInput.substring(userInput.lastIndexOf(" at ")+4, userInput.length());
			normalTime = getDateTime(normalDateTime);
		}
		return normalTime;
	}

	public static LocalDateTime getDateTime(String string) throws DateTimeException{
		String dateTime = string.trim();
		if(dateTime.contains(" ")){
			String splitStrSpace[] = dateTime.split("\\s");
			if(splitStrSpace.length == 2){
				if(DateTime.isWordMonth(splitStrSpace[1])){
					String date = splitStrSpace[0];
					String month = splitStrSpace[1];
					if(DateTime.isIntDate(date)){
						return timeFromIntDateAndWordMonth(date, month);
					}
				}else{
					String date = splitStrSpace[0];
					String time = splitStrSpace[1];
					if(DateTime.isNumberDateFormat(date) && DateTime.is12hTimeFormat(time)){
						return timeFromNumDateAnd12hTime(date, time);
					}else if(DateTime.isNumberDateFormat(date) && DateTime.is24hTimeFormat(time)){
						return timeFromNumDateAnd24hTime(date, time);
					}else if(DateTime.isDayDateFormat(date) && DateTime.is12hTimeFormat(time)){
						return timeFromDayDateAnd12hTime(date, time);
					}else if(DateTime.isDayDateFormat(date) && DateTime.is24hTimeFormat(time)){
						return timeFromDayDateAnd24hTime(date, time);
					}
				}
			}
			if(splitStrSpace.length == 3){
				String date = splitStrSpace[0] + " " + splitStrSpace[1];
				String time = splitStrSpace[2];
				if(DateTime.isWordDateFormat(date) && DateTime.is12hTimeFormat(time)){
					return timeFromWordNoYrAnd12hTime(date, time);
				}else if(DateTime.isWordDateFormat(date) && DateTime.is24hTimeFormat(time)){
					return timeFromWordNoYrAnd24hTime(date, time);
				}
			}
			if(splitStrSpace.length == 4){
				String date = splitStrSpace[0] + " " + splitStrSpace[1] + " " + splitStrSpace[2];
				String time = splitStrSpace[3];
				if(DateTime.isWordDateFormat(date) && DateTime.is12hTimeFormat(time)){
					return timeFromWordAnd12hTime(date, time);
				}else if(DateTime.isWordDateFormat(date) && DateTime.is24hTimeFormat(time)){
					return timeFromWordAnd24hTime(date, time);
				}
			}
		}else if(DateTime.is12hTimeFormat(dateTime) || DateTime.is24hTimeFormat(dateTime)){
			if(DateTime.is12hTimeFormat(dateTime)){
				return timeFrom12hTime(dateTime);
			}
			if(DateTime.is24hTimeFormat(dateTime)){
				return timeFrom24hTime(dateTime);
			}
		}else if(DateTime.isDayDateFormat(dateTime)){
			return timeFromDayDate(dateTime);
		}else if(DateTime.isNumberDateFormat(dateTime)){
			return timeFromNumDate(dateTime);
		}
		return null;
	}

	public static LocalDateTime timeFromIntDateAndWordMonth(String date, String month) throws DateTimeException{
		int day = Integer.parseInt(date);
		int intMonth = getIntFromMonth(month);
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime specified = LocalDateTime.of(current.getYear(), intMonth, day, 0, 0, 0, 0);
		return specified;
	}

	public static LocalDateTime timeFromNumDateAnd12hTime(String date, String time) throws DateTimeException{
		String splitSlash[] = date.split("\\/");
		LocalDateTime current = LocalDateTime.now();
		if(splitSlash.length == 2){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			String timeSub = time.substring(0, time.length()-2);
			int time12h = Integer.parseInt(timeSub);
			int hour;
			int minute;
			if (time12h<=12) {
				hour = time12h;
				minute = 0;							
			} else {
				hour = time12h/100;
				minute = time12h%100;
			}
			if(time.contains("pm") && hour!=12){					
				hour = hour + 12;					
			}
			LocalDateTime time1 = LocalDateTime.of(current.getYear(), month, day, hour, minute);
			return time1;
		}else if(splitSlash.length == 3){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			int year = Integer.parseInt(splitSlash[2]);
			String timeSub = time.substring(0, time.length()-2);
			int time12h = Integer.parseInt(timeSub);
			int hour;
			int minute;
			if (time12h<=12) {
				hour = time12h;
				minute = 0;							
			} else {
				hour = time12h/100;
				minute = time12h%100;
			}
			if(time.contains("pm") && hour!=12){					
				hour = hour + 12;					
			}
			if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
				throw new DateTimeException("invalid year");
			}
			year = (year>999) ? year : 2000+year;
			LocalDateTime time2 = LocalDateTime.of(year, month, day, hour, minute);
			return time2;
		}
		return null;
	}

	public static LocalDateTime timeFromNumDateAnd24hTime(String date, String time) throws DateTimeException{
		String splitSlash[] = date.split("\\/");
		LocalDateTime current = LocalDateTime.now();
		if(splitSlash.length == 2){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			int hour = Integer.parseInt(time.substring(0, time.length()-2));
			int min = Integer.parseInt(time.substring(2));
			LocalDateTime time1 = LocalDateTime.of(current.getYear(), month, day, hour, min);
			return time1;
		}else if(splitSlash.length == 3){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			int year = Integer.parseInt(splitSlash[2]);
			int hour = Integer.parseInt(time.substring(0, time.length()-2));
			int min = Integer.parseInt(time.substring(2));
			if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
				throw new DateTimeException("invalid year");
			}
			year = (year>999) ? year : 2000+year;
			LocalDateTime time2 = LocalDateTime.of(year, month, day, hour, min);
			return time2;
		}
		return null;
	}

	public static LocalDateTime timeFromDayDateAnd12hTime(String date, String time) throws DateTimeException{
		LocalDateTime current = LocalDateTime.now();
		String timeSub = time.substring(0, time.length()-2);
		int time12h = Integer.parseInt(timeSub);
		int hour;
		int minute;
		if (time12h<=12) {
			hour = time12h;
			minute = 0;							
		} else {
			hour = time12h/100;
			minute = time12h%100;
		}
		if(time.contains("pm") && hour!=12){					
			hour = hour + 12;					
		}
		LocalDateTime specified;
		if(date.equals("tomorrow") || date.equals("tmr")){
			specified = current.plusDays(1L);
		}else{
			specified = current.plusDays(differenceInDays(date, current));
		}
		specified = specified.withHour(hour).withMinute(minute);
		return specified;
	}

	public static LocalDateTime timeFromDayDateAnd24hTime(String date, String time) throws DateTimeException{
		LocalDateTime current = LocalDateTime.now();
		int hour = Integer.parseInt(time.substring(0, time.length()-2));
		int min = Integer.parseInt(time.substring(2));
		if(date.equals("tomorrow") || date.equals("tmr")){
			LocalDateTime specified = current.plusDays(1);
			specified = specified.withHour(hour);
			specified = specified.withMinute(min);
			return specified;
		}else{
			LocalDateTime specified = current.plusDays(differenceInDays(date, current));
			specified = specified.withHour(hour);
			specified = specified.withMinute(min);
			return specified;
		}
	}

	public static LocalDateTime timeFromWordNoYrAnd12hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		String timeSub = time.substring(0, time.length()-2);
		int time12h = Integer.parseInt(timeSub);
		LocalDateTime current = LocalDateTime.now();
		int hour;
		int minute;
		if (time12h<=12) {
			hour = time12h;
			minute = 0;							
		} else {
			hour = time12h/100;
			minute = time12h%100;
		}
		if(time.contains("pm") && hour!=12){					
			hour = hour + 12;					
		}
		LocalDateTime specified = LocalDateTime.of(current.getYear(), month, day, hour, minute);
		return specified;	
	}

	public static LocalDateTime timeFromWordNoYrAnd24hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int hour = Integer.parseInt(time.substring(0, time.length()-2));
		int min = Integer.parseInt(time.substring(2));
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime specified = LocalDateTime.of(current.getYear(), month, day, hour, min);
		return specified;
	}

	public static LocalDateTime timeFromWordAnd12hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		String timeSub = time.substring(0, time.length()-2);
		int time12h = Integer.parseInt(timeSub);
		int hour;
		int minute;
		if (time12h<=12) {
			hour = time12h;
			minute = 0;							
		} else {
			hour = time12h/100;
			minute = time12h%100;
		}
		if(time.contains("pm") && hour!=12){					
			hour = hour + 12;					
		}
		if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
			throw new DateTimeException("invalid year");
		}
		year = (year>999) ? year : 2000+year;
		LocalDateTime specified = LocalDateTime.of(year, month, day, hour, minute);
		return specified;
	}

	public static LocalDateTime timeFromWordAnd24hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		int hour = Integer.parseInt(time.substring(0, time.length()-2));
		int min = Integer.parseInt(time.substring(2));
		if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
			throw new DateTimeException("invalid year");
		}
		year = (year>999) ? year : 2000+year;
		LocalDateTime specified = LocalDateTime.of(year, month, day, hour, min);
		return specified;
	}

	public static LocalDateTime timeFrom12hTime(String dateTime) throws DateTimeException{
		LocalDateTime current = LocalDateTime.now();
		String timeSub = dateTime.substring(0, dateTime.length()-2);
		int time12h = Integer.parseInt(timeSub);
		int hour;
		int minute;
		if (time12h<=12) {
			hour = time12h;
			minute = 0;							
		} else {
			hour = time12h/100;
			minute = time12h%100;
		}
		if(dateTime.contains("pm") && hour!=12){					
			hour = hour + 12;					
		}
		LocalDateTime specified = current.withHour(hour).withMinute(minute);
		if(specified.isAfter(current)){
			return specified;
		}else{
			return specified.plusDays(1L);
		}
	}

	public static LocalDateTime timeFrom24hTime(String dateTime) throws DateTimeException{
		LocalDateTime current = LocalDateTime.now();
		int hour = Integer.parseInt(dateTime.substring(0, dateTime.length()-2));
		int min = Integer.parseInt(dateTime.substring(2));
		LocalDateTime specified = current.withHour(hour).withMinute(min);
		if(specified.isAfter(current)){
			return specified;
		}else{
			return specified.plusDays(1L);
		}
	}
		

	public static LocalDateTime timeFromDayDate(String dateTime) throws DateTimeException{
		LocalDateTime current = LocalDateTime.now();
		if(dateTime.equals("tomorrow") || dateTime.equals("tmr")){
			LocalDateTime specified = current.plusDays(1L);
			return specified.withHour(0).withMinute(0);
		}else{
			LocalDateTime specified = current.plusDays(differenceInDays(dateTime, current));
			return specified.withHour(0).withMinute(0);
		}
	}

	public static LocalDateTime timeFromNumDate(String dateTime) throws DateTimeException{
		String splitStrSpace[] = dateTime.split("/");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = Integer.parseInt(splitStrSpace[1]);
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime specified;
		if (splitStrSpace.length==3) {
			int year = Integer.parseInt(splitStrSpace[2]);
			if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
				throw new DateTimeException("invalid year");
			}
			year = (year>999) ? year : 2000+year;
			specified = LocalDateTime.of(year, month, day, 0, 0);
		} else {
			specified = LocalDateTime.of(current.getYear(), month, day, 0, 0);
		}
		return specified;
	}

	public static long differenceInDays(String day, LocalDateTime current){
		DayOfWeek currentDay = current.getDayOfWeek();
		int currentDayInt = currentDay.getValue();
		int difference = 0;
		if(day.equals("monday") || day.equals("mon")){
			difference = 1 + 7 - currentDayInt;
		}else if(day.equals("tuesday") || day.equals("tue")){
			if(2 > currentDayInt){
				difference = 2 - currentDayInt;
			}else{
				difference = 2 + 7 - currentDayInt;
			}
		}else if(day.equals("wednesday") || day.equals("wed")){
			if(3 > currentDayInt){
				difference = 3 - currentDayInt;
			}else{
				difference = 3 + 7 - currentDayInt;
			}
		}else if(day.equals("thursday") || day.equals("thu")){
			if(4 > currentDayInt){
				difference = 4 - currentDayInt;
			}else{
				difference = 4 + 7 - currentDayInt;
			}
		}else if(day.equals("friday") || day.equals("fri")){
			if(5 > currentDayInt){
				difference = 5 - currentDayInt;
			}else{
				difference = 5 + 7 - currentDayInt;
			}
		}else if(day.equals("saturday") || day.equals("sat")){
			if(6 > currentDayInt){
				difference = 6 - currentDayInt;
			}else{
				difference = 6 + 7 - currentDayInt;
			}
		}else if(day.equals("sunday") || day.equals("sun")){
			if(7 > currentDayInt){
				difference = 7 - currentDayInt;
			}else{
				difference = 7 + 7 - currentDayInt;
			}
		}
		long temp = Long.valueOf(String.valueOf(difference));
		return temp;
	}

	public static int getIntFromMonth(String string){
		if(string.equals("january") || string.equals("jan")){
			return 1;
		}else if(string.equals("february") || string.equals("feb")){
			return 2;
		}else if(string.equals("march") || string.equals("mar")){
			return 3;
		}else if(string.equals("april") || string.equals("apr")){
			return 4;
		}else if(string.equals("may")){
			return 5;
		}else if(string.equals("june") || string.equals("jun")){
			return 6;
		}else if(string.equals("july") || string.equals("jul")){
			return 7;
		}else if(string.equals("august") || string.equals("aug")){
			return 8;
		}else if(string.equals("september") || string.equals("sep")){
			return 9;
		}else if(string.equals("october") || string.equals("oct")){
			return 10;
		}else if(string.equals("november") || string.equals("nov")){
			return 11;
		}else if(string.equals("december") || string.equals("dec")){
			return 12;
		}
		return 0;
	}
}