package weinber.szakdolgozat.edzoterem.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import weinber.szakdolgozat.edzoterem.entity.Email;

@Service("mailService")
public class EmailService implements EmailSercieIF{
	
	private final Log log=LogFactory.getLog(this.getClass());
	
	@Value("${spring.mail.username}")
	private String MessageFrom;
	
    @Autowired
    JavaMailSender mailSender;
 
    @Override
    public void sendEmail(Email mail) {
        SimpleMailMessage simpleMail = null;
 
        try {
        	simpleMail=new SimpleMailMessage();
        	simpleMail.setFrom(mail.getFrom());
        	simpleMail.setTo(mail.getTo());
        	simpleMail.setSubject(mail.getSubject());
        	simpleMail.setText(mail.getMessage());
        	
        	mailSender.send(simpleMail);
 
 
        } catch (Exception e) {
        	log.error("Hiba erről "+mail.getFrom()+" erre "+mail.getTo()+ e);
        }
        }

	@Override
	public Email createEmail(String from, String to, String subject, String message) {
		Email email=new Email(message, from, to, subject);
		return email;
	}
    }

