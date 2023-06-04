package com.backend.SafeSQL.service;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.User;

import javax.mail.MessagingException;
import javax.naming.Context;

import java.nio.charset.StandardCharsets;

@Service
public class MailService {
	
	@Autowired
    private JavaMailSender emailSender;
	@Autowired
	private UserRepository userDAO;



	public void resetPassword(User user) throws Exception {
	
	
		
		if(userDAO.findByEmail(user.getEmail()) == null ) 
			throw new Exception("No existe el usuario con email "+user.getEmail());
		
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo(user.getEmail());
    	
    	//long aleatorio = Math.round(Math.random()*99999999);
    	
    	String ruta = "http://localhost:4200/user/changePassword/"+user.getEmail();
    	message.setText(""+ruta);

        emailSender.send(message);
        System.out.println("OK");
    }


}
