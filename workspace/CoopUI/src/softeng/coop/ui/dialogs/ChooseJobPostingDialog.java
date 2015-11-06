package softeng.coop.ui.dialogs;

import java.util.Locale;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.JobPosting;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.composites.ChooseJobPostingComponent;
import softeng.coop.ui.viewdefinitions.IOkCancelView;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;
import softeng.ui.vaadin.mvp.IViewListener;
import softeng.ui.vaadin.mvp.ViewEvent;

public class ChooseJobPostingDialog
	extends CoopDialog<BeanItem<JobPosting>>
{
	private static final long serialVersionUID = 1L;

	private ChooseJobPostingComponent chooseJobPostingComponent;

	
	public ChooseJobPostingDialog()
	{
		this.setHeight("440px");
		
		this.getOkCancelView()
			.addOkListener(new IViewListener<OkCancelViewModel, 
					ICoopContext, IOkCancelView>() 
		{
			
			@Override
			public void onEvent(
					ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event) 
			{
				JobPosting selectedJobPosting = chooseJobPostingComponent.getSelectedJobPosting();
			
				if (selectedJobPosting != null)
					setModel(new BeanItem<JobPosting>(selectedJobPosting));
				else
					setModel(null);
			}
		});
	}
	
	public ChooseJobPostingDialog(String caption)
	{
		this();
		this.setCaption(caption);
	}

	@Override
	public void dataBind() 
	{
		chooseJobPostingComponent.dataBind();
	}

	@Override
	protected void setupUI() 
	{
		super.setupUI();
		
		this.chooseJobPostingComponent = new ChooseJobPostingComponent();
		this.chooseJobPostingComponent.setSizeFull();
		this.addComponent(chooseJobPostingComponent);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		chooseJobPostingComponent.setCaption(getLocalizedString("TABLE_CAPTION"));
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		chooseJobPostingComponent.setReadOnly(readOnly);
	}
	
}
