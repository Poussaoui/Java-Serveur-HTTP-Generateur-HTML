package casir.miniproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class StudentDomParser {

	private String fileName;
		
    public StudentDomParser(String fileName) {
		this.fileName = fileName;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public List<Student> getAllStudentsFromXML() throws ParserConfigurationException, SAXException, IOException, Exception {
        //Initialize a list of students
        List<Student> students = new ArrayList<Student>();
        Student student = null;

        // Parse the given document
        DomParser doc = new DomParser(this.fileName);
        int count = doc.getChildCount("students", 0, "student");
        for (int i = 0; i < count; i++) {

            //Create new Student Object
            student = new Student();

            student.setId(Integer.parseInt(doc.getChildAttribute("students", 0, "student", i, "id")));
            student.setFirstName(doc.getChildValue("student", i, "firstName", 0));
            student.setLastName(doc.getChildValue("student", i, "lastName", 0));
            student.setMail(doc.getChildValue("student", i, "mail", 0));

            //Add Student to list
            students.add(student);
        }

        return students;
    }
	
	public void addAllStudentToXML(List<Student> students) throws Exception {
		// Parse the given document
        DomParser doc = new DomParser(this.fileName);
        doc.addAllChildsAttributes(students);
	}
	
	public List<Student> addStudent(Student student) {
		List<Student> oldStudentsList = null;
		try {
			oldStudentsList = getAllStudentsFromXML();
			 // add student to old list
			oldStudentsList.add(student);
			// add new list to xml file
			addAllStudentToXML(oldStudentsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return oldStudentsList;
	}
	
	public List<Student> deleteStudent(String studentID) throws Exception {
		List<Student> newStudentList = new ArrayList<Student>();
		try {
			for (Student student : getAllStudentsFromXML()) {
				System.err.println(student.getId() +"=="+Integer.parseInt(studentID));
				if (student.getId() != Integer.parseInt(studentID)) {
					System.err.println("Yes");
					newStudentList.add(student);
				}
			}
			
			addAllStudentToXML(newStudentList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newStudentList;
	}
	
	public List<Student> updateStudent(Student studentWithModification) {
		List<Student> newStudents = new ArrayList<Student>();
		try {
						
			for(Student student : getAllStudentsFromXML()) {
				if(student.getId() != studentWithModification.getId())
				{
					newStudents.add(student);
				}else {
					newStudents.add(studentWithModification);
				}
			}
			
			addAllStudentToXML(newStudents);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newStudents;
	}
	
	public List<Student> searchStudent(String searchStr) {
		List<Student> foundList = new ArrayList<Student>();
		try {
			for (Student student : getAllStudentsFromXML()) {
				if (student.getFirstName().toLowerCase().equals(searchStr.toLowerCase())) {
					foundList.add(student);
				}else if (student.getLastName().toLowerCase().equals(searchStr.toLowerCase())) {
					foundList.add(student);
				}else if (student.getMail().toLowerCase().equals(searchStr.toLowerCase())) {
					foundList.add(student);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foundList;
	}
	
}
