package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;

import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.IGroupManagementCardView.FilterOptions;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class GroupManagementCardPresenter 
extends Presenter<BeanItem<CoOp>, ICoopContext, IGroupManagementCardView> 
{
	private StudentsWriterManager manager;

	private PropertyChangeEventSubscription<IGroupManagementCardView.FilterOptions>
		filterOptionsSubscription;
	private IListener<CoOp> selectedCoopChangedListener;
	
	public GroupManagementCardPresenter(IGroupManagementCardView view) 
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
			new PropertyChangeEventSubscription<IGroupManagementCardView.FilterOptions>();
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
		
		updateGroupTableList();	
	}

	private void updateGroupTableList() 
	{	
		if (getContext().getSelectedCoop() == null)
		{
			getView().getGroupTableView().setModel(null);
			
			getView().getGroupTableView().dataBind();
			
			return;
		}
		
		FilterOptions filterOptions =
			getView().getFilterOptions().getBean();
		
		GroupSearchCriteria criteria = new GroupSearchCriteria();
		
		criteria.setCoop(getContext().getSelectedCoop());

		if (getContext().getSelectedCoop().isHasGroupGrade())
		{
			//set the additional fields
			if (filterOptions.isGraded())
			{
				criteria.setMaxGrade(10f);
				criteria.setMinGrade(0f);
			}
			
			if (filterOptions.isNotGraded())
			{
				criteria.setMaxGrade(null);
				criteria.setMinGrade(null);
			}
			
			if (filterOptions.isPassed())
				criteria.setPassed(filterOptions.isPassed());
		}
		
		if (filterOptions.isEmpty())
			criteria.setEmpty(true);	

		if (filterOptions.isIncomplete())
			criteria.setFull(false);
	
//		if (filterOptions.isAssignedJobs())
//			criteria.setAssignedToJob(true);
//		
//		if (filterOptions.isNotAssignedJobs())
//			criteria.setAssignedToJob(false);
		
		SearchResult<Group> groups = manager.searchGroups(criteria);
		
		//find groups with/without jobs
		Collection<Group> finalGroups = new ArrayList<Group>();
		if (filterOptions.isAssignedJobs())
		{
			for (Group group : groups.getList())
			{
				if (group.getJob()!= null)
					finalGroups.add(group);
			}
		}
		else if (filterOptions.isNotAssignedJobs())
		{
			for (Group group : groups.getList())
			{
				if (group.getJob() == null)
					finalGroups.add(group);
			}
		}
		
		if (filterOptions.isAssignedJobs() || filterOptions.isNotAssignedJobs())
			getView().getGroupTableView().setModel(finalGroups);
		else 
			getView().getGroupTableView().setModel(groups.getList());
		getView().getGroupTableView().setParentModel(getContext().getSelectedCoop());
		
		getView().getGroupTableView().dataBind();
			
		
	}

	@Override
	protected void attachToView() 
	{
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
		
		getView().getOkCancelView().addOkFailedListener(new IListener<RuntimeException>() 
		{
			
			@Override
			public void onEvent(RuntimeException event) 
			{
				revert();
			}
		});
		
		
		
		filterOptionsSubscription.add(new IListener<PropertyChangeEvent<FilterOptions>>() 
		{
			
			@Override
			public void onEvent(PropertyChangeEvent<FilterOptions> event) 
			{
				updateGroupTableList();	
			}
		});
		
		filterOptionsSubscription.startListeningTo(getView().getFilterOptions());
		
	}

	@Override
	protected void detachFromView()
	{
		getContext().removeSelectedCoopChangedListener(selectedCoopChangedListener);
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
		
		if (getView().getSelectedGroup() == null)
			return;
		
		if (!getView().isDataValid())
		{
			getContext().showInvalidDataNotification();
			
			return;
		}
		
		Group selectedGroup = getView().getSelectedGroup().getBean();
		
		getView().commitChangesToModel();
		
		TransactionScope scope = manager.beginTransaction();
		
		try	
		{
			manager.persistGroup(selectedGroup);
			
//			for (Registration registration : selectedGroup.getRegistrations())
//				manager.persistRegistration(registration);
			
			scope.commit();

			getContext().showDataSavedNotification();
			
			getView().getGroupTableView().refreshRowCache();
		}
		finally
		{
			scope.dispose();
		}
		
		getView().dataBind();
		
	}


	@Override
	protected void setupView() 
	{
		Session session = getContext().getSession();
		
		if (!session.getStudentsManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		manager = session.getStudentsManager().getWriterManager();
		
		getView().setModel(new BeanItem<CoOp>(getContext().getSelectedCoop()));
		
		getView().dataBind();
		
		updateGroupTableList();
	}

}
