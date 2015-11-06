package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

public interface IInvitationsBoxView
extends IView<BeanItem<Registration>, ICoopContext>
{
	InvitationType getInvitationType();
	void setInvitationType(InvitationType invitationType);
	
	void addAcceptInvitationListener(IListener<ElementExecutionVote<Invitation>> listener);
	void removeAcceptInvitationListener(IListener<ElementExecutionVote<Invitation>> listener);
}
