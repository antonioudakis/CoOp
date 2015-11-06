package softeng.coop.ui.dialogs;

import java.util.Locale;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

public class ChooseInsuranceContractDialog
extends CoopDialog<BeanItem<InsuranceContract>>
{
	private static final long serialVersionUID = 1L;
	
	private CoOp coop;
	
	private InsuranceContractsTableComponent tableComponent =
		new InsuranceContractsTableComponent();
	
	public ChooseInsuranceContractDialog(CoOp coop)
	{
		if (coop == null) 
			throw new IllegalArgumentException("Argument 'coop' must not be null.");
		
		this.coop = coop;
		
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
		setModel(tableComponent.getSelectedItem());
	}

	@Override
	public void dataBind()
	{
		tableComponent.setParentModel(coop);
		tableComponent.setModel(coop.getInsuranceContracts());
		
		tableComponent.dataBind();
		
		if (getModel() != null)
			tableComponent.setSelectedValue(getModel().getBean());
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		tableComponent.setSizeFull();
		tableComponent.setReadOnly(true);
		tableComponent.setAddVisible(false);
		tableComponent.setEditVisible(false);
		tableComponent.setDeleteVisible(false);
		
		addComponent(tableComponent);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		tableComponent.setCaption(getLocalizedString("TABLE_CAPTION"));
		
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		tableComponent.setReadOnly(readOnly);
		tableComponent.setUserSelectable(!readOnly);
	}

}
