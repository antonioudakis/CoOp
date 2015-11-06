package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.coops.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class RequirementsTableComponent 
extends TableComponent<CoOp, Requirement>
{
	private static final long serialVersionUID = 1L;

	public RequirementsTableComponent(
			String caption, 
			List<ColumnSpecification> columnSpecifications)
	{
		super(caption, columnSpecifications);
	}
	
	public RequirementsTableComponent(
			String caption)
	{
		this(caption, getDefaultColumnSpecifications());
	}
	
	public RequirementsTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = 
			new ArrayList<TableComponent.ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		specifications.add(new ColumnSpecification("type", "TYPE_CAPTION"));
		
		return specifications;
	}

	@Override
	public Class<?> getType()
	{
		return Requirement.class;
	}

	@Override
	protected IModalView<BeanItem<Requirement>> showAddForm(BeanItem<Requirement> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Requirement>> showEditForm(BeanItem<Requirement> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<Requirement>> showForm(BeanItem<Requirement> item)
	{
		RequirementDialog dialog = new RequirementDialog();
		
		dialog.setModel(item);
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Requirement createNewElement()
	{
		Requirement requirement = new Requirement();
		requirement.setName(new Multilingual());
		requirement.getName().setLiterals(new HashSet<Literal>());
		
		return requirement;
	}

	@Override
	protected Presenter<Collection<Requirement>, ICoopContext, ? extends IView<Collection<Requirement>, ICoopContext>> supplyPresenter()
	{
		return new RequirementsTablePresenter(this);
	}

	@Override
	protected BeanItemContainer<Requirement> createBeanItemContainer(Collection<Requirement> collection)
	{
		CoOpsManager manager = getContext().getSession().getCoOpsManager();
		
		if (manager != null)
		{
			return new DataItemContainer<Requirement>(
					Requirement.class, 
					collection, 
					manager, 
					getSpecifiedPropertyIds());
		}

		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<Requirement> createItem(Requirement obj)
	{
		if (obj == null) return null;
		
		CoOpsManager manager = getContext().getSession().getCoOpsManager();
		
		if (manager != null)
		{
			return new DataItem<Requirement>(obj, manager);
		}
		
		return super.createItem(obj);
	}

	@Override
	protected void addToParent(Requirement item)
	{
		item.setCoOp(getParentModel());
	}

	@Override
	protected void removeFromParent(Requirement item)
	{
		item.setCoOp(null);
	}

}
