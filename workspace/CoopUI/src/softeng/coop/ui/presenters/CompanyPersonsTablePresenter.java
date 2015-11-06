package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.ui.vaadin.mvp.*;

public class CompanyPersonsTablePresenter
extends Presenter<Collection<CompanyPerson>, ICoopContext, ITableView<Company, CompanyPerson>>
{
	private CompaniesWriterManager manager;

	public CompanyPersonsTablePresenter(ITableView<Company, CompanyPerson> view)
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
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<CompanyPerson>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<CompanyPerson> event)
			{
				canDelete(event);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<CompanyPerson> event)
	{
		CompanyPerson person = event.getElement();
		
		LocalizedMessageAppender appender = new LocalizedMessageAppender();
		
		if (person.getCompany() != null)
		{
			if (person.getCompany().getContactPerson() == person)
			{
				appender.add("PERSON_IS_CONTACT_CAPTION");
				
				event.markAsFailed();
			}
		}
		
		if (!person.getBranches().isEmpty())
		{
			appender.add("PERSON_PARTICIPATES_IN_BRANCHES_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!person.getManagedJobPostings().isEmpty())
		{
			appender.add("PERSON_MANAGES_JOB_POSTINGS_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!person.getManagedJobPostingParts().isEmpty())
		{
			appender.add("PERSON_MANAGES_JOB_POSTING_PARTS_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!person.getManagedJobParts().isEmpty())
		{
			appender.add("PERSON_MANAGES_JOB_PARTS_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!appender.isEmpty())
		{
			getContext().showEntityInUseNotification(appender.toString());
		}
	}

	protected void onDelete(CompanyPerson person)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());
	}

	protected void onEdit(CompanyPerson person)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());

		manager.markAsChanged(person);
	}

	protected void onAdd(CompanyPerson person)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());

		manager.markAsChanged(person);
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (session.getCompaniesManager() == null ||
				!session.getCompaniesManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		manager = session.getCompaniesManager().getWriterManager();
	}

}
