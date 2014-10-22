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

    protected static void writeFileUsingJDOM(ArrayList<Task> taskList, String fileName) throws IOException {
        Document doc = new Document();
        for(Task task : taskList){
            Element tasks = new Element("Task");
            tasks.addContent(new Element("Description").setText(task.getDescription()));
            Element labs = new Element("Labels");
            tasks.addContent(labs);
            for(int i=0; i<task.getNumLabels(); i++){
            	tasks.addContent(labs.addContent(task.getLabels().get(i)));
            }
            tasks.addContent(new Element("Importance").setText(""+task.getImportance()));
            if(task.isDeadLineTask())
            {
            	tasks.addContent(new Element("Deadline").setText(""+task.getDeadline()));
            }
            if(task.isEvent())
            {
            	tasks.addContent(new Element("Event")).setText(""+task.getStartTime() + "" + task.getEndTime());
            }
          
        }
        //JDOM document is ready now, lets write it to file now
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        //output xml to console for debugging
        //xmlOutputter.output(doc, System.out);
        xmlOutputter.output(doc, new FileOutputStream(fileName));
    }
    
    
    
    
    
 
}