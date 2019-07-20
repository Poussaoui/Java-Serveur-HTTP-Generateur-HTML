package casir.miniproject;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.Exception;
import java.util.List;

class ProjectMainTest {

    @BeforeEach // this function is called before each test
    void setUp(){
        // init context
    }

    @Test // this test is valid if the expected exception is thrown
    void addStudentIntoTheList() {
    	StudentDomParser studentDomParser = new StudentDomParser("studentTEST.xml");
    	Student student1 = new Student(1, "toto", "toto", "toto@mail.fr");
    	List<Student> StudentsList = studentDomParser.addStudent(student1);
    	assertEquals(1, StudentsList.size());
    	try {
			StudentsList = studentDomParser.deleteStudent("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test // this test is valid if the expected exception is thrown
    void deleteStudentIntoTheList() {
    	StudentDomParser studentDomParser = new StudentDomParser("studentTEST.xml");
    	Student student1 = new Student(1, "toto", "toto", "toto@mail.fr");
    	List<Student> StudentsList = studentDomParser.addStudent(student1);
    	try {
			StudentsList = studentDomParser.deleteStudent("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	assertEquals(0, StudentsList.size());
    }
    
    @Test // this test is valid if the expected exception is thrown
    void updateStudentIntoTheList() {
    	
    	StudentDomParser studentDomParser = new StudentDomParser("studentTEST.xml");
    	Student student1 = new Student(1, "toto", "toto", "toto@mail.fr");
    	Student newStudent1 = new Student(1, "titi", "titi", "titi@gmail.fr");
    	List<Student> StudentsList = studentDomParser.addStudent(student1);
    	
    	StudentsList = studentDomParser.updateStudent(newStudent1);
    	
    	assertEquals(newStudent1.getFirstName(), StudentsList.get(0).getFirstName());
    	
    	try {
			StudentsList = studentDomParser.deleteStudent("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
