package softeng.coop.ui.composites;

import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;
import com.vaadin.data.util.*;

/**
 * @author Master
 *
 * @param <M>
 */
public abstract class HierarchicalPickerField<M> 
	extends PickerField<M>
	implements IHierarchicalModalView<M>
{
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;
	
	private IHierarchyProvider<M> hierarchyProvider = null;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	
	public HierarchicalPickerField(String propertyId)
	{
		super(propertyId);
	}
	
	@Override
	public void setHierarchyProvider(IHierarchyProvider<M> hierarchyProvider)
	{
		if (hierarchyProvider == null) 
			throw new IllegalArgumentException("Argument 'hierarchyProvider' must not be null.");
		
		this.hierarchyProvider = hierarchyProvider;
	}

	@Override
	public IHierarchyProvider<M> getHierarchyProvider()
	{
		return this.hierarchyProvider;
	}

	@Override
	protected IModalView<BeanItem<M>> showBrowseForm(BeanItem<M> item)
	{
		M preselectedValue = this.getModel();
		
		if (preselectedValue == null) 
			preselectedValue = this.hierarchyProvider.getDefault();
		
		HierarchyDialog<M> dialog = 
			new HierarchyDialog<M>(
					this.hierarchyProvider.getHierarchy(), 
					this.getItemCaptionId());
		
		String caption = getBrowseFormCaption();
		
		if (caption != null) dialog.setCaption(caption);
		
		dialog.setModal(true);
		
		dialog.setModel(this.createBeanItem(preselectedValue));
		
		this.getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}
	
	/**
	 * If not null, supplies the caption for the browse dialog.
	 * Default implementation returns null.
	 */
	protected String getBrowseFormCaption()
	{
		return null;
	}
}
