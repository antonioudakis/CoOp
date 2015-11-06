package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.Item;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.ui.vaadin.mvp.IListener;

@SuppressWarnings("serial")
public class TelephoneDialog
extends CoopDialog<BeanItem<Telephone>>
{
	private static class TelephoneForm
	extends MultilingualValidationForm<Telephone>
	{
		public TelephoneForm()
		{
			super(Telephone.class);
		}
	}
	
	private TelephoneForm form = new TelephoneForm();
	
	private ComboBox typeComboBox = new EnumComboBox(TelephoneType.class);
	
	private TextField numberTextField = new TextField();
	
	private TextArea commentTextField = new TextArea();
	
	private static List<String> propertyIds = createPropertyIds();
	
	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), propertyIds);
	}
	
	public TelephoneDialog()
	{
		form.setWidth("100%");
		form.setImmediate(true);
		form.setWriteThrough(false);
		
		typeComboBox.setNullSelectionAllowed(false);
		typeComboBox.setWidth("100%");
		
		numberTextField.setWidth("100%");
		numberTextField.setNullRepresentation("");
		numberTextField.setNullSettingAllowed(true);
		
		commentTextField.setWidth("100%");
		commentTextField.setNullRepresentation("");
		commentTextField.setNullSettingAllowed(true);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept(event);
			}
		});

		addComponent(form);
	}

	protected void onAccept(CommandExecutionVote event)
	{
		if (!isDataValid())
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
		
		list.add("type");
		list.add("number");
		list.add("comment");
		
		return list;
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));
		
		typeComboBox.setCaption(getLocalizedString("TYPE_CAPTION"));
		numberTextField.setCaption(getLocalizedString("NUMBER_CAPTION"));
		commentTextField.setCaption(getLocalizedString("COMMENT_CAPTION"));
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();

		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("type"))
				{
					return typeComboBox;
				}
				else if (propertyId.equals("number"))
				{
					return numberTextField;
				}
				else if (propertyId.equals("comment"))
				{
					return commentTextField;
				}
				
				return null;
			}
		});
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
