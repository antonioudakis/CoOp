package softeng.coop.ui.viewdefinitions;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import java.util.*;

import com.vaadin.data.util.*;
import com.vaadin.event.dd.*;
import com.vaadin.ui.Table.TableDragMode;

public interface ITableView<P, M>
	extends IView<Collection<M>, ICoopContext>, ISelectable<M>
{
	/**
	 * The type of M.
	 */
	Class<?> getType();
	
	/**
	 * The optional container of the rows.
	 */
	P getParentModel();
	void setParentModel(P parentModel);
	
	void addParentModelChangeListener(IViewListener<Collection<M>, ICoopContext, ITableView<P, M>> listener);
	void removeParentModelChangeListener(IViewListener<Collection<M>, ICoopContext, ITableView<P, M>> listener);
	
	/**
	 * Fired before adding an element and before showing the form, 
	 * if a form is provisioned for addition. 
	 * Gives the chance to abort addition before showing the addition form.
	 */
	void addPreAddListener(IListener<CommandExecutionVote> listener);
	void removePreAddListener(IListener<CommandExecutionVote> listener);
	
	/**
	 * Fired before adding an element but after showing the form, 
	 * if a form is provisioned for addition. 
	 * Gives the chance to abort addition after showing the addition form
	 * by possibly inspecting the new element.
	 */
	void addCanAddListener(IListener<ElementExecutionVote<M>> listener);
	void removeCanAddListener(IListener<ElementExecutionVote<M>> listener);
	
	/**
	 * Fired before editing an element and before showing the form, 
	 * if a form is provisioned for editing. 
	 * Gives the chance to abort editing 
	 * by possibly inspecting the selected element.
	 */
	void addCanEditListener(IListener<ElementExecutionVote<M>> listener);
	void removeCanEditListener(IListener<ElementExecutionVote<M>> listener);

	/**
	 * Fired before deleting an element and before deleting confirmation is requested.
	 * Gives the chance to abort deletion by possibly inspecting the selected element. 
	 */
	void addCanDeleteListener(IListener<ElementExecutionVote<M>> listener);
	void removeCanDeleteListener(IListener<ElementExecutionVote<M>> listener);

	/**
	 * Optional handler which enables the view as a drop target. Default is null.
	 */
	DropHandler getDropHandler();
	void setDropHandler(DropHandler dropHandler);
	
	/**
	 * Drag mode for the view. Default is TableDragMode.NONE. 
	 */
	TableDragMode getDragMode();
	void setDragMode(TableDragMode dragMode);
	
	/**
	 * Get the container being used internally.
	 * Reflects the transient set of items at any time.
	 * This is in contrast to the model where the items displayed
	 * are updated to the model upon 'commit' only, if autoCommit is false.
	 */
	BeanItemContainer<M> getContainer();
	
	/**
	 * Discards and recreates the internal row cache. 
	 * Call this if you make changes that affect the rows but the information 
	 * about the changes are not automatically propagated to the Table.
	 * This typically happens for generated or dynamic columns. 
	 */
	void refreshRowCache();
	
	/**
	 * Add an element to the rows. If order is supported, the 
	 * element is added at the end. 
	 * If the component is not write-through,
	 * a commit action must be invoked to update the underlying collection.
	 * @param element A non-null element.
	 * @return Returns true if no veto was raised and the element was successfully added.
	 */
	boolean addElement(M element);
	
	/**
	 * Remove an element from the rows.
	 * If the component is not write-through,
	 * a commit action must be invoked to update the underlying collection.
	 * @param element A non-null element.
	 * @return Returns true if no veto was raised and the element was successfully found and removed.
	 */
	boolean deleteElement(M element);
	
	/**
	 * Get the index of an element in the rows.
	 * @param element A non-null element.
	 * @return Returns the zero-based index of the row containing the element or -1 if not found.
	 */
	int indexOfElement(M element);
	
	/**
	 * Move an element in the rows.
	 */
	void moveElement(int fromIndex, int toIndex);
	
	/**
	 * Move an element in the rows.
	 * @return Returns false if the element does not exist in the rows.
	 */
	boolean moveElement(M element, int toIndex);
	
	/**
	 * The number of rows.
	 * If the component is not write-through,
	 * a commit action must be invoked to update the underlying collection.
	 */
	int elementsCount();

	/**
	 * Determines if the table rows are selectable by the user. Default is true.
	 */
	boolean isUserSelectable();
	void setUserSelectable(boolean selectable);
}
