package softeng.ui.vaadin.mvp;

/**
 * Represents an event originated from a view specifically concerning the
 * mechanism of MVP pattern. 
 * @param <M> The type of the model.
 * @param <C> The type of the application context. 
 * @param <V> The type of the view.
 */
public class ViewEvent<M, C, V extends IView<M, C>>
{
	private V view;
	
	/**
	 * Construct with reference to the view which originates the event.
	 */
	public ViewEvent(V view)
	{
		if (view == null) 
			throw new IllegalArgumentException("Argument 'view' must not be null.");
		
		this.view = view;
	}

	/**
	 * Get the view which originated the event.
	 */
	public V getView()
	{
		return view;
	}
}
