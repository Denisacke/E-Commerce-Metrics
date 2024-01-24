package com.commerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 * Class which sends emails upon account creation or complaint submissions
 */
@Component
@Scope("singleton")
public class MailSender {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    @Value("${spring.mail.username}")
    private String emailCredential;

    @Value("${spring.mail.password}")
    private String passwordCredential;

    private final Session session;

    public MailSender() {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");

        this.session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailCredential, passwordCredential);
                    }
                });
    }

    public void sendCredentials(String email, String username, String password) {

        try {
            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(emailCredential));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            msg.setSubject("Here are your credentials for your employee account");
            String sb = "" + "We are happy to have you on our team!\n" +
                    "Username: " + username + "\n" +
                    "Password: " + password + "\n";
            msg.setText(sb);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            logger.debug(e.getMessage());
        }
    }

    public void sendResponse(String email, String response) {
        try {
            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(emailCredential));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            msg.setSubject("Response regarding your complaint");

            msg.setText(response);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            logger.debug(e.getMessage());
        }
    }
}
