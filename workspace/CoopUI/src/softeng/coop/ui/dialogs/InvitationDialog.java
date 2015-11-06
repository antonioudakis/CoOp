package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.forms.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.coop.ui.composites.*; 
import softeng.ui.vaadin.mvp.IListener;

import com.vaadin.ui.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public class InvitationDialog
extends CoopDialog<BeanItem<Invitation>>
{
	private InvitationForm form;
	
	private InvitationMembersTableComponent recepientsTableComponent;
	
	private TextArea textField;
	
	private static List<String> propertyIds = createPropetyIds();
	
	public InvitationDialog()
	{
		setHeight("480px");
		
		VerticalLayout layout = new VerticalLayout();
		
		layout.setWidth("100%");;
		layout.setSpacing(true);
		
		UserLanguageComboBox languagesComboBox = new UserLanguageComboBox();
		
		layout.addComponent(languagesComboBox);
		
		form = new InvitationForm();
		
		form.setWidth("100%");
		form.setImmediate(true);
		form.setWriteThrough(false);
		
		layout.addComponent(form);
		
		setLayout(layout);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("text"))
				{
					textField = new TextArea();
					textField.setCaption(getLocalizedString("TEXT_CAPTION"));
					textField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
					textField.setWidth("100%");
					textField.setNullRepresentation("");
					textField.setNullSettingAllowed(true);
					
					return textField;
				}
				else if (propertyId.equals("recepients"))
				{
					recepientsTableComponent = new InvitationMembersTableComponent();
					recepientsTableComponent.setCaption(getLocalizedString("RECEPIENTS_CAPTION"));
					recepientsTableComponent.setWidth("100%");
					recepientsTableComponent.setParentModel(getModel().getBean());
					
					return recepientsTableComponent;
				}
				
				return null;
			}
		});
		
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
			getContext().showInvalidDataNotification();
			
			event.markAsFailed();
			
			return;
		}
		
		form.commit();
	}

	private static List<String> createPropetyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("text");
		list.add("recepients");
		
		return list;
	}

	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), propertyIds);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		if (textField != null)
		{
			textField.setCaption(getLocalizedString("TEXT_CAPTION"));
			textField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
		}
		
		if (recepientsTableComponent != null)
		{
			recepientsTableComponent.setCaption(getLocalizedString("RECEPIENTS_CAPTION"));
		}
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		getOkCancelView().setModel(OkCancelViewModel.ApplyOrSelect);
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		form.setReadOnly(readOnly);
	}
	
	@Override
	public boolean isDataValid()
	{
		return form.isValid();
	}
}
