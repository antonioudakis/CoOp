package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

public class FinancialSourceDialog
extends CoopDialog<BeanItem<FinancialSource>>
{
	private static final long serialVersionUID = 1L;
	
	private FinancialSourceDataComponent dataComponent = new FinancialSourceDataComponent();

	@Override
	public void dataBind()
	{
		dataComponent.setModel(getModel());
		
		dataComponent.dataBind();
	}

	@Override
	public boolean isDataValid()
	{
		return dataComponent.isDataValid();
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		dataComponent.setWidth("100%");
		
		addComponent(dataComponent);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote vote)
			{
				onAccept(vote);
			}
		});
	}

	private void onAccept(CommandExecutionVote vote)
	{
		if (!dataComponent.isDataValid())
		{
			getContext().showInvalidDataNotification();
			vote.markAsFailed();
			return;
		}
		
		dataComponent.commitChangesToModel();
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
		
		dataComponent.setReadOnly(readOnly);
	}
}
