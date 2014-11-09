/**
* ReadFileJDOM reads the file from a XML file. 
* @author A0111499B
* Zi Jie
*
*/
package storage;

import java.io.File;

import java.io.IOException;

 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.input.DOMBuilder;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
public class ReadFileJDOM {
	
	/**
	 * Get JDOM document from DOM Parser
	 * 
	 */
    protected static org.jdom2.Document useDOMParser(String fileName)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(fileName));
        DOMBuilder domBuilder = new DOMBuilder();
        return domBuilder.build(doc);
 
    }
}
