package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailSender {
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendMail(final String to, final String subject, final String msg) {
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("rickinshah.21.cs@iite.indusuni.ac.in");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg, true);
			emailSender.send(mimeMessage);
		} catch(MessagingException e) {
			e.printStackTrace();
		}
		
	}
}
