package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.jobpostings.*;
import softeng.coop.business.jobs.*;
import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.ITwinSelectTableComponentView.FilterOptions;

import softeng.ui.vaadin.mvp.*;

public class TwinSelectTablePresenter 
	extends Presenter<CoOp, ICoopContext, ITwinSelectTableComponentView>
{
	private JobsWriterManager jobsWriterManager;
	
	private PropertyChangeEventSubscription<ITwinSelectTableComponentView.FilterOptions> 
		jobPostingFilterOptionsSubscription;
	private PropertyChangeEventSubscription<ITwinSelectTableComponentView.FilterOptions>
		groupFilterOptionsSubscription;
	
	private IListener<CoOp> selectedCoopChangedListener;
	
	public TwinSelectTablePresenter(ITwinSelectTableComponentView view) 
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
		
		jobPostingFilterOptionsSubscription = 
			new PropertyChangeEventSubscription<ITwinSelectTableComponentView.FilterOptions>();
		
		groupFilterOptionsSubscription =
			new PropertyChangeEventSubscription<ITwinSelectTableComponentView.FilterOptions>();
		
	}

	private void bindToCoOp(CoOp coop)
	{
		if (jobsWriterManager == null) return;
		
		getView().setModel(coop);
		
		if (coop != null )
		{	
			updateJobPostingList();
			
			updateGroupList();
			
			getView().setJobs(findCoopJobs(coop));
		}
		
		getView().dataBind();
	}
	
	private void initializeManagers() 
	{
		jobsWriterManager = this.getContext().getSession().getJobsManager().getWriterManager();
		
		if (jobsWriterManager == null)
			getContext().showAccessDeniedNotification();
	}

	@Override
	protected void setupView() 
	{
		initializeManagers();
		
		CoOp selectedCoop = this.getContext().getSelectedCoop();
		
		bindToCoOp(selectedCoop);
	}

	@Override
	protected void attachToView() 
	{
		getContext().addSelectedCoopChangedListener(selectedCoopChangedListener);
		
		//set eventListeners
		getView().addJobAssignedListener(new IViewListener<CoOp, ICoopContext, ITwinSelectTableComponentView>() 
		{
			
			@Override
			public void onEvent(
					ViewEvent<CoOp, ICoopContext, ITwinSelectTableComponentView> event) 
			{
				onAssignJob();
			}
		});
		
		getView().addJobAssignmentRemovedListener(new IViewListener<CoOp, ICoopContext, ITwinSelectTableComponentView>() 
		{
			
			@Override
			public void onEvent(
					ViewEvent<CoOp, ICoopContext, ITwinSelectTableComponentView> event) 
			{
				onUnassignJob();
			}
		});
		
		jobPostingFilterOptionsSubscription.add(new IListener<PropertyChangeEvent<FilterOptions>>() 
		{
			
			@Override
			public void onEvent(PropertyChangeEvent<FilterOptions> event) 
			{
				updateJobPostingList();
				
			}
		});
		
		jobPostingFilterOptionsSubscription.startListeningTo(getView().getJobPostingsFilterOptions());
		
		groupFilterOptionsSubscription.add(new IListener<PropertyChangeEvent<FilterOptions>>() 
		{
			
			@Override
			public void onEvent(PropertyChangeEvent<FilterOptions> event) 
			{
				updateGroupList();
				
			}
		});
		
		groupFilterOptionsSubscription.startListeningTo(getView().getGroupFilterOptions());
	}
	
	@Override
	protected void detachFromView()
	{
		getContext().removeSelectedCoopChangedListener(selectedCoopChangedListener);
	}

	protected void updateGroupList() 
	{	
		FilterOptions filterOptions = getView().getGroupFilterOptions().getBean();
	
		StudentsManager manager = getContext().getSession().getStudentsManager();
		
		if (manager == null)
		{
			throw new CoopUIAccessDeniedException(getContext());
		}
		
		GroupSearchCriteria criteria = new GroupSearchCriteria();
		
		criteria.setAssignedToJob(false);
		
		criteria.setEmpty(false);
		
		criteria.setCoop(getView().getModel());
		
		if (filterOptions.isQualifiedForAssignmentOnly())
			criteria.setQualifiedForAssignment(true);
		
		if (filterOptions.getLocationFilter() != null)
		{
			criteria.setLocation(filterOptions.getLocationFilter());
		}
		
		if (filterOptions.getCategoryFilter() != null)
		{
			criteria.setCategory(filterOptions.getCategoryFilter());
		}
		
		SearchResult<Group> result = manager.searchGroups(criteria); 
		
		getView().setGroups(new Vector<Group>(result.getList()));
	}

	protected void updateJobPostingList() 
	{
		JobPostingsManager manager = getContext().getSession().getJobPostingsManager();
		
		if (manager == null)
		{
			throw new CoopUIAccessDeniedException(getContext());
		}
		
		CoOp coop = getContext().getSelectedCoop();
		
		if (coop == null)
		{
			return;
		}
		
		FilterOptions filterOptions = getView().getJobPostingsFilterOptions().getBean();
		
		JobPostingsSearchCriteria criteria = new JobPostingsSearchCriteria();
		
		criteria.setCoop(coop);
		
		criteria.setFull(false);
		
		if (filterOptions.getLocationFilter() != null)
			criteria.setLocation(filterOptions.getLocationFilter());
		
		if (filterOptions.getCategoryFilter() != null)
			criteria.setCategory(filterOptions.getCategoryFilter());
		
		SearchResult<JobPosting> result = manager.searchJobPostings(criteria);
		
//		Vector<JobPosting> jobPostingVector = new Vector<JobPosting>();
//		
//		for (JobPosting jobPosting : result.getList())
//		{
//			if (jobPosting.getRemainingSeats()>0)
//			{
//				jobPostingVector.add(jobPosting);
//			}
//		}
//		
//		getView().setJobPostings(jobPostingVector);
		getView().setJobPostings(result.getList());
	}
	
	private List<Job> findCoopJobs(CoOp currentCoOp)
	{	
//		StringBuffer notificationMessage = new StringBuffer();
//		boolean registrationWithoutGroups = false;
//		Vector<Job> setOfAssignedJobs = new Vector<Job>();
//		
//		for (Registration registration : currentCoOp.getRegistrations())
//		{
//			if (registration.getGroup() == null)
//			{
//				registrationWithoutGroups = true;
//				notificationMessage.append(registration.getStudent().getSurname() + ", ");
//				continue;
//			}
//			Job currentJob = registration.getGroup().getJob();
//			if (currentJob != null)
//				setOfAssignedJobs.add(currentJob);
//		}
//		
//		//if there are groups without registration show an warning
//		if (registrationWithoutGroups)
//		{
//			notificationMessage.deleteCharAt(notificationMessage.length()-2);
//			
////			this.getView().getApplication().getMainWindow()
////			.showNotification(getLocalizedString("REGISTRATIONS_WITHOUT_GROUPS_MESSAGE")
////					, notificationMessage.toString()
////					, Window.Notification.TYPE_WARNING_MESSAGE);
//		}
//		
//		return setOfAssignedJobs;
		
		JobsSearchCriteria criteria = new JobsSearchCriteria();
		
		criteria.setCoop(currentCoOp);

		SearchResult<Job> results = jobsWriterManager.searchJobs(criteria);

		return results.getList();
	}

	private void revert(Job assignedJob)
	{
		Session session = getContext().getSession();
		
		session.revertOutstanding();
		
		if (assignedJob.getId() > 0)
		{
			session.refreshEntity(assignedJob);
			
			if (assignedJob.getGroup() != null)
				session.refreshEntity(assignedJob.getGroup());
			
			session.refreshEntity(assignedJob.getJobPosting());
		}
		
		CoOp selectedCoop = getContext().getSelectedCoop();
		
		bindToCoOp(selectedCoop);
	}

	private void onAssignJob()
	{
		JobPosting	selectedJobPosting = getView().getSelectedJobPosting();
		Group selectedGroup = getView().getSelectedGroup();
		
		if (selectedJobPosting == null || selectedGroup == null) return;
		
		TransactionScope scope = jobsWriterManager.beginTransaction();
		
		Job assignedJob = null;
		
		int remainingSeats = selectedJobPosting.getSeatsNumber() - selectedJobPosting.getJobs().size();
		
		try
		{
			try
			{
				assignedJob = jobsWriterManager.createJob(
						selectedJobPosting, 
						selectedGroup, 
						getView().getJobStartDate()
				);
				
				jobsWriterManager.changeState(assignedJob, JobStateType.Assigned);
				
				scope.commit();	
				
			}
			finally
			{
				scope.dispose();
			}
		}
		catch (RuntimeException ex)
		{
			if (assignedJob != null) revert(assignedJob);

			throw ex;
		}
		
		//after successful commit refresh containers
		remainingSeats--;
		
		getView().addJobToContainer(assignedJob);
		
//				if (selectedJobPosting.getRemainingSeats() == 0)
//					getView().removeJobPostingFromContainer(selectedJobPosting);
		if (remainingSeats == 0)
			getView().removeJobPostingFromContainer(selectedJobPosting);
		
		getView().removeGroupFromContainer(selectedGroup);
		
		getView().refreshJobPostingsTable();
		
		getContext().showDataSavedNotification();
	}

	private void onUnassignJob()
	{
		Job selectedJob = getView().getSelectedJob();
		
		if (selectedJob == null) return;
		
		for (JobPart jobPart : selectedJob.getJobParts())
		{
			if (jobPart.getPayments().size() > 0)
			{
				getContext().showEntityInUseNotification(
						getLocalizedString("JOB_HAS_PAYMENTS_CAPTION"));
				
				return;
			}
		}
		
		Group jobGroup = selectedJob.getGroup();
		
		JobPosting jobPosting = selectedJob.getJobPosting();
		
		TransactionScope scope = jobsWriterManager.beginTransaction();
		
		int remainingSeats = jobPosting.getSeatsNumber() - jobPosting.getJobs().size();
		
		try
		{
			try
			{
				jobPosting.getJobs().remove(selectedJob);
				
				jobsWriterManager.deleteJob(selectedJob);
				
				jobGroup.setJob(null);
				
				scope.commit();
			}
			finally
			{
				scope.dispose();
			}
		}
		catch (RuntimeException ex)
		{
			revert(selectedJob);

			throw ex;
		}
		
		remainingSeats++;

		//after successful commit refresh containers
		getView().removeJobFromContainer(selectedJob);
		
		if (!jobGroup.getRegistrations().isEmpty())
			getView().addGroupToContainer(jobGroup);
		
//				if (jobJobPosting.getRemainingSeats() == 1)
//					getView().addJobPostingToContainer(jobJobPosting);
		if (remainingSeats == 1)
			getView().addJobPostingToContainer(jobPosting);
		
		getView().refreshJobPostingsTable();
		
		getContext().showDataSavedNotification();
	}

}
