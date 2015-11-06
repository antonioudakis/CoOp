package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.companies.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public class CategoriesComponent
extends HierarchyComponent<Category>
{
	private static final long serialVersionUID = 1L;
	
	public CategoriesComponent()
	{
		this.setItemCaptionId("name");
	}
	
	public CategoriesComponent(String caption)
	{
		this();
		setCaption(caption);
	}

	@Override
	protected IModalView<BeanItem<Category>> showAddForm(BeanItem<Category> item)
	{
		return showDialog(item);
	}

	@Override
	protected IModalView<BeanItem<Category>> showEditForm(BeanItem<Category> item)
	{
		return showDialog(item);
	}

	@Override
	protected Category createNewElement()
	{
		Category category = new Category();
		
		category.setName(new Multilingual());
		category.getName().setLiterals(new HashSet<Literal>());
		
		category.setChildCategories(new HashSet<Category>());
		
		category.setCompanies(new HashSet<Company>());
		
		category.setPreferredByRegistrations(new HashSet<Registration>());
		
		return category;
	}

	@Override
	protected Presenter<HierarchicalBeanItemContainer<Category>, ICoopContext, ? extends IView<HierarchicalBeanItemContainer<Category>, ICoopContext>> supplyPresenter()
	{
		return new CategoriesPresenter(this);
	}

	private IModalView<BeanItem<Category>> showDialog(BeanItem<Category> item)
	{
		CategoryDialog dialog = new CategoryDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected BeanItem<Category> createItem(Category obj)
	{
		if (obj == null) return null;
		
		CompaniesManager manager = getContext().getSession().getCompaniesManager();
		
		if (manager != null)
			return new DataItem<Category>(obj, manager);

		return super.createItem(obj);
	}

	
}
