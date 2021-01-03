package weinber.szakdolgozat.edzoterem.entity;

import javax.persistence.Entity;


public class Email {
	
	private String message;
	
	private String from;
	
	private String to;
	
	private String subject;
	
	
	
	public Email() {
		
	}


	public Email(String message, String from, String to, String subject) {
		this.message = message;
		this.from = from;
		this.to = to;
		this.subject = subject;
	}


	public Email(String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

}
