package softeng.coop.business.students.strategies;

import softeng.coop.dataaccess.*;

import softeng.coop.business.students.*;

import java.util.*;

import javax.persistence.*;

/**
 * This strategy uses e-mail to inform recepients of new invitations
 * and senders of invitation acceptances.
 */
public class EmailInvitationStrategy
extends Strategy
implements IInvitationStrategy
{
	private boolean groupSentMails;
	
	/**
	 * Create.
	 * @param groupSentMails If true, attempt to send same messages in a single batch.
	 */
	public EmailInvitationStrategy(boolean groupSentMails)
	{
		this.groupSentMails = groupSentMails;
	}
	
//	public EmailInvitationStrategy()
//	{
//		this(false);
//	}
//	
	/**
	 * Functions as a key to maps of mail messages by locale and gender.
	 */
	@SuppressWarnings("unused")
	private static class MailVariationKey
	{
		private boolean female;
		
		private Locale locale;
		
		public MailVariationKey(boolean female, Locale locale)
		{
			if (locale == null) 
				throw new IllegalArgumentException("Argument 'locale' must not be null.");
			
			this.female = female;
			this.locale = locale;
		}

		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof MailVariationKey)) return false;
			
			MailVariationKey other = (MailVariationKey)o;

			return this.female == other.female && this.locale == other.locale;
		}

		@Override
		public int hashCode()
		{
			return locale.hashCode() + (female ? 17 : 0);
		}

		public boolean isFemale()
		{
			return female;
		}

		public Locale getLocale()
		{
			return locale;
		}
	
	}
	
	private static class MailMessage
	{
		private String title;
		private String body;
		private Collection<String> recepients;
		
		public MailMessage(String title, String body)
		{
			if (title == null) 
				throw new IllegalArgumentException("Argument 'title' must not be null.");
			
			if (body == null) 
				throw new IllegalArgumentException("Argument 'body' must not be null.");
			
			this.title = title;
			this.body = body;
			this.recepients = new ArrayList<String>();
		}
		
		public String getTitle()
		{
			return title;
		}
		
		public String getBody()
		{
			return body;
		}
		
		public Collection<String> getRecepients()
		{
			return recepients;
		}
	}
	
	@Override
	public void onPostInvitation(Invitation invitation, EntityManager entityManager)
	{
		// Group same messages in order to avoid multiple sends.
		HashMap<MailVariationKey, MailMessage> messagesMap = null;
		
		if (groupSentMails) messagesMap = new HashMap<MailVariationKey, MailMessage>();
		
		for (Registration recepientRegistration : invitation.getRecepients())
		{
			Student recepient = recepientRegistration.getStudent();
			
			Student sender = invitation.getSender().getStudent();
			
			Locale preferredLocale = getLanguageLocale(recepient.getPreferredLanguage());
			
			MailVariationKey mailVariationKey = 
				new MailVariationKey(sender.getGender() == Gender.Female, preferredLocale);
			
			// Have we created this message variation?
			MailMessage mailMessage = null;
			
			if (groupSentMails) mailMessage = messagesMap.get(mailVariationKey);
			
			// If we haven't, create it and store it in the map.
			if (mailMessage == null)
			{
				String titleKey, bodyKey;
				
				switch (sender.getGender())
				{
					case Female:
						titleKey = "INVITATION_SENT_TITLE_FEMALE_SENDER";
						bodyKey = "INVITATION_SENT_BODY_FEMALE_SENDER";
						break;
						
					default:
						titleKey = "INVITATION_SENT_TITLE_MALE_SENDER";
						bodyKey = "INVITATION_SENT_BODY_MALE_SENDER";
						break;
				}
				
				String title = 
					String.format(
							getLocalizedString(preferredLocale, titleKey), 
							sender.getName(), 
							sender.getSurname());
				
				String body =
					String.format(
							getLocalizedString(preferredLocale, bodyKey), 
							sender.getName(), 
							sender.getSurname(), 
							sender.getSerialNumber());
				
				mailMessage = new MailMessage(title, body);
				
				messagesMap.put(mailVariationKey, mailMessage);
			}
			
			mailMessage.getRecepients().add(recepient.getEmail());
			
			if (!groupSentMails) sendMail(recepient.getEmail(), mailMessage.getTitle(), mailMessage.getBody());

		}
		
		if (groupSentMails)
		{
			// Enumerate all message variations and send them. 
			for (MailMessage message : messagesMap.values())
			{
				sendMail(message.getRecepients(), message.getTitle(), message.getBody());
			}
		}
		
	}

	@Override
	public void onAcceptInvitation(Registration recepient, Invitation invitation, EntityManager entityManager)
	{
		Student recepientStudent = recepient.getStudent();
		
		Locale preferredLocale = getLanguageLocale(recepientStudent.getPreferredLanguage());
		
		String titleKey, bodyKey;
		
		switch (recepient.getStudent().getGender())
		{
			case Female:
				titleKey = "INVITATION_ACCEPTED_TITLE_FEMALE_RECEPIENT";
				bodyKey = "INVITATION_ACCEPTED_BODY_FEMALE_RECEPIENT";
				break;
				
			default:
				titleKey = "INVITATION_ACCEPTED_TITLE_MALE_RECEPIENT";
				bodyKey = "INVITATION_ACCEPTED_BODY_MALE_RECEPIENT";
				break;
		}
		
		String title = 
			String.format(
					getLocalizedString(preferredLocale, titleKey), 
					recepientStudent.getName(), 
					recepientStudent.getSurname());
		
		String body =
			String.format(
					getLocalizedString(preferredLocale, bodyKey), 
					recepientStudent.getName(), 
					recepientStudent.getSurname(), 
					recepientStudent.getSerialNumber(), 
					recepientStudent.getEmail());
		
		Student senderStudent = invitation.getSender().getStudent();

		sendMail(senderStudent.getEmail(), title, body);
	}
	
}
