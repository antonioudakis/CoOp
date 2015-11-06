package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.forms.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

public class ReportDialog
extends CoopDialog<BeanItem<Report>>
{
	private static final long serialVersionUID = 1L;
	
	private ReportAttachmentsTableComponent attachmentsTableComponent =
		new ReportAttachmentsTableComponent();
	
	private TextField titleTextField = new TextField();
	
	private TextArea commentsTextArea = new TextArea();
	
	private TextField gradeTextField = new TextField();
	
	private ReportForm form = new ReportForm();
	
	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), getPropertyIds());
	}
	
	private List<String> getPropertyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		AuthenticatedUser user = getContext().getSession().getAuthenticatedUser();
		
		list.add("title");
		
		if (user instanceof FacultyUser)
		{
			list.add("grade");
			list.add("comments");
		}
		
		list.add("attachments");
		
		return list;
	}

	@SuppressWarnings("serial")
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setWidth("770px");
		setHeight("500px");

		form.setWidth("100%");
		form.setImmediate(true);
		form.setWriteThrough(false);
		
		addComponent(form);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("title"))
				{
					titleTextField.setWidth("100%");
					titleTextField.setCaption(getLocalizedString("TITLE_CAPTION"));
					
					return titleTextField;
				}
				else if (propertyId.equals("comments"))
				{
					commentsTextArea.setWidth("100%");
					commentsTextArea.setCaption(getLocalizedString("COMMENTS_CAPTION"));
					commentsTextArea.setNullRepresentation("");
					commentsTextArea.setNullSettingAllowed(true);
					
					return commentsTextArea;
				}
				else if (propertyId.equals("attachments"))
				{
					attachmentsTableComponent.setWidth("100%");
					attachmentsTableComponent.setParentModel(getModel().getBean());
					attachmentsTableComponent.setCaption(getLocalizedString("ATTACHMENTS_CAPTION"));
					
					return attachmentsTableComponent;
				}
				else if (propertyId.equals("grade"))
				{
					gradeTextField.setWidth("100%");
					gradeTextField.setCaption(getLocalizedString("GRADE_CAPTION"));
					gradeTextField.setNullRepresentation("");
					gradeTextField.setNullSettingAllowed(true);
					
					return gradeTextField;
				}
				
				return null;
			}
		});
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote vote)
			{
				onAccept(vote);
			}
		});
		
	}

	protected void onAccept(CommandExecutionVote vote)
	{
		if (!isDataValid())
		{
			getContext().showInvalidDataNotification();
			vote.markAsFailed();
			return;
		}
		
		form.commit();
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		titleTextField.setCaption(getLocalizedString("TITLE_CAPTION"));
		commentsTextArea.setCaption(getLocalizedString("COMMENTS_CAPTION"));
		gradeTextField.setCaption(getLocalizedString("GRADE_CAPTION"));
		attachmentsTableComponent.setCaption(getLocalizedString("ATTACHMENTS_CAPTION"));
	}

	@Override
	public boolean isDataValid()
	{
		return form.isValid();
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		form.setReadOnly(readOnly);
	}
	
}
