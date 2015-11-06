package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class JobPartsTableComponent 
	extends TableComponent<Job, JobPart> 
{	
	private static final long serialVersionUID = 1L;

	public JobPartsTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	public JobPartsTableComponent(String caption) 
	{
		super(caption, getDefaultColumnSpecifications());
	}
	
	public JobPartsTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("description", "DESCRIPTION_CAPTION"));
		specifications.add(new ColumnSpecification("startDate", "START_DATE_CAPTION"));
		specifications.add(new ColumnSpecification("endDate", "END_DATE_CAPTION"));
		specifications.add(new ColumnSpecification("comments", "COMMENTS_CAPTION"));
		specifications.add(new ColumnSpecification("branch.name", "BRANCH_NAME_CAPTION"));
		specifications.add(new ColumnSpecification("managingCompanyPerson.surname", "MANAGING_PERSON_CAPTION"));
		 
		return specifications;
	}

	@Override
	public Class<?> getType() 
	{
		return JobPart.class;
	}

	@Override
	protected IModalView<BeanItem<JobPart>> showAddForm(BeanItem<JobPart> item) 
	{
		JobPartDialog  jobPartDialog = new JobPartDialog();
		
		jobPartDialog.setModel(item);
		
		jobPartDialog.setModal(true);
		
		this.getWindow().addWindow(jobPartDialog);
		
		Company company = this.getParentModel().getJobPosting().getCompany();
		if (company != null)
			jobPartDialog.setCompanyForJobPart(company);
		
		jobPartDialog.dataBind();
		
		return jobPartDialog;
	}

	@Override
	protected IModalView<BeanItem<JobPart>> showEditForm(BeanItem<JobPart> item) 
	{
		JobPartDialog  jobPartDialog = new JobPartDialog();
		
		jobPartDialog.setModel(item);
		
		jobPartDialog.setModal(true);
		
		this.getWindow().addWindow(jobPartDialog);
		
		Company company = this.getParentModel().getJobPosting().getCompany();
		if (company != null)
			jobPartDialog.setCompanyForJobPart(company);
		
		jobPartDialog.dataBind();
		
		return jobPartDialog;
	}

	@Override
	protected JobPart createNewElement() 
	{	
		JobPart jobPart = Constructor.createJobPart();
		
		return jobPart;
	}

	@Override
	protected Presenter<Collection<JobPart>, ICoopContext, ? extends IView<Collection<JobPart>, ICoopContext>> 
		supplyPresenter() 
	{
		return null;
	}

	@Override
	protected void addToParent(JobPart addedItem) 
	{
		Job parent = getParentModel();
		
//		if(parent.getJobParts() == null)
//			parent.setJobParts(new LinkedHashSet<JobPart>());
		
		addedItem.setJob(parent);
		//parent.getJobParts().add(addedItem);
	}

	@Override
	protected void removeFromParent(JobPart item) 
	{
		item.setJob(null);
	}

	@Override
	protected BeanItemContainer<JobPart> createBeanItemContainer(
			Collection<JobPart> collection) 
	{
		DataItemContainer<JobPart> container;
		
		container = new DataItemContainer<JobPart>(JobPart.class, 
				collection, 
				this.getContext().getSession().getJobsManager()
		);
		
		container.addNullableNestedContainerProperty("branch.name");
		
		container.addNullableNestedContainerProperty("managingCompanyPerson.surname");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	protected BeanItem<JobPart> createItem(JobPart obj)
	{
		DataItem<JobPart> item = 
			new DataItem<JobPart>(
					obj, 
					this.getContext().getSession().getJobsManager());
		
		item.addNullableNestedProperty("branch.name");
		
		item.addNullableNestedProperty("managingCompanyPerson.surname");
		
		return item;
	}
	
	

}
