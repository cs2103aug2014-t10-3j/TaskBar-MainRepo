//@author A0115718E
package interpreter;
/**
 * @author Xiaofan
 */

public class DateTime {
	
	public static Boolean hasEventDate(String string){
		if(string.contains(" on ") && string.contains(" from ") && string.contains(" to ")){
			return true;
		}
		return false;
	}
	public static Boolean is24hTimeFormat(String string){
		if(string.length() == 4){
			try{
				if(isOnlyNumbers(string)){
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
				if(isOnlyNumbers(splitStrSlash[0]) && isOnlyNumbers(splitStrSlash[1]) && isOnlyNumbers(splitStrSlash[2])){
				return true;
				}
			}catch(IndexOutOfBoundsException e){
				if(isOnlyNumbers(splitStrSlash[0]) && isOnlyNumbers(splitStrSlash[1])){
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
				if(isOnlyNumbers(splitStrSpace[0]) && isWordMonth(splitStrSpace[1])){
					return true;
				}
			}else if(splitStrSpace.length == 3){
				if(isOnlyNumbers(splitStrSpace[0]) && isWordMonth(splitStrSpace[1]) && isOnlyNumbers(splitStrSpace[2])){
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
