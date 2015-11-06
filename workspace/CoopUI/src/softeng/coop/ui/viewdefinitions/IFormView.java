package softeng.coop.ui.viewdefinitions;

import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;

/**
 * An abstraction of a form editing a model.
 * It doesn't include a save/cancel mechanism.
 * The mechanism should be provided by a parent view.
 */
public interface IFormView<M>
	extends IView<M, ICoopContext>
{
	/**
	 * Checks if the data entered is valid.
	 */
	boolean isDataValid();
	
	/**
	 * Discard edits to the model.
	 */
	void discardChanges();
	
	/**
	 * Commit edits to the model.
	 * Succeeds only when isDataValid returns true.
	 * Note that this updates the underlying model, 
	 * but does not persist.
	 */
	void commitChangesToModel();

}
