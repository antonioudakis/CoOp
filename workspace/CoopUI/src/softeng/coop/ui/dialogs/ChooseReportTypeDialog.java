package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.*;
import com.vaadin.data.validator.*;
import com.vaadin.ui.*;

import softeng.coop.business.*;
import softeng.coop.business.reporting.IParameterType;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class ChooseReportTypeDialog
extends CoopDialog<ReportTypeParameters>
implements IChooseReportTypeView
{
	private static final long serialVersionUID = 1L;
	
	private ComboBox reportTypesComboBox = new ComboBox();
	
	private Form parametersForm = new Form();
	
	private BeanItemContainer<ReportType> reportTypesContainer;
	
	private PropertysetItem parametersItem;
	
	private Panel parametersPanel = new Panel();
	
	private EventSubscription<ModelEvent<ReportType>, IListener<ModelEvent<ReportType>>> selectedReportTypeChangedSubscription =
		new EventSubscription<ModelEvent<ReportType>, IListener<ModelEvent<ReportType>>>();
	
	private List<IParameterType> parameterTypes;
	
	private Map<String, IParameterType> parameterTypesMap = new HashMap<String, IParameterType>();
	
	private ReportScope reportScope;
	
	private CoOp coop;
	
	public ChooseReportTypeDialog(ReportScope reportScope)
	{
		if (reportScope == null) 
			throw new IllegalArgumentException("Argument 'reportScope' must not be null.");
		
		this.reportScope = reportScope;
	}
	
	public ChooseReportTypeDialog(ReportScope reportScope, CoOp coop)
	{
		this(reportScope);
		
		this.coop = coop;
	}

	@Override
	public void dataBind()
	{
		if (getModel() != null)
		{
			reportTypesComboBox.setValue(getModel().getReportType());
		}
	}

	@SuppressWarnings("serial")
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setWidth("670px");
		setHeight("420px");

		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote vote)
			{
				onExecute(vote);
			}
		});
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		
		setContent(layout);
		
		reportTypesComboBox.setWidth("100%");

		reportTypesComboBox.setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_PROPERTY);
		reportTypesComboBox.setItemCaptionPropertyId("name");
		reportTypesComboBox.setNullSelectionAllowed(false);
		reportTypesComboBox.setTextInputAllowed(false);
		reportTypesComboBox.setImmediate(true);
		
		reportTypesComboBox.addListener(new Property.ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				onSelectedReportTypeChanged((ReportType)reportTypesComboBox.getValue());
			}
		});
		
		layout.addComponent(reportTypesComboBox);
		
		parametersPanel.setSizeFull();
		parametersPanel.setVisible(false);
		
		parametersForm.setWidth("100%");
		parametersForm.setImmediate(true);
		parametersForm.setWriteThrough(false);
		
		parametersPanel.addComponent(parametersForm);
		
		layout.addComponent(parametersPanel);
		layout.setExpandRatio(parametersPanel, 1.0f);
		
		parametersForm.setFormFieldFactory(new DefaultFieldFactory()
		{

			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				Field field = null;
				
				IParameterType parameterType = parameterTypesMap.get(propertyId);
				
				if (parameterType != null)
				{
					switch (parameterType.getDataType())
					{
						case Location:
							{
								LocationPickerField locationPickerField = new LocationPickerField();
								locationPickerField.setClearAllowed(!parameterType.isRequired());
								field = locationPickerField;
							}
							break;
							
						case Category:
							{
								CategoryPickerField categoryPickerField = new CategoryPickerField();
								categoryPickerField.setClearAllowed(!parameterType.isRequired());
								field = categoryPickerField;
							}
							break;
							
						case CoopFinancialSource:
							{
								FinancialSourcePickerField financialSourcePickerField = 
									new FinancialSourcePickerField(getContext().getSelectedCoop());
								financialSourcePickerField.setClearAllowed(!parameterType.isRequired());
								field = financialSourcePickerField;
							}
							break;
							
						case JobSiteType:
							{
								ComboBox comboBox = new EnumComboBox(JobSiteType.class);
								comboBox.setNullSelectionAllowed(!parameterType.isRequired());
								field = comboBox;
							}
							break;
							
						case PaymentType:
							{
								ComboBox comboBox = new EnumComboBox(PaymentType.class);
								comboBox.setNullSelectionAllowed(!parameterType.isRequired());
								field = comboBox;
							}
							break;
							
						case Gender:
							{
								ComboBox comboBox = new EnumComboBox(Gender.class);
								comboBox.setNullSelectionAllowed(!parameterType.isRequired());
								field = comboBox;
							}
							break;
							
						case Division:
							{
								CoOp selectedCoop = getContext().getSelectedCoop();
								if (selectedCoop != null)
								{
									DivisionPickerField pickerField = 
										new DivisionPickerField(selectedCoop.getLesson().getDepartment());
									
									pickerField.setClearAllowed(!parameterType.isRequired());
									
									field = pickerField;
								}
								else
								{
									return null;
								}
							}
							break;
					}
				}
				
				if (field == null)
					field = super.createField(item, propertyId, uiContext);
				
				field.setWidth("100%");
				
				if (field instanceof TextField)
				{
					TextField textField = (TextField)field;
					textField.setNullRepresentation("");
					textField.setNullSettingAllowed(true);
				}
				
				if (parameterType != null)
				{
					Locale locale = getLocale();
					
					field.setCaption(parameterType.getName(locale));
					field.setDescription(parameterType.getDescription(getLocale()));
					
					switch (parameterType.getDataType())
					{
						case Int:
							field.addValidator(new IntegerValidator(getLocalizedString("NOT_AN_INTEGER_MESSAGE")));
							break;
							
						case Double:
							field.addValidator(new DoubleValidator(getLocalizedString("NOT_A_DOUBLE_MESSAGE")));
							break;
					}
				}
				
				field.setRequired(parameterType.isRequired());
				
				return field;
			}
		});
		
	}

	protected void onExecute(CommandExecutionVote vote)
	{
		ReportType reportType = getSelectedReportType();
		
		if (reportType == null)
		{
			getContext().showInvalidDataNotification();
			vote.markAsFailed();
			
			return;
		}
		
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		
		if (parametersItem != null)
		{
			if (!parametersForm.isValid())
			{
				getContext().showInvalidDataNotification();
				vote.markAsFailed();
				
				return;
			}
			
			parametersForm.commit();
			
			for (Object propertyId : parametersItem.getItemPropertyIds())
			{
				String propertyName = (String)propertyId;
				
				Object value = parametersItem.getItemProperty(propertyId).getValue();
				
				parametersMap.put(propertyName, value);
			}
			
		}
		
		CoOp selectedCoop;
		
		if (coop == null)
			selectedCoop = getContext().getSelectedCoop();
		else
			selectedCoop = coop;
		
		parametersMap.put("_CURRENT_COOP", selectedCoop);
		parametersMap.put("_CURRENT_USER", getContext().getSession().getAuthenticatedUser());
		
		if (selectedCoop != null)
		{
			parametersMap.put("_CURRENT_DEPARTMENT", selectedCoop.getLesson().getDepartment());
		}
		
		ReportTypeParameters reportTypeParameters = 
			new ReportTypeParameters(reportType, parametersMap);

		setModel(reportTypeParameters);
	}

	protected void onSelectedReportTypeChanged(ReportType value)
	{
		ManagerBase baseManager = getContext().getSession().getBaseManager();
		
		String reportComments = baseManager.getLiteral(value.getComments());
		
		Notification notification = new Notification(reportComments, null, Notification.TYPE_TRAY_NOTIFICATION);
		notification.setPosition(Notification.POSITION_CENTERED_BOTTOM);
		notification.setHtmlContentAllowed(false);
		
		getWindow().showNotification(notification);
		
		selectedReportTypeChangedSubscription.fire(new ModelEvent<ReportType>(value));
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));

		reportTypesComboBox.setCaption(
				getLocalizedString("AVAILABLE_REPORT_TYPES_CAPTION"));
		
		DataUtilities.updateMultilingual(reportTypesComboBox);
	}

	@Override
	public BeanItemContainer<ReportType> getReportTypesContainer()
	{
		return reportTypesContainer;
	}

	@Override
	public void setReportTypesContainer(BeanItemContainer<ReportType> reportTypesContainer)
	{
		reportTypesContainer.sort(new Object[] { reportTypesComboBox.getItemCaptionPropertyId() }, new boolean[] { true });
		
		this.reportTypesContainer = reportTypesContainer;
		
		this.reportTypesComboBox.setContainerDataSource(reportTypesContainer);
	}

	@Override
	protected Presenter<ReportTypeParameters, ICoopContext, ? extends IView<ReportTypeParameters, ICoopContext>> supplyPresenter()
	{
		return new ChooseReportTypePresenter(this);
	}

	@Override
	public void addSelectedReportTypeChangedListener(IListener<ModelEvent<ReportType>> listener)
	{
		selectedReportTypeChangedSubscription.add(listener);
	}

	@Override
	public void removeSelectedReportTypeChangedListener(IListener<ModelEvent<ReportType>> listener)
	{
		selectedReportTypeChangedSubscription.remove(listener);
	}

	@Override
	public ReportType getSelectedReportType()
	{
		return (ReportType)reportTypesComboBox.getValue();
	}

	@Override
	public List<IParameterType> getParameterTypes()
	{
		return parameterTypes;
	}

	@Override
	public void setParameterTypes(List<IParameterType> parameterTypes)
	{
		this.parameterTypes = parameterTypes;
		
		parameterTypesMap.clear();
		
		if (parameterTypes != null)
		{
			parametersItem = new PropertysetItem();
			
			for (IParameterType parameterType : parameterTypes)
			{
				Class<?> type;
				
				Object defaultValue = parameterType.getDefaultValue(getContext().getLocale());
				
				switch (parameterType.getDataType())
				{
					case Boolean:
						type = Boolean.class;
						if (defaultValue == null) defaultValue = false;
						break;
						
					case Category:
						type = Category.class;
						break;
						
					case Date:
						type = Date.class;
						break;
						
					case Int:
						type = Integer.class;
						break;
						
					case Location:
						type = Location.class;
						break;
						
					case CoopFinancialSource:
						type = FinancialSource.class;
						break;
						
					case JobSiteType:
						type = JobSiteType.class;
						break;
						
					case PaymentType:
						type = PaymentType.class;
						break;
						
					case Gender:
						type = Gender.class;
						break;
						
					case Division:
						type = Division.class;
						break;
						
					default:
						type = String.class;
						break;
				}
				
				if (defaultValue != null)
				{
					if (!type.isAssignableFrom(defaultValue.getClass())) defaultValue = null;
				}
				
				@SuppressWarnings({ "rawtypes", "unchecked" })
				ObjectProperty property = new ObjectProperty(defaultValue, type);
				
				parametersItem.addItemProperty(parameterType.getKey(), property);
				
				parameterTypesMap.put(parameterType.getKey(), parameterType);
			}
			
			parametersPanel.setVisible(true);
		}
		else
		{
			parametersItem = null;
			
			parametersPanel.setVisible(false);
		}
		
		parametersForm.setItemDataSource(parametersItem);
	}

	@Override
	public PropertysetItem getParameters()
	{
		return parametersItem;
	}

	@Override
	public ReportScope getReportScope()
	{
		return reportScope;
	}
}
