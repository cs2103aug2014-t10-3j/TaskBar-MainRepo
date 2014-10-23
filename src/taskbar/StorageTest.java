package taskbar;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class StorageTest {
	
	 public static void main(String[] args){
	 
	 
	
		Storage storage = Storage.getInstance();
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("sleep");
		Task task1 = new Task("Sleep on bed",labels, 5 );
		storage.addTask(task1);
		
		try{
			storage.writeFile();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			storage.readFile();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
