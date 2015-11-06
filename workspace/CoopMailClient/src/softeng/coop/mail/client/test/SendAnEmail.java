package softeng.coop.mail.client.test;

import java.util.*;

import org.junit.*;

import softeng.coop.mail.client.MailClient;

public class SendAnEmail 
{
	@Test
	public void sendAnEmailToAmoral()
	{
		MailClient mailClient = new MailClient();
		
		ArrayList<String> emails = new ArrayList<String>();
		emails.add("amoral@netmode.ntua.gr");
		mailClient.postMail(emails, "test", "this is a test from java.", "amoral@mail.ntua.gr");
	}
	
	@Test 
	public void sendEmailToMultipleRecipients()
	{
		MailClient mailClient = new MailClient();
		
		ArrayList<String> emails = new ArrayList<String>();
		emails.add("amoral@netmode.ntua.gr");
		emails.add("amoralios@gmail.com");
		mailClient.postMail(emails, "test2", "this is a test with multiple recipients", "amoral@netmode.ntua.gr");
	}
	
	@Test
	public void sendEmailToMultipleRecipientWithAWrongEmailAddress()
	{
		MailClient mailClient = new MailClient();
		
		ArrayList<String> emails = new ArrayList<String>();
		emails.add("amoral@netmode.ntua.gr");
		emails.add("amoralios@ gmail.com");
		List<String> result = mailClient.postMail(emails, "test3", "this is a test with multiple recipients", "amoral@netmode.ntua.gr");
		
		Assert.assertNotNull(result);
	}
}
