package softeng.coop.ui.dialogs;

import java.util.Locale;
import java.util.Set;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.ui.vaadin.mvp.IListener;

import com.vaadin.data.util.BeanItem;

public class ChooseBranchPersonDialog 
	extends CoopDialog<BeanItem<CompanyPerson>>
{
	private static final long serialVersionUID = 1L;

	private Branch branch;
	private BranchPersonsTableComponent tableComponent;
	
	public ChooseBranchPersonDialog(Branch branch)
	{
		if (branch == null) 
			throw new IllegalArgumentException("Argument 'branch' must not be null.");
		
		this.branch = branch;
		
		this.tableComponent = new BranchPersonsTableComponent();
		
		this.addComponent(tableComponent);
		
		this.setWidth("600px");
		this.setHeight("400px");
	}
	
	@Override
	public void dataBind() 
	{
		//find the company person of a branch
		Set<CompanyPerson> companyPersons = branch.getPersons();
		
		tableComponent.setModel(companyPersons);
		
		tableComponent.setParentModel(branch);
		
		tableComponent.dataBind();
		
		if (getModel() != null)
			tableComponent.setSelectedValue(getModel().getBean());
		
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();

		tableComponent.setEditVisible(false);
		tableComponent.setAddVisible(false);
		tableComponent.setDeleteVisible(false);
		
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
