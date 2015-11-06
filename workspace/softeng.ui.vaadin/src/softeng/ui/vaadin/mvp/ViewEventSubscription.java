package softeng.ui.vaadin.mvp;

/**
 * Implementation of the event subscription mechanism for MVP events. 
 * @param <M> The type of the model.
 * @param <C> The type of the application context. 
 * @param <V> The type of the view.
 */
public class ViewEventSubscription<M, C, V extends IView<M, C>>
	extends EventSubscription<ViewEvent<M, C, V>, IViewListener<M, C, V>>
{
}
