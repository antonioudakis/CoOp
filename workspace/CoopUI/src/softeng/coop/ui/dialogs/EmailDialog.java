package softeng.coop.ui.dialogs;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import com.vaadin.data.*;
import com.vaadin.ui.*;

import java.util.*;

import softeng.coop.business.students.*;

@SuppressWarnings("serial")
public class EmailDialog
extends CoopDialog<EmailModel>
implements IEmailView
{
	private MultilingualValidationForm<EmailMessage> form;
	
	private TextField senderField;
	
	private TextField titleField;
	
	private RichTextArea bodyField;
	
	private static List<String> propertyIds = createPropertyIds();
	
	public EmailDialog()
	{
		setHeight("580px");
		setWidth("640px");
		
		VerticalLayout layout = new VerticalLayout();
		
		layout.setSizeFull();
		layout.setSpacing(true);
		
		UserLanguageComboBox languagesComboBox = new UserLanguageComboBox();
		
		layout.addComponent(languagesComboBox);
		layout.setExpandRatio(languagesComboBox, 0.0f);
		
		form = new MultilingualValidationForm<EmailMessage>(EmailMessage.class);
		
		form.setSizeFull();
		form.setImmediate(true);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("sender"))
				{
					senderField = new TextField();
					
					senderField.setCaption(getLocalizedString("SENDER_CAPTION"));
					
					senderField.setWidth("100%");
					senderField.setNullRepresentation("");
					senderField.setNullSettingAllowed(true);
					
					return senderField;
				}
				else if (propertyId.equals("title"))
				{
					titleField = new TextField();
					
					titleField.setCaption(getLocalizedString("TITLE_CAPTION"));
					titleField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
					
					titleField.setWidth("100%");
					titleField.setNullRepresentation("");
					titleField.setNullSettingAllowed(true);
					
					return titleField;
				}
				else if (propertyId.equals("body"))
				{
					bodyField = new RichTextArea();
					
					bodyField.setCaption(getLocalizedString("BODY_CAPTION"));
					bodyField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
					
					bodyField.setWidth("100%");
					bodyField.setHeight("260px");
					bodyField.setNullRepresentation("");
					bodyField.setNullSettingAllowed(true);
					
					return bodyField;
				}
				
				return null;
			}
		});
		
		layout.addComponent(form);
		layout.setExpandRatio(form, 1.0f);
		
		setLayout(layout);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onApply(event);
			}
		});
	}

	protected void onApply(CommandExecutionVote event)
	{
		if (!form.isValid())
		{
			event.markAsFailed();
			
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		form.commit();
	}

	private static List<String> createPropertyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("title");
		list.add("sender");
		list.add("body");
		
		return list;
	}

	@Override
	public void dataBind()
	{
		StudentsManager manager = getContext().getSession().getStudentsManager();
		
		if (manager == null || !manager.isWriteable())
		{
			throw new CoopUIAccessDeniedException(getContext());
		}
		
		DataItem<EmailMessage> emailMessageItem = new DataItem<EmailMessage>(getModel().getMessage(), manager);
		
		form.setItemDataSource(emailMessageItem, propertyIds);

		// Remove the validators of multilingual fields now.
		// Allow them to have any length.
		
		removeMultilingualValidator(titleField);
		removeMultilingualValidator(bodyField);
	}
	
	private void removeMultilingualValidator(Field field)
	{
		if (field != null)
		{
			Validator validatorToBeRemoved = null;
			
			for (Validator validator : field.getValidators())
			{
				if (validator instanceof MultilingualLiteralsValidator)
				{
					validatorToBeRemoved = validator;
					break;
				}
			}
			
			if (validatorToBeRemoved != null) field.removeValidator(validatorToBeRemoved);
		}
	}

	@Override
	protected Presenter<EmailModel, ICoopContext, ? extends IView<EmailModel, ICoopContext>> supplyPresenter()
	{
		return new EmailPresenter(this);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));
		form.setDescription(getLocalizedString("FORM_DESCRIPTION"));

		if (senderField != null)
		{
			senderField.setCaption(getLocalizedString("SENDER_CAPTION"));
		}

		if (titleField != null)
		{
			titleField.setCaption(getLocalizedString("TITLE_CAPTION"));
			titleField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
		}
		
		if (bodyField != null)
		{
			bodyField.setCaption(getLocalizedString("BODY_CAPTION"));
			bodyField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
		}

	}

	@Override
	public boolean isDataValid()
	{
		return form.isValid();
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		getOkCancelView().setModel(OkCancelViewModel.ApplyOrSelect);
		
		// Disable the ENTER and CANCEL buttons from closing the dialog. 
		getOkCancelView().setShortcutsEnabled(false);
		
		getOkCancelView().dataBind();
	}

}
