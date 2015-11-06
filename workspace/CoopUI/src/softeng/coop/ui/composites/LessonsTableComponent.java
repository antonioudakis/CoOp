package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.business.coops.CoOpsManager;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.presenters.LessonsTablePresenter;
import softeng.coop.ui.viewdefinitions.*;
import softeng.ui.vaadin.mvp.*;

public class LessonsTableComponent
	extends TableComponent<Department, Lesson>
{
	private static final long serialVersionUID = 1L;

	private static String[] defaultSortFields = new String[] { "name" };
	private static boolean[] defaultSortDirections = new boolean[] { true }; 

	public LessonsTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "LESSON_NAME_CAPTION"));
		
		return specifications;
	}

	@Override
	public Class<?> getType()
	{
		return Lesson.class;
	}

	@Override
	protected IModalView<BeanItem<Lesson>> showAddForm(BeanItem<Lesson> item)
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<Lesson>> showEditForm(BeanItem<Lesson> item)
	{
		return null;
	}

	@Override
	protected Lesson createNewElement()
	{
		Lesson lesson = new Lesson();
		lesson.setName(new Multilingual());
		lesson.getName().setLiterals(new HashSet<Literal>());
		
		return lesson;
	}

	@Override
	protected Presenter<Collection<Lesson>, ICoopContext, ? extends IView<Collection<Lesson>, ICoopContext>> supplyPresenter()
	{
		return new LessonsTablePresenter(this);
	}

	@Override
	protected BeanItemContainer<Lesson> createBeanItemContainer(Collection<Lesson> collection)
	{
		Session session = getContext().getSession();
		
		if (session == null) 
			return super.createBeanItemContainer(collection);
		
		CoOpsManager manager = session.getCoOpsManager();
		
		if (manager == null) 
			return super.createBeanItemContainer(collection);
		
		DataItemContainer<Lesson> container = 
			new DataItemContainer<Lesson>(
					Lesson.class, 
					collection, 
					manager,
					getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	protected void addToParent(Lesson item)
	{
		item.setDepartment(getParentModel());
	}

	@Override
	protected void removeFromParent(Lesson item)
	{
		item.setDepartment(null);
	}
	
	@Override
	public void dataBind()
	{
		super.dataBind();
		
		BeanItemContainer<Lesson> container = getContainer();
		
		if (container != null)
			container.sort(defaultSortFields, defaultSortDirections);
	}
}
