package com.newsletter;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

public class NewsletterSender {
    public static void main(String[] args) {
        Properties config = ConfigLoader.load();

        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        final String username = config.getProperty("email.username");
        final String password = config.getProperty("email.password");
        final String recipient = config.getProperty("email.recipient");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient)
            );
            message.setSubject("email");

            String html = """
                <h1 style='color:#0fbab7;'>Newsletter Java</h1>
                <p>Essa é sua primeira <b>newsletter com HTML</b> usando Java + Gradle!</p>
                <p><a href='https://seusite.com' style='background:#0fbab7;color:white;padding:10px 15px;text-decoration:none;'>Acesse agora</a></p>
            """;

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(html, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("sent succefully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
