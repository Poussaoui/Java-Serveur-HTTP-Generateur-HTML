package casir.miniproject;

public class Student {
	public static int LAST_ID = 1;
    private Integer id;
    private String firstName;
    private String lastName;
    private String mail;

    public Student() {
    }

    public Student(Integer id, String firstName, String lastName, String mail) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
    	if(LAST_ID <= id)
    		LAST_ID = id+1;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", mail=" + mail + "] \n";
    }
    
    public static int getNextId() {
    	return LAST_ID++ ;
    }        
    
    public static int resetId() {
    	return LAST_ID = 1;
    }  
}
