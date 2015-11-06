package softeng.coop.ui.presenters;

import com.vaadin.data.util.*;
import com.vaadin.ui.Window.Notification;

import softeng.coop.dataaccess.*;

import softeng.coop.business.companies.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;

import softeng.ui.vaadin.data.HierarchicalBeanItemContainer;
import softeng.ui.vaadin.data.HierarchyModel;
import softeng.ui.vaadin.mvp.*;

public class ChooseActivitySectorPresenter
extends Presenter<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView>
{
	private CompaniesManager manager;
	
	private HierarchyModel<ActivitySector> hierarchyModel =
		new ActivitySectorHierarchyModel();
	
	private HierarchicalBeanItemContainer<ActivitySector> container;

	public ChooseActivitySectorPresenter(IChooseActivitySectorView view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		manager = getContext().getSession().getCompaniesManager();
		
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());
		
		getView().addSearchListener(new IViewListener<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView>()
		{
			@Override
			public void onEvent(ViewEvent<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView> event)
			{
				onSearch();
			}
		});
		
		getView().getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			
			@Override
			public void onEvent(CommandExecutionVote vote)
			{
				onExecute(vote);
			}
		});
	}

	protected void onExecute(CommandExecutionVote vote)
	{
		if (!searchByCode(getView().getCurrentCode())) vote.markAsFailed();
	}

	protected void onSearch()
	{
		searchByCode(getView().getCurrentCode());
	}

	@Override
	protected void setupView()
	{
		setRootContainer();
		
		getView().dataBind();
	}
	
	private void setRootContainer()
	{
		if (manager == null) return;
		
		container =
			new HierarchicalBeanItemContainer<ActivitySector>(
					ActivitySector.class, manager.getRootActivitySectors(), hierarchyModel);
		
		getView().setHierarchicalContainer(container);
	}

	private boolean searchByCode(String code)
	{
		if (code == null) 
			throw new IllegalArgumentException("Argument 'code' must not be null.");
		
		if (manager == null || container == null) return false;
		
		code = code.trim();
		
		ActivitySector foundActivitySector = manager.getActivitySector(code);
		
		if (foundActivitySector != null)
		{
			getView().setModel(new BeanItem<ActivitySector>(foundActivitySector));
			
			getView().dataBind();
			
			return true;
		}
		else
		{
			getView().setModel(null);
			
			getContext().showNotification(
					getLocalizedString("NOT_FOUND_CAPTION"), 
					getLocalizedString("NOT_FOUND_DESCRIPTION"), 
					Notification.TYPE_HUMANIZED_MESSAGE);

			getView().dataBind();
			
			return false;
		}
		
	}
}
