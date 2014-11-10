//@author A0115718E
/**
 * This class identifies the task's description and tags of the input string
 * and returns them as the parameters for Task object;
 * Input from user should include preposition i.e. "on" "at" "from" "by" "to";
 * Any content with the double quotes " " will be escaped.
 * Tags should come after "#";
 * 
 */
package logic.interpreter;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.util.ArrayList;

public class CommandDetails {

	public static final String ON = " on ";
	public static final String AT = " at ";
	public static final String FROM = " from ";
	public static final String BY = " by ";
	public static final String TO = " to ";
	
	/**
	 * @return boolean value, checks if preposition is present
	 */

	private static Boolean isPrepPresent(String userInput){
		if(userInput.contains(AT) || userInput.contains(BY) ||
				userInput.contains(FROM) || userInput.contains(ON)){
			return true;
		}
		return false;
	}

	/**
	 * @return the description of the task as a string object
	 * Regular Expression "add(.*?\\s)at\\s" means anything between "add" and "at ";
	 * Regular Expression "add(.*?\\s)by\\s" means anything between "add" and "by ";
	 * Regular Expression "add(.*?\\s)from\\s" means anything between "add" and "from ";
	 * Regular Expression "add(.*?\\s)on\\s" means anything between "add" and "on ";
	 * UserInput.replaceAll("\\s+#[^\\s]+", "") with regular Expression "\\s+#[^\\s]+" 
	 * means to remove all " #" followed by whatever word behind, e.g. " #school"
	 * Regular Expression "#(\\w+|\\w+)" means any word followed by "#"
	 * 
	 */
	
	public static String getDescription(String userInput){
		String description = null;
		
		if(containsTwoQuotes(userInput)){
			description = getQuotedDescription(userInput);
		}else if(isPrepPresent(userInput)){
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
		return description;
	}
	
	/**
	 * @return a string without the tags for further operations
	 */
	
	public static String removeTagString(String userInput){
		String cutUserInput = userInput.replaceAll("\\s+#[^\\s]+", "");
		return cutUserInput;
	}
	
	/**
	 * @return a string without the tags and the escaped substring for further operations
	 */
	
	public static String removeDescriptionString(String userInput){
		String tmp = getQuotedDescription(userInput);
		String cutUserInput = userInput.replace(tmp, "");
		return cutUserInput;
	}
	
	/**
	 * @return a string within double quotes
	 */
	
	private static String getQuotedDescription(String userInput){
		int index1 = userInput.indexOf('\"');
		int index2 = userInput.lastIndexOf('\"');
		return userInput.substring(index1+1, index2);
	}
	
	/**
	 * @return an arraylist of tags for further operations
	 */
	
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
	
	private static Boolean containsTwoQuotes(String str){
		int count = 0;
		for(int i=0;i<str.length();i++){
			if(str.charAt(i) == '\"'){
				count++;
			}
		}
		if(count == 2){
			return true;
		}
		return false;
	}
}