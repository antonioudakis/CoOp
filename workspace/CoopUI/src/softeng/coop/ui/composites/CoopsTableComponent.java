package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.CoopsTablePresenter;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class CoopsTableComponent
	extends TableComponent<Lesson, CoOp>
{
	private static final long serialVersionUID = 1L;

	public CoopsTableComponent(String caption, List<TableComponent.ColumnSpecification> columnSpecifications)
	{
		super(caption, columnSpecifications);
	}

	public CoopsTableComponent(List<TableComponent.ColumnSpecification> columnSpecifications)
	{
		super(columnSpecifications);
	}

	public CoopsTableComponent(String caption)
	{
		this(caption, getDefaultColumnSpecifications());
	}

	public CoopsTableComponent()
	{
		this(getDefaultColumnSpecifications());
	}

	private static List<TableComponent.ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<TableComponent.ColumnSpecification> specifications =
			new ArrayList<TableComponent.ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		
		specifications.add(new ColumnSpecification("academicYear", "ACADEMIC_YEAR_CAPTION"));

		return specifications;
	}

	@Override
	public Class<?> getType()
	{
		return CoOp.class;
	}

	@Override
	protected IModalView<BeanItem<CoOp>> showAddForm(BeanItem<CoOp> item)
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<CoOp>> showEditForm(BeanItem<CoOp> item)
	{
		return null;
	}

	@Override
	protected CoOp createNewElement()
	{
		CoOp coop = Constructor.createCoop();
		
		coop.setLesson(getParentModel());
		
		Calendar calendar = Calendar.getInstance();
		
		coop.setAcademicYear(calendar.get(Calendar.YEAR));
		
		coop.setSemester(1);
		
		coop.setGradePolicy(GradePolicyType.HasGrade);
		
		coop.setStartDate(calendar.getTime());
		
		calendar.add(Calendar.YEAR, 1);
		
		coop.setEndDate(calendar.getTime());
		
		return coop;
	}

	@Override
	protected Presenter<Collection<CoOp>, ICoopContext, ? extends IView<Collection<CoOp>, ICoopContext>> supplyPresenter()
	{
		return new CoopsTablePresenter(this);
	}

	@Override
	protected BeanItemContainer<CoOp> createBeanItemContainer(Collection<CoOp> collection)
	{
		if (getContext() == null) 
			return super.createBeanItemContainer(collection);
		
		CoOpsManager manager = getContext().getSession().getCoOpsManager();
		
		if (manager != null)
		{
			DataItemContainer<CoOp> container = 
				new DataItemContainer<CoOp>(CoOp.class, collection, manager);

			container.setContainerPropertyIds(getSpecifiedPropertyIds());
			
			return container;
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<CoOp> createItem(CoOp obj)
	{
		if (getContext() == null) return super.createItem(obj);
		
		CoOpsManager manager = getContext().getSession().getCoOpsManager();
		
		if (manager != null)
		{
			if (obj == null) return null;
			
			DataItem<CoOp> item = new DataItem<CoOp>(obj, manager);

			return item;
		}
		
		return super.createItem(obj);
	}

}
