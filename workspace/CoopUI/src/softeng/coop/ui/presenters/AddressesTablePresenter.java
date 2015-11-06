package softeng.coop.ui.presenters;

import java.util.Collection;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.ITableView;
import softeng.coop.business.*;
import softeng.coop.business.companies.*;
import softeng.coop.business.faculties.*;
import softeng.coop.business.students.*;
import softeng.ui.vaadin.mvp.*;

public class AddressesTablePresenter 
	extends Presenter<Collection<Address>, ICoopContext, ITableView<Person, Address>>
{
	private static abstract class Persister
	{
		public abstract void add(Address address);
		public abstract void edit(Address address);
		public abstract void delete(Address address);
	}

	private static class StudentPersister extends Persister
	{
		private StudentsWriterManager manager;
		private Student student;
		
		public StudentPersister(StudentsWriterManager manager, Student student)
		{
			if (manager == null) 
				throw new IllegalArgumentException("Argument 'manager' must not be null.");
			
			this.manager = manager;
			this.student = student;
		}
		
		@Override
		public void add(Address address)
		{
			this.manager.markAsChanged(address);
			address.setPerson(this.student);
		}

		@Override
		public void edit(Address address)
		{
			this.manager.markAsChanged(address);
		}

		@Override
		public void delete(Address address)
		{
		}
	}
	
	private static class FacultyUserPersister extends Persister
	{
		private FacultyUsersWriterManager manager;
		private FacultyUser facultyUser;
		
		public FacultyUserPersister(FacultyUsersWriterManager manager, FacultyUser facultyUser)
		{
			if (manager == null) 
				throw new IllegalArgumentException("Argument 'manager' must not be null.");
			if (facultyUser == null) 
				throw new IllegalArgumentException("Argument 'facultyUser' must not be null.");
			
			this.manager = manager;
			this.facultyUser = facultyUser;
		}
		
		@Override
		public void add(Address address)
		{
			this.manager.markAsChanged(address);
			address.setPerson(facultyUser);
		}

		@Override
		public void edit(Address address)
		{
			this.manager.markAsChanged(address);
		}

		@Override
		public void delete(Address address)
		{
		}
	}
	
	private static class CompanyPersonPersister extends Persister
	{
		private CompaniesWriterManager manager;
		private CompanyPerson person;
		
		public CompanyPersonPersister(CompaniesWriterManager manager, CompanyPerson person)
		{
			if (manager == null) 
				throw new IllegalArgumentException("Argument 'manager' must not be null.");
			if (person == null) 
				throw new IllegalArgumentException("Argument 'person' must not be null.");
			
			this.manager = manager;
			this.person = person;
		}
		
		@Override
		public void add(Address address)
		{
			this.manager.markAsChanged(address);
			address.setPerson(person);
		}
		@Override
		public void edit(Address address)
		{
			this.manager.markAsChanged(address);
		}
		
		@Override
		public void delete(Address address)
		{
		}
	}
	
	private Persister persister;
	
	public AddressesTablePresenter(ITableView<Person, Address> view)
	{
		super(view);
	}
	
	private void initializeManager()
	{
		if (getView().getParentModel() == null)
		{
			this.persister = null;
			this.getView().setReadOnly(true);
			
			return;
		}
		
		ManagerBase readerManager = null;
		
		if (getView().getParentModel() instanceof Student)
		{
			StudentsManager manager = getContext().getSession().getStudentsManager();
			readerManager = manager;
			if (manager.isWriteable())
			{
				this.persister = 
					new StudentPersister(manager.getWriterManager(), (Student)getView().getParentModel());
			}
		}
		else if (getView().getParentModel() instanceof FacultyUser)
		{
			FacultyUsersManager manager = getContext().getSession().getFacultyUsersManager();
			readerManager = manager;
			
			if (manager.isWriteable())
			{
				this.persister = 
					new FacultyUserPersister(manager.getWriterManager(), (FacultyUser)getView().getParentModel());
			}
		}
		else if (getView().getParentModel() instanceof CompanyPerson)
		{
			CompaniesManager manager = getContext().getSession().getCompaniesManager();
			readerManager = manager;
			
			if (manager.isWriteable())
			{
				this.persister =
					new CompanyPersonPersister(manager.getWriterManager(), (CompanyPerson)getView().getParentModel());
			}
		}
		else
		{
			persister = null;
		}
		
		if (readerManager == null)
		{
			// This should never happen.
			throw new CoopUIUserException(getLocalizedString("READ_ACCESS_DENIED"));
		}
		
		getView().setReadOnly(!readerManager.isWriteable());
	}
	
	@Override
	protected void attachToView()
	{
		this.getView().addParentModelChangeListener(new IViewListener<Collection<Address>, ICoopContext, ITableView<Person,Address>>()
			{
				@Override
				public void onEvent(ViewEvent<Collection<Address>, ICoopContext, ITableView<Person, Address>> event)
				{
					initializeManager();
				}
			});
			
			getView().addAddingListener(new IListener<ModelEvent<Address>>()
			{
				@Override
				public void onEvent(ModelEvent<Address> event)
				{
					onAdd(event.getModel());
				}
			});
			
			getView().addEditingListener(new IListener<ModelEvent<Address>>()
			{
				@Override
				public void onEvent(ModelEvent<Address> event)
				{
					onEdit(event.getModel());
				}
			});
			
			getView().addDeletingListener(new IListener<ModelEvent<Address>>()
			{
				@Override
				public void onEvent(ModelEvent<Address> event)
				{
					onDelete(event.getModel());
				}
			});
	}

	@Override
	protected void setupView()
	{
		this.initializeManager();
	}

	protected void onEdit(Address model)
	{
		if (this.persister == null) return;

		persister.edit(model);
	}

	protected void onDelete(Address model)
	{
		if (persister == null) return;

		persister.delete(model);
	}

	protected void onAdd(Address model)
	{
		if (this.persister == null) return;
		
		persister.add(model);

	}

}
