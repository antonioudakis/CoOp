package softeng.coop.ui.presenters;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.mail.client.MailClient;
import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import java.util.*;

import com.vaadin.ui.Window.Notification;

public class EmailPresenter
extends Presenter<EmailModel, ICoopContext, IEmailView>
{
	// Used to fetch string from multilinguals.
	private ManagerBase manager;
	
	// This holds the message after selecting a language from the multilingual fields. 
	private static class TranslatedMessage
	{
		private String title, body;
		
		private Collection<String> recepientAddresses;
		
		public TranslatedMessage(String title, String body)
		{
			if (title == null) 
				throw new IllegalArgumentException("Argument 'title' must not be null.");
			
			if (body == null) 
				throw new IllegalArgumentException("Argument 'body' must not be null.");
			
			this.title = title;
			this.body = body;
			this.recepientAddresses = new ArrayList<String>();
		}

		public String getTitle()
		{
			return title;
		}

		public String getBody()
		{
			return body;
		}
		
		public Collection<String> getRecepientAddresses()
		{
			return this.recepientAddresses;
		}
	}
	
	// Map for messages by recepient language. 
	private Map<Language, TranslatedMessage> translatedMessagesMap;

	public EmailPresenter(IEmailView view)
	{
		super(view);
		
		translatedMessagesMap = new HashMap<Language, TranslatedMessage>();
	}

	@Override
	protected void attachToView()
	{
		manager = getContext().getSession().getBaseManager();
		
		getView().getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				onOk();
			}
		});
	}

	protected void onOk()
	{
		translatedMessagesMap.clear();
		
		EmailModel emailModel = getView().getModel();
		
		if (emailModel == null) return;
		
		EmailMessage message = emailModel.getMessage();
		
		for (AuthenticatedUser recepient : emailModel.getRecepients())
		{
			if (recepient.getEmail() == null) continue;
			
			Language preferredLanguage = recepient.getPreferredLanguage();
			
			// Have we produced a message for this preferred language before?
			TranslatedMessage translatedMessage = translatedMessagesMap.get(preferredLanguage);
			
			if (translatedMessage == null)
			{
				String title = manager.getLiteral(message.getTitle(), preferredLanguage);
				String body = manager.getLiteral(message.getBody(), preferredLanguage);
				
				translatedMessage = new TranslatedMessage(title, body);
				
				translatedMessagesMap.put(preferredLanguage, translatedMessage);
			}
			
			translatedMessage.getRecepientAddresses().add(recepient.getEmail());
		}
		
		// Enumerate all message variations and send batches by preferred language.
		MailClient mailClient = new MailClient();
		
		mailClient.setContentType("text/html; charset=utf-8");
		
		for (TranslatedMessage translatedMessage : translatedMessagesMap.values())
		{
			mailClient.postMail(
					translatedMessage.getRecepientAddresses(), 
					translatedMessage.getTitle(), 
					translatedMessage.getBody(), 
					message.getSender());
		}
		
		getContext().showNotification(
				getContext().getLocalizedString("MAIL_SENT_CAPTION"), 
				getContext().getLocalizedString("MAIL_SENT_DESCRIPTION"), 
				Notification.TYPE_HUMANIZED_MESSAGE);
		
	}

	@Override
	protected void setupView()
	{
		getView().dataBind();
	}

}
