package casir.miniproject;

public class HTMLPage {

	private String header;
	private String body;
	private String footer;
	
	
	public HTMLPage() {
		this.header = "";
		this.body = "";
		this.footer = "";
	}

	public HTMLPage(String header, String body, String footer) {
		this.header = header;
		this.body = body;
		this.footer = footer;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}

	@Override
	public String toString() {
		return header + body + footer;
	}
	
}
