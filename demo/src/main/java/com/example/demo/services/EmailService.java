package com.example.demo.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.email.EmailSender;

@Service
public class EmailService implements EmailSender{

	private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    private JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	@Override
	@Async
	public void send(String to, String email) {
		try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("CONFIRM YOUR EMAIL");
            helper.setFrom("info@truongnhudat.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("FAILED TO SEND EMAIL", e);
            throw new IllegalStateException("FAILED TO SEND EMAIL");
        }
		
	}

}
