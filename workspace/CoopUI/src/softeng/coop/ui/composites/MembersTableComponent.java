package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

@SuppressWarnings("serial")
public abstract class MembersTableComponent<P>
extends TableComponent<P, Registration>
{
	public MembersTableComponent()
	{
		super(getDefaultColumnSpecifications());
		
		setEditVisible(false);
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications =
			new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("student.surname", "SURNAME_CAPTION"));
		specifications.add(new ColumnSpecification("student.name", "NAME_CAPTION"));
		specifications.add(new ColumnSpecification("student.fatherName", "FATHER_NAME_CAPTION"));
		specifications.add(new ColumnSpecification("student.serialNumber", "SERIAL_NUMBER_CAPTION"));

		return specifications;
	}
	
	@Override
	public Class<?> getType() 
	{
		return Registration.class;
	}

	@Override
	protected IModalView<BeanItem<Registration>> showAddForm(
			BeanItem<Registration> item) 
	{
		ChooseGroupMemberDialog dialog = new ChooseGroupMemberDialog();
		
		dialog.setModel(item);
		
		dialog.setExcludedRegistrations(getExcludedRegistrations());
		
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}
	
	protected abstract Collection<Registration> getExcludedRegistrations();

	@Override
	protected IModalView<BeanItem<Registration>> showEditForm(
			BeanItem<Registration> item) 
	{
		return null;
	}

	@Override
	protected Registration createNewElement() 
	{
		return null;
	}

	@Override
	protected BeanItemContainer<Registration> createBeanItemContainer(Collection<Registration> collection)
	{
		Session session = getContext().getSession();
		
		if (session.getStudentsManager() != null)
		{
			DataItemContainer<Registration> container = 
				new DataItemContainer<Registration>(Registration.class, collection, session.getStudentsManager());
			
			container.addNullableNestedContainerProperty("student.name");
			container.addNullableNestedContainerProperty("student.surname");
			container.addNullableNestedContainerProperty("student.fatherName");
			container.addNullableNestedContainerProperty("student.serialNumber");
			
			container.setContainerPropertyIds(getSpecifiedPropertyIds());
			
			return container;
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<Registration> createItem(Registration obj)
	{
		Session session = getContext().getSession();
		
		if (session.getStudentsManager() != null && obj != null)
		{
			DataItem<Registration> item = 
				new DataItem<Registration>(obj, session.getStudentsManager());
			
			item.addNullableNestedProperty("student.name");
			item.addNullableNestedProperty("student.surname");
			item.addNullableNestedProperty("student.fatherName");
			item.addNullableNestedProperty("student.serialNumber");
			
			return item;
		}

		return super.createItem(obj);
	}

	@Override
	protected String getResourceBaseName()
	{
		return MembersTableComponent.class.getCanonicalName();
	}


}
