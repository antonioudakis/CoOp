package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;

import softeng.ui.vaadin.mvp.*;

public class ChooseProfessorDialog
	extends CoopDialog<BeanItem<Professor>>
	implements IChooseProfessorView
{
	private static class FilterOptionsForm 
		extends MultilingualValidationForm<FilterOptions>
	{
		private static final long serialVersionUID = 1L;
	
		public FilterOptionsForm()
		{
			super(FilterOptions.class);
		}
	}

	private static final long serialVersionUID = 1L;
	
	private Table professorsTable;
	
	private DataItemContainer<Professor> professorsContainer;
	
	private DataItemContainer<University> universitiesContainer;
	
	private DataItemContainer<Department> departmentsContainer;
	
	private DataItemContainer<Division> divisionsContainer;
	
	private DataItem<FilterOptions> filterOptionsItem;
	
	private ComboBox universitiesComboBox;
	
	private ComboBox departmentsComboBox;
	
	private ComboBox divisionsComboBox;
	
	private FilterOptionsForm filterOptionsForm;
	
	private String[] professorColumns = { "surname", "name" };
	
	public ChooseProfessorDialog()
	{
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				BeanItem<Professor> selectedItem =
					professorsContainer.getItem(professorsTable.getValue());
				
				setModel(selectedItem);
			}
		});
	}
	
	@Override
	public void dataBind()
	{
		if (getModel() != null)
		{
			professorsTable.setValue(getModel().getBean());

			professorsTable.setCurrentPageFirstItemId(getModel().getBean());
		}
		else
		{
			professorsTable.setValue(null);
		}
	}

	@SuppressWarnings("serial")
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setHeight("560px");
		
		Session session = getContext().getSession();
		
		if (session == null)
			throw new CoopUISystemException("Session should be available.");
		
		filterOptionsItem = 
			new DataItem<FilterOptions>(
					new FilterOptions(), 
					session.getBaseManager());
		
		VerticalLayout layout = new VerticalLayout();
		
		layout.setSizeFull();
		
		layout.setSpacing(true);
		
		this.setContent(layout);
		
		professorsTable = new Table();
		
		professorsTable.setSizeFull();
		professorsTable.setSelectable(true);
		layout.addComponent(professorsTable);
		layout.setExpandRatio(professorsTable, 1.0f);
		
		filterOptionsForm = new FilterOptionsForm();
		filterOptionsForm.setWidth("100%");
		filterOptionsForm.setImmediate(true);
		layout.addComponent(filterOptionsForm);
		layout.setExpandRatio(filterOptionsForm, 0.0f);
		
		filterOptionsForm.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("university"))
				{
					universitiesComboBox = new ComboBox(getLocalizedString("UNIVERSITY_CAPTION"));
					
					universitiesComboBox.setWidth("100%");
					universitiesComboBox.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
					universitiesComboBox.setItemCaptionPropertyId("name");
					universitiesComboBox.setTextInputAllowed(false);
					universitiesComboBox.setNullSelectionAllowed(true);
					
					return universitiesComboBox;
				}
				else if (propertyId.equals("department"))
				{
					departmentsComboBox = new ComboBox(getLocalizedString("DEPARTMENT_CAPTION"));
					
					departmentsComboBox.setWidth("100%");
					departmentsComboBox.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
					departmentsComboBox.setItemCaptionPropertyId("name");
					departmentsComboBox.setTextInputAllowed(false);
					departmentsComboBox.setNullSelectionAllowed(true);
					departmentsComboBox.setNullSelectionItemId(null);
					
					return departmentsComboBox;
				}
				else if (propertyId.equals("division"))
				{
					divisionsComboBox = new ComboBox(getLocalizedString("DIVISION_CAPTION"));
					
					divisionsComboBox.setWidth("100%");
					divisionsComboBox.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
					divisionsComboBox.setItemCaptionPropertyId("name");
					divisionsComboBox.setTextInputAllowed(false);
					divisionsComboBox.setNullSelectionAllowed(true);
					
					return divisionsComboBox;
				}
				
				return null;
			}
		});

		ArrayList<String> filterProperties = new ArrayList<String>();
		
		filterProperties.add("university");
		filterProperties.add("department");
		filterProperties.add("division");
		
		filterOptionsForm.setItemDataSource(filterOptionsItem, filterProperties);
		
	}
	
	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		this.setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		professorsTable.setCaption(getLocalizedString("PROFESSORS_CAPTION"));
		professorsTable.setColumnHeader("name", getLocalizedString("NAME_CAPTION"));
		professorsTable.setColumnHeader("surname", getLocalizedString("SURNAME_CAPTION"));
		
		filterOptionsForm.setCaption(getLocalizedString("FILTERS_CAPTION"));
		
		if (universitiesComboBox != null)
			universitiesComboBox.setCaption(getLocalizedString("UNIVERSITY_CAPTION"));
		
		if (departmentsComboBox != null)
			departmentsComboBox.setCaption(getLocalizedString("DEPARTMENT_CAPTION"));
		
		if (divisionsComboBox != null)
			divisionsComboBox.setCaption(getLocalizedString("DIVISION_CAPTION"));
	}

	@Override
	public DataItem<FilterOptions> getFilterOptionsItem()
	{
		return filterOptionsItem;
	}

	@Override
	public DataItemContainer<University> getUniversitiesContainer()
	{
		return this.universitiesContainer;
	}

	@Override
	public void setUniversitiesContainer(DataItemContainer<University> container)
	{
		this.universitiesContainer = container;
		
		if (this.universitiesComboBox != null)
		{
			this.universitiesComboBox.setContainerDataSource(container);
			
			this.universitiesComboBox.setEnabled(container.size() > 1);
		}
	}

	@Override
	public DataItemContainer<Department> getDepartmentsContainer()
	{
		return this.departmentsContainer;
	}

	@Override
	public void setDepartmentsContainer(DataItemContainer<Department> container)
	{
		this.departmentsContainer = container;

		if (this.departmentsComboBox != null)
		{
			this.departmentsComboBox.setContainerDataSource(container);
			
			this.departmentsComboBox.setEnabled(container.size() > 1);
		}
	}

	@Override
	public DataItemContainer<Division> getDivisionsContainer()
	{
		return this.divisionsContainer;
	}

	@Override
	public void setDivisionsContainer(DataItemContainer<Division> container)
	{
		this.divisionsContainer = container;
		
		if (this.divisionsComboBox != null)
		{
			this.divisionsComboBox.setContainerDataSource(container);
			
			this.divisionsComboBox.setEnabled(container.size() > 0);
		}
	}

	@Override
	public DataItemContainer<Professor> getProfessorsContainer()
	{
		return this.professorsContainer;
	}

	@Override
	public void setProfessorsContainer(DataItemContainer<Professor> container)
	{
		this.professorsContainer = container;
		
		if (this.professorsTable != null)
		{
			this.professorsTable.setContainerDataSource(container);
			
			this.professorsTable.setVisibleColumns(professorColumns);
		}
	}

	@Override
	protected Presenter<BeanItem<Professor>, ICoopContext, ? extends IView<BeanItem<Professor>, ICoopContext>> supplyPresenter()
	{
		return new ChooseProfessorPresenter(this);
	}
	
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		professorsTable.setSelectable(!readOnly);
	}

}
