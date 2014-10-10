package parser;

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
		
	}
}
