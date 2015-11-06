package softeng.coop.ui.viewdefinitions;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CheckListViewElementEvent;
import softeng.ui.vaadin.mvp.*;

import java.util.*;

public interface ICheckListView<M>
	extends IView<Set<M>, ICoopContext>
{
	/**
	 * The type of an element. 
	 */
	Class<?> getType();
	
	/**
	 * The total set of elements available.
	 */
	Set<M> getAvailableElements();
	void setAvailableElements(Set<M> availableElements);
	
	/**
	 * The elements currently selected.
	 */
	Set<M> getSelectedElements();
	
	/**
	 * Add event listener for element check click.
	 */
	void addElementClickListener(IListener<CheckListViewElementEvent<M>> listener);
	/**
	 * Remove event listener for element check click.
	 */
	void removeElementClickListener(IListener<CheckListViewElementEvent<M>> listener);
}
