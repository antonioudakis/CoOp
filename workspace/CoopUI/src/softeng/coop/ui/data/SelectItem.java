package softeng.coop.ui.data;

import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;


/**
 *It is used for supporting multilignual lists in OptionGroups
 *
 * @param <T> : the class type that represents the value of the selected item
 */
public class SelectItem<T> 
	//extends CoopField<T>
{
	private T value;
	private String caption;
	private IView<?, ICoopContext> view;

	/**
	 * @param value : the stored Value in the select itme
	 * @param captionKey : the description of item that is store in the resource bundle of the view 
	 * @param view : the view that uses the SelecteItem
	 */
	public SelectItem(T value, String captionKey, IView<?, ICoopContext> view)
	{	
		if (value == null) 
			throw new IllegalArgumentException("Argument 'value' must not be null.");
		if (captionKey == null) 
			throw new IllegalArgumentException("Argument 'captionKey' must not be null.");
		if (view == null) 
			throw new IllegalArgumentException("Argument 'view' must not be null.");
		
		this.value = value;
		this.caption = captionKey;
		this.view = view;
	}
	
	public T getValue()
	{
		return this.value;
	}

	@Override
	public String toString()
	{
		return view.getLocalizedString(caption);
	}
}
	
