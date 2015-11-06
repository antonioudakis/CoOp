package softeng.coop.ui.viewdefinitions.viewmodels;

/**
 * Supplies a way for a command listener to vote for 
 * command execution failure. This inhibits further listeners
 * and informs the caller not to proceed as success.
 */
public class CommandExecutionVote
{
	private boolean failed;
	
	/**
	 * Create. Initially, property isFailed is false.
	 */
	public CommandExecutionVote()
	{
		this.failed = false;
	}
	
	/**
	 * Get the failure status. Default is false. 
	 * It is set to true by invoking markAsFailed.
	 */
	public boolean isFailed()
	{
		return this.failed;
	}
	
	/**
	 * Set the failure status to true.
	 * Subsequent calls to isFailed will return true.
	 */
	public void markAsFailed()
	{
		this.failed = true;
	}
}
