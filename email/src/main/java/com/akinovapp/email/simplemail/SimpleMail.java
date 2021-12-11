package com.akinovapp.email.simplemail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleMail {

    @Autowired
    private JavaMailSender javaMailSender;

    public void simpleMailSender(String sendTo, String mailSubject, String mailBody){

        System.out.println("Email Sending...");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("akinovapp@gmail.com");
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setSubject(mailSubject);
        simpleMailMessage.setText(mailBody);

        javaMailSender.send(simpleMailMessage);

        System.out.println("Email Sent!!");
    }

}
