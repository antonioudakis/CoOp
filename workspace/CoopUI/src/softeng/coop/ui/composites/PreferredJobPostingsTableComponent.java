package softeng.coop.ui.composites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

import softeng.coop.business.ManagerBase;
import softeng.coop.dataaccess.JobPosting;
import softeng.coop.dataaccess.Registration;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.data.DataItem;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.dialogs.ChooseJobPostingDialog;
import softeng.coop.ui.viewdefinitions.IModalView;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.Presenter;

@SuppressWarnings("serial")
public class PreferredJobPostingsTableComponent 
	extends TableComponent<Registration, JobPosting> 
{
	public PreferredJobPostingsTableComponent(
			List<TableComponent.ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}
	
	public PreferredJobPostingsTableComponent()
	{
		super(getDefaltColumnSpecifications());
	}

	public PreferredJobPostingsTableComponent(String caption)
	{
		super(caption, getDefaltColumnSpecifications());
	}
	
	private static List<TableComponent.ColumnSpecification> getDefaltColumnSpecifications()
	{
		List<TableComponent.ColumnSpecification> specifications =
			new ArrayList<TableComponent.ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		
		specifications.add(new ColumnSpecification("description", "DESCRIPTION_CAPTION"));
		
		specifications.add(new ColumnSpecification("benefits.salaryOffered", "SALARY_CAPTION"));
		specifications.add(new ColumnSpecification("benefits.accommodationOffered", "ACCOMMODATION_CAPTION"));
		specifications.add(new ColumnSpecification("benefits.insuranceOffered", "INSURANCE_CAPTION"));
		specifications.add(new ColumnSpecification("benefits.transportationOffered", "TRANSPORTATION_CAPTION"));
		
		return specifications;
	}
	
	@Override
	protected BeanItemContainer<JobPosting> createBeanItemContainer(
			Collection<JobPosting> collection) 
	{
		if (this.getContext() == null) 
		{
			return super.createBeanItemContainer(collection);
		}
		
		if (collection == null) return null;
		
		ManagerBase manager = this.getContext().getSession().getBaseManager();
		
		if (manager != null)
		{
			DataItemContainer<JobPosting> container = 
				new DataItemContainer<JobPosting>(JobPosting.class, collection, manager);

			container.addNullableNestedContainerProperty("benefits.salaryOffered");
			container.addNullableNestedContainerProperty("benefits.accommodationOffered");
			container.addNullableNestedContainerProperty("benefits.insuranceOffered");
			container.addNullableNestedContainerProperty("benefits.transportationOffered");

			container.setContainerPropertyIds(getSpecifiedPropertyIds());
	
			return container;
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<JobPosting> createItem(JobPosting obj) 
	{
		if (this.getContext() == null) return super.createItem(obj);
		
		if (obj == null) return null;
		
		ManagerBase manager = this.getContext().getSession().getBaseManager();
			
		if (manager != null)
		{
			DataItem<JobPosting> item = new DataItem<JobPosting>(obj,  manager);
			
			item.addNullableNestedProperty("benefits.salaryOffered");
			item.addNullableNestedProperty("benefits.accommodationOffered");
			item.addNullableNestedProperty("benefits.insuranceOffered");
			item.addNullableNestedProperty("benefits.transportationOffered");
			
			return item;
		}
		else
			return super.createItem(obj);
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
		ChooseJobPostingDialog dialog = new ChooseJobPostingDialog();
		dialog.setModal(true);
		
		this.getApplication().getMainWindow().addWindow(dialog);
		dialog.dataBind();
		return dialog;
	}

	@Override
	protected JobPosting createNewElement() 
	{
		return null;
	}

	@Override
	protected Presenter<Collection<JobPosting>, ICoopContext, ? extends IView<Collection<JobPosting>, ICoopContext>> 
		supplyPresenter() 
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<JobPosting>> showEditForm(
			BeanItem<JobPosting> item) 
	{
		return null;
	}

}
