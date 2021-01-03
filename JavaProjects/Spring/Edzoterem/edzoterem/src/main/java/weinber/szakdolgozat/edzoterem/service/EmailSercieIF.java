package weinber.szakdolgozat.edzoterem.service;

import weinber.szakdolgozat.edzoterem.entity.Email;

public interface EmailSercieIF {
	
	/**
	 * Send an email from the weinbergym@gmail.com.
	 * The parameters of the mail is set in the Email object.
	 * @param The email to be sent.
	 */
    public void sendEmail(Email mail);
    
    /**
	 * Creates an email.
	 * @param The sender.
	 * @param The receiver.
	 * @param The subject of the mail.
	 * @param The actual messeage of the mail.
	 */
    public Email createEmail(String from, String to, String subject, String message);
}
