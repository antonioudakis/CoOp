package softeng.coop.ui.composites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

import softeng.coop.business.ManagerBase;
import softeng.coop.business.Session;
import softeng.coop.business.companies.CompaniesManager;
import softeng.coop.dataaccess.Category;
import softeng.coop.dataaccess.Registration;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.data.CategoryHierarchyModel;
import softeng.coop.ui.data.DataItem;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.dialogs.HierarchyDialog;
import softeng.coop.ui.viewdefinitions.IModalView;
import softeng.ui.vaadin.data.HierarchicalBeanItemContainer;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.Presenter;

public class PreferredCategoriesTableComponent
	extends TableComponent<Registration, Category>
{
	private static final long serialVersionUID = 1L;

	@Override
	protected BeanItemContainer<Category> createBeanItemContainer(
			Collection<Category> collection) 
	{
		if (this.getContext() == null) 
		{
			return super.createBeanItemContainer(collection);
		}
		
		if (collection == null) return null;
		
		ManagerBase manager = this.getContext().getSession().getBaseManager();
		
		if (manager != null)
		{
			DataItemContainer<Category> container = new DataItemContainer<Category>(Category.class, collection, manager);

			container.setContainerPropertyIds(getSpecifiedPropertyIds());
	
			return container;
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<Category> createItem(Category obj) 
	{
		if (this.getContext() == null) return super.createItem(obj);
		
		if (obj == null) return null;
		
		ManagerBase manager = this.getContext().getSession().getBaseManager();
			
		if (manager != null)
		{
			return new DataItem<Category>(obj,  manager);
		}
		else
			return super.createItem(obj);
	}

	public PreferredCategoriesTableComponent(
			List<TableComponent.ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);	
	}

	public PreferredCategoriesTableComponent() 
	{
		super(getDefaultColumnSpecifications());	
	}
	
	public PreferredCategoriesTableComponent(String caption) 
	{
		super(caption, getDefaultColumnSpecifications());	
	}
	
	private static List<TableComponent.ColumnSpecification> 
		getDefaultColumnSpecifications() 
	{
		List<TableComponent.ColumnSpecification> columnSpecifications =
			new ArrayList<TableComponent.ColumnSpecification>();
		
		TableComponent.ColumnSpecification categoryName =
			new ColumnSpecification("name", "NAME_CAPTION");
		
		columnSpecifications.add(categoryName);

		return columnSpecifications;
	}

	@Override
	public Class<?> getType() 
	{
		return Category.class;
	}

	@Override
	protected IModalView<BeanItem<Category>> showAddForm(BeanItem<Category> item) 
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Category>> showEditForm(
			BeanItem<Category> item) 
	{
		return null;
	}

	private IModalView<BeanItem<Category>> showForm(BeanItem<Category> item)
	{
		HierarchyDialog<Category> dialog = 
			new HierarchyDialog<Category>(this.getHierarchy(), "name");
		
		dialog.setModal(true);
		dialog.setModel(item);
		dialog.setCaption(getContext().getLocalizedString("CHOOSE_CATEGORY_CAPTION"));
		
		this.getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	private HierarchicalBeanItemContainer<Category> getHierarchy() 
	{
		Session session = this.getContext().getSession();
		
		CompaniesManager manager = session.getCompaniesManager();
		
		if (manager == null)
			return null;
		
		List<Category> rootCategories = manager.getRootCategories();
		
		DataItemContainer<Category> rootDataItems = 
			new DataItemContainer<Category>(Category.class, rootCategories, manager);
		
		HierarchicalBeanItemContainer<Category> container =
			new HierarchicalBeanItemContainer<Category>(rootDataItems, new CategoryHierarchyModel(getContext()));
		
		return container;
	}

	@Override
	protected Category createNewElement() 
	{
		return null;
	}

	@Override
	protected Presenter<Collection<Category>, ICoopContext, ? extends IView<Collection<Category>, ICoopContext>> supplyPresenter()
	{
		return null;
	}

}
