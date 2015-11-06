package softeng.coop.ui.viewdefinitions.viewmodels;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import java.util.*;

import javax.validation.constraints.*;

import softeng.validation.constraints.*;

/**
 * A mail message supporting multilingual title and body.
 */
public class EmailMessage
{
	@NotNull
	private Multilingual title;
	
	@NotNull
	private Multilingual body;

	@NotNull
	@Email
	private String sender;
	
	public EmailMessage()
	{
		title = new Multilingual();
		title.setLiterals(new HashSet<Literal>());
		title.setTransient(true);
		
		body = new Multilingual();
		body.setLiterals(new HashSet<Literal>());
		body.setTransient(true);
		
		ICoopContext context = CoopApplicationServlet.getCurrentApplication();
		
		if (context != null)
		{
			AuthenticatedUser user = context.getSession().getAuthenticatedUser();
			
			sender = user.getEmail();
		}
	}

	public Multilingual getTitle()
	{
		return title;
	}

	public void setTitle(Multilingual title)
	{
		if (title == null) 
			throw new IllegalArgumentException("Argument 'title' must not be null.");
		
		this.title = title;
	}

	public Multilingual getBody()
	{
		return body;
	}

	public void setBody(Multilingual body)
	{
		if (body == null) 
			throw new IllegalArgumentException("Argument 'body' must not be null.");
		
		this.body = body;
	}

	public String getSender()
	{
		return sender;
	}

	public void setSender(String sender)
	{
		if (sender == null) 
			throw new IllegalArgumentException("Argument 'sender' must not be null.");

		this.sender = sender;
	}
	
}
