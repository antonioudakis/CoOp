package softeng.coop.ui.composites;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import softeng.coop.dataaccess.CoOp;
import softeng.coop.mail.client.MailClient;
import softeng.coop.ui.CoopUIUserException;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.viewdefinitions.IEmailClientView;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.Presenter;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class EmailClientComponent 
	extends CoopComponent<BeanItem<CoOp>> 
	implements IEmailClientView
{

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Button sendButton;
	@AutoGenerated
	private TextField bodyTextField;
	@AutoGenerated
	private TextField subjectTextField;

	private Collection<String> emails;
	private String from = "info@coop.ntua.gr";
	
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public EmailClientComponent() 
	{
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	@Override
	protected void setupUI() 
	{
		super.setupUI();
		
		this.sendButton.addListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				if (emails == null)
				{
					throw new CoopUIUserException(getLocalizedString("NO_EMAILS_ERROR_MESSAGE"));
				}
				
				MailClient client = new MailClient();
				
				List<String> failedEmails =
					client.postMail(emails, getEmailSubject(), getEmailBody(), getEmailFrom());
				
				if (failedEmails != null)
				{
					String failedEmailsToString = "";
					for (String failedEmail : failedEmails)
					{
						failedEmailsToString = failedEmailsToString + failedEmail +", ";
					}
					throw new CoopUIUserException(getLocalizedString("FAILED_EMAILS_MESSAGE")+failedEmailsToString);
				}
				
			}
		});
		
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale) 
	{
		super.setupLocalizedCaptions(locale);
		
		this.subjectTextField.setCaption(getLocalizedString("SUBJECT_CAPTION"));
		
		this.bodyTextField.setCaption(getLocalizedString("BODY_CAPTION"));
		
		this.sendButton.setCaption(getLocalizedString("SEND_BUTTON_CAPTION"));
	}

	@Override
	protected Presenter<BeanItem<CoOp>, ICoopContext, ? extends IView<BeanItem<CoOp>, ICoopContext>> 
		supplyPresenter() 
	{
		return null;
	}

	@Override
	public void dataBind() 
	{
		// TODO Auto-generated method stub
		
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		
		// subjectTextField
		subjectTextField = new TextField();
		subjectTextField.setCaption("����");
		subjectTextField.setImmediate(false);
		subjectTextField.setWidth("100.0%");
		subjectTextField.setHeight("-1px");
		mainLayout.addComponent(subjectTextField);
		
		// bodyRichTextArea
		bodyTextField = new TextField();
		bodyTextField.setStyleName("�������");
		bodyTextField.setImmediate(false);
		bodyTextField.setWidth("100.0%");
		bodyTextField.setHeight("60px");
		mainLayout.addComponent(bodyTextField);
		
		// sendButton
		sendButton = new Button();
		sendButton.setCaption("��������");
		sendButton.setImmediate(false);
		sendButton.setWidth("100.0%");
		sendButton.setHeight("-1px");
		mainLayout.addComponent(sendButton);
		
		return mainLayout;
	}

	@Override
	public Collection<String> getEmailRecipients() 
	{
		return this.emails;
	}

	@Override
	public void setEmailRecipients(Collection<String> emails) 
	{
		this.emails = emails;
	}

	@Override
	public String getEmailBody() 
	{
		return (String) this.bodyTextField.getValue();
	}

	@Override
	public void setEmailBody(String body) 
	{
		this.bodyTextField.setValue(body);
	}

	@Override
	public String getEmailSubject() 
	{
		return (String) this.subjectTextField.getValue();
	}

	@Override
	public void setEmailSubject(String subject) 
	{
		this.subjectTextField.setValue(subject);
	}

	@Override
	public String getEmailFrom() 
	{
		return from;
	}
	
	@Override
	public void setEmailFrom(String from)
	{
		this.from = from;
	}

}
