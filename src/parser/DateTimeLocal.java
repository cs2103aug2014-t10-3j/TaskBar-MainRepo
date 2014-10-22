/**
 * @author Xiaofan
 */

package parser;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeLocal {
	
	public static LocalDateTime getStartDateTime(String userInput){
		//return start time from user input
		String startDateTime = null;
		Pattern pattern = Pattern.compile("from(.*?\\s)to\\s");
		Matcher matcher = pattern.matcher(userInput);
		while(matcher.find()){
			startDateTime = matcher.group(1);
		}
		LocalDateTime startTime = getDateTime(startDateTime);
		return startTime;
	}
	
	public static LocalDateTime getEndDateTime(String userInput){
		//return end time from user input
		String endDateTime = null;
		Pattern pattern = Pattern.compile("to(.*?\\s)");
		Matcher matcher = pattern.matcher(userInput);
		while(matcher.find()){
			endDateTime = matcher.group(1);
		}
		LocalDateTime endTime = getDateTime(endDateTime);
		return endTime;
	}
	
	public static LocalDateTime getScheduledDateTime(String userInput){
		//return scheduled time from user input
		String scheduledDateTime = null;
		Pattern pattern = Pattern.compile("by(.*?\\s)");
		Matcher matcher = pattern.matcher(userInput);
		while(matcher.find()){
			scheduledDateTime = matcher.group(1);
		}
		LocalDateTime scheduledTime = getDateTime(scheduledDateTime);
		return scheduledTime;
	}
	
	public static LocalDateTime getNormalDateTime(String userInput){
		//return normal time from user input
		String normalDateTime = null;
		LocalDateTime normalTime = null;
		if(userInput.contains("on")){
			Pattern pattern = Pattern.compile("on(.*?\\s)");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				normalDateTime = matcher.group(1);
			}
			normalTime = getDateTime(normalDateTime);
		}else if(userInput.contains("at")){
			Pattern pattern = Pattern.compile("at(.*?\\s)");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				normalDateTime = matcher.group(1);
			}
			normalTime = getDateTime(normalDateTime);
		}
		return normalTime;
	}
	
	public static LocalDateTime getDateTime(String string){
		String dateTime = string.trim();
		if(dateTime.contains(" ")){
			String splitStrSpace[] = dateTime.split("\\s");
			if(splitStrSpace.length == 2){
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
	
	public static LocalDateTime timeFromNumDateAnd12hTime(String date, String time){
		String splitSlash[] = date.split("\\/");
		if(splitSlash.length == 2){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			String timeSub = time.substring(0, time.length()-2);
			int time12h = Integer.parseInt(timeSub);
			if(time.contains("pm")){
				time12h = time12h + 12;
			}
			LocalDateTime time1 = LocalDateTime.of(2014, month, day, time12h, 0);
			return time1;
		}else if(splitSlash.length == 3){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			int year = Integer.parseInt(splitSlash[2]);
			String timeSub = time.substring(0, time.length()-2);
			int time12h = Integer.parseInt(timeSub);
			if(time.contains("pm")){
				time12h = time12h + 12;
			}
			LocalDateTime time2 = LocalDateTime.of(year, month, day, time12h, 0);
			return time2;
		}
		return null;
	}
	
	public static LocalDateTime timeFromNumDateAnd24hTime(String date, String time){
		String splitSlash[] = date.split("\\/");
		if(splitSlash.length == 2){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			int hour = Integer.parseInt(time.substring(0, time.length()-2));
			int min = Integer.parseInt(time.substring(2));
			LocalDateTime time1 = LocalDateTime.of(2014, month, day, hour, min);
			return time1;
		}else if(splitSlash.length == 3){
			int day = Integer.parseInt(splitSlash[0]);
			int month = Integer.parseInt(splitSlash[1]);
			int year = Integer.parseInt(splitSlash[2]);
			int hour = Integer.parseInt(time.substring(0, time.length()-2));
			int min = Integer.parseInt(time.substring(2));
			LocalDateTime time2 = LocalDateTime.of(year, month, day, hour, min);
			return time2;
		}
		return null;
	}
	
	public static LocalDateTime timeFromDayDateAnd12hTime(String date, String time){
		LocalDateTime current = LocalDateTime.now();
		String timeSub = time.substring(0, time.length()-2);
		int time12h = Integer.parseInt(timeSub);
		if(time.contains("pm")){
			time12h = time12h + 12;
		}
		if(date == "tomorrow" || date == "tmr"){
			LocalDateTime specified = current.plusDays(1);
			return specified.withHour(time12h);
		}else{
			LocalDateTime specified = current.plusDays(differenceInDays(date, current));
			return specified.withHour(time12h);
		}
	}
	
	public static LocalDateTime timeFromDayDateAnd24hTime(String date, String time){
		LocalDateTime current = LocalDateTime.now();
		int hour = Integer.parseInt(time.substring(0, time.length()-2));
		int min = Integer.parseInt(time.substring(2));
		if(date == "tomorrow" || date == "tmr"){
			LocalDateTime specified = current.plusDays(1);
			specified.withHour(hour);
			specified.withMinute(min);
			return specified;
		}else{
			LocalDateTime specified = current.plusDays(differenceInDays(date, current));
			specified.withHour(hour);
			specified.withMinute(min);
			return specified;
		}
	}
	
	public static LocalDateTime timeFromWordNoYrAnd12hTime(String date, String time){
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		String timeSub = time.substring(0, time.length()-2);
		int time12h = Integer.parseInt(timeSub);
		if(time.contains("pm")){
			time12h = time12h + 12;
		}
		LocalDateTime specified = LocalDateTime.of(2014, month, day, time12h, 0);
		return specified;	
	}
	
	public static LocalDateTime timeFromWordNoYrAnd24hTime(String date, String time){
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int hour = Integer.parseInt(time.substring(0, time.length()-2));
		int min = Integer.parseInt(time.substring(2));
		LocalDateTime specified = LocalDateTime.of(2014, month, day, hour, min);
		return specified;
	}
	
	public static LocalDateTime timeFromWordAnd12hTime(String date, String time){
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		String timeSub = time.substring(0, time.length()-2);
		int time12h = Integer.parseInt(timeSub);
		if(time.contains("pm")){
			time12h = time12h + 12;
		}
		LocalDateTime specified = LocalDateTime.of(year, month, day, time12h, 0);
		return specified;
	}
	
	public static LocalDateTime timeFromWordAnd24hTime(String date, String time){
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		int hour = Integer.parseInt(time.substring(0, time.length()-2));
		int min = Integer.parseInt(time.substring(2));
		LocalDateTime specified = LocalDateTime.of(year, month, day, hour, min);
		return specified;
	}
	
	public static LocalDateTime timeFrom12hTime(String dateTime){
		LocalDateTime current = LocalDateTime.now();
		String timeSub = dateTime.substring(0, dateTime.length()-2);
		int time12h = Integer.parseInt(timeSub);
		if(dateTime.contains("pm")){
			time12h = time12h + 12;
		}
		LocalDateTime specified = current.withHour(time12h);
		return specified;
	}
	
	public static LocalDateTime timeFrom24hTime(String dateTime){
		LocalDateTime current = LocalDateTime.now();
		int hour = Integer.parseInt(dateTime.substring(0, dateTime.length()-2));
		int min = Integer.parseInt(dateTime.substring(2));
		LocalDateTime specified = current.withHour(hour);
		return specified.withMinute(min);
	}
	
	public static LocalDateTime timeFromDayDate(String dateTime){
		LocalDateTime current = LocalDateTime.now();
		if(dateTime == "tomorrow" || dateTime == "tmr"){
			LocalDateTime specified = current.plusDays(1);
			return specified;
		}else{
			LocalDateTime specified = current.plusDays(differenceInDays(dateTime, current));
			return specified;
		}
	}
	
	public static LocalDateTime timeFromNumDate(String dateTime){
		String splitStrSpace[] = dateTime.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = Integer.parseInt(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		LocalDateTime specified = LocalDateTime.of(year, month, day, 0, 0);
		return specified;
	}
	
	public static long differenceInDays(String day, LocalDateTime current){
		DayOfWeek currentDay = current.getDayOfWeek();
		long currentDayInt = currentDay.getValue();
		long difference = 0;
		if(day == "monday" || day == "mon"){
			difference = 1 + 7 - currentDayInt;
		}else if(day == "tuesday" || day == "tue"){
			if(2 > currentDayInt){
				difference = 2 - currentDayInt;
			}else{
				difference = 2 + 7 - currentDayInt;
			}
		}else if(day == "wednesday" || day == "wed"){
			if(3 > currentDayInt){
				difference = 3 - currentDayInt;
			}else{
				difference = 3 + 7 - currentDayInt;
			}
		}else if(day == "thursday" || day == "thu"){
			if(4 > currentDayInt){
				difference = 4 - currentDayInt;
			}else{
				difference = 4 + 7 - currentDayInt;
			}
		}else if(day == "friday" || day == "fri"){
			if(5 > currentDayInt){
				difference = 5 - currentDayInt;
			}else{
				difference = 5 + 7 - currentDayInt;
			}
		}else if(day == "saturday" || day == "sat"){
			if(6 > currentDayInt){
				difference = 6 - currentDayInt;
			}else{
				difference = 6 + 7 - currentDayInt;
			}
		}else if(day == "sunday" || day == "sun"){
			if(7 > currentDayInt){
				difference = 7 - currentDayInt;
			}else{
				difference = 7 + 7 - currentDayInt;
			}
		}
		return difference;
	}
	
	public static int getIntFromMonth(String string){
		if(string == "january" || string == "jan"){
			return 1;
		}else if(string == "february" || string == "feb"){
			return 2;
		}else if(string == "march" || string == "mar"){
			return 3;
		}else if(string == "april" || string == "apr"){
			return 4;
		}else if(string == "may"){
			return 5;
		}else if(string == "june" || string == "jun"){
			return 6;
		}else if(string == "july" || string == "jul"){
			return 7;
		}else if(string == "august" || string == "aug"){
			return 8;
		}else if(string == "september" || string == "sep"){
			return 9;
		}else if(string == "october" || string == "oct"){
			return 10;
		}else if(string == "november" || string == "nov"){
			return 11;
		}else if(string == "december" || string == "dec"){
			return 12;
		}
		return 0;
	}
}