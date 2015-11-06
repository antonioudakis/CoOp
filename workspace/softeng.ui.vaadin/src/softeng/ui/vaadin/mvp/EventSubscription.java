package softeng.ui.vaadin.mvp;

import java.util.*;

/**
 * Implementation of the event subscription mechanism for event listeners. 
 * @param <E> The type of the event.
 * @param <L> The type of the listener. Must listen for type E.
 */
public class EventSubscription<E, L extends IListener<E>>
{
	private List<L> listeners;
	
	public EventSubscription()
	{
		this.listeners = new ArrayList<L>();
	}
	
	/**
	 * Add a listener.
	 */
	public void add(L listener)
	{
		if (listener == null) 
			throw new IllegalArgumentException("Argument 'listener' must not be null.");
		
		listeners.add(listener);
	}
	
	/**
	 * Remove a listener.
	 */
	public void remove(L listener)
	{
		if (listener == null) 
			throw new IllegalArgumentException("Argument 'listener' must not be null.");
		
		listeners.remove(listener);
	}
	
	/**
	 * Fire an event to all subscribed listeners.
	 */
	public void fire(E event)
	{
		for (L listener : this.listeners)
		{
			listener.onEvent(event);
		}
	}
}
