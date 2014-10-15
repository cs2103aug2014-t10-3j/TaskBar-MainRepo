/**
 * @author Xiaofan
 */

package parser;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Command {
	
	public static final String ON = "on";
	public static final String AT = "at";
	public static final String FROM = "from";
	public static final String BY = "by";

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
		int[] prepArray;
		prepArray = new int[4];
		
		prepArray[0] = userInput.indexOf(AT);
		prepArray[1] = userInput.indexOf(BY);
		prepArray[2] = userInput.indexOf(FROM);
		prepArray[3] = userInput.indexOf(ON);
		
		int smallestIndex = 0;
		
		for(int i=1; i<4; i++){
			if(prepArray[i] < prepArray[smallestIndex]){
				smallestIndex = i; 
			}
		}
		
		
		if(smallestIndex == 0){
			Pattern pattern = Pattern.compile("add(.*?\\s)at\\s");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				description = matcher.group(1);
			}
		}else if(smallestIndex == 1){
			Pattern pattern = Pattern.compile("add(.*?\\s)by\\s");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				description = matcher.group(1);
			}
		}else if(smallestIndex == 2){
			Pattern pattern = Pattern.compile("add(.*?\\s)from\\s");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				description = matcher.group(1);
			}
		}else if(smallestIndex == 3){
			Pattern pattern = Pattern.compile("add(.*?\\s)on\\s");
			Matcher matcher = pattern.matcher(userInput);
			while(matcher.find()){
				description = matcher.group(1);
			}
		}
		return description.trim();
	}
	
	public static int getImportance(String userInput){
		int count = userInput.length() - userInput.replaceAll("\\!", "").length();
		return count;
	}
	
	public static String getDateAndTime(String userInput){
		//add date and time part!
	}
	
}