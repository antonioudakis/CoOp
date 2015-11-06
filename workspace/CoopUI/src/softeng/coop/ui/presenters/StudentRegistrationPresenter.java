package softeng.coop.ui.presenters;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.ui.vaadin.mvp.*;

/**
 * A generic presenter that supports views of model type 
 * BeanItem[Registration]. Handles current coop and registration
 * change. 
 * @param <V> The type of the view. Must be an IView[BeanItem[Registration], ICoopContext].
 */
public class StudentRegistrationPresenter<V extends IView<BeanItem<Registration>, ICoopContext>>
extends Presenter<BeanItem<Registration>, ICoopContext, V>
{
	/**
	 * The user's StudentsWriterManager.
	 */
	protected StudentsWriterManager manager;
	
	private IListener<CoOp> selectedCoopSchangeListener;

	public StudentRegistrationPresenter(V view) 
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
		
		getView().setModel(registrationItem);
		
		getView().dataBind();
		
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

	/**
	 * Persist the view's raw model. It has been validated by now.
	 * Default implementation saves the registration itself.
	 * Override to save group or job.
	 * @param registration The view's registration.
	 */
	protected void persistModel(Registration registration)
	{
		manager.persistRegistration(registration);
	}

}
