/**
 * @author Xiaofan
 */

package parser;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.joestelmach.natty.*;

public class DateTimeLocal {
	
	public static LocalDateTime getStartDateTime(String userInput){
		//return start time from user input using natty lib
	}
	
	public static LocalDateTime getEndDateTime(String userInput){
		//return end time from user input using natty lib
	}
	
	public static LocalDateTime scheduledDateTime(String userInput){
		//return scheduled time from user input using natty lib
	}
	
	private static void getDatesFromUserInput(String userInput){
		Parser parser = new Parser();
		List<DateGroup> dateGroups = parser.parse(userInput);
		if(dateGroups > 0){
			for(DateGroup group:dateGroups) {
				List<Date> datesFromInput = group.getDates();
			}
			Date currentDate = new Date();
			for(Date dateDate: datesFromInput){
					//more things to add
					//natty library
			}
		}else{
			datesFromInput = null;
		}
	}
	
}