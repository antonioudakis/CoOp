package softeng.coop.ui.dialogs;

import java.util.Locale;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.coop.ui.composites.*;

import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

import com.vaadin.ui.*;

public class JobPostingPartDialog 
extends CoopDialog<BeanItem<JobPostingPart>>
implements IJobPostingPartView
{
	private static final long serialVersionUID = 1L;

	private JobPostingPartComponent jobPostingPartComponent;
	
	private Company company = null;
	
	public JobPostingPartDialog()
	{
		VerticalLayout layout = new VerticalLayout();
		
		layout.setSizeFull();
		layout.setSpacing(true);
		
		layout.addComponent(new UserLanguageComboBox());
		
		Panel panel = new Panel();
		panel.setSizeFull();
		
		jobPostingPartComponent = new JobPostingPartComponent();

		jobPostingPartComponent.setWidth("100%");
		panel.addComponent(jobPostingPartComponent);
		
		layout.addComponent(panel);
		layout.setExpandRatio(panel, 1.0f);
		
		this.setWidth("650px");
		this.setHeight("580px");
		
		this.setLayout(layout);
		
		this.getOkCancelView().setModel(OkCancelViewModel.ApplyOrSelect);
	}
	
	@Override
	public void dataBind() 
	{
		this.jobPostingPartComponent.setCompanyForJobPostingPart(this.company);
		
		this.jobPostingPartComponent.dataBind();
	}

	@Override
	public void setCompanyForJobPostingPart(Company company) 
	{
		this.company = company;
	}

	@Override
	public Company getCompanyForJobPostingPart() 
	{
		return this.company;
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		this.getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				if (!jobPostingPartComponent.isDataValid())
				{
					event.markAsFailed();
					getContext().showInvalidDataNotification();
					return;
				}
				
				jobPostingPartComponent.commitChangesToModel();
			}
		});
		
	}
	
	@Override
	public void setModel(BeanItem<JobPostingPart> jobPostingPart)
	{
		super.setModel(jobPostingPart);
		
		this.jobPostingPartComponent.setModel(jobPostingPart);
	}

	@Override
	public boolean isDataValid()
	{
		return this.jobPostingPartComponent.isDataValid();
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		jobPostingPartComponent.setReadOnly(readOnly);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}
	
}
