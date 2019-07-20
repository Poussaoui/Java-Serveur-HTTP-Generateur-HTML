package casir.miniproject;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ProjectMain {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, Exception {
    	// get students list from XML file 
    	StudentDomParser studentDomParser = new StudentDomParser("students.xml");
        List<Student> students = studentDomParser.getAllStudentsFromXML();

     // generate HTML object with data
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		
		// send data to interpreter 
		SaxInterpreter interpreter = new SaxInterpreter(students);
        saxParser.parse("htmlGeneratorFile.xml", interpreter);
        
        // print generated html string
        //System.out.println(interpreter.htmlGenerated);
        
        Server server = new Server(interpreter.htPage);
        server.start();
        
    }

    public static int add(int a, int b) throws Exception {
        int c = a + b;
        if (c == 0) {
            throw new Exception("c is zeroooo !");
        }
        return c;
    }
}