/*
 * This is a better way of file handling, using JDOM XML. Under Construction
 * This is Writing File portion. 
 * 
 * Writer: Zi Jie 
 * 
 */
package taskbar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class WriteFileJDOM {

	protected static void writeFileUsingJDOM(ArrayList<Task> taskList,
			String fileName) throws IOException {
		Document doc = new Document();
		for (Task task : taskList) {
			Element tasks = new Element("Task");
			tasks.addContent(new Element("Description").setText(task
					.getDescription()));
			Element labs = new Element("Labels");
			tasks.addContent(labs);
			if (task.getImportance() != 0)
				tasks.addContent(new Element("Importance").setText(""
						+ task.getImportance()));

			if (task.getDeadline()!=null) {
				tasks.addContent(new Element("TimeStamp1").setText(task
						.getStartTime().toString()));
			} else {
				tasks.addContent(new Element("TimeStamp1").setText(null));
			}

			if (task.getEndTime()!=null) {
				tasks.addContent(new Element("TimeStamp2").setText(task
						.getEndTime().toString()));
			} else {
				tasks.addContent(new Element("TimeStamp2").setText(null));
			}

		}
		// JDOM document is ready now, lets write it to file now
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		// output xml to console for debugging
		xmlOutputter.output(doc, System.out);
		xmlOutputter.output(doc, new FileOutputStream(fileName));
	}

}