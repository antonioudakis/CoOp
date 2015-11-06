package softeng.ui.vaadin.mvp;

/**
 * Prototype for event listeners.
 * @param <E> The type of the event.
 */
public interface IListener<E>
{
	/**
	 * The method called when the event is fired.
	 */
	void onEvent(E event);
}
