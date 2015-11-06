package softeng.coop.ui.viewdefinitions;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.ui.vaadin.mvp.*;

public interface IEmailClientView 
	extends IView<BeanItem<CoOp>, ICoopContext>
{
	public Collection<String> getEmailRecipients();
	public void setEmailRecipients(Collection<String> emails);
	
	public String getEmailBody();
	public void setEmailBody(String body);
	
	public String getEmailSubject();
	public void setEmailSubject(String subject);
	
	public String getEmailFrom();
	public void setEmailFrom(String from);
}
