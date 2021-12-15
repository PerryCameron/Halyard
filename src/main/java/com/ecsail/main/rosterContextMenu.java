package com.ecsail.main;

import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;
import javafx.event.ActionEvent;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

public class rosterContextMenu extends ContextMenu {
    MenuItem emailClient = new MenuItem("Open in Email Client");
    MenuItem registration = new MenuItem("Send Renewal Link");
    private EmailLinkCreator linkCreator = new EmailLinkCreator();

    private Object_Person person;
    private String email;


    public rosterContextMenu(Object_MembershipList m) {
        this.person = SqlSelect.getPersonByPid(m.getPid());
        this.email = SqlSelect.getEmail(this.person);
        getItems().addAll(emailClient,registration);


        registration.setOnAction((ActionEvent e) -> {

            String link = linkCreator.createLink();
            String linkData = encodeURI(linkCreator.createLinkData(m));
            String body = link + linkData;
            // email ID of Recipient.
            String recipient = email;

            String host = "smtp.gmail.com";

            // Getting system properties
            Properties properties = new Properties();

            // Setting up mail server
            properties.put("mail.smtp.username","");
            properties.put("mail.smtp.password", "");
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true"); // TLS
            // creating session object to get properties
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("mail.smtp.username"),
                            properties.getProperty("mail.smtp.password"));
                }
            });

            // Used to debug SMTP issues
            session.setDebug(true);

            try
            {
                // MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From Field: adding senders email to from field.
                message.setFrom(new InternetAddress("perry.lee.cameron@gmail.com"));

                // Set To Field: adding recipient's email to from field.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

                // Set Subject: subject of the email
                message.setSubject("2022 Membership Dues");

                // set body of the email.
                message.setText(body);

                // Send email.
                Transport.send(message);
                System.out.println("Mail successfully sent");
            }
            catch (MessagingException mex)
            {
                mex.printStackTrace();
            }

        });

        emailClient.setOnAction((ActionEvent e) -> {

            String link = linkCreator.createLink();
            String linkData = encodeURI(linkCreator.createLinkData(m));
            String body = link + linkData;
            String subject = encodeURI("2022 Membership Renewal");
            System.out.println(body);

            Desktop desktop = Desktop.getDesktop();

            String message = "mailto:" + email + "?subject="+subject+"&body=" + body;
            URI uri = URI.create(message);

            try {
                desktop.mail(uri);
            } catch (IOException f) {
                f.printStackTrace();
            }
        });
    }

    public static String encodeURI(String s) {
        String result;
        try {
            result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } // This exception should never occur.
        catch (Exception e) {
            result = s;
        }

        return result;
    }

}
