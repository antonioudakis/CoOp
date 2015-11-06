package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.faculties.*;
import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.IUserManagementCardView.FilterOptions;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class UserManagementCardPresenter extends Presenter<BeanItem<Department>, ICoopContext, IUserManagementCardView> 
{
	private Session session;
	private StudentsWriterManager studentsManager;
	private FacultyUsersWriterManager facultiesManager;
	private PropertyChangeEventSubscription<FilterOptions> filterOptionsSubscription;
	
	private IListener<CoOp> selectedCoOpChangedListener;
	
	public UserManagementCardPresenter(IUserManagementCardView view) 
	{
		super(view);
		
		filterOptionsSubscription = 
			new PropertyChangeEventSubscription<FilterOptions>();
	}

	@Override
	protected void attachToView() 
	{
		selectedCoOpChangedListener = new IListener<CoOp>()
		{
			@Override
			public void onEvent(CoOp coop)
			{
				onSelectedCoopChanged(coop);
			}
		};
		
		getContext().addSelectedCoopChangedListener(selectedCoOpChangedListener);
		
		getView().addSelectedUserTypeChangeListener(new IViewListener<BeanItem<Department>, ICoopContext, IUserManagementCardView>()
		{
			@Override
			public void onEvent(ViewEvent<BeanItem<Department>, ICoopContext, IUserManagementCardView> event)
			{
				onSelectedUserTypeChanged();
			}
		});
		
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
			public void onEvent(
					ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event) 
			{
				cancel();
				
			}
		});
		
		filterOptionsSubscription.add(new IListener<PropertyChangeEvent<FilterOptions>>() 
		{
			
			@Override
			public void onEvent(PropertyChangeEvent<FilterOptions> event) 
			{
				//updateStudentsList();
				updateUsersTable(getContext().getSelectedCoop());
			}
		});
		
		filterOptionsSubscription.startListeningTo(getView().getFilterOptions());
		
		getView().getButtonRegisterCurrentCoop().addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				onAddingRegistration();
				
			}
		});
		
		getView().getButtonRemoveSelectedRegistration().addListener(new ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				onRemovingSelectedRegistration();
				
			}
		});
	}

	protected void onSelectedUserTypeChanged()
	{
		updateUsersTable(getContext().getSelectedCoop());
	}

	protected void onSelectedCoopChanged(CoOp coop)
	{
		updateUsersTable(coop);
	}

	protected void onRemovingSelectedRegistration() 
	{
		Registration selectedRegistration = getView().getSelectedRegistration();
		
		if (selectedRegistration == null)
		{
			return;
		}
		
		Student student = (Student) getView().getSelectedUser().getBean();
		
		boolean isExistingUser = student.getId()>0;
		
		if (!isExistingUser)
		{ 
			getContext().showNotification(
					getLocalizedString("ERROR_SAVE_STUDENT_CAPTION"), 
					getLocalizedString("ERROR_SAVE_STUDENT_DESCRIPTION"), 
					Window.Notification.TYPE_WARNING_MESSAGE);
			return;
		}
		
		Group group = selectedRegistration.getGroup();
		
		CoOp coop = selectedRegistration.getCoop();
		
		// Now check proactively if the registration can be deleted. 
		// The registration must not have payments and must not be assigned to jobs.
		
		LocalizedMessageAppender appender = new LocalizedMessageAppender();
		
		if (group.getJob() != null)
			appender.add("REGISTRATION_IS_ASSIGNED_TO_JOB_CAPTION");
		
		if (selectedRegistration.getPayments().size() > 0)
			appender.add("REGISTRATION_HAS_PAYMENTS_CAPTION");
		
		if (!appender.isEmpty())
		{
			getContext().showEntityInUseNotification(appender.toString());
			return;
		}
		
		// Proactive check passed. Start transaction.
		
		try
		{
			TransactionScope transactionScope = studentsManager.beginTransaction();
			
			try
			{
				student.getRegistrations().remove(selectedRegistration);
				
				if (student.getActiveRegistration() == selectedRegistration)
				{
					student.setActiveRegistration(null);
					student.setDefaultCoOp(null);
				}
				
				selectedRegistration.setStudent(null);
				
				if (group != null)
				{	
					group.getRegistrations().remove(selectedRegistration);
					
					selectedRegistration.setGroup(null);
					
					studentsManager.persistGroup(group);
						
					studentsManager.persistRegistration(selectedRegistration);
					
					if (group.getRegistrations().isEmpty())
						studentsManager.deleteGroup(group);
				}
		
				studentsManager.deleteRegistration(selectedRegistration);
				
				transactionScope.commit();
			}
			finally
			{
				transactionScope.dispose();
			}
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			
			throw ex;
		}

		getView().getCoOpsRegistrationTable().removeItem(selectedRegistration);
		
		if (coop != null)
		{
			if (group != null && session.isLoaded(coop, "groups"))
				coop.getGroups().remove(group);
			
			if (session.isLoaded(coop, "registrations"))
				coop.getRegistrations().remove(selectedRegistration);
		}
		
		getContext().showDataSavedNotification();
		
		getView().getButtonRegisterCurrentCoop().setEnabled(getContext().getSelectedCoop() != null);
		
	}

	protected void onAddingRegistration() 
	{
		//if there is registration, the user is a student
		
		if (getView().getSelectedUser() == null) return;
		
		Student student = (Student) getView().getSelectedUser().getBean();
		
		boolean isExistingUser = student.getId()>0;
		
		if (!isExistingUser)
		{
			getContext().showNotification(
					getLocalizedString("ERROR_SAVE_STUDENT_CAPTION"), 
					getLocalizedString("ERROR_SAVE_STUDENT_DESCRIPTION"), 
					Window.Notification.TYPE_ERROR_MESSAGE);
			
			return;
		}
		
		CoOp coop = getContext().getSelectedCoop();
		
		if (coop == null) 
		{
			getContext().showNotification(
					getLocalizedString("NO_SELECTED_COOP_FOR_REGISTRATION_CAPTION"), 
					getLocalizedString("NO_SELECTED_COOP_FOR_REGISTRATION_DESCRIPTION"), 
					Window.Notification.TYPE_ERROR_MESSAGE);
			
			return;
		}
		
		//search if current coop exists in user registrations
		
		for (Registration registration : student.getRegistrations())
		{
			if (registration.getCoop() == coop)
			{
				// The registration already exists so there is no need to be added
				getContext().showNotification(
						getLocalizedString("REGISTRATION_ALREADY_EXISTS_CAPTION"), 
						getLocalizedString("REGISTRATION_ALREADY_EXISTS_DESCRIPTION"), 
						Window.Notification.TYPE_WARNING_MESSAGE);
				
				return;
			}
		}
		
		Registration registration = Constructor.createRegistration();
		
		TransactionScope transactionScope = studentsManager.beginTransaction();

		try
		{
			try
			{
				Group group = Constructor.createGroup();
				group.setCoOp(coop);
				group.getRegistrations().add(registration);
				group.setComments(student.getSurname());
				
				registration.setCoop(getContext().getSelectedCoop());
				registration.setStudent(student);
				registration.setRegistrationDate(new Date());
				student.getRegistrations().add(registration);
				
				
				studentsManager.persistRegistration(registration);
				
				student.setActiveRegistration(registration);
				student.setDefaultCoOp(coop);
				
				studentsManager.persistStudent(student);
				
				studentsManager.persistGroup(group);
				
				registration.setGroup(group);
				
				studentsManager.persistRegistration(registration);
				
				transactionScope.commit();
			}
			finally
			{
				transactionScope.dispose();
			}
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			
			throw ex;
		}

		getView().getCoOpsRegistrationTable().addItem(registration);
		
		if (session.isLoaded(coop, "group") && registration.getGroup() != null)
		{
			coop.getGroups().add(registration.getGroup());
		}
		
		if (session.isLoaded(coop, "registrations"))
		{
			coop.getRegistrations().add(registration);
		}

		getContext().showDataSavedNotification();
		
	}

	private void save()
	{	
		if (studentsManager == null || facultiesManager == null) return;
		
		if (getView().getModel() == null) return;
		
		if (getView().getSelectedUser() == null) return;
		
		AuthenticatedUser selectedUser = getView().getSelectedUser().getBean();
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		getView().commitChangesToModel();
		
		//find if the component is adding a new user
		boolean isExistingUser = selectedUser.getId() > 0;
		
		try
		{
		
			// If the user is new, first save having as creator the operating user. 
			if (!isExistingUser)
			{
				if (selectedUser instanceof Student)
				{
					studentsManager.persistStudent((Student)selectedUser);
				}
				else if (selectedUser instanceof FacultyUser)
				{
					facultiesManager.persistFacultyUser((FacultyUser)selectedUser);
				}
			}
		
			TransactionScope transactionScope = null;
			
			try
			{
				if (selectedUser instanceof Student)
				{
					transactionScope = studentsManager.beginTransaction();
					
					if (!isExistingUser)
					{
						Tracking tracking = new Tracking();
						tracking.setCreated(new Date());
						tracking.setCreatedBy(selectedUser);
						tracking.setModified(new Date());
						tracking.setModifiedBy(getContext().getSession().getAuthenticatedUser());
						selectedUser.setTracking(tracking);
							
						studentsManager.persistStudent((Student)selectedUser);
					}
					else
					{
						selectedUser.getTracking().setModified(new Date());
						selectedUser.getTracking().setModifiedBy(getContext().getSession().getAuthenticatedUser());
							
						studentsManager.persistStudent((Student) selectedUser);
					}
				}
				else if (selectedUser instanceof Professor)
				{
					transactionScope = facultiesManager.beginTransaction();
					
					Tracking tracking = new Tracking();
					
					if (!isExistingUser)
					{
						facultiesManager.persistProfessor((Professor)selectedUser);
						
						tracking.setCreated(new Date());
						tracking.setCreatedBy(selectedUser);
						tracking.setModified(new Date());
						tracking.setModifiedBy(getView().getContext().getSession().getAuthenticatedUser());	
						
						selectedUser.setTracking(tracking);
					}
					
					facultiesManager.persistProfessor((Professor) selectedUser);	
					
				}
				else
				{
					transactionScope = facultiesManager.beginTransaction();
					
					Tracking tracking = new Tracking();
					
					if (!isExistingUser)
					{
						facultiesManager.persistFacultyUser((FacultyUser) selectedUser);
						
						tracking.setCreated(new Date());
						tracking.setCreatedBy(selectedUser);
						tracking.setModified(new Date());
						tracking.setModifiedBy(getView().getContext().getSession().getAuthenticatedUser());	
						
						selectedUser.setTracking(tracking);
					}
					
					facultiesManager.persistFacultyUser((FacultyUser) selectedUser);
				}
				
				transactionScope.commit();
			}
			finally
			{
				transactionScope.dispose();
			}
			
			getContext().showDataSavedNotification();
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			
			getView().dataBind();
			
			if (!isExistingUser)
			{
				selectedUser.setId(0);
			}
			
			throw ex;
		}
	}

	private void cancel()
	{
		getView().discardChanges();
	}
	
	@Override
	protected void setupView() 
	{
		session = getContext().getSession();
		
		if (session.getStudentsManager() != null && session.getStudentsManager().isWriteable())
			studentsManager = session.getStudentsManager().getWriterManager();
		
		if (session.getFacultyUsersManager() != null && session.getFacultyUsersManager().isWriteable())
			facultiesManager = session.getFacultyUsersManager().getWriterManager();
		
		if (studentsManager == null && facultiesManager == null)
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		Department department = session.getAuthenticatedUser().getDepartment();
		
		if (department != null)
		{
			getView().setModel(new DataItem<Department>(department, session.getBaseManager()));
		}
		else
		{
			getView().setModel(null);
			throw new CoopUIUserException(getLocalizedString("NO_CURRENT_COOP_SELECTED_ERROR_MESSAGE"));
		}
		
		getView().dataBind();
		
		updateUsersTable(getContext().getSelectedCoop());
	}

	@Override
	protected void detachFromView()
	{
		if (selectedCoOpChangedListener != null)
			getContext().removeSelectedCoopChangedListener(selectedCoOpChangedListener);
		
		super.detachFromView();
	}
	
	private void updateUsersTable(CoOp coop)
	{
		IUserManagementCardView view = getView();
		
		switch (view.getSelectedUserType())
		{
			case None:
				view.getUserTable().setModel(null);
				break;
				
			case Student:
				if (studentsManager == null)
				{
					view.getUserTable().setModel(null);
				}
				else
				{
					FilterOptions filterOptions = getView().getFilterOptions().getBean();
					
					StudentsSearchCriteria criteria = new StudentsSearchCriteria();
					
					if (getContext().getSelectedCoop() != null && filterOptions.isInSelectedCoop())
					{
						criteria.setCoop(getContext().getSelectedCoop());
					}
					
					if (filterOptions.isNoAma())
						criteria.setHasAMA(false);
					
					if (filterOptions.isNoIban())
						criteria.setHasIBAN(false);
					
					if (filterOptions.isNotAssignedToJob())
						criteria.setAssignedToJob(false);
					
					if (filterOptions.isWithoutUsername())
						criteria.setHasUserName(false);
					
					if (filterOptions.getSurnamePrefix() != null)
						criteria.setSurname(filterOptions.getSurnamePrefix());
					
					if (filterOptions.getAcademicYear() != null)
						criteria.setAcademicYear(filterOptions.getAcademicYear());
					
					if (filterOptions.getCreationYear() != null)
					{
						try
						{
							int creationYear = Integer.parseInt(filterOptions.getCreationYear());
							
							Calendar startCalendar = Calendar.getInstance();
							
							startCalendar.set(creationYear, 0, 1);
							
							Calendar endCalendar = Calendar.getInstance();
							
							endCalendar.set(creationYear + 1, 0, 1);
							
							criteria.setCreatedAfter(startCalendar.getTime());
							criteria.setCreatedBefore(endCalendar.getTime());
						}
						catch (NumberFormatException e)
						{
							// Do nothing if the creation year is not parseable.
						}
					}
					
					if (filterOptions.isAllRequiredFieldsComplete())
					{
						criteria.setHasFatherName(true);
						criteria.setHasMotherName(true);
						criteria.setHasDateOfBirth(true);
						criteria.setMinAddressesCount(1);
						criteria.setMinTelephonesCount(2);
						criteria.setHasSerialNumber(true);
						criteria.setHasIdNumber(true);
						criteria.setHasIdIssuer(true);
						criteria.setHasTaxId(true);
						criteria.setHasTaxDivision(true);
						criteria.setHasSocialSecurityId(true);
						criteria.setHasIBAN(true);
						criteria.setHasNationality(true);
					}
					
					SearchResult<Student> result = studentsManager.searchStudents(criteria);
					
					List<Student> resultsList = result.getList();
					
					List<AuthenticatedUser> usersList = new ArrayList<AuthenticatedUser>(resultsList.size());
					
					usersList.addAll(resultsList);
					
					view.getUserTable().setModel(usersList);
					
					view.getUserTable().setUserType(UserType.Student);
				}

				break;
				
			case Faculty:
				if (facultiesManager == null)
				{
					view.getUserTable().setModel(null);
				}
				else
				{
					FilterOptions filterOptions = getView().getFilterOptions().getBean();
					
					FacultyUserSearchCriteria criteria = new FacultyUserSearchCriteria();
					
					criteria.setUniversity(filterOptions.getUniversity());
					
					criteria.setDepartment(filterOptions.getDepartment());
					
					criteria.setDivision(filterOptions.getDivision());
					
					if (filterOptions.getSurnamePrefix() != null)
						criteria.setSurname(filterOptions.getSurnamePrefix());
					
					SearchResult<FacultyUser> result = 
						facultiesManager.searchFacultyUsers(criteria);
					
					List<FacultyUser> resultsList = result.getList();
					
					List<AuthenticatedUser> usersList = new ArrayList<AuthenticatedUser>(resultsList.size());
					
					for (FacultyUser user : resultsList)
					{
						if (!(user instanceof Professor))
						{
							usersList.add(user);
						}
					}
					
					view.getUserTable().setModel(usersList);

					view.getUserTable().setUserType(UserType.Faculty);
				}
				
				break;
				
			case Professor:
				if (facultiesManager == null)
				{
					view.getUserTable().setModel(null);
				}
				else
				{
					FilterOptions filterOptions = getView().getFilterOptions().getBean();
					
					ProfessorSearchCriteria criteria = new ProfessorSearchCriteria();

					criteria.setUniversity(filterOptions.getUniversity());
					
					criteria.setDepartment(filterOptions.getDepartment());
					
					criteria.setDivision(filterOptions.getDivision());
					
					if (filterOptions.getSurnamePrefix() != null)
						criteria.setSurname(filterOptions.getSurnamePrefix());
					
					SearchResult<Professor> result = facultiesManager.searchProfessors(criteria);
					
					List<Professor> resultsList = result.getList();
					
					List<AuthenticatedUser> usersList = new ArrayList<AuthenticatedUser>(resultsList.size());
					
					usersList.addAll(resultsList);
					
					view.getUserTable().setModel(usersList);

					view.getUserTable().setUserType(UserType.Professor);
				}
				
				break;
		}
		
		view.getUserTable().dataBind();
		view.getUserTable().setSelectedValue(null);
	}

}
