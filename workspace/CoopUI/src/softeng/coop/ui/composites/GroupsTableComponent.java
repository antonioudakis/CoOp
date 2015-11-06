package softeng.coop.ui.composites;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public class GroupsTableComponent 
	extends TableComponent<CoOp, Group> 
{

	public GroupsTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}

	public GroupsTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications =
			new ArrayList<ColumnSpecification>();
		
		/* Vaadin hack: In order for the generated column to be sortable, 
		 * it has to overlap with existing field */

		specifications.add(
				new ColumnSpecification(
						"id", 
						"GROUP_MEMBERS_CAPTION", 
						new GroupMembersColumnGenerator()));
		
		return specifications;
	}
	
	@Override
	public Class<?> getType() 
	{
		return Group.class;
	}

	@Override
	protected IModalView<BeanItem<Group>> showAddForm(BeanItem<Group> item) 
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<Group>> showEditForm(BeanItem<Group> item) 
	{
		return null;
	}

	@Override
	protected Group createNewElement() 
	{
		Group group = Constructor.createGroup();
		
		group.setCoOp(this.getParentModel());
		
		return group;
	}

	@Override
	protected Presenter<Collection<Group>, ICoopContext, ? extends IView<Collection<Group>, ICoopContext>> 
		supplyPresenter() 
	{
		return new GroupsTablePresenter(this);
	}

	@Override
	protected void addToParent(Group item) 
	{
		item.setCoOp(getParentModel());
	}

	@Override
	protected void removeFromParent(Group item) 
	{
		item.setCoOp(null);
	}

	@Override
	protected BeanItemContainer<Group> createBeanItemContainer(Collection<Group> collection)
	{
		/* Vaadin hack: In order for the generated columns to be sortable, 
		 * they have to overlap with existing sortable fields.
		 * All generated columns in this component overlap with static fields.
		 * So, we override this method and specify that the container's properties
		 * are not only the static ones, which are given by getSpecifiedPropertyIds, 
		 * but all visible ones, which are given by getVisiblePropertyIds. */
		DataItemContainer<Group> container = 
			new DataItemContainer<Group>(Group.class, collection, getContext().getSession().getBaseManager(), getVisiblePropertyIds());
		
		container.addNullableNestedContainerProperty("job.jobPosting.name");
		
		return container;
	}

	@Override
	protected BeanItem<Group> createItem(Group obj)
	{
		DataItem<Group> item = new DataItem<Group>(obj, getContext().getSession().getBaseManager());
		
		item.addNullableNestedProperty("job.jobPosting.name");
		
		return item;
	}

}
