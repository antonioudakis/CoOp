package softeng.coop.ui.viewdefinitions.viewmodels;

/**
 * Batch payment action type specified in FinancialActionViewModel.
 */
public enum FinancialActionType
{
	/**
	 * Add payments of specific type, source and state.
	 */
	Add,
	
	/**
	 * Clear payments of specific type, source and state.
	 */
	Clear,
	
	/**
	 * Change state  of payments of specific type, source and state.
	 */
	ChangeState
}
