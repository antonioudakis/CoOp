package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.ui.vaadin.mvp.*;

public class BranchPersonsTablePresenter
extends Presenter<Collection<CompanyPerson>, ICoopContext, ITableView<Branch, CompanyPerson>>
{
	private CompaniesWriterManager manager;

	public BranchPersonsTablePresenter(ITableView<Branch, CompanyPerson> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		getView().addAddingListener(new IListener<ModelEvent<CompanyPerson>>()
		{
			@Override
			public void onEvent(ModelEvent<CompanyPerson> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<CompanyPerson>>()
		{
			@Override
			public void onEvent(ModelEvent<CompanyPerson> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<CompanyPerson>>()
		{
			@Override
			public void onEvent(ModelEvent<CompanyPerson> event)
			{
				onDelete(event.getModel());
			}
		});
	}

	protected void onDelete(CompanyPerson model)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());
	}

	protected void onEdit(CompanyPerson model)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());
		
		manager.markAsChanged(model);
	}

	protected void onAdd(CompanyPerson model)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());
		
		manager.markAsChanged(model);
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (session.getCompaniesManager() != null && 
				session.getCompaniesManager().isWriteable())
		{
			manager = session.getCompaniesManager().getWriterManager();
		}
	}

}
