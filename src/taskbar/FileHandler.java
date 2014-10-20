/*
 * File IO
 * Writer: ZiJie
 */
package taskbar;
import java.io.*;
import java.util.ArrayList;

public class FileHandler{
	
		
		public ArrayList<Task> readFromFile() throws IOException{
			FileReader reader = new FileReader("database.txt");
			BufferedReader br = new BufferedReader(reader);
			ArrayList<Task> allTasks = new ArrayList<Task>();
			String line;
			while( (line = br.readLine()) != null ){
				String description;
				ArrayList<String> labels = new ArrayList<String>();
				int importance;
				
				/*
				 * Format of toString()
				 * Description \n numLabels("labels.size()") \n labels \n importance
				 */
				description = line;
				line = br.readLine();
				int numLabels = Integer.parseInt(line);
				for(int i=0; i<numLabels; i++)
				{
					line = br.readLine();
					labels.add(line);	
				}
				line = br.readLine();
				importance = (Integer.parseInt(line));
				
				Task newTask = new Task(description,labels, importance);
				allTasks.add(newTask);
				//TODO: how to add in deadline and isdone?
			}	
		
			br.close();
			reader.close();
			return allTasks;
		}
	
		
		public void writeToFile(ArrayList<Task> taskAL) {
			try {

			    FileWriter outFile = new FileWriter("database.txt");

			    PrintWriter out = new PrintWriter(outFile);

			    // Write text to file
			    for(int i=0; i< taskAL.size(); i++)
			    {
			    out.printf("%s", taskAL.get(i).toString());
			    }
			    out.close();

			} catch (IOException e) {

			    e.printStackTrace(); //handle locally

			}
		}
		
	
}

