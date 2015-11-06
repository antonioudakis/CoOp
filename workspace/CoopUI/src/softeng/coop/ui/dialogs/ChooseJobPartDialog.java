package softeng.coop.ui.dialogs;

import java.util.Locale;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.ui.vaadin.mvp.IListener;

@SuppressWarnings("serial")
public class ChooseJobPartDialog
extends CoopDialog<BeanItem<JobPart>>
{
	private Job job;
	
	private JobPartsTableComponent tableComponent;
	
	public ChooseJobPartDialog(Job job)
	{
		if (job == null) 
			throw new IllegalArgumentException("Argument 'job' must not be null.");
		
		this.job = job;
		
		tableComponent = new JobPartsTableComponent();
		
		tableComponent.setReadOnly(true);
		tableComponent.setAddVisible(false);
		tableComponent.setEditVisible(false);
		tableComponent.setDeleteVisible(false);
		tableComponent.setSizeFull();
		
		addComponent(tableComponent);
		
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
	public void dataBind()
	{
		tableComponent.setParentModel(job);
		
		tableComponent.setModel(job.getJobParts());
		
		tableComponent.dataBind();
		
		if (getModel() != null)
			tableComponent.setSelectedValue(getModel().getBean());
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
