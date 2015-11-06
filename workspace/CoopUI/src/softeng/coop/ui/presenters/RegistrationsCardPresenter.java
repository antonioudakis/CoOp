package softeng.coop.ui.presenters;

import java.util.Collection;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.EmailDialog;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.coop.ui.viewdefinitions.viewmodels.EmailModel;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;

import softeng.ui.vaadin.mvp.*;

public class RegistrationsCardPresenter
extends Presenter<BeanItem<CoOp>, ICoopContext, IRegistrationsCardView>
{
	private StudentsWriterManager manager;
	
	private IListener<CoOp> selectedCoopChangedListener;
	
	private PropertyChangeEventSubscription<IRegistrationsCardView.FilterOptions>
		filterOptionsSubscription;

	public RegistrationsCardPresenter(IRegistrationsCardView view)
	{
		super(view);

		selectedCoopChangedListener = new IListener<CoOp>()
		{
			@Override
			public void onEvent(CoOp coop)
			{
				bindToCoOp(coop);
			}
		};
		
		filterOptionsSubscription = 
			new PropertyChangeEventSubscription<IRegistrationsCardView.FilterOptions>();
	}

	@Override
	protected void attachToView()
	{
		getContext().addSelectedCoopChangedListener(selectedCoopChangedListener);
		
		filterOptionsSubscription.add(
				new IListener<PropertyChangeEvent<IRegistrationsCardView.FilterOptions>>()
		{
			
			@Override
			public void onEvent(PropertyChangeEvent<IRegistrationsCardView.FilterOptions> event)
			{
				updateRegistrationsList();
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
		
		getView().getOkCancelView().addOkFailedListener(new IListener<RuntimeException>()
		{
			@Override
			public void onEvent(RuntimeException event)
			{
				revert();
			}
		});
		
		getView().addSendMailListener(new IListener<Collection<AuthenticatedUser>>()
		{
			
			@Override
			public void onEvent(Collection<AuthenticatedUser> recepients)
			{
				onSendMail(recepients);
			}
		});
	}

	protected void onSendMail(Collection<AuthenticatedUser> recepients)
	{
		EmailDialog emailDialog = new EmailDialog();
		
		EmailModel emailModel = new EmailModel();
		
		emailModel.setRecepients(recepients);
		
		emailDialog.setModel(emailModel);
		
		emailDialog.setModal(true);
		
		getContext().getMainWindow().addWindow(emailDialog);
		
		emailDialog.dataBind();
	}

	protected void revert()
	{
		getContext().getSession().revertOutstanding();
		
		getView().discardChanges();
	}

	protected void cancel()
	{
		getView().discardChanges();
	}

	protected void save()
	{
		if (manager == null) return;
		
		if (getView().getModel() == null) return;
		
		Registration registration = 
			getView().getRegistrationsTableView().getSelectedValue();
		
		if (registration == null) return;
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		getView().commitChangesToModel();

		manager.persistRegistration(registration);

		getContext().showDataSavedNotification();
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (session.getStudentsManager() == null || 
				!session.getStudentsManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		manager = session.getStudentsManager().getWriterManager();
		
		bindToCoOp(getContext().getSelectedCoop());
	}

	@Override
	protected void detachFromView()
	{
		getContext().removeSelectedCoopChangedListener(selectedCoopChangedListener);
	}
	
	private void bindToCoOp(CoOp coop)
	{
		if (manager == null) return;
		
		DataItem<CoOp> coopItem;
		
		if (coop != null)
			coopItem = new DataItem<CoOp>(coop, manager);
		else
			coopItem = null;
		
		getView().setModel(coopItem);
		
		getView().dataBind();
		
		updateRegistrationsList();
		
	}

	private void updateRegistrationsList()
	{
		if (manager == null) return;
		
		if (getContext().getSelectedCoop() == null)
		{
			getView().getRegistrationsTableView().setModel(null);
			
			getView().getRegistrationsTableView().dataBind();

			return;
		}
		
		IRegistrationsCardView.FilterOptions filterOptions =
			getView().getFilterOptions().getBean();

		RegistrationsSearchCriteria criteria = new RegistrationsSearchCriteria();
		
		criteria.setCoop(getContext().getSelectedCoop());
		
		if (filterOptions.isNoAma())
			criteria.setHasAMA(false);
		
		if (filterOptions.isNoIban())
			criteria.setHasIBAN(false);
	
		if (filterOptions.isNotAssignedToJob())
			criteria.setAssignedToJob(false);
		
		if (filterOptions.isNotQualifiedForAssignment())
			criteria.setQualifiedForAssignment(false);
		
		if (filterOptions.isNotQualifiedForCompletion())
			criteria.setQualifiedForCompletion(false);
		
		if (filterOptions.isNotPassed())
			criteria.setPassed(false);
		
		if (filterOptions.isNotGraded())
			criteria.setGraded(false);
		
		if (filterOptions.getMinGrade() != null)
			criteria.setMinGrade(filterOptions.getMinGrade());
		
		if (filterOptions.getMaxGrade() != null)
			criteria.setMaxGrade(filterOptions.getMaxGrade());
		
		if (filterOptions.isNoInsuranceContract())
			criteria.setHasContract(false);
		
		if (filterOptions.isNoSocialSecurityId())
			criteria.setHasSocialSecurityId(false);
		
		SearchResult<Registration> result = manager.searchRegistrations(criteria);
		
		getView().getRegistrationsTableView().setModel(result.getList());
		
		getView().getRegistrationsTableView().dataBind();
		
		getView().dataBind();
		
	}

}
