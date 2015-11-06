package softeng.coop.ui.viewdefinitions.viewmodels;

import softeng.coop.ui.viewdefinitions.*;

public class CheckListViewElementEvent<M>
{
	private boolean selected;
	private M element;
	private ICheckListView<M> view;
	
	public CheckListViewElementEvent(ICheckListView<M> view, M element, boolean selected)
	{
		if (view == null) 
			throw new IllegalArgumentException("Argument 'view' must not be null.");
		
		if (element == null) 
			throw new IllegalArgumentException("Argument 'element' must not be null.");
		
		this.view = view;
		this.element = element;
		this.selected = selected;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public M getElement()
	{
		return element;
	}

	public ICheckListView<M> getView()
	{
		return view;
	}
}
