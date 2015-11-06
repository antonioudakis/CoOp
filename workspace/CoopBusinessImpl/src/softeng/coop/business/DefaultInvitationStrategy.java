package softeng.coop.business;

import javax.persistence.EntityManager;

import softeng.coop.business.students.*;

import softeng.coop.dataaccess.*;

public class DefaultInvitationStrategy implements IInvitationStrategy {

	@Override
	public void onPostInvitation(Invitation invitation,
			EntityManager entityManager) 
	{
		System.out.println("------- No invitation event strategy defined. event emitted:onPostInvitation----------");

	}

	@Override
	public void onAcceptInvitation(Registration recepient, Invitation invitation,
			EntityManager entityManager) 
	{
		System.out.println("------- No invitation event strategy defined. event emitted: onAcceptInvitation----------");

	}

}
