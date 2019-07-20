package casir.miniproject;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class HTMLGenerator {

	StudentDomParser studentDomParser;
	SAXParserFactory saxParserFactory;
	SAXParser saxParser;
	String fileName = "students.xml";
	String HtmlFileName = "htmlGeneratorFile.xml";

	public HTMLGenerator() {
		try {
			studentDomParser = new StudentDomParser(fileName);
			saxParserFactory = SAXParserFactory.newInstance();
			saxParser = saxParserFactory.newSAXParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HTMLPage addStudent(Student student) throws Exception {
		List<Student> students = studentDomParser.addStudent(student);
		// send data to interpreter
		SaxInterpreter interpreter = new SaxInterpreter(students);
		saxParser.parse(HtmlFileName, interpreter);
		return interpreter.htPage;
	}
	
	public HTMLPage searchStudentList(String searchTerm) throws Exception {		
		Student.resetId();
		List<Student> students = studentDomParser.searchStudent(searchTerm);
		// send data to interpreter
		SaxInterpreter interpreter = new SaxInterpreter(students);
		saxParser.parse(HtmlFileName, interpreter);
		return interpreter.htPage;
	}
	
	public HTMLPage deleteStudent(String studentID) throws Exception {	
		Student.resetId();
		List<Student> students = studentDomParser.deleteStudent(studentID);
		// send data to interpreter
		SaxInterpreter interpreter = new SaxInterpreter(students);
		saxParser.parse(HtmlFileName, interpreter);
		return interpreter.htPage;
	}
	
	public HTMLPage updateStudent(Student student) throws Exception {	
		Student.resetId();
		List<Student> students = studentDomParser.updateStudent(student);
		// send data to interpreter
		SaxInterpreter interpreter = new SaxInterpreter(students);
		saxParser.parse(HtmlFileName, interpreter);
		return interpreter.htPage;
	}
	
	public HTMLPage showAllStudent() throws Exception {	
		Student.resetId();
		List<Student> students = studentDomParser.getAllStudentsFromXML();
		// send data to interpreter
		SaxInterpreter interpreter = new SaxInterpreter(students);
		saxParser.parse(HtmlFileName, interpreter);
		return interpreter.htPage;
	}

}
