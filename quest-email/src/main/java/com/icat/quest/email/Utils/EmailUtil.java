package com.icat.quest.email.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icat.quest.common.utils.MailerConfigResolver;
import com.icat.quest.email.vo.EmailVo;

public class EmailUtil {

	private static final Logger			logger	= LoggerFactory.getLogger(EmailUtil.class);

	
	public static void send(EmailVo emailVo) {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", MailerConfigResolver.getProperty("mail.smtp.host", "").trim());  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.port", MailerConfigResolver.getProperty("mail.smtp.port", "").trim());  
        props.put("mail.smtp.socketFactory.port", MailerConfigResolver.getProperty("mail.smtp.socketFactory.port", "").trim());  
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
		
		
		final String password = MailerConfigResolver.getProperty("password", "").trim();
		final String sender = MailerConfigResolver.getProperty("sender", "").trim();
		try{
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(sender, password);
			}
		});
		
		MimeMessage message = new MimeMessage(session);
		Multipart multipart = new MimeMultipart();
		
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(sender));
		message.setSubject(emailVo.getSubject());
		emailVo.getCcList().forEach(email->{
			try {
				message.addRecipient(RecipientType.CC, new InternetAddress(email));
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		});
		emailVo.getBccList().forEach(email->{
			try {
				message.addRecipient(RecipientType.BCC, new InternetAddress(email));
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		});
		emailVo.getToList().forEach(email->{
			try {
				message.addRecipient(RecipientType.TO, new InternetAddress(email));
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		});
		if(emailVo.getPriority() != null) {
		message.setHeader("X-Priority", emailVo.getPriority().trim()) ;
		}
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		message.setHeader("Content-Type", "text/html; charset=UTF-8");
		messageBodyPart.setContent(emailVo.getMessageBody(), "text/html");
		multipart.addBodyPart(messageBodyPart);
		
		
		String emailFileBasePath = MailerConfigResolver.getProperty("email.temp.dir", "");
		Map<String,byte[]> fileAndbyteDataMap = new HashMap<>();
		List<String> fileNameWithPath = new ArrayList<>();
		File emailDir = new File(emailFileBasePath);
		if (!emailDir.isDirectory())
			emailDir.mkdirs();
		for(String fileName:fileAndbyteDataMap.keySet()) {
			
			messageBodyPart = new MimeBodyPart();

			String outputEmailFileFullUrl = emailFileBasePath + fileName;
			File f = new File(outputEmailFileFullUrl);

			if (!f.isFile())
				f.createNewFile();

			@SuppressWarnings("resource")
			FileOutputStream outputStream = new FileOutputStream(f);

				outputStream.write(fileAndbyteDataMap.get(fileName));

			
			DataSource source = new FileDataSource(outputEmailFileFullUrl);
			fileNameWithPath.add(outputEmailFileFullUrl);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
			
		}
		Map<String,Map<String,byte[]>> inlineImages = new HashMap<>();
		if(inlineImages != null && inlineImages.size()>0)
			for(String cid : inlineImages.keySet())
			{
				for(String fileName : inlineImages.get(cid).keySet())
				{
					String outputEmailFileFullUrl = emailFileBasePath + fileName;
					File f = new File(outputEmailFileFullUrl);

					if (!f.isFile())
						f.createNewFile();

					@SuppressWarnings("resource")
					FileOutputStream outputStream = new FileOutputStream(f);

						outputStream.write(inlineImages.get(cid).get(fileName));

					
						messageBodyPart = new MimeBodyPart();
					    DataSource source = new FileDataSource(outputEmailFileFullUrl);
					    messageBodyPart.setDataHandler(new DataHandler(source));
					    messageBodyPart.setHeader("Content-Type", "image/"+FilenameUtils.getExtension(fileName)+";"+ "name="+fileName);
					    messageBodyPart.setHeader("Content-ID", "<"+cid+">") ;
					    messageBodyPart.setFileName(fileName);
					    messageBodyPart.setHeader("Content-Disposition", "inline");
					    multipart.addBodyPart(messageBodyPart);
					
				}
				
			}
			
		message.setContent(multipart);
		 logger.info("Message is :::::::::::::::::::::::::::::::===============>>>>>>>>>>>>>>>>" + message.getContent().toString());
			
		 Transport.send(message);
	
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}	
	}
}
