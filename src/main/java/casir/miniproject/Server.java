package casir.miniproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	
	private int port = 2019;
    private ServerSocket serverSocket;
    public HTMLPage htmlCode;
    public HTMLGenerator htmlGenerator;
    
    public Server(HTMLPage htPage) {
    	this.htmlCode = htPage;
    	this.htmlGenerator = new HTMLGenerator();
    	try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // start server 
    public void start() {
    	
        System.out.println("Server started at : " + serverSocket.getLocalSocketAddress());
        
        while (true) {
            try {

                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // read request
                String line;
                line = in.readLine();
                StringBuilder raw = new StringBuilder();
                raw.append("" + line);
                boolean isPost;
                isPost = line.startsWith("POST");
                int contentLength = 0;
                while (!(line = in.readLine()).equals("")) {
                    raw.append('\n' + line);
                    if (isPost) {
                        final String contentHeader = "Content-Length: ";
                        if (line.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                        }
                    }
                }
                StringBuilder body = new StringBuilder();
                if (isPost) {
                    int c = 0;
                    for (int i = 0; i < contentLength; i++) {
                        c = in.read();
                        body.append((char) c);
                    }
                    System.err.println("Data : " + body);
                    String[] parts = body.toString().split("&");
                    String action = parts[0];
                    if(action.contains("add")) {
                    	
                    	String id =  getValue(parts[1]);
                        System.out.println("ID : " + id);
                        String firstName =  getValue(parts[2]);
                        System.out.println("firstName :" + firstName);
                        String lastName =  getValue(parts[3]);
                        System.out.println("lastName : " + lastName);
                        String email =  getValue(parts[4]);
                        System.out.println("email : " + email);
                        
                        Student student= new Student(Integer.parseInt(id), firstName, lastName, email);

                        this.htmlCode = htmlGenerator.addStudent(student);
                        
                    }else if(action.contains("delete")) {
                    	String id = getValue(parts[1]);
                        System.out.println("ID TO DELETE : " + id);
                        this.htmlCode = htmlGenerator.deleteStudent(id);
                    }else if(action.contains("save")) {
                    	String id = getValue(parts[1]);
                        String firstName = getValue(parts[2]);
                        String lastName = getValue(parts[3]);
                        String email = getValue(parts[4]);
                        
                        Student student= new Student(Integer.parseInt(id), firstName, lastName, email);
                        this.htmlCode = htmlGenerator.updateStudent(student);
                        
                    }else if(action.contains("home")) {
                    	this.htmlCode = htmlGenerator.showAllStudent();
                        
                    }else {
                        String searchTerm =  getValue(parts[1]);
                        System.out.println("ID : " + searchTerm);
                        this.htmlCode = htmlGenerator.searchStudentList(searchTerm);
                    }              
                }
                raw.append(body.toString());
                
                // send response
                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write("\r\n");
                out.write(htmlCode.toString());
                // do not in.close();
                out.flush();
                out.close();
                socket.close();
                //
            } catch (Exception e) {
    			e.printStackTrace();
            }
        }
        
    }
    
    public String getValue(String NameVar) {
    	String[] NameId = NameVar.split("=");
    	return NameId[1];
	}
}
