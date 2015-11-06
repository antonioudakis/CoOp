package softeng.coop.ui.viewdefinitions;

/**
 * A root-level form view that also contains the 'save' and 'cancel' buttons.
 * @param <M> The model type of the view.
 */
public interface ICardFormView<M>
extends IFormView<M>
{
	/**
	 * The cluster of 'save' and 'cancel' buttons.
	 */
	IOkCancelView getOkCancelView();
}
