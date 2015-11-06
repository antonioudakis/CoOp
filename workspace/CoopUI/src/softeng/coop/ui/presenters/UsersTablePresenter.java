package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.faculties.*;
import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.ui.vaadin.mvp.*;

public class UsersTablePresenter 
	extends Presenter<Collection<AuthenticatedUser>, ICoopContext, IUserTableView>
{
	private StudentsWriterManager studentManager;
	private FacultyUsersWriterManager facultyManager;

	private static final int STUDENT_ROLE_ID = 3;
	private static final int OPERATOR_ROLE_ID = 2;
	private static final int PROFESSOR_ROLE_ID = 4;
	
	public UsersTablePresenter(IUserTableView view) 
	{
		super(view);
	}

	@Override
	protected void attachToView() 
	{
		getView().addAddingListener(new IListener<ModelEvent<AuthenticatedUser>>() 
		{
			@Override
			public void onEvent(ModelEvent<AuthenticatedUser> event) 
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<AuthenticatedUser>>() 
		{
			@Override
			public void onEvent(ModelEvent<AuthenticatedUser> event) 
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<AuthenticatedUser>>() 
		{
			@Override
			public void onEvent(ModelEvent<AuthenticatedUser> event) 
			{
				onDelete(event.getModel());
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<AuthenticatedUser>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<AuthenticatedUser> event)
			{
				canDelete(event);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<AuthenticatedUser> event)
	{
		AuthenticatedUser user = event.getElement();
		
		LocalizedMessageAppender appender = new LocalizedMessageAppender();
		
		if (!user.getReports().isEmpty())
		{
			appender.add("USER_HAS_REPORTS_CAPTION");
			
			event.markAsFailed();
		}
		
		if (user instanceof Student)
		{
			Student student = (Student)user;
			
			if (!student.getRegistrations().isEmpty())
			{
				appender.add("STUDENT_HAS_REGISTRATIONS_CAPTION");
				
				event.markAsFailed();
			}
		}
		
		if (user instanceof FacultyUser)
		{
			FacultyUser facultyUser = (FacultyUser)user;
			
			if (user instanceof Professor)
			{
				Professor professor = (Professor)facultyUser;
				
				if (!professor.getAcademicallyDirectedCoOps().isEmpty())
				{
					appender.add("PROFESSOR_IS_ACADEMIC_DIRECTOR_CAPTION");
					
					event.markAsFailed();
				}
				if (!professor.getInstitutionallyDirectedCoOps().isEmpty())
				{
					appender.add("PROFESSOR_IS_INSTITUTIONAL_DIRECTOR_CAPTION");
					
					event.markAsFailed();
				}
				if (!professor.getScientificallyDirectedCoOps().isEmpty())
				{
					appender.add("PROFESSOR_IS_SCIENTIFIC_DIRECTOR_CAPTION");
					
					event.markAsFailed();
				}
				if (!professor.getSupervisedCoOps().isEmpty())
				{
					appender.add("PROFESSOR_SUPERVISES_COOPS_CAPTION");
					
					event.markAsFailed();
				}
				if (!professor.getSupervisedGroups().isEmpty())
				{
					appender.add("PROFESSOR_SUPERVISES_GROUPS_CAPTION");
					
					event.markAsFailed();
				}
				if (!professor.getSupervisedJobs().isEmpty())
				{
					appender.add("PROFESSOR_SUPERVISES_JOBS_CAPTION");
					
					event.markAsFailed();
				}
				if (!professor.getSupervisedJobPostings().isEmpty())
				{
					appender.add("PROFESSOR_SUPERVISES_JOB_POSTINGS_CAPTION");
					
					event.markAsFailed();
				}
			}
			
		}
		
		if (!appender.isEmpty())
		{
			getContext().showEntityInUseNotification(appender.toString());
		}
	}

	protected void onDelete(AuthenticatedUser model) 
	{
		try
		{
			if (model.getTracking() != null) 
			{
				model.getTracking().setCreatedBy(null);
				model.getTracking().setModifiedBy(null);
			}
			
			if (model instanceof Student)
			{
				Student student = (Student)model;
				
				if (model.getId() > 0) 
				{
					studentManager.persistStudent(student);
					studentManager.deleteStudent(student);
				}
			}
			else if (model instanceof FacultyUser)
			{
				if (model.getId() > 0) 
				{
					FacultyUser facultyUser = (FacultyUser)model;
					
					facultyManager.persistFacultyUser(facultyUser);
					facultyManager.deleteFacultyUser(facultyUser);
				}
			}
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			throw ex;
		}
		
		getContext().showDataSavedNotification();
	}
	
	private void markAsChanged(AuthenticatedUser user)
	{
		switch (getView().getUserType())
		{
			case Student:
				studentManager.markAsChanged(user);
				
			case Faculty:
			case Professor:
				facultyManager.markAsChanged(user);
				
			default:
				break;
		}
	}

	protected void onEdit(AuthenticatedUser model) 
	{
		markAsChanged(model);
	}

	protected void onAdd(AuthenticatedUser model) 
	{
		Session session = getContext().getSession();
		
		Role role = null;
		
		switch (getView().getUserType())
		{
			case Student:
				role = session.getRole(STUDENT_ROLE_ID);
				break;
				
			case Professor:
				role = session.getRole(PROFESSOR_ROLE_ID);
				break;
				
			case Faculty:
				role = session.getRole(OPERATOR_ROLE_ID);
				break;
		}
		
		if (role != null)
		{
			model.getRoles().add(role);
			
			if (session.isLoaded(role, "users"))
			{
				role.getUsers().add(model);
			}
		}
		
		markAsChanged(model);
	}

	@Override
	protected void setupView() 
	{
		Session session = getContext().getSession();
		
		if (!session.getFacultyUsersManager().isWriteable() || 
				!session.getStudentsManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
		}
		
		studentManager = session.getStudentsManager().getWriterManager();
		facultyManager = session.getFacultyUsersManager().getWriterManager();
	}

}
