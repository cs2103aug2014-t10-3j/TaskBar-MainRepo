package taskbar;
/**
 * @author Xiaofan
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.util.ArrayList;

public class Command {

	public static final String ON = "on";
	public static final String AT = "at";
	public static final String FROM = "from";
	public static final String BY = "by";
	public static final String TO = "to";

	public static int SINGLEWORD = -1;
	
	public static String getCommand(String userInput) {
		int indexFirstSpace = userInput.indexOf(' ');
		if (indexFirstSpace == SINGLEWORD) {
			return userInput;
		}
		String command = userInput.substring(0, indexFirstSpace);
		return command;
	}

	public static String getDescriptionOnly(String userInput){
		try{
			int whiteSpaceIndex = userInput.indexOf(' ');
			return userInput.substring(whiteSpaceIndex, userInput.length());
		}catch(IllegalArgumentException e){
			return null;
		}
	}

	public static Boolean isPrepPresent(String userInput){
		if(userInput.contains(AT) || userInput.contains(BY) || userInput.contains(FROM) || userInput.contains(ON)){
			return true;
		}
		return false;
	}

	public static String getDescription(String userInput){
		String description = null;
		if(isPrepPresent(userInput)){
			int[] prepArray;
			prepArray = new int[4];
			Arrays.fill(prepArray, 1000);

			if(userInput.contains(AT)){
				prepArray[0] = userInput.indexOf(AT);
			}
			if(userInput.contains(BY)){
				prepArray[1] = userInput.indexOf(BY);
			}
			if(userInput.contains(FROM)){
				prepArray[2] = userInput.indexOf(FROM);
			}
			if(userInput.contains(ON)){
				prepArray[3] = userInput.indexOf(ON);
			}

			int smallestIndex = 0;

			for(int i=1; i<4; i++){
				if(prepArray[i] < prepArray[i-1]){
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
		}else{
			return userInput.substring(userInput.indexOf("add ")+4, userInput.length());
		}
	}

	public static int getImportance(String userInput){
		int count = userInput.length() - userInput.replaceAll("\\!", "").length();

		return count;
	}
	
	public static String removeImportanceTagString(String userInput){
		String cutUserInput1 = userInput.replaceAll("\\!", "");
		String cutUserInput2 = cutUserInput1.replaceAll("\\s+#[^\\s]+", "");
		return cutUserInput2;
	}
	
	public static ArrayList<String> getTag(String userInput){
		Pattern tagPattern = Pattern.compile("#(\\w+|\\w+)");
		Matcher mat = tagPattern.matcher(userInput);
		ArrayList<String> tags = new ArrayList<String>();
		while(mat.find()){
			tags.add(mat.group(1));
		}
		return tags;		
	}

	public static String getTypeOfTask(String userInput){
		//"by" indicates a scheduled task
		if(userInput.contains(BY)){
			return "scheduled task";
		}else if(userInput.contains(ON) && userInput.contains(FROM) && userInput.contains(TO)){
			return "event";
		}else if(userInput.contains(ON) || userInput.contains(AT)){
			return "task";
		}else if(userInput.contains(FROM) && userInput.contains(TO)){
			return "event";
		}else{
			return "floating";
		}
	}
}