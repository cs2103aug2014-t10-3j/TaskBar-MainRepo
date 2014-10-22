/*
 * Storage class
 * Writer: ZiJie
 *
 */
package taskbar;

import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.Element;

public class Storage {

<<<<<<< HEAD
	private static ArrayList<Task> allTasks = new ArrayList<Task>();

	public Storage() {
=======
	private ArrayList<Task> allTasks;
	private FileHandler fileHandler;
	private static Storage storage;
	
	/**
	 * Constructor for the class. Since Singleton pattern is applied, this 
	 * constructor is made private access.
	 */
	private Storage(){
		try{
			fileHandler = new FileHandler();
			allTasks = fileHandler.readFromFile();
		}catch(Exception e){
			e.printStackTrace();
			//TODO add in proper handling for IOException
		}
		
	}
	
	/**
	 * This method reinforce the Singleton pattern for class Storage, which 
	 * means each time the software is run, there should be only one instance
	 * of class Storage.
	 * @return The static storage instance.
	 */
	public static Storage getInstance(){
		if(storage != null){
			return storage;
		}
		storage = new Storage();
		return storage;
	}

	public ArrayList<Task> getAllTasks() {
		// need to figure out how to sort them
		// maybe can have the default display as Sort by Importance display.
		return allTasks;
>>>>>>> origin/master
	}

	public void addTask(Task taskFromLogic) {
		allTasks.add(taskFromLogic);
		fileHandler.writeToFile(allTasks);
		//TODO maybe figure out a way to make incremental modificaiton to the recorded file? 
	}


	public int deleteTask(Task taskToBeDeleted) {
		//The specified task should exist if the software is running correctly.
		assert allTasks.contains(taskToBeDeleted);
		
		boolean removed = allTasks.remove(taskToBeDeleted);
		fileHandler.writeToFile(allTasks);
		if (removed) {
			return 1;
		}
		return 0;
	}

	public ArrayList<Task> searchTask(String keyWord) {

		ArrayList<Task> searchedTasks = new ArrayList<Task>();

		for (int i = 0; i < allTasks.size(); i++) {
			if (allTasks.get(i).getDescription().toLowerCase().contains(
					keyWord.toLowerCase())) {
				searchedTasks.add(allTasks.get(i));
			}
		}

		return searchedTasks;

	}
	
	//TODO add in search by time date, getDoneTasks, getNotDoneTasks

	public ArrayList<Task> sortByTime() {
		boolean swapped = true;
		int j = 0;
		Task tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < allTasks.size() - j; i++) {
				if (allTasks.get(i).getDeadline().isBefore(allTasks.get(i + 1).getDeadline()))
				{
					tmp = allTasks.get(i);
					allTasks.set(i, allTasks.get(i + 1));
					allTasks.set(i + 1, tmp);
					swapped = true;
				}
				
			}
		}
		return allTasks;
	}

	//TODO Refactor this to sort a given ArrayList
	public ArrayList<Task> sortByImportance() {
		boolean swapped = true;
		int j = 0;
		Task tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < allTasks.size() - j; i++) {
				if (allTasks.get(i).getImportance() < allTasks.get(i + 1)
						.getImportance()) {
					tmp = allTasks.get(i);
					allTasks.set(i, allTasks.get(i + 1));
					allTasks.set(i + 1, tmp);
					swapped = true;
				}
				
			}
		}
		return allTasks;
	}
		
	


	public void writeFile() throws IOException{
		 String fileName = "task.xml";
		 WriteFileJDOM.writeFileUsingJDOM(allTasks, fileName);
	}
	
	//IN PROGRESSS
	public void readFile(){
		
	}
	}
	

