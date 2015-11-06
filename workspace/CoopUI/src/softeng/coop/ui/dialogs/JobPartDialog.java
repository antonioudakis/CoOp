package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class JobPartDialog 
	extends CoopDialog<BeanItem<JobPart>>
	implements IJobPartDialogView
{
	private static final long serialVersionUID = 1L;

	private JobPartComponent jobPartComponent;
	
	private Company company = null;
	
	public JobPartDialog() 
	{
		this.setWidth("650px");
		this.setHeight("580px");
		
		VerticalLayout layout = new VerticalLayout();
		//layout.setWidth("100%");
		layout.setSizeFull();
		layout.setSpacing(true);
		
		layout.addComponent(new UserLanguageComboBox());
		
		Panel panel = new Panel();
		
		panel.setWidth("100%");
		panel.setHeight("100%");
		layout.addComponent(panel);
		
		jobPartComponent = new JobPartComponent();
		panel.addComponent(jobPartComponent);	
		layout.setExpandRatio(panel, 1.0f);
		
		this.setWidth("650px");
		this.setHeight("580px");
		
		this.setLayout(layout);
		
		this.getOkCancelView().setModel(OkCancelViewModel.ApplyOrSelect);
	}

	
	@Override
	public void dataBind() 
	{
		jobPartComponent.setCompanyForJobPart(this.company);
		jobPartComponent.dataBind();
	}
	
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		this.jobPartComponent.setWidth("100%");
		
		this.getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				if (!jobPartComponent.isDataValid())
				{
					event.markAsFailed();
					getContext().showInvalidDataNotification();
					return;
				}
				
				jobPartComponent.commitChangesToModel();
			}
		});
		
	}
	
	@Override
	public void setModel(BeanItem<JobPart> jobPart)
	{
		super.setModel(jobPart);
		
		this.jobPartComponent.setModel(jobPart);
	}

	@Override
	public boolean isDataValid()
	{
		return this.jobPartComponent.isDataValid();
	}


	@Override
	public void setCompanyForJobPart(Company company) 
	{
		this.company = company;
	}


	@Override
	public Company getCompanyForJobPart() 
	{
		return this.company;
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
		
		jobPartComponent.setReadOnly(readOnly);
	}
}
