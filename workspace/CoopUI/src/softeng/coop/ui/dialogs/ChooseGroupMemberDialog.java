package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.ui.*;
import com.vaadin.data.Item;
import com.vaadin.data.util.*;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.data.MultilingualValidationForm;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.coop.ui.presenters.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class ChooseGroupMemberDialog 
extends CoopDialog<BeanItem<Registration>> 
implements IChooseGroupMemberDialogView
{
	private static class FilterOptionsForm
	extends MultilingualValidationForm<FilterOptions>
	{
		public FilterOptionsForm()
		{
			super(FilterOptions.class);
		}
	}
	
	private GroupMembersTableComponent tableComponent;
	
	private FilterOptionsForm filterOptionsForm;
	
	private Collection<Registration> excludedRegistrations;
	
	private BeanItem<FilterOptions> filterOptionsItem;
	
	private TextField surnamePrefixTextField;
	
	private LocationPickerField locationPickerField;
	
	private CategoryPickerField categoryPickerField;
	
	private CheckBox notAssignedCheckBox;
	
	private static List<String> filterPropertyIds = createFilterPropetyIds();
	
	public ChooseGroupMemberDialog() 
	{
		this.setWidth("650px");
		this.setHeight("530px");
		
		this.filterOptionsItem = new BeanItem<FilterOptions>(new FilterOptions());

		VerticalLayout layout = new VerticalLayout();
		
		layout.setSizeFull();
		layout.setSpacing(true);
		
		tableComponent = new GroupMembersTableComponent();
		tableComponent.setSizeFull();
		layout.addComponent(tableComponent);
		layout.setExpandRatio(tableComponent, 1.0f);
		
		tableComponent.setAddVisible(false);
		tableComponent.setEditVisible(false);
		tableComponent.setDeleteVisible(false);
		
		filterOptionsForm = new FilterOptionsForm();
		filterOptionsForm.setWidth("100%");
		filterOptionsForm.setImmediate(true);
		filterOptionsForm.setWriteThrough(true);
		layout.addComponent(filterOptionsForm);
		layout.setExpandRatio(filterOptionsForm, 0.0f);
		
		getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>() 
		{
			@Override
			public void onEvent(
					ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event) 
			{
				setModel(tableComponent.getSelectedItem());
			}
		});
		
		filterOptionsForm.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("surnamePrefix"))
				{
					surnamePrefixTextField = new TextField();
					surnamePrefixTextField.setCaption(getLocalizedString("SURNAME_PREFIX_CAPTION"));
					surnamePrefixTextField.setDescription(getLocalizedString("SURNAME_PREFIX_DESCRIPTION"));
					surnamePrefixTextField.setWidth("100%");
					surnamePrefixTextField.setNullRepresentation("");
					surnamePrefixTextField.setNullSettingAllowed(true);
					
					surnamePrefixTextField.addListener(new FieldEvents.TextChangeListener()
					{
						@Override
						public void textChange(TextChangeEvent event)
						{
							surnamePrefixTextField.setValue(event.getText());
						}
					});
					
					return surnamePrefixTextField;
				}
				else if (propertyId.equals("preferredLocation"))
				{
					CoOp coop = getContext().getSelectedCoop();
					
					if (coop.isAllowLocationPreferences())
					{
						locationPickerField = new LocationPickerField();
						locationPickerField.setCaption(getLocalizedString("PREFERRED_LOCATION_CAPTION"));
						locationPickerField.setDescription(getLocalizedString("PREFERRED_LOCATION_DESCRIPTION"));
						locationPickerField.setWidth("100%");
						
						return locationPickerField;
					}
				}
				else if (propertyId.equals("preferredCategory"))
				{
					CoOp coop = getContext().getSelectedCoop();

					if (coop.isAllowCategoryPreferences())
					{
						categoryPickerField = new CategoryPickerField();
						categoryPickerField.setCaption(getLocalizedString("PREFERRED_CATEGORY_CAPTION"));
						categoryPickerField.setDescription(getLocalizedString("PREFERRED_CATEGORY_DESCRIPTION"));
						categoryPickerField.setWidth("100%");
						
						return categoryPickerField;
					}
				}
				else if (propertyId.equals("notAssignedToJob") && !isUserStudent())
				{
					notAssignedCheckBox = new CheckBox();
					notAssignedCheckBox.setCaption(getLocalizedString("NOT_ASSIGNED_CAPTION"));
					notAssignedCheckBox.setDescription(getLocalizedString("NOT_ASSIGNED_DSCRTIPTION"));
					notAssignedCheckBox.setWidth("100%");
					
					return notAssignedCheckBox;
				}
				
				return null;
			}
		});
		
		setLayout(layout);
	}

	private static List<String> createFilterPropetyIds()
	{
		ArrayList<String> list = new ArrayList<String>(10);
		
		list.add("surnamePrefix");
		list.add("preferredLocation");
		list.add("preferredCategory");
		list.add("notAssignedToJob");
		
		return list;
	}

	@Override
	public void dataBind() 
	{
		if (getModel() != null)
			tableComponent.setSelectedValue(getModel().getBean());
		else
			tableComponent.setSelectedValue(null);
	}

	@Override
	protected void setupUI() 
	{
		super.setupUI();
		
		filterOptionsForm.setItemDataSource(filterOptionsItem, filterPropertyIds);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		tableComponent.setCaption(getLocalizedString("TABLE_CAPTION"));
		
		filterOptionsForm.setCaption(getLocalizedString("FILTER_OPTIONS_FORM_CAPTION"));
		
		if (surnamePrefixTextField != null)
		{
			surnamePrefixTextField.setCaption(getLocalizedString("SURNAME_PREFIX_CAPTION"));
			surnamePrefixTextField.setDescription(getLocalizedString("SURNAME_PREFIX_DESCRIPTION"));
		}
		
		if (locationPickerField != null)
		{
			locationPickerField.setCaption(getLocalizedString("PREFERRED_LOCATION_CAPTION"));
			locationPickerField.setDescription(getLocalizedString("PREFERRED_LOCATION_DESCRIPTION"));
		}
		
		if (categoryPickerField != null)
		{
			categoryPickerField.setCaption(getLocalizedString("PREFERRED_CATEGORY_CAPTION"));
			categoryPickerField.setDescription(getLocalizedString("PREFERRED_CATEGORY_DESCRIPTION"));
		}
		
		if (notAssignedCheckBox != null)
		{
			notAssignedCheckBox.setCaption(getLocalizedString("NOT_ASSIGNED_CAPTION"));
			notAssignedCheckBox.setDescription(getLocalizedString("NOT_ASSIGNED_DSCRTIPTION"));
		}
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		tableComponent.setReadOnly(readOnly);
		tableComponent.setUserSelectable(!readOnly);
	}

	@Override
	public ITableView<Group, Registration> getTableView()
	{
		return tableComponent;
	}

	@Override
	protected Presenter<BeanItem<Registration>, ICoopContext, ? extends IView<BeanItem<Registration>, ICoopContext>> supplyPresenter()
	{
		return new ChooseGroupMemberPresenter(this);
	}

	@Override
	public BeanItem<FilterOptions> getFilterOptionsItem()
	{
		return filterOptionsItem;
	}

	@Override
	public Collection<Registration> getExcludedRegistrations()
	{
		return excludedRegistrations;
	}

	@Override
	public void setExcludedRegistrations(Collection<Registration> excludedRegistrations)
	{
		this.excludedRegistrations = excludedRegistrations;
	}
}
