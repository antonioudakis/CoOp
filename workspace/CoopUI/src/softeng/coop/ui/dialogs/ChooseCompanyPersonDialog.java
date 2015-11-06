package softeng.coop.ui.dialogs;

import java.util.Locale;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.ui.vaadin.mvp.IListener;

import com.vaadin.data.util.*;

public class ChooseCompanyPersonDialog
extends CoopDialog<BeanItem<CompanyPerson>>
{
	private static final long serialVersionUID = 1L;
	
	private CompanyPersonsTableComponent tableComponent;
	
	private Company company;
	
	public ChooseCompanyPersonDialog(Company company)
	{
		if (company == null) 
			throw new IllegalArgumentException("Argument 'company' must not be null.");
		
		tableComponent = new CompanyPersonsTableComponent();
		
		tableComponent.setSizeFull();
		
		this.company = company;

		this.addComponent(tableComponent);
		
		this.setWidth("600px");
		this.setHeight("400px");

		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept();
			}
		});
	}

	@Override
	public void dataBind()
	{
		tableComponent.setParentModel(company);
		tableComponent.setModel(company.getPersons());
		
		tableComponent.dataBind();
		
		if (getModel() != null)
			tableComponent.setSelectedValue(getModel().getBean());
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		tableComponent.setAddVisible(false);
		tableComponent.setEditVisible(false);
		tableComponent.setDeleteVisible(false);
		
	}

	protected void onAccept()
	{
		setModel(tableComponent.getSelectedItem());
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		tableComponent.setReadOnly(readOnly);
		tableComponent.setUserSelectable(!readOnly);
	}
	
}
