package com.example.alkemyChallenge.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")

    private String from;
    private static final String SUBJECT = "Disney Alkemy Challenge";
    private static final String TEXT = "Thank you for registering in our application. \n" +
            "Now you will be able to get to know the wonderful world of Disney, meet its characters and movies.";
    public void sendThread(String to) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(SUBJECT);
            message.setText(TEXT);
            sender.send(message);
        }).start();
    }
}