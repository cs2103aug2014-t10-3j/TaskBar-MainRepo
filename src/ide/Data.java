package ide;

import taskbar.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



import javafx.beans.property.SimpleStringProperty;

public class Data {
	private SimpleStringProperty desc;
	private SimpleStringProperty tag;
	private SimpleStringProperty date;
	private SimpleStringProperty time;
	
	DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
	
	public Data(Task task) {
		String newDesc = task.getDescription();
		ArrayList<String> newTag = task.getLabels();
		LocalDateTime time1 = task.getStartTime();
		LocalDateTime time2 = task.getEndTime();
		
		desc = new SimpleStringProperty(newDesc);
		tag = new SimpleStringProperty(arrayToString(newTag));
		date = new SimpleStringProperty(processDate(time1, time2));
		time = new SimpleStringProperty(processTime(time1, time2));		
	}
	
	public Data (String newDesc, ArrayList<String> newTag, LocalDateTime time1, LocalDateTime time2) {
		desc = new SimpleStringProperty(newDesc);
		tag = new SimpleStringProperty(arrayToString(newTag));
		date = new SimpleStringProperty(processDate(time1, time2));
		time = new SimpleStringProperty(processTime(time1, time2));
	}
	
	public String getDesc() {
		return desc.get();
	}
	
	public String getTag() {
		return tag.get();
	}
	
	public String getDate() {
		return date.get();
	}
	
	public String getTime() {
		return time.get();
	}
	
	public void setDesc(String newDesc) {
		desc.set(newDesc);
	}
	
	public void setTag(ArrayList<String> labels) {
		tag.set(arrayToString(labels));
	}
	
	public void setDate(LocalDateTime t1, LocalDateTime t2) {
		date.set(processDate(t1, t2));
	}
	
	public void setTime(LocalDateTime t1, LocalDateTime t2) {
		time.set(processTime(t1, t2));
	}
	
	private String arrayToString(ArrayList<String> arr) {
		String str="";
		if (arr.size()!=0) {
			for (String s:arr) {
				str += s + ", ";
			}
			str.substring(0, str.length()-2);
		}
		return str;
	}
	
	private String processDate(LocalDateTime t1, LocalDateTime t2) {
		assert (t1!=null || t2==null);		
		String str="";
		
		if (t1!=null) {
			if (t2==null) {
				str += t1.format(dateFmt);
			} else {
				if (t1.toLocalDate().isEqual(t2.toLocalDate())) {
					str += t1.format(dateFmt);
				} else {
					str += t1.format(dateFmt) + "-" + t2.format(dateFmt);
				}
			}
			
		} 		
		return str;
	}
	
	private String processTime(LocalDateTime t1, LocalDateTime t2) {
		assert (t1!=null || t2==null);
		String str = "";
		
		if (t1!=null) {
			if (t2==null) {
				if (!isZeroTime(t1)) {
					str += t1.format(timeFmt);
				}				
			} else {
				if (t1.toLocalDate().isEqual(t2.toLocalDate())) {
					str += t1.format(timeFmt) + "-" + t2.format(timeFmt);
				}
			}
		}
		
		return str;
	}
	
	private boolean isZeroTime(LocalDateTime t) {
		return (t.getHour()==0) && (t.getMinute()==0);
	}
	

}
