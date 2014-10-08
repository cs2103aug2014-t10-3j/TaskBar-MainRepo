package taskbar;

import java.util.ArrayList;

public class Task {
	public Task(String description, ArrayList<String> labels, int importance) {
		super();
		this.description = description;
		this.labels = labels;
		this.importance = importance;
	}
	
	private String description;
	private boolean isDone;
	private ArrayList<String> labels;
	private int importance;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public ArrayList<String> getLabels() {
		return labels;
	}
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	/* commented out because our intended equals() method agrees with java.lang.Object.
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			return this.getdescription().equals(task.getdescription());
		} else {
			return false;
		}
	}*/
}

