/*
 * Storage class
 * Writer: ZiJie
 *
 */
package taskbar;

import java.util.ArrayList;

public class Storage {

	private static ArrayList<Task> allTasks = new ArrayList<Task>();
	private static Storage storage;
	
	/**
	 * Constructor for the class. Since Singleton pattern is applied, this 
	 * constructor is made private access.
	 */
	private Storage() {

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

	public void addTask(Task taskFromLogic) {
		allTasks.add(taskFromLogic);
	}

	// TODO add in exception handling/throwing (Task not found)
	public int deleteTask(Task taskToBeDeleted) {
		boolean removed = allTasks.remove(taskToBeDeleted);
		if (removed) {
			return 1;
		}
		return 0;
	}

	// No longer useful, since we use delete+add to update.
	public int updateTask(Task oldTask, Task newTask) {
		// Overwrite taskA with taskB
		if (allTasks.contains(oldTask)) {
			allTasks.set(allTasks.indexOf(oldTask), newTask);
			return 1;
		}
		return 0; // no such task found to update
	}

	public ArrayList<Task> getAllTasks() {
		// need to figure out how to sort them
		// maybe can have the default display as Sort by Importance display.
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

	public ArrayList<Task> searchTask(String keyWord) {

		ArrayList<Task> searchedTasks = new ArrayList<Task>();

		for (int i = 0; i < allTasks.size(); i++) {
			if (allTasks.get(i).getDescription().contains(keyWord)) {
				searchedTasks.add(allTasks.get(i));
			}
		}

		return searchedTasks;

	}

	public ArrayList<Task> getTasks() {
		// need to figure out how to sort them
		// maybe can have the default display as Sort by Importance display.
		return allTasks;

	}

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
		
	}


