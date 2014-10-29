package taskbar;
/**
 * @author Xiaofan
 */
import java.time.DateTimeException;

public class DateTime {
	
	public static Boolean is24hTimeFormat(String string){
		if(string.length() == 4){
			try{
				int stringInt = Integer.parseInt(string);
				if(isOnlyNumbers(string) && stringInt <= 2359){
					return true;
				}
			}catch(NumberFormatException e){
				return false;
			}
			
		}
		return false;
	}
	
	public static Boolean is12hTimeFormat(String string){
		int length = string.length();
		String substring = string.substring(length-2, length);
		if(substring.equals("am") || substring.equals("pm")){
			return true;
		}
		return false;
	}
	
	public static Boolean isDayDateFormat(String string){
		if(string.equals("tomorrow") || string.equals("tmr") || string.equals("today")){
			return true;
		}
		if(string.equals("monday") || string.equals("tuesday") || string.equals("wednesday") || string.equals("thursday")
				|| string.equals("friday") || string.equals("saturday") || string.equals("sunday")){
			return true;
		}
		if(string.equals("mon") || string.equals("tue") || string.equals("wed") || string.equals("thu") || string.equals("fri")
				|| string.equals("sat") || string.equals("sun")){
			return true;
		}
		return false;
	}
	
	public static Boolean isNumberDateFormat(String string){
		if(string.contains("/")){
			String splitStrSlash[] = string.split("\\/");
			try{
				if(isIntDate(splitStrSlash[0]) && isIntMonth(splitStrSlash[1]) && isYear(splitStrSlash[2])){
				return true;
				}
			}catch(IndexOutOfBoundsException e){
				if(isIntDate(splitStrSlash[0]) && isIntMonth(splitStrSlash[1])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Boolean isWordDateFormat(String string){
		if(string.contains(" ")){
			String splitStrSpace[] = string.split("\\s");
			if(splitStrSpace.length == 2){
				if(isIntDate(splitStrSpace[0]) && isWordMonth(splitStrSpace[1])){
					return true;
				}
			}else if(splitStrSpace.length == 3){
				if(isIntDate(splitStrSpace[0]) && isWordMonth(splitStrSpace[1]) && isYear(splitStrSpace[2])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Boolean isOnlyNumbers(String string){
		try{
			Integer.parseInt(string);
			return true;
		}catch(NumberFormatException nfe){
			return false;
		}
	}
	
	public static Boolean isIntDate(String string){
		try{
			int date = Integer.parseInt(string);
			if(date >= 1 && date <= 31){
				return true;
			}else{
				return false;
			}
		}catch(NumberFormatException nfe){
			return false;
		}
	}
	
	public static Boolean isIntMonth(String string){
		try{
			int month = Integer.parseInt(string);
			if(month >= 1 && month <= 12){
				return true;
			}else{
				return false;
			}
		}catch(NumberFormatException nfe){
			return false;
		}
	}
	
	public static Boolean isYear(String string){
		if(string.length() == 2 || string.length() == 4){
			return true;
		}
		return false;
	}
	
	public static Boolean isWordMonth(String string){
		if(string.equals("january") || string.equals("jan") || string.equals("february") || string.equals("feb") || string.equals("march")
				|| string.equals("mar") || string.equals("april") || string.equals("apr") || string.equals("may") || string.equals("june")
				|| string.equals("jun") || string.equals("july") || string.equals("jul") || string.equals("august") || string.equals("aug")
				|| string.equals("september") || string.equals("sep") || string.equals("october") || string.equals("oct")
				|| string.equals("november") || string.equals("nov") || string.equals("december") || string.equals("dec")){
			return true;
		}
		return false;
	}
	
}
