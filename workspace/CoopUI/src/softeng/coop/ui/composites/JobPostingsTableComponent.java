package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class JobPostingsTableComponent 
	extends TableComponent<Company, JobPosting> 
{
	private static final long serialVersionUID = 1L;
	
	private static String[] defaultSortFields = new String[] { "name" };
	private static boolean[] defaultSortDirections = new boolean[] { true }; 

	public JobPostingsTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	public JobPostingsTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}
	
	public JobPostingsTableComponent(String caption)
	{
		super(caption, getDefaultColumnSpecifications());
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		 
		return specifications;
	}
	
	@Override
	public Class<?> getType() 
	{
		return JobPosting.class;
	}

	@Override
	protected IModalView<BeanItem<JobPosting>> showAddForm(
			BeanItem<JobPosting> item) 
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<JobPosting>> showEditForm(
			BeanItem<JobPosting> item) 
	{
		return null;
	}

	@Override
	protected JobPosting createNewElement() 
	{
		JobPosting jobPosting = Constructor.createJobPosting();
		
		jobPosting.setCompany(getParentModel());
		
		CoOp coop = getContext().getSelectedCoop();
		
		jobPosting.setCoOp(coop);
		coop.getJobPostings().add(jobPosting);
		
		jobPosting.setSeatsNumber(1);
		
		return jobPosting;
	}

	@Override
	protected Presenter<Collection<JobPosting>, ICoopContext, ? extends IView<Collection<JobPosting>, ICoopContext>> 
		supplyPresenter() 
	{
		return new JobPostingsTablePresenter(this);
	}

	@Override
	protected BeanItemContainer<JobPosting> createBeanItemContainer(
			Collection<JobPosting> collection) 
	{
		Session session = getContext().getSession();
		
		ManagerBase manager;
		
		if (session.getJobPostingsManager() != null)
			manager = session.getJobPostingsManager();
		else
			manager = session.getBaseManager();
		
		DataItemContainer<JobPosting> container = 
			new DataItemContainer<JobPosting>(JobPosting.class, 
					collection, 
					manager
			);
		
		container.addNullableNestedContainerProperty("benefits.accommodationOffered");
		container.addNullableNestedContainerProperty("benefits.salaryOffered");
		container.addNullableNestedContainerProperty("benefits.insuranceOffered");
		container.addNullableNestedContainerProperty("benefits.transportationOffered");
		container.addNullableNestedContainerProperty("company.name");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	protected BeanItem<JobPosting> createItem(JobPosting obj) 
	{
		if (obj == null)
			return null;
		
		Session session = getContext().getSession();
		
		ManagerBase manager;
		
		if (session.getJobPostingsManager() != null)
			manager = session.getJobPostingsManager();
		else
			manager = session.getBaseManager();

		DataItem<JobPosting> item = new DataItem<JobPosting>(obj, manager);
		
		item.addNullableNestedProperty("benefits.accommodationOffered");
		item.addNullableNestedProperty("benefits.salaryOffered");
		item.addNullableNestedProperty("benefits.insuranceOffered");
		item.addNullableNestedProperty("benefits.transportationOffered");
		item.addNullableNestedProperty("company.name");
		
		return item;
	}

	@Override
	protected void addToParent(JobPosting item)
	{
		item.setCompany(getParentModel());
	}

	@Override
	protected void removeFromParent(JobPosting item)
	{
		item.setCompany(null);
	}
	
	@Override
	public void dataBind()
	{
		super.dataBind();
		
		BeanItemContainer<JobPosting> container = getContainer();

		if (container != null)
			container.sort(defaultSortFields, defaultSortDirections);
	}
	
}
