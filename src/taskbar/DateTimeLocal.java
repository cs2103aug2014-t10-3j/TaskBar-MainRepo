package taskbar;
/**
 * @author Xiaofan
 */

import java.time.LocalDateTime;
import java.time.DayOfWeek;


public class DateTimeLocal {
	
	public static LocalDateTime getStartDateTime(String userInput){
		//return start time from user input
		String startDateTime = null;
		startDateTime = userInput.substring(userInput.lastIndexOf(" from ")+6, userInput.lastIndexOf(" to "));
		LocalDateTime startTime = getDateTime(startDateTime);
		return startTime;
	}
	
	public static LocalDateTime getEndDateTime(String userInput){
		//return end time from user input
		String endDateTime = null;
		endDateTime = userInput.substring(userInput.lastIndexOf(" to ")+4, userInput.length());
		LocalDateTime endTime = getDateTime(endDateTime);
		return endTime;
	}
	
	public static LocalDateTime getScheduledDateTime(String userInput){
		//return scheduled time from user input
		String scheduledDateTime = null;
		scheduledDateTime = userInput.substring(userInput.lastIndexOf(" by ")+4, userInput.length());
		LocalDateTime scheduledTime = getDateTime(scheduledDateTime);
		return scheduledTime;
	}
	
	public static LocalDateTime getNormalDateTime(String userInput){
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
				if(time12h != 12){
					time12h = time12h + 12;
				}
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
				if(time12h != 12){
					time12h = time12h + 12;
				}
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
			if(time12h != 12){
				time12h = time12h + 12;
			}
		}
		if(date.equals("tomorrow") || date.equals("tmr")){
			LocalDateTime specified = current.plusDays(1L);
			specified = specified.withMinute(0);
			return specified.withHour(time12h);
		}else{
			LocalDateTime specified = current.plusDays(differenceInDays(date, current));
			specified = specified.withMinute(0);
			return specified.withHour(time12h);
		}
	}
	
	public static LocalDateTime timeFromDayDateAnd24hTime(String date, String time){
		LocalDateTime current = LocalDateTime.now();
		int hour = Integer.parseInt(time.substring(0, time.length()-2));
		int min = Integer.parseInt(time.substring(2));
		if(date.equals("tomorrow") || date.equals("tmr")){
			LocalDateTime specified = current.plusDays(1L);
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
	
	public static LocalDateTime timeFromWordNoYrAnd12hTime(String date, String time){
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		String timeSub = time.substring(0, time.length()-2);
		int time12h = Integer.parseInt(timeSub);
		if(time.contains("pm")){
			if(time12h != 12){
				time12h = time12h + 12;
			}
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
			if(time12h != 12){
				time12h = time12h + 12;
			}
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
			if(time12h != 12){
				time12h = time12h + 12;
			}
		}
		LocalDateTime specified = current.withHour(time12h);
		return specified.withMinute(0);
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
		if(dateTime.equals("tomorrow") || dateTime.equals("tmr")){
			LocalDateTime specified = current.plusDays(1L);
			return specified.withHour(0).withMinute(0);
		}else{
			LocalDateTime specified = current.plusDays(differenceInDays(dateTime, current));
			return specified.withHour(0).withMinute(0);
		}
	}
	
	public static LocalDateTime timeFromNumDate(String dateTime){
		String splitStrSpace[] = dateTime.split("/");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = Integer.parseInt(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		LocalDateTime specified = LocalDateTime.of(year, month, day, 0, 0);
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