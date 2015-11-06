package softeng.coop.mail.client;

import java.util.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import softeng.coop.business.CoOpException;


public class MailClient 
{
	private String contentType;
	
	public MailClient()
	{
		setContentType("text/plain; charset=utf-8"); 
	}
	
	/**
	 * Send email to recipients using the javax.mail API. Two resources need to be edited:
	 * 1. mailConfig.properties : configures the javax.mail Session
	 * 2. secret.properties : holds the username and password of the smtp user
	 * @param emails: a collection of the recipients emails
	 * @param subject: the Subject of the email
	 * @param message: the body of the email (as text)
	 * @param from: the sender's email address
	 * @return return null or a list of failed email repicipients
	 */
	public List<String> postMail( Collection<String> emails, String subject, String message , String from)
	{
		boolean debug = false;
		ArrayList<String> failedEmails = new ArrayList<String>();
		
//		final String username = "username@gmail.com";
//		final String password = "password";
//
//		//Set the host smtp address
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.ntua.gr");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");;
//		props.put("mail.smtp.port", "587");


		// create some properties and get the default Session
		Properties prop = null;
		Properties secret = null;
		try {
			prop = loadProperties();
			
			secret = loadSecret();
		} catch (IOException e) 
		{
			throw new CoOpException("MAILClient.java: Cannot load configuration", e);
		}
		
		Session session;
		
		if (secret != null)
		{
			final String username = secret.getProperty("username");
			final String password = secret.getProperty("password");
			
			session = Session.getInstance(prop, 
				new javax.mail.Authenticator() 
				{
					protected PasswordAuthentication getPasswordAuthentication() 
					{
						return new PasswordAuthentication(username, password);
					}
			});
		}
		else
		{
			session = Session.getInstance(prop);
		}

		session.setDebug(debug);


		// create a message
		MimeMessage msg = new MimeMessage(session);
		
		// set the from and to address
		InternetAddress addressFrom;
		try
		{
			addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);
		} catch (Exception e) 
		{
			throw new CoOpException("MaiLClient.java: from address is not a valid address");
		}
		
		InternetAddress[] addressTo = new InternetAddress[emails.toArray().length]; 
		
		for (int i = 0; i < emails.toArray().length; i++)
		{
			try 
			{
				addressTo[i] = new InternetAddress((String) emails.toArray()[i]);
			}
			catch (Exception e) 
			{	
				failedEmails.add((String) emails.toArray()[i]);
			}
		}
		
		//remove null values
		ArrayList<InternetAddress> addressToWithOutNullValues = new ArrayList<InternetAddress>();
		
		for(InternetAddress address : addressTo)
		{
			if (address != null)
			{
				addressToWithOutNullValues.add(address);
			}
		}
		
		if (addressToWithOutNullValues.isEmpty())
		{
			return failedEmails;
		}
		
		InternetAddress[] actualRecipients = new InternetAddress[addressToWithOutNullValues.toArray().length];
		for (int i=0 ; i<addressToWithOutNullValues.toArray().length ; i++)
		{
			actualRecipients[i] = (InternetAddress) addressToWithOutNullValues.toArray()[i];
		}
		
		try 
		{
			msg.setRecipients(Message.RecipientType.BCC, actualRecipients);
		}
		catch (Exception e) 
		{
			throw new CoOpException("MailClient.java: Cannnot create message recipients list.", e);
		}

		// Optional : You can also set your custom headers in the Email if you Want
		//msg.addHeader("MyHeaderName", "myHeaderValue");

		// Setting the Subject and Content Type
		try
		{
			msg.setSubject(subject, "utf-8");
			msg.setContent(message, contentType);
			
			Transport.send(msg);
		} 
		catch (Exception e) 
		{
			throw new CoOpException("MailClient.java: Cannot send email.", e);
		}
		
		if (failedEmails.isEmpty()) 
			return null;
		else
			return failedEmails;
	}
	
	private Properties loadProperties() throws IOException
	{
		Properties prop = new Properties();
		
		InputStream in = getClass().getResourceAsStream("mailConfig.properties");
		
		prop.load(in);
		
		in.close();
		
		return prop;
	}
	
	private Properties loadSecret() throws IOException 
	{
		Properties prop = new Properties();
		
		InputStream in = getClass().getResourceAsStream("secret.properties");
		
		if (in == null) return null;
		
		prop.load(in);
		
		in.close();
		
		return prop;
	}

	/**
	 * Set the content type. Default is "text/plain; charset=utf-8".
	 */
	public void setContentType(String contentType)
	{
		if (contentType == null) 
			throw new IllegalArgumentException("Argument 'contentType' must not be null.");
		
		this.contentType = contentType;
	}

	/**
	 * Get the content type. Default is "text/plain; charset=utf-8".
	 */
	public String getContentType()
	{
		return contentType;
	}
}
