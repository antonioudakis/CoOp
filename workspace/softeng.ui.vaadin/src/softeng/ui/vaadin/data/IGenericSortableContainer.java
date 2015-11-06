package softeng.ui.vaadin.data;

import java.util.*;

import com.vaadin.data.Container.Sortable;

/**
 * Container of items of type T which allows sorting by any comparator.
 */
public interface IGenericSortableContainer<T> extends Sortable
{
	void sort(Comparator<T> comparator);
}
