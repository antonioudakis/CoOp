package softeng.coop.ui.presenters;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class StudentPreferencesCardPresenter 
	extends Presenter<BeanItem<Student>, ICoopContext, IStudentPreferencesCardView>
{
	private StudentsWriterManager manager;
	
	private IListener<CoOp> selectedCoopSchangeListener;

	public StudentPreferencesCardPresenter(IStudentPreferencesCardView view) 
	{
		super(view);
	}

	@Override
	protected void setupView() 
	{
		Session session = getContext().getSession();
		
		if (session.getStudentsManager() == null || !session.getStudentsManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
			
			return;
		}
		
		manager = session.getStudentsManager().getWriterManager();
		
		AuthenticatedUser authenticatedUser = 
			getContext().getSession().getAuthenticatedUser();
		
		if (!(authenticatedUser instanceof Student))
		{
			throw new CoopUISystemException("Authenticated user should have been a Student.");
		}

		Student student = (Student)authenticatedUser;
		
		BeanItem<Student> studentItem = new BeanItem<Student>(student);
		
		getView().setModel(studentItem);
		
		getView().dataBind();
		
		CoOp currentCoop = getView().getContext().getSelectedCoop();

		setSelectedRegistration(currentCoop);
	}
	
	private void setSelectedRegistration(CoOp coop) 
	{
		if (manager == null) return;
		
		//find selected registration
		Student user = (Student) getView().getContext().getSession().getAuthenticatedUser();
		
		Registration registration = user.getActiveRegistration();
		
		BeanItem<Registration> registrationItem;
		
		if (registration != null)
			registrationItem = new BeanItem<Registration>(registration);
		else
			registrationItem = null;
		
		IRegistrationPreferencesView registrationPreferencesView =
			getView().getRegistrationPreferencesView();

		if (registration != null)
		{
			boolean isEditingAllowed = registration.getCoop().isInRegistration();
			registrationPreferencesView.setReadOnly(!isEditingAllowed);
			getView().getOkCancelView().setReadOnly(!isEditingAllowed);
		}
		else
		{
			getView().getOkCancelView().setReadOnly(true);
		}
		
		registrationPreferencesView.setModel(registrationItem);
		registrationPreferencesView.dataBind();
		
	}
	
	@Override
	protected void attachToView()
	{
		selectedCoopSchangeListener = new IListener<CoOp>()
		{
			@Override
			public void onEvent(CoOp coop)
			{
				setSelectedRegistration(coop);
			}
		};
		
		getContext().addSelectedCoopChangedListener(selectedCoopSchangeListener);
		
		getView().getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				save(event);
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
		
	}

	@Override
	protected void detachFromView()
	{
		if (selectedCoopSchangeListener != null)
		{
			getContext().removeSelectedCoopChangedListener(selectedCoopSchangeListener);
			
			selectedCoopSchangeListener = null;
		}
	}

	private void cancel()
	{
		getView().discardChanges();
		
		if (getContext().getSession() != null)
		{
			getContext().getSession().refreshEntity(getView().getModel().getBean());
			
			getView().dataBind();
		}
	}

	private void save(CommandExecutionVote vote)
	{
		if (manager == null) return;
		
		if (!getView().isDataValid())
		{
			vote.markAsFailed();
			
			getView().getContext().showInvalidDataNotification();

			return;
		}

		getView().commitChangesToModel();
		
		BeanItem<Registration> registrationItem =
			getView().getRegistrationPreferencesView().getModel();
		
		if (registrationItem != null)
		{
			manager.persistRegistration(registrationItem.getBean());
			
			getView().getContext().showDataSavedNotification();
		}
	}

}
