package softeng.ui.vaadin.mvp;

/**
 * An event that pertains to a model.
 * @param <M> The type of the model.
 */
public class ModelEvent<M>
{
	private M model;
	
	/**
	 * Create the event,.
	 * @param model The model, which can be null.
	 */
	public ModelEvent(M model)
	{
		this.model = model;
	}
	
	/**
	 * Get the model.
	 */
	public M getModel()
	{
		return this.model;
	}
}
