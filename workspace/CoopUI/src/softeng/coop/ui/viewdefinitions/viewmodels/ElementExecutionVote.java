package softeng.coop.ui.viewdefinitions.viewmodels;

/**
 * Supplies a way for a command listener to vote for 
 * command execution failure for a given selected element. 
 * This inhibits further listeners
 * and informs the caller not to proceed as success.
 * @param <M>
 */
public class ElementExecutionVote<M>
extends CommandExecutionVote
{
	private M element;
	
	/**
	 * Create.
	 * @param element The element on which the command applies.
	 */
	public ElementExecutionVote(M element)
	{
		this.element = element;
	}
	
	/**
	 * The element on which the command applies.
	 */
	public M getElement()
	{
		return element;
	}
}
