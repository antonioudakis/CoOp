package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

public class ChooseFinancialSourceDialog
extends CoopDialog<BeanItem<FinancialSource>>
{
	private static final long serialVersionUID = 1L;
	
	private FinancialSourcesTableComponent tableComponent;
	
	public ChooseFinancialSourceDialog(CoOp coop)
	{
		tableComponent = new FinancialSourcesTableComponent();
		tableComponent.setParentModel(coop);
		tableComponent.setWidth("100%");
		
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
		if (getModel() != null)
			tableComponent.setSelectedValue(getModel().getBean());
		else
			tableComponent.setSelectedValue(null);
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		tableComponent.setSizeFull();
		
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
		
		tableComponent.setCaption(getLocalizedString("FINANCIAL_SOURCES_CAPTION"));
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		tableComponent.setReadOnly(readOnly);
		tableComponent.setUserSelectable(!readOnly);
	}
}
