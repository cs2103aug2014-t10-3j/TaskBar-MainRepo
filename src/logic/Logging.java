package logic;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	static FileHandler fileTxt;
	static SimpleFormatter formatter;
	public static Logger logger;
	
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



