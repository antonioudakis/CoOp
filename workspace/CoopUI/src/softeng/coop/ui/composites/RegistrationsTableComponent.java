package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.DataItem;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class RegistrationsTableComponent
extends TableComponent<CoOp, Registration>
{
	private static final long serialVersionUID = 1L;

	private static List<ColumnSpecification> defaultColumnSpecifications = 
		getDefaultColumnSpecificatios();

	private static String[] defaultSortFields = new String[] { "student.surname", "student.name" };
	private static boolean[] defaultSortDirections = new boolean[] { true, true }; 
	
	public RegistrationsTableComponent(List<ColumnSpecification> columnSpecifications)
	{
		super(columnSpecifications);
		
		setAddVisible(false);
		setEditVisible(false);
		setDeleteVisible(false);
	}
	
	private static List<ColumnSpecification> getDefaultColumnSpecificatios()
	{
		ArrayList<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("student.surname", "SURNAME_CAPTION"));
		specifications.add(new ColumnSpecification("student.name", "NAME_CAPTION"));
		
		return specifications;
	}

	public RegistrationsTableComponent()
	{
		this(defaultColumnSpecifications);
	}

	@Override
	public Class<?> getType()
	{
		return Registration.class;
	}

	@Override
	protected IModalView<BeanItem<Registration>> showAddForm(BeanItem<Registration> item)
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<Registration>> showEditForm(BeanItem<Registration> item)
	{
		return null;
	}

	@Override
	protected Registration createNewElement()
	{
		if (getContext().getSelectedCoop() == null) return null;
		
		Registration registration = Constructor.createRegistration();
		
		return registration;
	}

	@Override
	protected Presenter<Collection<Registration>, ICoopContext, ? extends IView<Collection<Registration>, ICoopContext>> supplyPresenter()
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
			container.addNullableNestedContainerProperty("student.serialNumber");
			container.addNullableNestedContainerProperty("student.immigrant");
			container.addNullableNestedContainerProperty("student.hasSpecialNeeds");
			container.addNullableNestedContainerProperty("student.taxId");
			container.addNullableNestedContainerProperty("student.socialSecurityId");
			container.addNullableNestedContainerProperty("student.ama");
			container.addNullableNestedContainerProperty("student.iban");
			container.addNullableNestedContainerProperty("student.idNumber");
			container.addNullableNestedContainerProperty("student.idIssuer");
			container.addNullableNestedContainerProperty("student.issuerLocation");
			container.addNullableNestedContainerProperty("group.job.jobPosting.name");
			container.addNullableNestedContainerProperty("group.job.jobPosting.description");
			
			container.setContainerPropertyIds(getSpecifiedPropertyIds());
			
			return container;
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<Registration> createItem(Registration obj)
	{
		if (obj == null) return null;
		
		Session session = getContext().getSession();
		
		if (session.getStudentsManager() != null)
		{
			DataItem<Registration> item = 
				new DataItem<Registration>(obj, session.getStudentsManager());
			
			item.addNullableNestedProperty("student.name");
			item.addNullableNestedProperty("student.surname");
			item.addNullableNestedProperty("student.serialNumber");
			item.addNullableNestedProperty("student.immigrant");
			item.addNullableNestedProperty("student.hasSpecialNeeds");
			item.addNullableNestedProperty("student.taxId");
			item.addNullableNestedProperty("student.socialSecurityId");
			item.addNullableNestedProperty("student.ama");
			item.addNullableNestedProperty("student.iban");
			item.addNullableNestedProperty("student.idNumber");
			item.addNullableNestedProperty("student.idIssuer");
			item.addNullableNestedProperty("student.issuerLocation");
			item.addNullableNestedProperty("group.job.jobPosting.name");
			item.addNullableNestedProperty("group.job.jobPosting.description");
			
			return item;
		}
		
		return super.createItem(obj);
	}
	
	@Override
	public void dataBind()
	{
		super.dataBind();
		
		BeanItemContainer<Registration> container = this.getContainer();
		
		if (container != null)
		{
			container.sort(defaultSortFields, defaultSortDirections);
		}
	}
}
