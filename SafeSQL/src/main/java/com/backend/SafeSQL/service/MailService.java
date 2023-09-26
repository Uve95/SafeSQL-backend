package com.backend.SafeSQL.service;


import org.hibernate.sql.Template;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.Message;
import com.backend.SafeSQL.model.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;

import java.nio.charset.StandardCharsets;

@Service
public class MailService {
	
	@Autowired
    private JavaMailSender emailSender;
	@Autowired
	private UserRepository userDAO;


	
	public void sendEmail(User user) throws jakarta.mail.MessagingException, MessagingException {
	     jakarta.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
	     MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
	     
	     
	     String content ="<td class=\"esd-stripe\" style=\"background-color: #fafafa;\" bgcolor=\"#fafafa\" align=\"center\">\r\n"
	     		+ "    <table class=\"es-content-body\" style=\"background-color: #ffffff;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\r\n"
	     		+ "        <tbody>\r\n"
	     		+ "            <tr>\r\n"
	     		+ "                <td class=\"esd-structure es-p40t es-p20r es-p20l\" style=\"background-color: transparent; background-position: left top;\" bgcolor=\"transparent\" align=\"left\">\r\n"
	     		+ "                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
	     		+ "                        <tbody>\r\n"
	     		+ "                            <tr>\r\n"
	     		+ "                                <td class=\"esd-container-frame\" width=\"560\" valign=\"top\" align=\"center\">\r\n"
	     		+ "                                    <table style=\"background-position: left top;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
	     		+ "                                        <tbody>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td class=\"esd-block-image es-p5t es-p5b\" align=\"center\" style=\"font-size: 0px;\"><a target=\"_blank\"><img src=\"https://demo.stripocdn.email/content/guids/e6d44644-6cb7-4025-98ae-4cf33a8fb43f/images/logowebjpg.jpg\"\" alt style=\"display: block;\" width=\"175\"></a></td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td class=\"esd-block-text es-p15t es-p15b\" align=\"center\">\r\n"
	     		+ "                                                    <h1 style=\"color: #333333; font-size: 20px;\"><b>RESTABLECIMIENTO DE CONTRASEÑA</b></h1>\r\n"
	     		+ "                                                </td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td class=\"esd-block-text es-p40r es-p40l\" align=\"center\">\r\n"
	     		+ "                                                    <p><br></p>\r\n"
	     		+ "                                                </td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td class=\"esd-block-text es-p35r es-p40l\" align=\"center\">\r\n"
	     		+ "                                                    <p>Hemos recibido una solicitud de restablecimiento de contraseña.</p>\r\n"
	     		+ "                                                </td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td class=\"esd-block-text es-p25t es-p40r es-p40l\" align=\"center\">\r\n"
	     		+ "                                                    <p>Su código de seguridad es : "+user.getToken()+"</p>\r\n"
	     		+ "                                                    <p><br></p>\r\n"
	     		+ "                                                    <p>Si no quieres restablecer tu contraseña, por favor ignora este mensaje. En caso contrario, pulsa en el siguiente enlace:</p>\r\n"
	     		+ "                                                </td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td class=\"esd-block-button es-p40t es-p40b es-p10r es-p10l\" align=\"center\"><span class=\"es-button-border-1686884775504 es-button-border\" style=\"background: #ffffff;\"><a href=\"http://localhost:4200/user/changePassword\" class=\"es-button es-button-1686884775447\" target=\"_blank\" style=\"color: #3d5ca3; mso-border-alt: 10px solid #ffffff;\">CAMBIAR CONTRASEÑA</a></span></td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                        </tbody>\r\n"
	     		+ "                                    </table>\r\n"
	     		+ "                                </td>\r\n"
	     		+ "                            </tr>\r\n"
	     		+ "                        </tbody>\r\n"
	     		+ "                    </table>\r\n"
	     		+ "                </td>\r\n"
	     		+ "            </tr>\r\n"
	     		+ "            <tr>\r\n"
	     		+ "                <td class=\"esd-structure es-p20t es-p10r es-p10l\" style=\"background-position: center center;\" align=\"left\">\r\n"
	     		+ "                    <!--[if mso]><table width=\"580\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"199\" valign=\"top\"><![endif]-->\r\n"
	     		+ "                    <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\r\n"
	     		+ "                        <tbody>\r\n"
	     		+ "                            <tr>\r\n"
	     		+ "                                <td class=\"esd-container-frame\" width=\"199\" align=\"left\">\r\n"
	     		+ "                                    <table style=\"background-position: center center;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
	     		+ "                                        <tbody>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td align=\"center\" class=\"esd-empty-container\" style=\"display: none;\"></td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                        </tbody>\r\n"
	     		+ "                                    </table>\r\n"
	     		+ "                                </td>\r\n"
	     		+ "                            </tr>\r\n"
	     		+ "                        </tbody>\r\n"
	     		+ "                    </table>\r\n"
	     		+ "                    <!--[if mso]></td><td width=\"20\"></td><td width=\"361\" valign=\"top\"><![endif]-->\r\n"
	     		+ "                    <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\r\n"
	     		+ "                        <tbody>\r\n"
	     		+ "                            <tr>\r\n"
	     		+ "                                <td class=\"esd-container-frame\" width=\"361\" align=\"left\">\r\n"
	     		+ "                                    <table style=\"background-position: center center;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
	     		+ "                                        <tbody>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td align=\"center\" class=\"esd-empty-container\" style=\"display: none;\"></td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                        </tbody>\r\n"
	     		+ "                                    </table>\r\n"
	     		+ "                                </td>\r\n"
	     		+ "                            </tr>\r\n"
	     		+ "                        </tbody>\r\n"
	     		+ "                    </table>\r\n"
	     		+ "                    <!--[if mso]></td></tr></table><![endif]-->\r\n"
	     		+ "                </td>\r\n"
	     		+ "            </tr>\r\n"
	     		+ "            <tr>\r\n"
	     		+ "                <td class=\"esd-structure es-p5t es-p20b es-p20r es-p20l\" style=\"background-position: left top;\" align=\"left\">\r\n"
	     		+ "                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
	     		+ "                        <tbody>\r\n"
	     		+ "                            <tr>\r\n"
	     		+ "                                <td class=\"esd-container-frame\" width=\"560\" valign=\"top\" align=\"center\">\r\n"
	     		+ "                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
	     		+ "                                        <tbody>\r\n"
	     		+ "                                            <tr>\r\n"
	     		+ "                                                <td class=\"esd-block-text\" esd-links-color=\"#666666\" align=\"center\">\r\n"
	     		+ "                                                </td>\r\n"
	     		+ "                                            </tr>\r\n"
	     		+ "                                        </tbody>\r\n"
	     		+ "                                    </table>\r\n"
	     		+ "                                </td>\r\n"
	     		+ "                            </tr>\r\n"
	     		+ "                        </tbody>\r\n"
	     		+ "                    </table>\r\n"
	     		+ "                </td>\r\n"
	     		+ "            </tr>\r\n"
	     		+ "        </tbody>\r\n"
	     		+ "    </table>\r\n"
	     		+ "</td>";

	       
	        message.setSubject("Reset password");
	        message.setTo(user.getEmail());
	        message.setText(content, true);
	        emailSender.send(mimeMessage);
	    }

		

	 }
		
/*
		
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo(user.getEmail());
    	
    	//long aleatorio = Math.round(Math.random()*99999999);
    	
    	String ruta = "http://localhost:4200/user/changePassword/"+user.getEmail();
    	message.setText(""+ruta);

        emailSender.send(message);
        System.out.println("OK");
        */
    



