package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

public class RequirementDialog
extends CoopDialog<BeanItem<Requirement>>
{
	private static final long serialVersionUID = 1L;
	
	private static class RequirementForm
	extends MultilingualValidationForm<Requirement>
	{
		private static final long serialVersionUID = 1L;

		public RequirementForm()
		{
			super(Requirement.class);
		}
	}
	
	private RequirementForm form = new RequirementForm();
	
	private TextField nameTextField = new TextField();
	
	private ComboBox typeComboBox = new EnumComboBox(RequirementType.class);
	
	private UserLanguageComboBox languagesComboBox = new UserLanguageComboBox();
	
	private static List<String> propertyIds =
		initializePropertyIds();

	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), propertyIds);
	}

	private static List<String> initializePropertyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("name");
		list.add("type");
		
		return list;
	}

	@SuppressWarnings("serial")
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		VerticalLayout layout = new VerticalLayout();
		
		layout.setWidth("100%");
		layout.setSpacing(true);
		
		setLayout(layout);
		
		getOkCancelView().setModel(OkCancelViewModel.ApplyOrSelect);
		
		addComponent(languagesComboBox);
		
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
					if (!isReadOnly())
						nameTextField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
					nameTextField.setWidth("100%");
					
					return nameTextField;
				}
				else if (propertyId.equals("type"))
				{
					typeComboBox.setCaption(getLocalizedString("TYPE_CAPTION"));
					typeComboBox.setWidth("100%");
					typeComboBox.setNullSelectionAllowed(false);
					typeComboBox.setTextInputAllowed(false);
					
					return typeComboBox;
				}
				
				return null;
			}
		});
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				if (!isDataValid())
				{
					event.markAsFailed();
					getContext().showInvalidDataNotification();
					return;
				}
				
				form.commit();
			}
		});
		
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));
		
		if (nameTextField != null)
		{
			nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
			if (!isReadOnly())
				nameTextField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
			else
				nameTextField.setDescription(null);
		}
		
		if (typeComboBox != null)
			typeComboBox.setCaption(getLocalizedString("TYPE_CAPTION"));
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
