package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.forms.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

public class InsuranceContractDialog
extends CoopDialog<BeanItem<InsuranceContract>>
{
	private static final long serialVersionUID = 1L;
	
	private InsuranceContractForm form = new InsuranceContractForm();

	private static List<String> propertyIds = createPropertyIds();
	
	private TextField nameTextField = new TextField();
	
	private InsuranceContractAttachmentsTableComponent attachmentsTableComponent =
		new InsuranceContractAttachmentsTableComponent();

	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), propertyIds);
	}

	private static List<String> createPropertyIds()
	{
		List<String> list = new ArrayList<String>();
		
		list.add("name");
		list.add("attachments");
		
		return list;
	}

	@SuppressWarnings("serial")
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept(event);
			}
		});
		
		nameTextField.setWidth("100%");
		nameTextField.setNullRepresentation("");
		nameTextField.setNullSettingAllowed(true);
		
		attachmentsTableComponent.setWidth("100%");
		
		form.setWidth("100%");
		form.setImmediate(true);
		form.setWriteThrough(false);
		
		addComponent(form);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("name"))
				{
					nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
					
					return nameTextField;
				}
				else if (propertyId.equals("attachments"))
				{
					attachmentsTableComponent.setParentModel(getModel().getBean());
					attachmentsTableComponent.setCaption(getLocalizedString("ATTACHMENTS_CAPTION"));
					
					return attachmentsTableComponent;
				}
				
				return null;
			}
		});
	}

	protected void onAccept(CommandExecutionVote event)
	{
		if (!isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			event.markAsFailed();
			
			return;
		}
		
		form.commit();
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));

		nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
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
