//@author A0111499B
/*
 * Storage class
 * Writer: ZiJie
 *
 *TODO: EXCEPTION HANDLING, TESTING, LOGGING:Logging.getInstance().info("").warning = Refer to controller handle enter method ,
 */
package storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.jdom2.Element;

import util.Logging;
import util.Task;

public class Storage {

	private ArrayList<Task> allTasks;
	private static Storage storage;

	/**
	 * Constructor for the class. Since Singleton pattern is applied, this
	 * constructor is made private access.
	 */
	private Storage() throws IOException {
		try {
			allTasks = new ArrayList<Task>();
			readFile();
			Logging.getInstance().info("Reading of File Successful");

		} catch (Exception e) {
			Logging.getInstance().warning("Reading of File failed, File might be Corrupted");
			throw new IOException();
		}
	}

	/**
	 * This method reinforce the Singleton pattern for class Storage, which
	 * means each time the software is run, there should be only one instance of
	 * class Storage.
	 * 
	 * @return The static storage instance.
	 * @throws IOException 
	 */
	public static Storage getInstance() throws IOException {
		if (storage == null) {
			storage = new Storage();
		}
		return storage;
	}


	public ArrayList<Task> getAllNotDoneTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task t : allTasks) {
			if (!t.isDone()) {
				result.add(t);
			}
		}
		sortByTime(result);
		return result;
	}

	public ArrayList<Task> getAllDoneTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task t : allTasks) {
			if (t.isDone()) {
				result.add(t);
			}
		}
		sortByTime(result);
		return result;
	}

	public ArrayList<Task> getTaskByKeyword(String keyWord) {

		ArrayList<Task> searchedTasks = new ArrayList<Task>();

		outer: for (Task t : allTasks) {
			if (!t.isDone()
					&& t.getDescription().toLowerCase()
							.contains(keyWord.toLowerCase())) {
				searchedTasks.add(t);
			}
			if (!t.isDone() && !t.getLabels().isEmpty()) {
				ArrayList<String> labels = t.getLabels();
				for (String l : labels) {
					if (l.contains(keyWord.toLowerCase())) {
						if(searchedTasks.contains(t)){
							continue outer;
						}
						searchedTasks.add(t);
					}
				}
			}
		}
		sortByTime(searchedTasks);
		return searchedTasks;
	}

	public ArrayList<Task> getTaskByLabel(String parameter) {
		ArrayList<Task> result = new ArrayList<Task>();
		outer: for (Task t : allTasks) {
			if (!t.isDone() && !t.getLabels().isEmpty()) {
				ArrayList<String> labels = t.getLabels();
				for (String l : labels) {
					if (l.contains(parameter.toLowerCase())) {
						if(result.contains(t)){
							continue outer;
						}
						result.add(t);
					}
				}
			}
		}
		sortByTime(result);
		return result;
	}

	public ArrayList<Task> getTasksOnADate(LocalDate referenceDate) {
		ArrayList<Task> result = new ArrayList<Task>();

		for (Task t : allTasks) {
			if (!t.isFloatingTask()) {
				if (t.isDeadLineTask()) {
					if (t.getDeadline().toLocalDate().equals(referenceDate)) {
						result.add(t);
					}
				} else if (t.isEvent()) {
					if (t.getStartTime().toLocalDate().compareTo(referenceDate) <= 0
							&& t.getEndTime().toLocalDate()
									.compareTo(referenceDate) >= 0) {
						result.add(t);
					}
				}
			}
		}
		sortByTime(result);
		return result;
	}
	
	public ArrayList<Task> getFloatingTasks() {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task t : allTasks) {
			if (!t.isDone() && t.isFloatingTask()) {
				result.add(t);
			}
		}
		sortByTime(result);
		return result;
	}

	public void addTask(Task taskFromLogic) {
		try {
			assert !allTasks.contains(taskFromLogic);
			allTasks.add(taskFromLogic);
			writeFile();
			Logging.getInstance().info("Writing of File Successful");
		} catch (Exception e) {
			Logging.getInstance().warning("Writing of File UnSuccessful");
		}
	}

	public void deleteTask(Task task) {
		try {
			assert allTasks.contains(task);
			allTasks.remove(task);
			writeFile();
			Logging.getInstance().info("Writing of File Successful");
		} catch (Exception e) {
			Logging.getInstance().warning("Writing of File UnSuccessful");;
		}
	}

	//for testing purposes. Returns task that is deleted.
	//Otherwise its same as deleteTask
	public Task testdeleteTask(Task task) {
		try {
			assert allTasks.contains(task);
			allTasks.remove(task);
			writeFile();
			Logging.getInstance().info("Writing of File Successful");
			return task;
		} catch (Exception e) {
			Logging.getInstance().warning("Writing of File UnSuccessful");;
		}
		return null;
	}

	
	public void completeTask(Task task) {
		try {
			assert allTasks.contains(task);
			task.setDone(true);
			writeFile();
			Logging.getInstance().info("Writing of File Successful");
		} catch (Exception e) {
			Logging.getInstance().warning("Writing of File UnSuccessful");;
		}
	}

	public void uncompleteTask(Task task) {
		try {
			assert allTasks.contains(task);
			task.setDone(false);
			writeFile();
			Logging.getInstance().info("Writing of File Successful");
		} catch (Exception e) {
			Logging.getInstance().warning("Writing of File UnSuccessful");;
		}
	}



	public void sortByTime(ArrayList<Task> list) {
		boolean swapped = true;
		int j = 0;
		Task tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < list.size() - j; i++) {
				try {
					if (list.get(i).getDeadline()
							.isAfter(list.get(i + 1).getDeadline())) {
						tmp = list.get(i);
						list.set(i, list.get(i + 1));
						list.set(i + 1, tmp);
						swapped = true;
					}
				} catch (NullPointerException e) {
					if (list.get(i).isFloatingTask() && !list.get(i + 1).isFloatingTask()) {
						tmp = list.get(i);
						list.set(i, list.get(i + 1));
						list.set(i + 1, tmp);
						swapped = true;
					}
				}
			}
		}
	}


	public void writeFile() throws IOException {
		String fileName = "ETtasks.xml";
		WriteFileJDOM.writeFileUsingJDOM(allTasks, fileName);
	}

	public void readFile() {
		final String fileName = "ETtasks.xml";
		org.jdom2.Document jdomDoc;
		try {
			// we can create JDOM Document from DOM, SAX and STAX Parser Builder
			// classes
			jdomDoc = ReadFileJDOM.useDOMParser(fileName);
			Logging.getInstance().info("Reading of File Successful");
			Element root = jdomDoc.getRootElement();
			List<Element> taskListElements = root.getChildren("Task");
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm");

			for (Element taskElement : taskListElements) {
				Task task1 = new Task();
				task1.setDescription(taskElement.getChildText("Description"));

				if (taskElement.getChildText("IsDone").equals("true")) {
					task1.setDone(true);
				} else if (taskElement.getChildText("IsDone").equals("false")) {
					task1.setDone(false);
				}

				int i = 0;
				ArrayList<String> labels = new ArrayList<String>();
				while ((taskElement.getChildText("Label" + i)) != null) {
					labels.add(taskElement.getChildText("Label" + i));
					i++;
				}
				task1.setLabels(labels);

				if (taskElement.getChildText("TimeStamp1") != "") {
					task1.setDeadline(LocalDateTime.parse(
							taskElement.getChildText("TimeStamp1"), formatter));
				} else {
					task1.setDeadline(null);
				}

				if (taskElement.getChildText("TimeStamp2") != "") {
					task1.setEndTime(LocalDateTime.parse(
							taskElement.getChildText("TimeStamp2"), formatter));
				} else {
					task1.setEndTime(null);
				}

				allTasks.add(task1);

			}
		} catch (FileNotFoundException e) {
			allTasks = new ArrayList<Task>();
		} catch (Exception e) {
			Logging.getInstance().warning("File might be Corrupted. Reading UnSuccessful");
		}
		
	}
}
