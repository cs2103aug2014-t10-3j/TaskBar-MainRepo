/*
 * Storage class
 * Writer: ZiJie
 *
 */
package taskbar;

import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.Element;

public class Storage {

	private ArrayList<Task> allTasks;
	private ReadFileJDOM readFile;
	private WriteFileJDOM writeFile;
	private static Storage storage;

	/**
	 * Constructor for the class. Since Singleton pattern is applied, this
	 * constructor is made private access.
	 */
	private Storage() {
		try {
			allTasks = new ArrayList<Task>();
			readFile();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO add in proper handling for IOException
		}

	}

	/**
	 * This method reinforce the Singleton pattern for class Storage, which
	 * means each time the software is run, there should be only one instance of
	 * class Storage.
	 * 
	 * @return The static storage instance.
	 */
	public static Storage getInstance() {
		if (storage != null) {
			return storage;
		}
		storage = new Storage();
		return storage;
	}

	public ArrayList<Task> getAllTasks() {
		// need to figure out how to sort them
		// maybe can have the default display as Sort by Importance display.
		return allTasks;

	}

	public void addTask(Task taskFromLogic) {
		try{
		allTasks.add(taskFromLogic);
		//writeFile();
		}catch(Exception e){
			e.printStackTrace();
		}
		// TODO maybe figure out a way to make incremental modificaiton to the
		// recorded file?
	}

	public int deleteTask(Task taskToBeDeleted) {
		try {// The specified task should exist if the software is running
				// correctly.
			assert allTasks.contains(taskToBeDeleted);

			boolean removed = allTasks.remove(taskToBeDeleted);
			writeFile();
			if (removed) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<Task> searchTask(String keyWord) {

		ArrayList<Task> searchedTasks = new ArrayList<Task>();

		for (int i = 0; i < allTasks.size(); i++) {
			if (allTasks.get(i).getDescription().toLowerCase()
					.contains(keyWord.toLowerCase())) {
				searchedTasks.add(allTasks.get(i));
			}
		}

		return searchedTasks;

	}

	// TODO add in search by time date, getDoneTasks, getNotDoneTasks

	public ArrayList<Task> sortByTime() {
		boolean swapped = true;
		int j = 0;
		Task tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < allTasks.size() - j; i++) {
				if (allTasks.get(i).getDeadline()
						.isBefore(allTasks.get(i + 1).getDeadline())) {
					tmp = allTasks.get(i);
					allTasks.set(i, allTasks.get(i + 1));
					allTasks.set(i + 1, tmp);
					swapped = true;
				}

			}
		}
		return allTasks;
	}

	// TODO Refactor this to sort a given ArrayList
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

	public void writeFile() throws IOException {
		String fileName = "/Users/tasks.xml";
		WriteFileJDOM.writeFileUsingJDOM(allTasks, fileName);
	}

	// IN PROGRESSS
	public void readFile() {
		final String fileName = "/Users/tasks.xml";
		org.jdom2.Document jdomDoc;
		try {
			// we can create JDOM Document from DOM, SAX and STAX Parser Builder
			// classes
			jdomDoc = ReadFileJDOM.useDOMParser(fileName);
			Element root = jdomDoc.getRootElement();
			List<Element> taskListElements = root.getChildren("Task");

			for (Element taskElement : taskListElements) {
				Task task1 = new Task();
				task1.setDescription(taskElement.getChildText("Description"));

				List<Element> list = root.getChildren("Labels");
				ArrayList<String> labelsList = new ArrayList<String>();
				for (Element label : list) {
					labelsList.add(label.getChildText("Label"));
				}

				task1.setLabels(labelsList);

				task1.setImportance(Integer.parseInt(taskElement
						.getChildText("Importance")));
				task1.setDeadline(LocalDateTime.parse(taskElement
						.getChildText("TimeStamp1")));
				task1.setEndTime(LocalDateTime.parse(taskElement
						.getChildText("TimeStamp2")));
				allTasks.add(task1);

			}
			// lets print Employees list information
			for (Task tasks : allTasks)
				System.out.println(tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
