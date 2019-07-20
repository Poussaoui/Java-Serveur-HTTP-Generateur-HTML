package casir.miniproject;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxInterpreter extends DefaultHandler{
	

	boolean pagetitle = false;
	boolean collection = false;
	List<Student> students;
	HTMLPage htPage;
	private String body;
	private String footer;
	private String header;
	
	public SaxInterpreter(List<Student> students) {
		this.students = students;
		this.htPage = new HTMLPage();

		this.header = "<!DOCTYPE html>\n" + "<html>" + "<head>" + "<meta charset='UTF-8'>"
				+ "<meta charset=\"UTF-8\">\n"
				+ "<meta name='viewport' content='width=device-width, initial-scale=1'>\n"
				+ "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>\n";
		this.body = "";
		this.footer = "";
	}

	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {
		
		if (qName.equalsIgnoreCase("pagetitle")) {
			pagetitle = true;
		}

		if (qName.equalsIgnoreCase("collection")) {
			collection = true;
		}

	}

	public void endElement(String uri, String localName,
		String qName) throws SAXException {
		
		if (qName.equalsIgnoreCase("webpage")) {
			footer += "<script>\n";

			footer += "function post(params, method) {\n"
					+ "    method = method || \"post\"; // Set method to post by default if not specified.\n" + "\n"
					+ "    var form = document.createElement(\"form\");\n"
					+ "    form.setAttribute(\"method\", method);\n" + "\n" + "    for(var key in params) {\n"
					+ "        if(params.hasOwnProperty(key)) {\n"
					+ "            var hiddenField = document.createElement(\"input\");\n"
					+ "            hiddenField.setAttribute(\"type\", \"hidden\");\n"
					+ "            hiddenField.setAttribute(\"name\", key);\n"
					+ "            hiddenField.setAttribute(\"value\", params[key]);\n" + "\n"
					+ "            form.appendChild(hiddenField);\n" + "        }\n" + "    }\n" + "\n"
					+ "    document.body.appendChild(form);\n" + "    form.submit();\n" + "}\n"; 
			
			footer += "function add_fields(id,firstname,lastname,email) {\n" +
					"var x = Math.floor((Math.random() * 10) + 1);"+
					"document.getElementById(\"myTable\").insertRow(-1).innerHTML = '<tr><input type=\"hidden\" name=\"action\" value=\"save\"><td><input name=\"id\" type=\"text\" value='+id+' disabled></td><td><input name=\"fisrtname\" id=\"fisrtnameTxt\" type=\"text\" value='+firstname+' required></td><td><input name=\"lastname\" id=\"lastnameTxt\" type=\"text\" value='+lastname+' required></td><td><input name=\"email\" id=\"emailTxt\" type=\"text\" value='+email+' required ></td><td><div class=\"input-group-btn\"><button id=\"btn_save'+x+'\" class=\"btn btn-default\" >Save</button></div></td></tr>';\n" + 

					"document.getElementById('btn_save'+x).addEventListener('click', function(){"+
					"var fisrtnameTxt = document.getElementById('fisrtnameTxt').value;"+
					"var lastnameTxt = document.getElementById('lastnameTxt').value;"+
					"var emailTxt = document.getElementById('emailTxt').value;"+
					"post({action: 'save',id: id,fisrtname: fisrtnameTxt,lastname: lastnameTxt,email: emailTxt});"+
					"});"+
					"}";
			
			footer += "</script>";
			
			footer += "</body>";
			footer += "\n</html>";
			htPage.setFooter(footer);
		}
		
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		if (pagetitle) {
			pagetitle = false;
			header += "<title>" + new String(ch, start, length) + "</title>";
			header += "</head>";
			htPage.setHeader(header);

			body += "<body>";
			body += "<div class=\"navbar navbar-inverse navbar-static-top\" role=\"navigation\">\n"
					+ "    <div class=\"collapse navbar-collapse navbar-ex1-collapse\">\n"
					+ "        <div class=\"col-sm-3 col-md-3 pull-right\">\n"
					+ "        <form method='POST' class=\"navbar-form\" role=\"search\">\n"
					+ "        <div class=\"input-group\">\n"
					+ "			 <input type=\"hidden\" name=\"action\" value=\"search\">"
					+ "            <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\n"
					+ "            <div class=\"input-group-btn\">\n"
					+ "                <button class=\"btn btn-default\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\n"
					+ "            </div>\n" + "        </div>\n" + "        </form>\n" + "        </div>\n"
					+ "    </div>\n" + "</div>";
		}

		if (collection) {
			collection = false;
			
        	// add date for each student to html string

			body += "<div class='container'>" + "<h3><a onclick=\"post({action: 'home'});\">" + new String(ch, start, length) + "</a></h3>" + "<br>" + "<br>";

			body += "<table class='table'>" + "<thead class='thead-dark'>" + "<tr>" + "<th scope='col'>ID</th>"
					+ "<th scope='col'>First Name</th>" + "<th scope='col'>Last Name</th>" + "<th scope='col'>Mail</th>"
					+ "<th scope='col'>Actions</th>" + "</tr>" + "</thead>";
			body += "<tbody>";

			for (Student student : this.students) {

				body += "<tr>" + "<td>" + student.getId() + "</td>" + "<td>" + student.getFirstName() + "</td>" + "<td>"
						+ student.getLastName() + "</td>" + "<td>" + student.getMail() + "</td>" + "<td>"
						+ "<button onclick=\" add_fields('"+ student.getId() +"','" + student.getFirstName() +"','"+ student.getLastName() +"','"+ student.getMail() +"');\" class='btn btn-default'>Edit</button>" 
						+ "<button onclick=\"post({action: 'delete',id: '" + student.getId() + "'});\" class='btn btn-default'>Delete</button>" 
						+ "</td>" + "</tr>";
			}

			body += "</tbody>" + "<tfoot id='myTable' >" + "<form method='POST'>" + "<tr>"
					+ "<input type=\"hidden\" name=\"action\" value=\"add\">"
					+ "<td><input name='id' type='text' value='"+ Student.getNextId() +"' readonly='true' /></td>" + "<td><input name='fisrtname' type='text' required /></td>"
					+ "<td><input name='lastname' type='text' required /></td>" + "<td><input name='email' type='text' required /></td>"
					+ "<td>" + "<div class=\"input-group-btn\">\n"
					+ "<button class=\"btn btn-default\" type=\"submit\">Add</button>\n" + "</div>\n" + "</td>"
					+ "</tr>" + "</form>" + "</tfoot>";

			body += "</table>";
			htPage.setBody(body);
		}
	}
	
}
