package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Task {
	private String description;
	private boolean isDone;
	private ArrayList<String> labels;

	private LocalDateTime timeStamp1;
	private LocalDateTime timeStamp2;

	public Task() {
		labels = new ArrayList<String>();
		this.isDone = false;
	}

	public Task(String description, ArrayList<String> labels) {
		super();
		this.description = description;
		this.labels = labels;
		this.isDone = false;
		timeStamp1 = timeStamp2 = null;
	}

	public Task(String description, ArrayList<String> labels,
			LocalDateTime time) {
		this(description, labels);
		timeStamp1 = time;
		timeStamp2 = null;
	}

	public Task(String description, ArrayList<String> labels,
			LocalDateTime start, LocalDateTime end) {
		this(description, labels);
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

	public int getNumLabels() {
		return labels.size();
	}

	// Specific to DeadlineTask
	public LocalDateTime getDeadline() {
		return timeStamp1;
	}

	// Specific to DeadlineTask
	public void setDeadline(LocalDateTime deadline) {
		this.timeStamp1 = deadline;
	}

	// Specific to Event
	public LocalDateTime getStartTime() {
		return timeStamp1;
	}

	// Specific to Event
	public void setStartTime(LocalDateTime startTime) {
		this.timeStamp1 = startTime;
	}

	// Specific to Event
	public LocalDateTime getEndTime() {
		return timeStamp2;
	}

	// Specific to Event
	public void setEndTime(LocalDateTime endTime) {
		this.timeStamp2 = endTime;
	}

	public String toString() {
		String thisString = "";
		thisString += description;
		thisString += "\n";
		thisString += ("" + labels.size());
		thisString += "\n";
		for (int i = 0; i < labels.size(); i++) {
			thisString += labels.get(i);
			thisString += "\n";
		}
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm");
		if (timeStamp1 != null && timeStamp2 != null) {
			thisString += timeStamp1.format(formatter);
			thisString += "\n";
			thisString += timeStamp2.format(formatter);
		}
		else if (timeStamp1 != null)
		{
			thisString += timeStamp1.format(formatter);
		}
		return thisString;
	}
}
