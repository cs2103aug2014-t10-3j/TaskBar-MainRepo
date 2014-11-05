//@author A0111499B
/*
 * This is a better way of file handling, using JDOM XML. Under Construction
 * This is Writing File portion. 
 * 
 * Writer: Zi Jie 
 * 
 */
package storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import util.Task;

public class WriteFileJDOM {

	protected static void writeFileUsingJDOM(ArrayList<Task> taskList,
			String fileName) throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		Document doc = new Document();
		doc.setRootElement(new Element("tasks"));
		for (Task task : taskList) {
			Element tasks = new Element("Task");
			tasks.addContent(new Element("Description").setText(task
					.getDescription()));
			
			
			if(task.isDone()){
				tasks.addContent(new Element("IsDone").setText("true"));
			}else{
				tasks.addContent(new Element("IsDone").setText("false"));
			}
			
			ArrayList<String> labels = task.getLabels();
			for(int i=0 ; i < task.getNumLabels(); i++){
				Element labs = new Element("Label" + i);
				tasks.addContent(labs);
				labs.setText(labels.get(i));
			}

			if (task.getDeadline()!=null) {
				tasks.addContent(new Element("TimeStamp1").setText(task
						.getStartTime().format(formatter)));
			} else {
				tasks.addContent(new Element("TimeStamp1").setText(""));
			}

			if (task.getEndTime()!=null) {
				tasks.addContent(new Element("TimeStamp2").setText(task
						.getEndTime().format(formatter)));
			} else {
				tasks.addContent(new Element("TimeStamp2").setText(""));
			}
			doc.getRootElement().addContent(tasks);

		}
		// JDOM document is ready now, lets write it to file now
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		// output xml to console for debugging
		//xmlOutputter.output(doc, System.out);
		xmlOutputter.output(doc, new FileOutputStream(fileName));
	}

}