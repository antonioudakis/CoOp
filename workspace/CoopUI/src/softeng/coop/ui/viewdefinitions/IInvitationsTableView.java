package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.viewdefinitions.viewmodels.*;

public interface IInvitationsTableView
extends ITableView<Registration, Invitation>
{
	/**
	 * Determines if this table view displays sent or received invitations.
	 */
	InvitationType getInvitationType();
	void setInvitationType(InvitationType contentType);
}
