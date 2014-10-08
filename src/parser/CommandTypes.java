package parser;

public class CommandTypes {

	public static int SINGLEWORD = -1;

	public String getCommand(String userInput) {
		int indexFirstSpace = userInput.indexOf(' ');
		if (indexFirstSpace == SINGLEWORD) {
			return userInput;
		}
		String command = userInput.substring(0, indexFirstSpace);
		return command;
	}

}
