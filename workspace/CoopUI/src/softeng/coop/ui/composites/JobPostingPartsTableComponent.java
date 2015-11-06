package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class JobPostingPartsTableComponent 
	extends TableComponent<JobPosting, JobPostingPart> 
{
	private static final long serialVersionUID = 1L;

	public JobPostingPartsTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	public JobPostingPartsTableComponent(String caption) 
	{
		super(caption, getDefaultColumnSpecifications());
	}
	
	public JobPostingPartsTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("description", "DESCRIPTION_CAPTION"));
		specifications.add(new ColumnSpecification("startDay", "START_DAY_CAPTION"));
		specifications.add(new ColumnSpecification("durationDays", "DURATION_CAPTION"));
		specifications.add(new ColumnSpecification("branch.name", "BRANCH_NAME_CAPTION"));
		specifications.add(new ColumnSpecification("managingCompanyPerson.surname", "MANAGING_PERSON_CAPTION"));
		
		return specifications;
	}
	
	@Override
	public Class<?> getType() 
	{
		return JobPostingPart.class;
	}

	@Override
	protected IModalView<BeanItem<JobPostingPart>> showAddForm(
			BeanItem<JobPostingPart> item) 
	{
		JobPostingPartDialog  dialog = new  JobPostingPartDialog();
		
		dialog.setModel(item);
		
		dialog.setModal(true);
		
		this.getWindow().addWindow(dialog);
		
		Company company = this.getParentModel().getCompany();
		if (company != null)
			dialog.setCompanyForJobPostingPart(company);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected IModalView<BeanItem<JobPostingPart>> showEditForm(
			BeanItem<JobPostingPart> item) 
	{
		JobPostingPartDialog dialog = new  JobPostingPartDialog();
		
		dialog.setModel(item);
		
		dialog.setModal(true);
		
		this.getWindow().addWindow(dialog);
		
		Company company = this.getParentModel().getCompany();
		if (company != null)
			dialog.setCompanyForJobPostingPart(company);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected JobPostingPart createNewElement() 
	{
		JobPostingPart jobPostingPart = Constructor.createJobPostingPart();
		
		return jobPostingPart;
	}

	@Override
	protected Presenter<Collection<JobPostingPart>, ICoopContext, ? extends IView<Collection<JobPostingPart>, ICoopContext>> 
	supplyPresenter() 
	{
		return null;
	}
	
	@Override
	protected void addToParent(JobPostingPart item) 
	{
		item.setJobPosting(getParentModel());
	}

	@Override
	protected void removeFromParent(JobPostingPart item) 
	{
		item.setJobPosting(null);
	}

	@Override
	protected BeanItemContainer<JobPostingPart> createBeanItemContainer(
			Collection<JobPostingPart> collection) 
	{
		DataItemContainer<JobPostingPart> container = 
			new DataItemContainer<JobPostingPart>(JobPostingPart.class, 
					collection, 
					this.getContext().getSession().getJobPostingsManager()
		);
		
		container.addNullableNestedContainerProperty("branch.name");
		container.addNullableNestedContainerProperty("managingCompanyPerson.surname");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	protected BeanItem<JobPostingPart> createItem(JobPostingPart obj) 
	{
		DataItem<JobPostingPart> item = new DataItem<JobPostingPart>(obj, this.getContext().getSession().getJobPostingsManager());
		
		item.addNullableNestedProperty("branch.name");
		item.addNullableNestedProperty("managingCompanyPerson.surname");
		
		return item;
	}
}
