package softeng.coop.ui.composites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Company;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.viewdefinitions.IModalView;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.Presenter;

@SuppressWarnings("serial")
public class CompanySelectorTableComponent 
	extends TableComponent<CoOp, Company> 
{
	public CompanySelectorTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	public CompanySelectorTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}

	public CompanySelectorTableComponent(String caption)
	{
		super(caption, getDefaultColumnSpecifications());
	}
	
	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		 
		return specifications;
	}
	
	
	@Override
	public Class<?> getType() 
	{
		return Company.class;
	}

	@Override
	protected IModalView<BeanItem<Company>> showAddForm(BeanItem<Company> item) 
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<Company>> showEditForm(BeanItem<Company> item) 
	{
		return null;
	}

	@Override
	protected Company createNewElement() 
	{
		return null;
	}

	@Override
	protected Presenter<Collection<Company>, ICoopContext, ? extends IView<Collection<Company>, ICoopContext>> 
		supplyPresenter() 
	{
		return null;
	}

	@Override
	protected BeanItemContainer<Company> createBeanItemContainer(
			Collection<Company> collection) 
	{
		DataItemContainer<Company> container = 
			new DataItemContainer<Company>(Company.class, 
					collection, 
					this.getContext().getSession().getBaseManager()
			);
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

}
