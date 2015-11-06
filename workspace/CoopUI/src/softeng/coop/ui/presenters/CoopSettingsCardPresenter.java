package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.business.coops.*;
import softeng.coop.business.payments.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.ICoopSettingsCardView.FilterOptions;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;

import softeng.ui.vaadin.mvp.*;

public class CoopSettingsCardPresenter 
	extends Presenter<BeanItemContainer<CoOp>, ICoopContext, ICoopSettingsCardView>
{
	private PropertyChangeEventSubscription<ICoopSettingsCardView.FilterOptions> filterOptionsSubscription;
	
	private CoOpsWriterManager manager;

	public CoopSettingsCardPresenter(ICoopSettingsCardView view)
	{
		super(view);
		
		filterOptionsSubscription = 
			new PropertyChangeEventSubscription<ICoopSettingsCardView.FilterOptions>();
	}

	@Override
	protected void setupView()
	{
		PaymentsManager paymentsManager = getContext().getSession().getPaymentsManager();
		
		if (paymentsManager != null)
		{
			getView().setAvailableFinancialSources(paymentsManager.getFinancialSources());
		}
		
		AuthenticatedUser user = getContext().getSession().getAuthenticatedUser();
		
		getView().setDepartment(user.getDepartment());
		
		updateCoopsList();
	}

	@Override
	protected void attachToView()
	{
		CoOpsManager coopsManager = getContext().getSession().getCoOpsManager();
		
		if (coopsManager == null || !coopsManager.isWriteable())
		{
			getContext().showAccessDeniedNotification();
			
			return;
		}
		
		this.manager = coopsManager.getWriterManager();

		filterOptionsSubscription.add(new IListener<PropertyChangeEvent<FilterOptions>>()
		{
			@Override
			public void onEvent(PropertyChangeEvent<FilterOptions> event)
			{
				updateCoopsList();
			}
		});
		
		filterOptionsSubscription.startListeningTo(getView().getFilterOptions());
		
		getView().getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				save();
			}
		});
		
		getView().getOkCancelView().addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				cancel();
			}
		});
		
		getView().getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				if (getView().isDataValid())
					getContext().showDataSavedNotification();
			}
		});
		
		getView().getOkCancelView().addOkFailedListener(new IListener<RuntimeException>()
		{
			@Override
			public void onEvent(RuntimeException event)
			{
				revert(event);
			}
		});
	}

	protected void revert(RuntimeException event)
	{
		getContext().getSession().revertOutstanding();
		
		getView().dataBind();
	}

	protected void cancel()
	{
		CoOp coop = getView().getSelectedCoop();
		
		if (coop == null) return;
		
		getView().discardChanges();
	}

	protected void save()
	{
		CoOp coop = getView().getSelectedCoop();
		
		if (coop == null) return;
		
		ICoopContext context = getContext();
		
		if (!getView().isDataValid()) 
		{
			context.showInvalidDataNotification();
			
			return;
		}
		
		getView().commitChangesToModel();
		
		manager.persistCoOp(coop);
		
		if (coop == context.getSelectedCoop())
		{
			context.fireSelectedCoopChanged();
		}
		
	}

	protected void updateCoopsList()
	{
		CoOpSearchCriteria criteria = new CoOpSearchCriteria();
		
		AuthenticatedUser user = getContext().getSession().getAuthenticatedUser();
		
		FilterOptions options = getView().getFilterOptions().getBean();
		
		criteria.setActiveOnly(options.isActiveOnly());
		
		criteria.setInRegistrationOnly(options.isInRegistrationOnly());
		
		criteria.setSetupOnly(options.isSetupOnly());
		
		if (options.getAcademicYear() != null)
			criteria.setYear(options.getAcademicYear());
		
		criteria.setLesson(options.getLesson());
		
		if (!options.isIncludingOtherDepartments())
			criteria.setDepartment(user.getDepartment());
		
		if (!options.isIncludingOtherUniversities() && options.isIncludingOtherDepartments())
			criteria.setUniversity(user.getDepartment().getUniversity());
		
		SearchResult<CoOp> coops = manager.searchCoOps(criteria);
		
		DataItemContainer<CoOp> coopsContainer = 
			new DataItemContainer<CoOp>(CoOp.class, coops.getList(), manager);
		
		Vector<String> properties = new Vector<String>();
		
		properties.add("name");
		properties.add("academicYear");
		properties.add("semester");
		properties.add("gradePolicy");
		properties.add("active");
		properties.add("inRegistration");
		properties.add("setup");
		properties.add("startDate");
		properties.add("endDate");
		
		coopsContainer.setContainerPropertyIds(properties);
		
		getView().setModel(coopsContainer);
		
		getView().dataBind();
	}

}
