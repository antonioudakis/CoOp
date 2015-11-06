package softeng.coop.ui.presenters;

import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.*;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Tree.TreeTargetDetails;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public class CategoriesPresenter
extends Presenter<HierarchicalBeanItemContainer<Category>, ICoopContext, IHierarchyView<Category>>
implements DropHandler
{
	private static final long serialVersionUID = 1L;

	private CompaniesWriterManager manager;
	
	private HierarchicalBeanItemContainer<Category> container;
	
	public CategoriesPresenter(IHierarchyView<Category> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getCompaniesManager() == null)
		{
			throw new CoopUIAccessDeniedException(getContext());
		}
		
		if (session.getCompaniesManager().isWriteable())
		{
			manager = session.getCompaniesManager().getWriterManager();
			
			getView().setDropHandler(this);
		
			getView().addAddingListener(new IListener<ModelEvent<Category>>()
			{
				@Override
				public void onEvent(ModelEvent<Category> event)
				{
					onAdd(event.getModel());
				}
			});
			
			getView().addEditingListener(new IListener<ModelEvent<Category>>()
			{
				@Override
				public void onEvent(ModelEvent<Category> event)
				{
					onEdit(event.getModel());
				}
			});
			
			getView().addDeletingListener(new IListener<ModelEvent<Category>>()
			{
				@Override
				public void onEvent(ModelEvent<Category> event)
				{
					onDelete(event.getModel());
				}
			});
		}
		
	}

	protected void onEdit(Category model)
	{
		manager.persistCategory(model);

		getContext().showDataSavedNotification();
	}

	protected void onDelete(Category model)
	{
		try
		{
			if (model.getId() > 0) manager.cascadeDeleteCategory(model.getId());
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			throw ex;
		}

		getContext().showDataSavedNotification();
	}

	protected void onAdd(Category model)
	{
		manager.persistCategory(model);
		
		getContext().showDataSavedNotification();
	}

	@Override
	protected void setupView()
	{
		if (getView().getModel() != null) return;
		
		DataItemContainer<Category> rootContainer = 
			new DataItemContainer<Category>(Category.class, manager.getRootCategories(), manager);
		
		container = 
			new HierarchicalBeanItemContainer<Category>(rootContainer, new CategoryHierarchyModel(getContext()));
		
		getView().setModel(container);
		
		getView().dataBind();
		
	}

	@Override
	public void drop(DragAndDropEvent event)
	{
		if (container == null) return;
		
		Transferable transferable = event.getTransferable();
		
		if (!(event.getTargetDetails() instanceof TreeTargetDetails)) return;

		if (!(transferable instanceof DataBoundTransferable)) return;
		
		TreeTargetDetails targetDetails = (TreeTargetDetails)event.getTargetDetails();
		
		DataBoundTransferable dataBoundTransferable = (DataBoundTransferable)transferable;
		
		Object dragged = dataBoundTransferable.getItemId();
		
		Category dropTargetCategory = (Category)targetDetails.getItemIdOver();
		
		if (dragged instanceof Category)
		{
			Category draggedCategory = (Category)dragged;
			
			if (container.setParent(draggedCategory, dropTargetCategory))
			{
				if (dropTargetCategory != null) getView().expand(dropTargetCategory);
				
				getView().setSelectedValue(draggedCategory);
				
				getContext().showDataSavedNotification();
			}
		}
		else if (dragged instanceof Company)
		{
			if (dropTargetCategory == null) return;
			
			Company draggedCompany = (Company)dragged;
			
			if (draggedCompany.getCategory() != null)
			{
				draggedCompany.getCategory().getCompanies().remove(draggedCompany);
			}
			
			dropTargetCategory.getCompanies().add(draggedCompany);
			draggedCompany.setCategory(dropTargetCategory);
			
			manager.persistCompany(draggedCompany);
			
			getView().setSelectedValue(dropTargetCategory);
			
			getContext().showDataSavedNotification();
		}
		
	}

	@Override
	public AcceptCriterion getAcceptCriterion()
	{
		return AcceptAll.get();
	}
}
