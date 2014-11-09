//@author A0116756Y
/**
 * Provides the Logger utility to be used across all components of ET.
 */
package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	static FileHandler fileTxt;
	static SimpleFormatter formatter;
	public static Logger logger;
	
	/**
	 * ET uses Logger in a Singleton fashion. This method allows other class to
	 * get the reference to the Logger instance in the current runtime.
	 * @return the singleton instance of Logger in the current runtime.
	 */
	public static Logger getInstance(){
		if (logger == null){
			logger = Logger.getLogger("ET");
			try {
				fileTxt = new FileHandler("ETLog.txt",true);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			formatter = new SimpleFormatter();  
			logger.setLevel(Level.ALL);
	        fileTxt.setFormatter(formatter);
			logger.addHandler(fileTxt);
		}
		return logger;
	}
}



