/**
 * @author Xiaofan
 */

package parser;
import java.util.*;

public class parser {
	
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String DISPLAY = "display";

	public static Task interpreter(String command) {
		
		String userInput = command.toLowerCase();
		
		String commandWord = Command.getCommand(userInput);
		
		if(commandWord == ADD){
			String commandDescription = Command.getDescription(userInput);
			String commandLocation = Command.getLocation(userInput);
			//add time and date
			//return a task
		}
		
	}

}
