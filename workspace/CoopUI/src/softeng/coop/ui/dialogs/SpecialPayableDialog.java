package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.ui.vaadin.mvp.IListener;

import com.vaadin.data.Item;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class SpecialPayableDialog<M>
extends CoopDialog<BeanItem<M>>
{
	private FinancialSourcePickerField financialSourcePickerField;
	
	private TextField paidDaysTextField;
	
	private MultilingualValidationForm<M> form;
	
	private static List<String> propertyIds = createPropertyIds();
	
	public SpecialPayableDialog(final CoOp coop, Class<M> payableType)
	{
		if (coop == null) 
			throw new IllegalArgumentException("Argument 'coop' must not be null.");
		
		if (payableType == null) 
			throw new IllegalArgumentException("Argument 'payableType' must not be null.");
		
		setWidth("500px");
		setHeight("280px");
		
		form = new MultilingualValidationForm<M>(payableType);
		form.setWidth("100%");
		form.setImmediate(true);
		form.setWriteThrough(false);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("paidDays"))
				{
					paidDaysTextField = new TextField();
					paidDaysTextField.setCaption(getLocalizedString("PAID_DAYS_CAPTION"));
					paidDaysTextField.setWidth("100%");
					paidDaysTextField.setNullRepresentation("");
					paidDaysTextField.setNullSettingAllowed(true);
					
					return paidDaysTextField;
				}
				else if (propertyId.equals("financialSource"))
				{
					financialSourcePickerField = new FinancialSourcePickerField(coop);
					financialSourcePickerField.setCaption(getLocalizedString("FINANCIAL_SOURCE_CAPTION"));
					financialSourcePickerField.setWidth("100%");
					financialSourcePickerField.setClearAllowed(false);
					
					return financialSourcePickerField;
				}
				
				return null;
			}
		});
		
		addComponent(form);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept(event);
			}
		});
		
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
		List<String> list = new ArrayList<String>();
		
		list.add("financialSource");
		list.add("paidDays");
		
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
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));
		
		if (paidDaysTextField != null)
		{
			paidDaysTextField.setCaption(getLocalizedString("PAID_DAYS_CAPTION"));
		}
		
		if (financialSourcePickerField != null)
		{
			financialSourcePickerField.setCaption(getLocalizedString("FINANCIAL_SOURCE_CAPTION"));
		}
	}

	@Override
	public boolean isDataValid()
	{
		return form.isValid();
	}

	@Override
	protected String getResourceBaseName()
	{
		return SpecialPayableDialog.class.getCanonicalName();
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		form.setReadOnly(readOnly);
	}

}
