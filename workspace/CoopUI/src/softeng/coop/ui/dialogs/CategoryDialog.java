package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

public class CategoryDialog
extends CoopDialog<BeanItem<Category>>
{
	private static final long serialVersionUID = 1L;
	
	private static class CategoryForm
	extends MultilingualValidationForm<Category>
	{
		private static final long serialVersionUID = 1L;

		public CategoryForm()
		{
			super(Category.class);
		}
	}

	private static List<String> propertyIds =
		initializePropertyIds();
	
	private CategoryForm form;

	private TextField nameTextField;
	
	private UserLanguageComboBox languagesComboBox;
	
	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), propertyIds);
	}

	private static List<String> initializePropertyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("name");
		
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

		languagesComboBox = new UserLanguageComboBox();
		addComponent(languagesComboBox);
		
		form = new CategoryForm();

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
					nameTextField = new TextField();
					nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
					
					if (!isReadOnly())
						nameTextField.setDescription(getContext().getLocalizedString("DIALOG_FIELD_IS_MULTILINGUAL_DESCRIPTION"));
					
					nameTextField.setWidth("100%");
					
					return nameTextField;
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
		
		getOkCancelView().setModel(OkCancelViewModel.ApplyOrSelect);
				
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
