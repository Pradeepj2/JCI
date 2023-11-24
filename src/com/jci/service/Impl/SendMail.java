package com.jci.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;


import javax.activation.*;

public class SendMail {

	public void sendEmail(InternetAddress[] toAddresses, String body, String subject, String filePath,
			String fileName) {

		int smtpPort = 587; // Use 587 for TLS or 465 for SSL
		
		
//		// for official use
//		String smtpHost = "smtp.office365.com";
//		String username = "jci.erp.ops@jcimail.in"; // Office 365 email address
//		String password = "Jute@1234";
	
		//for testing purpose
		String smtpHost = "smtp.gmail.com";
		String username = "pradeepcyfuturemeghdoot@gmail.com"; // Office 365 email address
		String password = "mgujrlhbvqcecwpb";

		                   
		
		// String recipient = "vishal.vishwakarma@cyfuture.com"; // Recipient's email
		// address

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(props);

		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, toAddresses);
			message.setFrom(new InternetAddress(username));
			message.setSubject(subject);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport transport = session.getTransport("smtp");
			transport.connect(smtpHost, smtpPort, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			System.out.println("Sent message successfully....");

		} catch (Exception e) {
			System.out.println("error in sending mail" + e);
			e.printStackTrace();
		}

	}

//	public void sendEmailWithAttachment(String file) throws FileNotFoundException {
//		String smtpHost = "smtp.gmail.com";
//		String smtpPort = "587"; // Use 587 for TLS or 465 for SSL
//		String username = "pradeepcyfuturemeghdoot@gmail.com"; // Office 365 email address
//		String password = "mrgpxyxujocedicx";
//		String body = "Invoice of distributed jute to mill";
//		String sub = "Invoice";
//
//		Properties props = new Properties();
//
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", smtpHost);
//		props.put("mail.smtp.port", smtpPort);
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//
//		Session session = Session.getInstance(props, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		});
//
//		session.setDebug(true);
//
//		MimeMessage mimeMessage = new MimeMessage(session);
//
//		try {
//			mimeMessage.setFrom(new InternetAddress("pradeepcyfuturemeghdoot@gmail.com"));
//			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("pradeep.rathor@cyfuture.com"));
//			mimeMessage.setSubject(sub);
//
//			MimeMultipart mimeMultipart = new MimeMultipart();
//			MimeBodyPart textBodyPart = new MimeBodyPart();
//			MimeBodyPart fileBodyPart = new MimeBodyPart();
//
//			try {
//				textBodyPart.setText(body);
//
//				try {
//					FileInputStream inputStream = new FileInputStream(new File(file));
//					DataSource source = new ByteArrayDataSource(inputStream, "application/octet-stream");
//					fileBodyPart.setDataHandler(new DataHandler(source));
//					fileBodyPart.setFileName(new File(file).getName());
//
//					mimeMultipart.addBodyPart(textBodyPart);
//					mimeMultipart.addBodyPart(fileBodyPart);
//					mimeMessage.setContent(mimeMultipart);
//
//					// Create a Transport object, connect, and send the message
//					Transport transport = session.getTransport("smtp");
//					transport.connect(smtpHost, username, password);
//					Transport.send(mimeMessage);
//
//					// Close the transport connection
//					transport.close();
//
//					// Close the InputStream
//					inputStream.close();
//
//					System.out.println("Email sent successfully");
//
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//					System.out.println("Error opening file: " + e.getMessage());
//
//					// Handle the exception or propagate it as needed
//					throw e;
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("Error creating email: " + e.getMessage());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Error creating email: " + e.getMessage());
//		}
//	}
}
