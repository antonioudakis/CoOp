package softeng.coop.ui.forms;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.data.*;

@SuppressWarnings("serial")
public class InvitationForm
extends MultilingualValidationForm<Invitation>
{
	public InvitationForm()
	{
		super(Invitation.class);
	}
}
