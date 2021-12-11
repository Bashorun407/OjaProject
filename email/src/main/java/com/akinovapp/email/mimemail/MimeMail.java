package com.akinovapp.email.mimemail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

//
//public class MimeMail {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendMimeMail(String sendTo, String subject, String mailBody, String attachment) throws MessagingException {
//
//        System.out.println("Mime Mail Sending...");
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//        mimeMessageHelper.setFrom("akinovapp@gmail.com");
//        mimeMessageHelper.setTo(sendTo);
//        mimeMessageHelper.setSubject(subject);
//        mimeMessageHelper.setText(mailBody);
//        mimeMessageHelper.setText("The following text is in HTML", "<p><h1>Hello Bashorun</h1>, today," +
//                " I wrote <emp>MIMEMESSAGE MAIL</emp>...<b>its a good day really</b> </p>");
//
//        //In other to add attachment, FileSystemResource class will be used to 'collect' or 'set' the file to be attached
//        FileSystemResource fileSystemResource = new FileSystemResource( new File(attachment));
//
//        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
//
//        mailSender.send(mimeMessage);
//        System.out.println("Mimi Mail Sent!!");
//
//    }
//}
