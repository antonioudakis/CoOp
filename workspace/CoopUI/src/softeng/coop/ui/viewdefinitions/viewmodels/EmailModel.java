package softeng.coop.ui.viewdefinitions.viewmodels;

import softeng.coop.dataaccess.*;

import java.util.*;

/**
 * A model to define a mail message and its recepients.
 * Used in IEmailView.
 */
public class EmailModel
{
	private EmailMessage message;
	
	private Collection<AuthenticatedUser> recepients;
	
	public EmailModel()
	{
		message = new EmailMessage();
		recepients = new ArrayList<AuthenticatedUser>();
	}

	public Collection<AuthenticatedUser> getRecepients()
	{
		return recepients;
	}

	public void setRecepients(Collection<AuthenticatedUser> recepients)
	{
		if (recepients == null) 
			throw new IllegalArgumentException("Argument 'recepients' must not be null.");
		
		this.recepients = recepients;
	}

	public EmailMessage getMessage()
	{
		return message;
	}
}
