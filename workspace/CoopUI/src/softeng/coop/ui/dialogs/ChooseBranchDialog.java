package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class ChooseBranchDialog 
	extends CoopDialog<BeanItem<Branch>> 
{
	private static final long serialVersionUID = 1L;

	private Company company, initialCompany;
	private BranchesTableComponent branchesTableComponent;
	private CompaniesTableComponent companiesTableComponent;
	private CheckBox showCompaniesCheckBox;
	
	private HorizontalLayout listsLayout;
	
	public ChooseBranchDialog(Company company, CoOp coop)
	{
		if (company == null) 
			throw new IllegalArgumentException("Argument 'company' must not be null.");
		
		this.company = company;
		this.initialCompany = company;
		
		VerticalLayout mainLayout = new VerticalLayout();
		
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		
		listsLayout = new HorizontalLayout();
		
		listsLayout.setSizeFull();
		listsLayout.setSpacing(true);
		
		if (coop != null)
		{
			showCompaniesCheckBox = new CheckBox();
			showCompaniesCheckBox.setWidth("100%");
			showCompaniesCheckBox.setImmediate(true);
			
			mainLayout.addComponent(showCompaniesCheckBox);
			
			showCompaniesCheckBox.addListener(new Property.ValueChangeListener()
			{
				@Override
				public void valueChange(ValueChangeEvent event)
				{
					onShowCompaniesChanged();
				}
			});
			
			companiesTableComponent = new CompaniesTableComponent();
			companiesTableComponent.setSizeFull();
			
			companiesTableComponent.setModel(coop.getCompanies());
			companiesTableComponent.dataBind();
			companiesTableComponent.setSelectedValue(company);
			companiesTableComponent.setImmediate(true);
			
			companiesTableComponent.addSelectedChangeListener(new IListener<ModelEvent<Company>>()
			{
				@Override
				public void onEvent(ModelEvent<Company> event)
				{
					onSelectedCompanyChanged();
				}
			});
			
			listsLayout.addComponent(companiesTableComponent);
			listsLayout.setExpandRatio(companiesTableComponent, 0.0f);
		}
		
		branchesTableComponent = new BranchesTableComponent();
		branchesTableComponent.setSizeFull();
		
		listsLayout.addComponent(branchesTableComponent);
		listsLayout.setExpandRatio(branchesTableComponent, 1.0f);
		
		mainLayout.addComponent(listsLayout);
		mainLayout.setExpandRatio(listsLayout, 1.0f);
		
		this.setLayout(mainLayout);
		
		if (coop != null)
			this.setWidth("700px");
		else
			this.setWidth("600px");

		this.setHeight("400px");
	}
	
	public ChooseBranchDialog(Company company)
	{
		this(company, null);
	}
	
	protected void onShowCompaniesChanged()
	{
		boolean showCompanies = showCompaniesCheckBox.booleanValue();
		
		if (!showCompanies)
		{
			if (initialCompany != company)
			{
				company = initialCompany;
				dataBind();
			}
			
			listsLayout.setExpandRatio(companiesTableComponent, 0.0f);
			listsLayout.setExpandRatio(branchesTableComponent, 1.0f);
		}
		else
		{
			company = companiesTableComponent.getSelectedValue();
			
			dataBind();

			listsLayout.setExpandRatio(companiesTableComponent, 0.35f);
			listsLayout.setExpandRatio(branchesTableComponent, 0.65f);
		}
	}

	protected void onSelectedCompanyChanged()
	{
		company = companiesTableComponent.getSelectedValue();
		
		dataBind();
	}

	@Override
	public void dataBind() 
	{
		branchesTableComponent.setParentModel(this.company);
		
		if (company != null)
		{
			//find all branches for the given company
			Set<Branch> branches = company.getBranches();
			
			branchesTableComponent.setModel(branches);
		}
		else
		{
			branchesTableComponent.setModel(null);
		}
		
		branchesTableComponent.dataBind();
		
		if (getModel() != null)
			branchesTableComponent.setSelectedValue(getModel().getBean());
		else
			branchesTableComponent.setSelectedValue(null);
		
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();

		branchesTableComponent.setAddVisible(false);
		branchesTableComponent.setEditVisible(false);
		branchesTableComponent.setDeleteVisible(false);
		
		if (companiesTableComponent != null)
		{
			companiesTableComponent.setAddVisible(false);
			companiesTableComponent.setEditVisible(false);
			companiesTableComponent.setDeleteVisible(false);
		}
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept();
			}
		});
	}

	protected void onAccept()
	{
		this.setModel(branchesTableComponent.getSelectedItem());
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		if (companiesTableComponent != null)
			companiesTableComponent.setCaption(getLocalizedString("COMPANIES_CAPTION"));
		
		if (showCompaniesCheckBox != null)
			showCompaniesCheckBox.setCaption(getLocalizedString("SHOW_COMPANIES_CAPTION"));
		
		branchesTableComponent.setCaption(getLocalizedString("BRANCHES_CAPTION"));
	}

	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		branchesTableComponent.setReadOnly(readOnly);
		branchesTableComponent.setUserSelectable(!readOnly);
		
		if (companiesTableComponent != null)
		{
			companiesTableComponent.setReadOnly(readOnly);
			companiesTableComponent.setUserSelectable(!readOnly);
		}
		
		if (showCompaniesCheckBox != null)
		{
			showCompaniesCheckBox.setVisible(!readOnly);
		}
	}
}
