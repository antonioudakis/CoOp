package softeng.coop.ui.presenters;

import java.math.*;
import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;
import softeng.coop.business.payments.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

public class FinancialCardPresenter
extends Presenter<BeanItem<FinancialActionViewModel>, ICoopContext, IFinancialCardView>
{
	private Session session;
	
	private StudentsWriterManager studentsManager;
	
	private PaymentsWriterManager paymentsManager;
	
	private IListener<CoOp> selectedCoopChangeListener;
	
	private List<FinancialSource> emptyFinancialSources = new ArrayList<FinancialSource>();
	
	public FinancialCardPresenter(IFinancialCardView view)
	{
		super(view);
	}

	protected void onSelectedCoOpChanged(CoOp coop)
	{
		if (coop == null)
		{
			getView().getAssignedRegistrationsTableView().setModel(null);
			getView().getAssignedRegistrationsTableView().dataBind();
			
			getView().getUnassignedRegistrationsTableView().setModel(null);
			getView().getUnassignedRegistrationsTableView().dataBind();
			
			getView().setFinancialSources(null, emptyFinancialSources);
			
			return;
		}
		
		RegistrationsSearchCriteria registrationsSearchCriteria = new RegistrationsSearchCriteria();
		
		registrationsSearchCriteria.setCoop(coop);
		
		registrationsSearchCriteria.setAssignedToJob(true);
		
		getView()
			.getAssignedRegistrationsTableView()
			.setModel(studentsManager.searchRegistrations(registrationsSearchCriteria).getList());
		
		getView()
			.getAssignedRegistrationsTableView()
			.dataBind();
		
		registrationsSearchCriteria.setAssignedToJob(false);
		
		getView()
			.getUnassignedRegistrationsTableView()
			.setModel(studentsManager.searchRegistrations(registrationsSearchCriteria).getList());
		
		getView()
			.getUnassignedRegistrationsTableView()
			.dataBind();
		
		List<FinancialSource> financialSources = new ArrayList<FinancialSource>(coop.getFinancialSources());

		// Sort financial sources by name.
		Collections.sort(financialSources, new Comparator<FinancialSource>()
		{
			@Override
			public int compare(FinancialSource source1, FinancialSource source2)
			{
				return source1.getName().compareTo(source2.getName());
			}
		});
		
		getView().setFinancialSources(coop, financialSources);
	}

	@Override
	protected void attachToView()
	{
		session = getContext().getSession();
		
		if (session.getStudentsManager() != null && 
				session.getStudentsManager().isWriteable() &&
				session.getPaymentsManager() != null &&
				session.getPaymentsManager().isWriteable())
		{
			studentsManager = session.getStudentsManager().getWriterManager();
			paymentsManager = session.getPaymentsManager().getWriterManager();
		}
		else
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		selectedCoopChangeListener = new IListener<CoOp>()
		{
			@Override
			public void onEvent(CoOp coop)
			{
				onSelectedCoOpChanged(coop);
			}
		};
		
		getContext().addSelectedCoopChangedListener(selectedCoopChangeListener);
		
		getView().addExecuteActionListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onExecuteAction(event);
			}
		});

		getView().getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onSave(event);
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
		
		getView().getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				onSaveSucceeded();
			}
		});
		
		getView().getOkCancelView().addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				onCancel();
			}
		});
	}

	protected void onCancel()
	{
		getView().discardChanges();
		
		Registration selectedRegistration = 
			getView().getAssignedRegistrationsTableView().getSelectedValue();
		
		if (selectedRegistration == null) return;
		
		session.refreshEntity(selectedRegistration);
		
		getView().getAssignedRegistrationsTableView().setSelectedValue(null);
		getView().getAssignedRegistrationsTableView().setSelectedValue(selectedRegistration);
	}

	protected void revert(RuntimeException event)
	{
		getContext().getSession().revertOutstanding();
	}

	protected void onSave(CommandExecutionVote event)
	{
		Registration selectedRegistration =
			getView().getAssignedRegistrationsTableView().getSelectedValue();
		
		if (selectedRegistration == null)
		{
			event.markAsFailed();
			
			return;
		}

		if (!getView().isDataValid())
		{
			event.markAsFailed();
			
			getContext().showInvalidDataNotification();
			return;
		}
		
		getView().commitChangesToModel();
		
		studentsManager.persistRegistration(selectedRegistration);
		
	}

	protected void onSaveSucceeded()
	{
		getView().getAssignedRegistrationsTableView().refreshRowCache();
		
		getContext().showDataSavedNotification();
	}

	protected void onExecuteAction(CommandExecutionVote event)
	{
		if (getView().getModel() == null)
		{
			event.markAsFailed();
			return;
		}
		
		switch (getView().getModel().getBean().getActionType())
		{
			case Add:
				batchAddPayments();
				break;
				
			case ChangeState:
				batchChangePaymentsState();
				break;
				
			case Clear:
				batchClearPayments();
				break;
		}
		
	}

	@Override
	protected void setupView()
	{
		BeanItem<FinancialActionViewModel> model;
		
		if (studentsManager != null)
			model = new BeanItem<FinancialActionViewModel>(new FinancialActionViewModel());
		else
			model = null;
		
		getView().setModel(model);
		
		getView().dataBind();
		
		onSelectedCoOpChanged(getContext().getSelectedCoop());
	}

	@Override
	protected void detachFromView()
	{
		super.detachFromView();

		if (selectedCoopChangeListener != null)
			getContext().removeSelectedCoopChangedListener(selectedCoopChangeListener);
	}

	private void batchAddPayments()
	{
		if (getView().getModel() == null) return;
		
		FinancialActionViewModel actionModel = getView().getModel().getBean();
		
		TransactionScope transactionScope = studentsManager.beginTransaction();
		
		Date paymentDate = actionModel.getPaymentDate();
		
		try
		{
			for (Registration registration : getView().getAssignedRegistrationsTableView().getModel())
			{
				if (registration.getGroup() == null) continue;
				
				Job job = registration.getGroup().getJob();
				
				if (job == null) continue;
				
				for (JobPart jobPart : job.getJobParts())
				{
					addPayment(registration, jobPart, actionModel, paymentDate, jobPart.getStartDate(), jobPart.getEndDate());
				}
			}
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}
	
	private void addPayment(
			Registration registration, 
			JobPart jobPart, 
			FinancialActionViewModel actionModel, 
			Date now, 
			Date startDate, 
			Date endDate)
	{
		BigDecimal dayCompensation = BigDecimal.ZERO;
		
		switch (jobPart.getSiteType())
		{
			case Local:
				dayCompensation = actionModel.getLocalDayCompensation();
				break;
				
			case Remote:
				dayCompensation = actionModel.getRemoteDayCompensation();
				break;
				
			case OffCountry:
				dayCompensation = actionModel.getOffCountryDayCompensation();
				break;
		}
		
		/* Determine the paid days.
			Initially, the default is the 'paidDays' field.
			But if there is a line in 'specialPayments' collection for this
			financial source, override the value. */
		int paidDays = jobPart.getPaidDays();
		
		for (JobPartSpecialPayable specialPayable : jobPart.getSpecialPayables())
		{
			if (specialPayable.getFinancialSource() == actionModel.getFinancialSource())
			{
				paidDays = specialPayable.getPaidDays();
			}
		}
		
		if (dayCompensation.equals(BigDecimal.ZERO) || paidDays == 0)
			return;
		
		BigDecimal amount = dayCompensation.multiply(new BigDecimal(paidDays));
		
		amount = amount.setScale(2, RoundingMode.HALF_UP);
		
		Payment payment = Constructor.createPayment();
		
		payment.setPaymentDate(now);
		
		if (jobPart != null)
		{
			payment.setJobPart(jobPart);
			
			if (session.isLoaded(jobPart, "payments"))
				jobPart.getPayments().add(payment);
		}
		
		payment.setSource(actionModel.getFinancialSource());
		payment.setState(actionModel.getPaymentState());
		payment.setType(actionModel.getPaymentType());
		
		payment.setStartDate(startDate);
		payment.setEndDate(endDate);
		
		payment.setAmount(amount);
		
		payment.setRegistration(registration);
		registration.getPayments().add(payment);
		
		paymentsManager.persistPayment(payment);
	}

	private void batchChangePaymentsState()
	{
		if (getView().getModel() == null) return;
		
		FinancialActionViewModel actionModel = getView().getModel().getBean();
		
		TransactionScope transactionScope = studentsManager.beginTransaction();
		
		try
		{
			for (Registration registration : getView().getAssignedRegistrationsTableView().getModel())
			{
				for (Payment payment : registration.getPayments())
				{
					if (payment.getSource() == actionModel.getFinancialSource() &&
							payment.getType() == actionModel.getPaymentType() &&
							payment.getState() != actionModel.getPaymentState())
					{
						payment.setState(actionModel.getPaymentState());
						paymentsManager.persistPayment(payment);
					}
				}
			}
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
	}

	private void batchClearPayments()
	{
		if (getView().getModel() == null) return;
		
		HashSet<Payment> removedPayments = new HashSet<Payment>();
		
		FinancialActionViewModel actionModel = getView().getModel().getBean();
		
		TransactionScope transactionScope = studentsManager.beginTransaction();
		
		try
		{
			for (Registration registration : getView().getAssignedRegistrationsTableView().getModel())
			{
				for (Payment payment : registration.getPayments())
				{
					if (payment.getSource() == actionModel.getFinancialSource() &&
							payment.getType() == actionModel.getPaymentType() && 
							payment.getState() == actionModel.getPaymentState())
					{
						JobPart jobPart = payment.getJobPart();
						
						if (jobPart != null)
						{
							if (session.isLoaded(jobPart, "payments"))
								jobPart.getPayments().remove(payment);

							payment.setJobPart(null);
						}

						removedPayments.add(payment);
					}
				}
	
				if (removedPayments.size() > 0)
				{
					registration.getPayments().removeAll(removedPayments);
					
					removedPayments.clear();
					
					studentsManager.persistRegistration(registration);
				}
			}
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
	}

}
