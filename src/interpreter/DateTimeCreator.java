//@author A0115718E
package interpreter;


import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.DayOfWeek;


public class DateTimeCreator {

	public static LocalDateTime getDateTime(String string) throws DateTimeException{
		String dateTime = string.trim();
		LocalDateTime specified = null;
		LocalDateTime current = LocalDateTime.now();
		if(DateTimeIdentifier.isNextWeek(string)){
			specified = timeFromNextWeek(string);
		}
		if(dateTime.contains(" ")){
			String splitStrSpace[] = dateTime.split("\\s");
			if(splitStrSpace.length == 2){
				if(DateTimeIdentifier.isWordMonth(splitStrSpace[1])){
					String date = splitStrSpace[0];
					String month = splitStrSpace[1];
					if(DateTimeIdentifier.isOnlyNumbers(date)){
						specified = timeFromIntDateAndWordMonth(date, month);
					}
				}else{
					String date = splitStrSpace[0];
					String time = splitStrSpace[1];
					if(DateTimeIdentifier.isNumberDateFormat(date) && DateTimeIdentifier.is12hTimeFormat(time)){
						specified = timeFromNumDateAnd12hTime(date, time);
					}else if(DateTimeIdentifier.isNumberDateFormat(date) && DateTimeIdentifier.is24hTimeFormat(time)){
						specified = timeFromNumDateAnd24hTime(date, time);
					}else if(DateTimeIdentifier.isDayDateFormat(date) && DateTimeIdentifier.is12hTimeFormat(time)){
						specified = timeFromDayDateAnd12hTime(date, time);
					}else if(DateTimeIdentifier.isDayDateFormat(date) && DateTimeIdentifier.is24hTimeFormat(time)){
						specified = timeFromDayDateAnd24hTime(date, time);
					}
				}
			}
			if(splitStrSpace.length == 3){
				String date = splitStrSpace[0] + " " + splitStrSpace[1];
				String time = splitStrSpace[2];
				if(DateTimeIdentifier.isWordDateFormat(date) && DateTimeIdentifier.is12hTimeFormat(time)){
					specified = timeFromWordNoYrAnd12hTime(date, time);
				}else if(DateTimeIdentifier.isWordDateFormat(date) && DateTimeIdentifier.is24hTimeFormat(time)){
					specified = timeFromWordNoYrAnd24hTime(date, time);
				}
			}
			if(splitStrSpace.length == 4){
				String date = splitStrSpace[0] + " " + splitStrSpace[1] + " " + splitStrSpace[2];
				String time = splitStrSpace[3];
				if(DateTimeIdentifier.isWordDateFormat(date) && DateTimeIdentifier.is12hTimeFormat(time)){
					specified = timeFromWordAnd12hTime(date, time);
				}else if(DateTimeIdentifier.isWordDateFormat(date) && DateTimeIdentifier.is24hTimeFormat(time)){
					specified = timeFromWordAnd24hTime(date, time);
				}
			}
		}else if(DateTimeIdentifier.is12hTimeFormat(dateTime) || DateTimeIdentifier.is24hTimeFormat(dateTime)){
			if(DateTimeIdentifier.is12hTimeFormat(dateTime)){
				specified = timeFrom12hTime(dateTime);
			}
			if(DateTimeIdentifier.is24hTimeFormat(dateTime)){
				specified = timeFrom24hTime(dateTime);
			}
		}else if(DateTimeIdentifier.isDayDateFormat(dateTime)){
			specified = timeFromDayDate(dateTime);
		}else if(DateTimeIdentifier.isNumberDateFormat(dateTime)){
			specified = timeFromNumDate(dateTime);
		}
		return specified;
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
		int day = Integer.parseInt(splitSlash[0]);
		int month = Integer.parseInt(splitSlash[1]);
		if(splitSlash.length == 3){
			int year = Integer.parseInt(splitSlash[2]);
			if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
				throw new DateTimeException("invalid year");
			}
			year = (year>999) ? year : 2000+year;
			LocalDateTime time1 = LocalDateTime.of(year, month, day, timeFrom12hTime(time).getHour(), timeFrom12hTime(time).getMinute());
			return time1;
		}
		LocalDateTime time2 = LocalDateTime.of(current.getYear(), month, day, timeFrom12hTime(time).getHour(), timeFrom12hTime(time).getMinute());
		return time2;
	}

	public static LocalDateTime timeFromNumDateAnd24hTime(String date, String time) throws DateTimeException{
		String splitSlash[] = date.split("\\/");
		LocalDateTime current = LocalDateTime.now();
		int day = Integer.parseInt(splitSlash[0]);
		int month = Integer.parseInt(splitSlash[1]);
		if(splitSlash.length == 3){
			int year = Integer.parseInt(splitSlash[2]);
			if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
				throw new DateTimeException("invalid year");
			}
			year = (year>999) ? year : 2000+year;
			LocalDateTime time1 = LocalDateTime.of(year, month, day, timeFrom24hTime(time).getHour(), timeFrom24hTime(time).getMinute());
			return time1;
		}
		LocalDateTime time2 = LocalDateTime.of(current.getYear(), month, day, timeFrom24hTime(time).getHour(), timeFrom24hTime(time).getMinute());
		return time2;
	}

	public static LocalDateTime timeFromDayDateAnd12hTime(String date, String time) throws DateTimeException{
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime specified;
		if(date.equals("tomorrow") || date.equals("tmr")){
			specified = current.plusDays(1L);
		}else{
			specified = current.plusDays(differenceInDays(date, current));
		}
		specified = specified.withHour(timeFrom12hTime(time).getHour()).withMinute(timeFrom12hTime(time).getMinute());
		return specified;
	}

	public static LocalDateTime timeFromDayDateAnd24hTime(String date, String time) throws DateTimeException{
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime specified = current.withHour(timeFrom24hTime(time).getHour()).withMinute(timeFrom24hTime(time).getMinute());
		if(date.equals("tomorrow") || date.equals("tmr")){
			specified = current.plusDays(1);
		}else{
			specified = current.plusDays(differenceInDays(date, current));
		}
			return specified;
	}

	public static LocalDateTime timeFromWordNoYrAnd12hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime specified = LocalDateTime.of(current.getYear(), month, day, timeFrom12hTime(time).getHour(), timeFrom12hTime(time).getMinute());
		return specified;	
	}

	public static LocalDateTime timeFromWordNoYrAnd24hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime specified = LocalDateTime.of(current.getYear(), month, day, timeFrom24hTime(time).getHour(), timeFrom24hTime(time).getMinute());
		return specified;
	}

	public static LocalDateTime timeFromWordAnd12hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
			throw new DateTimeException("invalid year");
		}
		year = (year>999) ? year : 2000+year;
		LocalDateTime specified = LocalDateTime.of(year, month, day, timeFrom12hTime(time).getHour(), timeFrom12hTime(time).getMinute());
		return specified;
	}

	public static LocalDateTime timeFromWordAnd24hTime(String date, String time) throws DateTimeException{
		String splitStrSpace[] = date.split("\\s");
		int day = Integer.parseInt(splitStrSpace[0]);
		int month = getIntFromMonth(splitStrSpace[1]);
		int year = Integer.parseInt(splitStrSpace[2]);
		if ((year<=999 && year>=100) || (year>=0 && year<=9)) {
			throw new DateTimeException("invalid year");
		}
		year = (year>999) ? year : 2000+year;
		LocalDateTime specified = LocalDateTime.of(year, month, day, timeFrom24hTime(time).getHour(), timeFrom24hTime(time).getMinute());
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
	
	public static LocalDateTime timeFromNextWeek(String dayTime){
		LocalDateTime specified = null;
		String splitStrSpace[] = dayTime.split("\\s");
		String day = splitStrSpace[1];
		LocalDateTime current = LocalDateTime.now();
		DayOfWeek currentDay = current.getDayOfWeek();
		int currentDayInt = currentDay.getValue();
		int dayFromString = getIntFromDay(day);
		if(dayFromString > currentDayInt){
			int difference = dayFromString - currentDayInt;
			int finalDiffInt = difference + 7;
			long temp = Long.valueOf(String.valueOf(finalDiffInt));
			specified = current.plusDays(temp);
		}else{
			specified = timeFromDayDate(day);
		}
		
		if(splitStrSpace.length == 3){
			String time = splitStrSpace[2];
			if(DateTimeIdentifier.is12hTimeFormat(time)){
				return specified.withHour(timeFrom12hTime(time).getHour()).withMinute(timeFrom12hTime(time).getMinute());
			}else if(DateTimeIdentifier.is24hTimeFormat(time)){
				return specified.withHour(timeFrom24hTime(time).getHour()).withMinute(timeFrom24hTime(time).getMinute());
			}
		}
		return specified.withHour(0).withMinute(0);
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
	
	public static int getIntFromDay(String day){
		int dayInt = 0;
		if(day.equals("monday") || day.equals("mon")){
			dayInt = 1;
		}else if(day.equals("tuesday") || day.equals("tue")){
			dayInt = 2;
		}else if(day.equals("wednesday") || day.equals("wed")){
			dayInt = 3;
		}else if(day.equals("thursday") || day.equals("thu")){
			dayInt = 4;
		}else if(day.equals("friday") || day.equals("fri")){
			dayInt = 5;
		}else if(day.equals("saturday") || day.equals("sat")){
			dayInt = 6;
		}else if(day.equals("sunday") || day.equals("sun")){
			dayInt = 7;
		}
		return dayInt;
	}

	public static long differenceInDays(String day, LocalDateTime current){
		DayOfWeek currentDay = current.getDayOfWeek();
		int currentDayInt = currentDay.getValue();
		int difference = 0;
		int dayInt = getIntFromDay(day);
		if(dayInt == 1){
			difference = 1 + 7 - currentDayInt;
		}else if(dayInt == 2){
			if(2 > currentDayInt){
				difference = 2 - currentDayInt;
			}else{
				difference = 2 + 7 - currentDayInt;
			}
		}else if(dayInt == 3){
			if(3 > currentDayInt){
				difference = 3 - currentDayInt;
			}else{
				difference = 3 + 7 - currentDayInt;
			}
		}else if(dayInt == 4){
			if(4 > currentDayInt){
				difference = 4 - currentDayInt;
			}else{
				difference = 4 + 7 - currentDayInt;
			}
		}else if(dayInt == 5){
			if(5 > currentDayInt){
				difference = 5 - currentDayInt;
			}else{
				difference = 5 + 7 - currentDayInt;
			}
		}else if(dayInt == 6){
			if(6 > currentDayInt){
				difference = 6 - currentDayInt;
			}else{
				difference = 6 + 7 - currentDayInt;
			}
		}else if(dayInt == 7){
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