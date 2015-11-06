package softeng.ui.vaadin.mvp;

import java.util.*;

/**
 * Base contract for all views.
 * @param <M> The type of the model associated with the view.
 * @param <C> The type of the application context associated with the view.
 */
public interface IView<M, C>
{
	/**
	 * Add a listener to be notified
	 * when the view is attached to the application.
	 * @param listener
	 */
	void addAttachListener(IViewListener<M, C, IView<M, C>> listener);
	
	/**
	 * Remove a listener previously attached to be notified
	 * when the view is attached to the application.
	 * @param listener
	 */
	void removeAttachListener(IViewListener<M, C, IView<M, C>> listener);
	
	/**
	 * Add a listener to be notified
	 * when the view is detached from the application.
	 * @param listener
	 */
	void addDetachListener(IViewListener<M, C, IView<M, C>> listener);
	
	/**
	 * Remove a listener previously attached to be notified
	 * when the view is detached from the application.
	 * @param listener
	 */
	void removeDetachListener(IViewListener<M, C, IView<M, C>> listener);
	
	/**
	 * Add a listener to be notified
	 * when the model is of the view is changed.
	 * @param listener
	 */
	void addModelChangeListener(IViewListener<M, C, IView<M, C>> listener);
	
	/**
	 * Remove a listener previously attached to be notified
	 * when the view is attached to the application.
	 * @param listener
	 */
	void removeModelChangeListener(IViewListener<M, C, IView<M, C>> listener);
	
	/**
	 * Get the view's model.
	 * Typically, the view uses the model information during dataBind method.
	 */
	M getModel();
	/**
	 * Set the view's model.
	 * Typically, this is set by the presenter during setupView method
	 * or any of its event listeners.
	 */
	void setModel(M model);

	/**
	 * Bind the supplied model to the visual elements of the view.
	 * Typically, this method is called by the presenter during setupView method
	 * or any of its event listeners.
	 */
	void dataBind();
	
	/**
	 * After being attached, the view returns it's associated application context,
	 * else returns null.
	 */
	C getContext();
	
	/**
	 * Get localized string that is appropriate
	 * to the user's language. 
	 */
	String getLocalizedString(String key);
	
	/**
	 * Get the current display locale of the view.
	 * This is only valid after the attach event, else returns null.
	 */
	Locale getLocale();
	
	
//	/**
//	 * Returns true when the data entered into the view is valid.
//	 */
//	boolean isValid();
}
