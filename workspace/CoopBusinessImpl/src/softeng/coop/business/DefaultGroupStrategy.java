package softeng.coop.business;

import javax.persistence.EntityManager;

import softeng.coop.business.students.IGroupStrategy;
import softeng.coop.dataaccess.Group;

public class DefaultGroupStrategy implements IGroupStrategy {

	@Override
	public void onOpenGroup(Group group, EntityManager entityManager) 
	{
		System.out.println("------- No group event strategy defined. event emitted: onOpenGroup----------");

	}

	@Override
	public void onCloseGroup(Group group, EntityManager entityManager) 
	{
		System.out.println("------- No group event strategy defined. event emitted:onCloseGroup ----------");

	}

}
