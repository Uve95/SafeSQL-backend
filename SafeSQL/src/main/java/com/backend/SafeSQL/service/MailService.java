package com.backend.SafeSQL.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.naming.Context;

import java.nio.charset.StandardCharsets;

@Service
public class MailService {
	
	@Autowired
    private JavaMailSender emailSender;



    public void sendEmail(String toEmail) {

    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo(toEmail);
    	
    	long aleatorio = Math.round(Math.random()*99999999);
    	
    	message.setText(""+aleatorio);

        emailSender.send(message);
        System.out.println("OK");
    }


}
