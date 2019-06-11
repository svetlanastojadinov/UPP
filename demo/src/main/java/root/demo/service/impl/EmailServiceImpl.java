package root.demo.service.impl;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import root.demo.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String receiver, String subject, String text) {
    	 try {
             SimpleMailMessage message = new SimpleMailMessage();
             message.setTo(receiver);
             message.setSubject(subject);
             message.setText(text);
             message.setFrom("naucnacentrala.upp@gmail.com");

             emailSender.send(message);
         } catch (MailException exception) {
             throw new BadRequestException(exception.getMessage());
         }
    }
}
