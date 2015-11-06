package softeng.coop.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.viewdefinitions.IOkCancelView;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;
import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.IViewListener;
import softeng.ui.vaadin.mvp.ViewEvent;

public class HierarchyDialog<M>
	extends CoopDialog<BeanItem<M>>
{
	private static final long serialVersionUID = 1L;
	
	private HierarchicalBeanItemContainer<M> itemContainer;
	
	private String itemCaptionId;
	
	private Tree tree;
	
	public HierarchyDialog(
			HierarchicalBeanItemContainer<M> itemContainer, 
			String itemCaptionId)
	{
		if (itemContainer == null) 
			throw new IllegalArgumentException("Argument 'itemContainer' must not be null.");

		if (itemCaptionId == null) 
			throw new IllegalArgumentException("Argument 'itemCaptionId' must not be null.");
		
		this.itemContainer = itemContainer;
		this.itemCaptionId = itemCaptionId;
		
		this.tree = new Tree();
		
		this.getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				setModel((BeanItem<M>)tree.getItem(tree.getValue()));
			}
		});

		this.setModal(true);
		this.setWidth("480px");
		this.setHeight("440px");
		
		Panel panel = new Panel();

		panel.setSizeFull();
		panel.setScrollable(true);
		
		panel.addComponent(tree);
		
		addComponent(panel);

	}

	public HierarchyDialog(
			HierarchicalBeanItemContainer<M> itemContainer,
			String itemCaptionId, 
			String caption)
	{
		this(itemContainer, itemCaptionId);
		
		this.setCaption(caption);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void dataBind()
	{
		// Preselect the current value.
		List<M> path = new ArrayList<M>();
		
		if (this.getModel() != null && this.getModel().getBean() != null)
		{
			for (
					M pathElement = this.getModel().getBean(); 
					pathElement != null; 
					pathElement = (M)itemContainer.getParent(pathElement))
			{
				path.add(pathElement);
			}
			
			for (int i = path.size() - 1; i >= 0; i--)
			{
				tree.expandItem(path.get(i));
			}
		
			if (isReadOnly()) tree.setReadOnly(false);
			tree.setValue(getModel().getBean());
			if (isReadOnly()) tree.setReadOnly(true);
			
			this.addListener(new FieldEvents.FocusListener()
			{
				private static final long serialVersionUID = 1L;
	
				@Override
				public void focus(FocusEvent event)
				{
					if (getModel() != null)
					{
						if (isReadOnly()) tree.setReadOnly(false);
						tree.setValue(getModel().getBean());
						if (isReadOnly()) tree.setReadOnly(true);
					}
				}
			});
			
			this.focus();
		}
		else
		{
			if (isReadOnly()) tree.setReadOnly(false);
			tree.setValue(null);
			if (isReadOnly()) tree.setReadOnly(true);
		}
		
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();

		tree.setContainerDataSource(this.itemContainer);
		tree.setItemCaptionMode(Tree.ITEM_CAPTION_MODE_PROPERTY);
		tree.setItemCaptionPropertyId(this.itemCaptionId);
		
		tree.setWidth("100%");
		
	}

	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		if (tree != null) tree.setReadOnly(readOnly);
	}

}
