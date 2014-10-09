package taskbar;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {
	private String description;
	private boolean isDone;
	private ArrayList<String> labels;
	private int importance;

	private LocalDateTime timeStamp1;
	private LocalDateTime timeStamp2;

	public Task(String description, ArrayList<String> labels, int importance) {
		super();
		this.description = description;
		this.labels = labels;
		this.importance = importance;
		this.isDone = false;
		timeStamp1 = timeStamp2 = null;
	}

	public Task(String description, ArrayList<String> labels, int importance,
			LocalDateTime time) {
		this(description, labels, importance);
		timeStamp1 = time;
	}

	public Task(String description, ArrayList<String> labels, int importance,
			LocalDateTime start, LocalDateTime end) {
		this(description, labels, importance);
		timeStamp1 = start;
		timeStamp2 = end;
	}

	public boolean isFloatingTask() {
		return timeStamp1 == null && timeStamp2 == null;
	}

	public boolean isDeadLineTask() {
		return timeStamp1 != null && timeStamp2 == null;
	}

	public boolean isEvent() {
		return timeStamp1 != null && timeStamp2 != null;
	}

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
	
	//Specific to DeadlineTask
	public LocalDateTime getDeadline() {
		return timeStamp1;
	}
	//Specific to DeadlineTask
	public void setDeadline(LocalDateTime deadline) {
		this.timeStamp1 = deadline;
	}
	//Specific to Event
	public LocalDateTime getStartTime() {
		return timeStamp1;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.timeStamp1 = startTime;
	}

	public LocalDateTime getEndTime() {
		return timeStamp2;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.timeStamp2 = endTime;
	}
}
