package taskbar;

import java.util.ArrayList;

public class Task {
	public Task(String discription, ArrayList<String> labels, int importance) {
		super();
		Discription = discription;
		this.labels = labels;
		this.importance = importance;
	}
	
	private String Discription;
	private boolean isDone;
	private ArrayList<String> labels;
	private int importance;
	
	public String getDiscription() {
		return Discription;
	}
	public void setDiscription(String discription) {
		Discription = discription;
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
	public int getNumLabels(){
		return labels.size();
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public String toString()
	{
		String thisString = "";
		thisString += Discription;
		thisString += "\n";
		thisString += Integer.toString(labels.size());
		thisString += "\n";
		for(int i=0; i<labels.size(); i++){
			thisString += labels.get(i);
			thisString += "\n";
		}
		thisString += "\n";
		thisString += Integer.toString(importance);
		
		return thisString;
	}
	/* commented out because our intended equals() method agrees with java.lang.Object.
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			return this.getDiscription().equals(task.getDiscription());
		} else {
			return false;
		}
	}*/
}

