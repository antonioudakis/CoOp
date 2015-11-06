package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.forms.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.ui.*;
import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public class PaymentDialog
extends CoopDialog<BeanItem<Payment>>
{
	private static final long serialVersionUID = 1L;
	
	private static List<String> propertyIds = createPropertyIds();
	
	private PaymentForm form = new PaymentForm();
	
	private ComboBox paymentTypeComboBox = new EnumComboBox(PaymentType.class); 
	private DateField paymentDateField = new DateField();
	private TextArea commentsTextArea = new TextArea();
	private TextField amountTextField = new TextField();
	private DateField startDateField = new DateField();
	private DateField endDateField = new DateField();
	private ComboBox stateComboBox = new EnumComboBox(PaymentState.class);
	private FinancialSourcePickerField financialSourcesPickerField;
	private JobPartPickerField jobPartPickerField;
	
	public PaymentDialog(Registration registration)
	{
		if (registration == null) 
			throw new IllegalArgumentException("Argument 'registration' must not be null.");
		
		if (registration.getGroup() == null)
			throw new IllegalArgumentException("Argument 'registration' does not belong to a group.");
		
		if (registration.getGroup().getJob() == null)
			throw new IllegalArgumentException("Argument 'registration' is not assigned to a job.");
		
		financialSourcesPickerField = new FinancialSourcePickerField(registration.getCoop());
		
		jobPartPickerField = new JobPartPickerField(registration.getGroup().getJob());
		
		jobPartPickerField.addListener(new Property.ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				onJobPartChange();
			}
		});
		
		jobPartPickerField.setParentAdjuster(new PickerField.ParentAdjuster<JobPart>()
		{

			@Override
			public void addToParent(JobPart element)
			{
				if (getModel() != null) element.getPayments().add(getModel().getBean());
			}

			@Override
			public void removeFromParent(JobPart element)
			{
				if (getModel() != null) element.getPayments().remove(getModel().getBean());
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
		
		setHeight("540px");
	}

	protected void onJobPartChange()
	{
		JobPart jobPart = (JobPart)jobPartPickerField.getValue();
		
		if (jobPart != null)
		{
			startDateField.setValue(jobPart.getStartDate());
			endDateField.setValue(jobPart.getEndDate());
		}
		else
		{
			startDateField.setValue(null);
			endDateField.setValue(null);
		}
	}

	protected void onAccept(CommandExecutionVote vote)
	{
		if (!form.isValid())
		{
			getContext().showInvalidDataNotification();
			
			vote.markAsFailed();
			
			return;
		}
		
		form.commit();
	}

	@Override
	public void dataBind()
	{
		form.setItemDataSource(getModel(), propertyIds);
	}

	private static List<String> createPropertyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("jobPart");
		list.add("type");
		list.add("source");
		list.add("amount");
		list.add("paymentDate");
		list.add("state");
		list.add("comment");
		list.add("startDate");
		list.add("endDate");
		
		return list;
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
		
		form.setWidth("100%");
		form.setImmediate(true);
		form.setWriteThrough(false);
		
		addComponent(form);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("type"))
				{
					paymentTypeComboBox.setWidth("100%");
					paymentTypeComboBox.setCaption(getLocalizedString("TYPE_CAPTION"));
					paymentTypeComboBox.setNullSelectionAllowed(false);
					
					return paymentTypeComboBox;
				}
				else if (propertyId.equals("jobPart"))
				{
					jobPartPickerField.setWidth("100%");
					jobPartPickerField.setCaption(getLocalizedString("JOB_PART_CAPTION"));
					jobPartPickerField.setClearAllowed(false);
					
					return jobPartPickerField;
				}
				else if (propertyId.equals("amount"))
				{
					amountTextField.setWidth("100%");
					amountTextField.setCaption(getLocalizedString("AMOUNT_CAPTION"));
					amountTextField.setNullSettingAllowed(true);
					amountTextField.setNullRepresentation("");
					
					return amountTextField;
				}
				else if (propertyId.equals("paymentDate"))
				{
					paymentDateField.setWidth("100%");
					paymentDateField.setCaption(getLocalizedString("PAYMENT_DATE_CAPTION"));
					paymentDateField.setResolution(DateField.RESOLUTION_MIN);
					
					return paymentDateField;
				}
				else if (propertyId.equals("state"))
				{
					stateComboBox.setWidth("100%");
					stateComboBox.setCaption(getLocalizedString("STATE_CAPTION"));
					stateComboBox.setNullSelectionAllowed(false);
					
					return stateComboBox;
				}
				else if (propertyId.equals("comment"))
				{
					commentsTextArea.setWidth("100%");
					commentsTextArea.setCaption(getLocalizedString("COMMENTS_CAPTION"));
					commentsTextArea.setNullRepresentation("");
					commentsTextArea.setNullSettingAllowed(true);
				}
				else if (propertyId.equals("startDate"))
				{
					startDateField.setWidth("100%");
					startDateField.setCaption(getLocalizedString("START_DATE_CAPTION"));
					startDateField.setResolution(DateField.RESOLUTION_DAY);
					
					return startDateField;
				}
				else if (propertyId.equals("endDate"))
				{
					endDateField.setWidth("100%");
					endDateField.setCaption(getLocalizedString("END_DATE_CAPTION"));
					endDateField.setResolution(DateField.RESOLUTION_DAY);
					
					return endDateField;
				}
				else if (propertyId.equals("source"))
				{
					financialSourcesPickerField.setWidth("100%");
					financialSourcesPickerField.setCaption(getLocalizedString("SOURCE_CAPTION"));
					
					return financialSourcesPickerField;
				}
				
				return null;
			}
		});
		
		financialSourcesPickerField.setClearAllowed(false);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));
		
		paymentTypeComboBox.setCaption(getLocalizedString("TYPE_CAPTION"));
		paymentDateField.setCaption(getLocalizedString("PAYMENT_DATE_CAPTION"));
		amountTextField.setCaption(getLocalizedString("AMOUNT_CAPTION"));
		stateComboBox.setCaption(getLocalizedString("STATE_CAPTION"));
		commentsTextArea.setCaption(getLocalizedString("COMMENTS_CAPTION"));
		startDateField.setCaption(getLocalizedString("START_DATE_CAPTION"));
		endDateField.setCaption(getLocalizedString("END_DATE_CAPTION"));
		financialSourcesPickerField.setCaption(getLocalizedString("SOURCE_CAPTION"));
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		form.setReadOnly(readOnly);
	}

}
