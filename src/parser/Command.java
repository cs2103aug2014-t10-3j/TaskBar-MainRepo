package parser;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Command {
	
	public static final String ON = "on";
	public static final String AT = "at";

	public static int SINGLEWORD = -1;

	public static String getCommand(String userInput) {
		int indexFirstSpace = userInput.indexOf(' ');
		if (indexFirstSpace == SINGLEWORD) {
			return userInput;
		}
		String command = userInput.substring(0, indexFirstSpace);
		return command;
	}

	public static String getDescription(String userInput){
		String description = null;
		
		if(userInput.indexOf(ON) < userInput.indexOf(AT)){
			Pattern pattern = Pattern.compile("add(.*?\\s)on\\s");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				description = matcher.group(1);
			}
		}else if(userInput.indexOf(AT) < userInput.indexOf(ON)){
			Pattern pattern = Pattern.compile("add(.*?\\s)at\\s");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				description = matcher.group(1);
			}
		}
		return description.trim();
	}
	
	public static String getLocation(String userInput){
		String location = null;
		
		if(userInput.indexOf(ON) < userInput.indexOf(AT)){
			location = userInput.substring(userInput.indexOf(AT)+3, userInput.length());
		}else if(userInput.indexOf(AT) < userInput.indexOf(ON)){
			Pattern pattern = Pattern.compile("\\sat(\\s.*?\\s)on\\s");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				location = matcher.group(1);
			}
		}
		return location.trim();
	}
}
