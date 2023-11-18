package com.commerce.service;

import org.springframework.beans.factory.annotation.Value;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailSender {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;


    private MailSender() {
    }

    public static void sendCredentials(String email, String username, String password) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("mail_tester@gmail.com", "your-gmail-password");
                        }
                    });

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("mail_tester@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            msg.setSubject("Here are your credentials for your employee account");
            StringBuilder sb = new StringBuilder("");
            sb.append("We are happy to have you on our team!\n");
            sb.append("Username: ").append(username).append("\n");
            sb.append("Password: ").append(password).append("\n");
            msg.setText(sb.toString());
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            System.out.println(e);
        }
    }

    public static void sendResponse(String email, String response) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("mail_tester@gmail.com", "your-gmail-password");
                        }
                    });

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("mail_tester@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            msg.setSubject("Response regarding your complaint");

            msg.setText(response);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            System.out.println(e);
        }
    }
}
