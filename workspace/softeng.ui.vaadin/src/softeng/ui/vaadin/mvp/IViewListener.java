package softeng.ui.vaadin.mvp;

/**
 * Prototypes a listener for events concerning the MVP mechanism.
 * @param <M> The type of the model.
 * @param <C> The type of the application context. 
 * @param <V> The type of the view.
 */
public interface IViewListener<M, C, V extends IView<M, C> >
	extends IListener<ViewEvent<M, C, V>>
{
}
