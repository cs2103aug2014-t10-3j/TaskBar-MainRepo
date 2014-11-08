//@author A0116760J
package gui;

import util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;





import javafx.beans.property.SimpleStringProperty;
/**
 * A specialized data structure representing a <code>Task</code> which can be
 * read by a <code>TableView</code>, have its properties extracted and displayed
 * in a <code>TableRow</code> <p>
 * The accessors of each property must follow the same format <code>get</code> +
 * <code>property's name<code>
 *
 */
public class TableData {
	private SimpleStringProperty order;
	private SimpleStringProperty desc;
	private SimpleStringProperty tag;
	private SimpleStringProperty date;
	private SimpleStringProperty time;
	private Task task;
	
	DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("EEE dd.MM.yy");	//e.g. Mon, 12.07.14 
	DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
	
	public TableData(Task task, int ord) {
		String newDesc = task.getDescription();
		ArrayList<String> newTag = task.getLabels();
		LocalDateTime time1 = task.getStartTime();
		LocalDateTime time2 = task.getEndTime();
		
		order = new SimpleStringProperty(Integer.toString(ord));
		desc = new SimpleStringProperty(newDesc);
		tag = new SimpleStringProperty(arrayToString(newTag));
		date = new SimpleStringProperty(processDate(time1, time2));
		time = new SimpleStringProperty(processTime(time1, time2));
		this.task = task; 
	}
	/**
	 * Gets the <code>order</code> property of this <code>Data</code>
	 * 
	 */
	public String getOrder() {
		return order.get();
	}
	
	/**
	 * Gets the <code>desc</code> property of this <code>Data</code>
	 * 
	 */
	public String getDesc() {
		return desc.get();
	}
	
	/**
	 * Gets the <code>tag</code> property of this <code>Data</code>
	 * 
	 */
	public String getTag() {
		return tag.get();
	}
	
	/**
	 * Gets the <code>date</code> property of this <code>Data</code>
	 * 
	 */
	public String getDate() {
		return date.get();
	}
	
	/**
	 * Gets the <code>time</code> property of this <code>Data</code>
	 * 
	 */
	public String getTime() {
		return time.get();
	}
	

	public Task getTask() {
		return task;
	}
	
	public void setOrder(int ord) {
		order.set(Integer.toString(ord));
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
			str = str.substring(0, str.length()-2);
		}
		return str;
	}
	
	/**
	 * Return the String representation of the date components
	 * of the two time stamps
	 * 
	 */
	private String processDate(LocalDateTime t1, LocalDateTime t2) {
		assert (t1!=null || t2==null);		
		String str="";
		
		if (t1!=null) {
			if (t2==null) {
				str += toCustomString(t1);
			} else {
				if (t1.toLocalDate().isEqual(t2.toLocalDate())) {
					str += toCustomString(t1);
				} else {
					str += toCustomString(t1) + " - " + toCustomString(t2);
				}
			}
			
		} 		
		return str;
	}
	
	/**
	 * Format the <code>LocalDateTime</code> to a <code>String</code>
	 *   
	 */
	private String toCustomString(LocalDateTime t) {
		String str = "";
		LocalDate today = LocalDateTime.now().toLocalDate();
		if (t.toLocalDate().isEqual(today)) {
			str = "Today";			
		} else if (t.toLocalDate().isEqual(today.plusDays(1))) {
			str = "Tomorrow";
		} else {
			str = t.format(dateFmt);
		}
		return str;
	}
	
	/**
	 * Return the String representation of the time components
	 * of the two time stamps
	 * 
	 */
	private String processTime(LocalDateTime t1, LocalDateTime t2) {
		assert (t1!=null || t2==null);
		String str = "";
		
		if (t1!=null) {
			if (t2==null) {
				if (!isZeroTime(t1)) {			//the time is not shown if it is 00:00
					str += t1.format(timeFmt);
				}				
			} else {
				if (t1.toLocalDate().isEqual(t2.toLocalDate())) {
					str += t1.format(timeFmt) + " - " + t2.format(timeFmt);
				}
			}
		}
		
		return str;
	}
	
	private boolean isZeroTime(LocalDateTime t) {
		return (t.getHour()==0) && (t.getMinute()==0);
	}
	

}
