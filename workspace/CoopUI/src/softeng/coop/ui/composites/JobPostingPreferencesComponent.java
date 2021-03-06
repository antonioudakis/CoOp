package softeng.coop.ui.composites;

import java.util.Locale;

import softeng.coop.business.coops.CoOpsManager;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.Presenter;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class JobPostingPreferencesComponent 
	extends CoopComponent<BeanItem<CoOp>> 
{

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private CheckBox jobPostingCompletedCheckBox;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private boolean inhibitValueChangeEvent;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public JobPostingPreferencesComponent() 
	{
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	@Override
	protected Presenter<BeanItem<CoOp>, ICoopContext, ? extends IView<BeanItem<CoOp>, ICoopContext>> supplyPresenter() 
	{
		return null;
	}

	@Override
	public void dataBind() 
	{
		if (this.getModel() == null) return;
		
		inhibitValueChangeEvent = true;
		
		try
		{
			jobPostingCompletedCheckBox.setValue(getModel().getBean().isSetup());
		}
		finally
		{
			inhibitValueChangeEvent = false;
		}
	}

	@Override
	protected void setupUI() 
	{
		super.setupUI();
		
		jobPostingCompletedCheckBox.setImmediate(true);
		
		jobPostingCompletedCheckBox.addListener(new Property.ValueChangeListener() 
		{
			
			@Override
			public void valueChange(ValueChangeEvent event) 
			{
				if (getModel() == null || inhibitValueChangeEvent)
					return;
				
				CoOp currentCoop = getModel().getBean();
				
				CoOpsManager readerManager = getContext().getSession().getCoOpsManager();
				
				if (readerManager == null || !readerManager.isWriteable())
				{
					getContext().showAccessDeniedNotification();
				}
				else
				{
					currentCoop.setSetup(jobPostingCompletedCheckBox.booleanValue());
					
					//try to save the coop
					readerManager.getWriterManager().persistCoOp(currentCoop);
					
					getContext().showDataSavedNotification();
				}
			}
		});
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale) 
	{
		super.setupLocalizedCaptions(locale);
		
		jobPostingCompletedCheckBox.setCaption(getLocalizedString("IS_SETUP_CAPTION"));
		jobPostingCompletedCheckBox.setDescription(getLocalizedString("IS_SETUP_DESCRIPTION"));
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout()
	{
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		
		// jobPostingCompletedCheckBox
		jobPostingCompletedCheckBox = new CheckBox();
		jobPostingCompletedCheckBox.setCaption("Ολοκλήρωση Θέσεων Εργασίας");
		jobPostingCompletedCheckBox.setImmediate(false);
		jobPostingCompletedCheckBox.setWidth("100.0%");
		jobPostingCompletedCheckBox.setHeight("-1px");
		mainLayout.addComponent(jobPostingCompletedCheckBox);
		
		return mainLayout;
	}

}
