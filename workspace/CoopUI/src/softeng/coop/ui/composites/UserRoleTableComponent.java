package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.coop.ui.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class UserRoleTableComponent 
	extends TableComponent<AuthenticatedUser, Role> 
{

	public UserRoleTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}
	
	public UserRoleTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	private static List<ColumnSpecification> 
		getDefaultColumnSpecifications() 
	{
		ArrayList<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
	
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		specifications.add(new ColumnSpecification("comment", "COMMENT_CAPTION"));
	
		return specifications;
	}

	@Override
	public Class<?> getType() 
	{
		return Role.class;
	}

	@Override
	protected IModalView<BeanItem<Role>> showAddForm(BeanItem<Role> item) 
	{
		ChooseRoleDialog dialog = new ChooseRoleDialog();
		
		dialog.setModel(item);
		
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected IModalView<BeanItem<Role>> showEditForm(BeanItem<Role> item) 
	{	
		return null;
	}

	@Override
	protected Role createNewElement() 
	{
		return null;
	}

	@Override
	protected Presenter<Collection<Role>, ICoopContext, ? extends IView<Collection<Role>, ICoopContext>> 
		supplyPresenter() 
	{
		return null;
	}

	@Override
	protected void addToParent(Role item)
	{
		Session session = getContext().getSession();
		
		AuthenticatedUser parent = getParentModel();
		
		if (parent != null)
		{
			if (session.isLoaded(item, "users"))
			{
				item.getUsers().add(parent);
			}
		}
	}

	@Override
	protected void removeFromParent(Role item)
	{
		Session session = getContext().getSession();
		
		AuthenticatedUser parent = getParentModel();
		
		if (parent != null)
		{
			if (session.isLoaded(item, "users"))
			{
				item.getUsers().remove(parent);
			}
		}
	}

}
