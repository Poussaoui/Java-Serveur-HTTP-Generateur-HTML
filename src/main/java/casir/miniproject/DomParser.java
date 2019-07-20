package casir.miniproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class DomParser {

    private Document mDoc;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private String xmlfile;
    
    public DomParser(String xmlfile) throws Exception {
    	this.xmlfile = xmlfile;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        mDoc = builder.parse(new FileInputStream(new File(xmlfile)));
    }

    public int getChildCount(String parentTag, int parentIndex, String childTag) {
        NodeList list = mDoc.getElementsByTagName(parentTag);
        Element parent = (Element) list.item(parentIndex);
        NodeList childList = parent.getElementsByTagName(childTag);
        return childList.getLength();
    }

    public String getChildValue(String parentTag, int parentIndex, String childTag, int childIndex) {
        NodeList list = mDoc.getElementsByTagName(parentTag);
        Element parent = (Element) list.item(parentIndex);
        NodeList childList = parent.getElementsByTagName(childTag);
        Element element = (Element) childList.item(childIndex);
        Node child = element.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public String getChildAttribute(String parentTag, int parentIndex, String childTag, int childIndex,
                                    String attributeTag) {
        NodeList list = mDoc.getElementsByTagName(parentTag);
        Element parent = (Element) list.item(parentIndex);
        NodeList childList = parent.getElementsByTagName(childTag);
        Element element = (Element) childList.item(childIndex);
        return element.getAttribute(attributeTag);
    }
    
    public void addAllChildsAttributes(List<Student> students)
    {
    	try {
    	    final Document document= builder.newDocument();
    	    
    	    final Element racine = document.createElement("students");
    	    document.appendChild(racine);
    	    
    	    for (Student std : students) {
    	    	
        	    final Element personne = document.createElement("student");
        	    personne.setAttribute("id",String.valueOf(std.getId()));
        	    racine.appendChild(personne);
        	    
        	    final Element firstName = document.createElement("firstName");
        	    firstName.appendChild(document.createTextNode(std.getFirstName()));
        			
        	    final Element lastName = document.createElement("lastName");
        	    lastName.appendChild(document.createTextNode(std.getLastName()));
        	    
        	    final Element mail = document.createElement("mail");
        	    mail.appendChild(document.createTextNode(std.getMail()));
        			
        	    personne.appendChild(firstName);
        	    personne.appendChild(lastName);	
        	    personne.appendChild(mail);	
    	    }
	
    	    final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	    final Transformer transformer = transformerFactory.newTransformer();
    	    final DOMSource source = new DOMSource(document);
    	    final StreamResult sortie = new StreamResult(new File(this.xmlfile));

    	    transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
    	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    	    transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");			

    	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    			
    	    transformer.transform(source, sortie);	
    	}
    	catch (Exception e) {
    	    e.printStackTrace();
    	}	
    }


}