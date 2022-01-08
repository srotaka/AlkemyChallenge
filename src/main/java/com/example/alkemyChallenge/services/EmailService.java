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
    private static final String SUBJECT = "CHALLENGE BACKEND - Java\n" +
            "Spring Boot (API) ";
    private static final String TEXT = "Bienvenido/a la API para explorar el mundo de Disney que permite conocer y modificar los\n" +
            "personajes que lo componen y entender en qué películas participaron.";
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