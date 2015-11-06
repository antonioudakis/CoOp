package softeng.coop.ui.presenters;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.DataUtilities;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class StudentCardPresenter extends Presenter<BeanItem<Student>, ICoopContext, IStudentCardView>
{

	public StudentCardPresenter(IStudentCardView view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
		if (getView().getModel() == null)
		{
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
		}
	}

	@Override
	protected void attachToView()
	{
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
		
		getView().getOkCancelView().addOkFailedListener(new IListener<RuntimeException>()
		{
			@Override
			public void onEvent(RuntimeException event)
			{
				revert();
			}
		});
		
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
		if (getView().getModel() == null) return;
		
		if (!getView().isDataValid())
		{
			vote.markAsFailed();
			
			getContext().showInvalidDataNotification();

			return;
		}
		
		StudentsManager manager = getContext().getSession().getStudentsManager();
		
		if (manager == null || !manager.isWriteable())
		{
			vote.markAsFailed();
			
			getContext().showAccessDeniedNotification();
			
			return;
		}
		
		Student student = getView().getModel().getBean();
		
		boolean isStudentFirstTimeValid = isStudentFirstTimeValid(student); 
		
		StudentsWriterManager writerManager = manager.getWriterManager();
		
		getView().commitChangesToModel();
		
		writerManager.persistStudent(getView().getModel().getBean());

		getContext().showDataSavedNotification();
		
		// Is the student data valid for the first time?
		if (isStudentFirstTimeValid) 
		{
			// Refresh tabs visibility, because more tabs could be now visible.
			getContext().fireSelectedCoopChanged();
			
			// If there are available co-ops for registration, offer them,
			// else do nothing.
			getContext().suggestRegistration();
		}
	}

	private boolean isStudentFirstTimeValid(Student student)
	{
		// Trick: If student has an Identification Number, 
		// which is a required field, this means that a successful UI
		// save has taken place that passed all UI validators.
		
		return !DataUtilities.studentHasCompleteProvision(student);
	}
	
	private void revert()
	{
		Session session = getContext().getSession();

		session.revertOutstanding();
		
		getView().dataBind();
		
	}

}
