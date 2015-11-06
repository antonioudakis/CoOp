package softeng.coop.business;

import java.util.*;

import javax.persistence.*;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import softeng.coop.business.students.*;
import softeng.coop.dataaccess.*;

public class StudentsWriterManagerImpl extends StudentsManagerImpl implements
		StudentsWriterManager 
{
	public StudentsWriterManagerImpl(Session session) 
	{
		super(session);
	}
	
	@Override
	public boolean isWriteable() 
	{
		return true;
	}


	@Override
	public StudentsWriterManager getWriterManager() 
	{
		return this;
	}


	@Override
	public void persistStudent(Student student) 
	{
		if (student == null) 
			throw new IllegalArgumentException("Argument 'student' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(student);
			
			this.markAsChanged(student);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteStudent(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM Student st WHERE st.id = :id")
				.setParameter("id", id)
				.executeUpdate();
			
			transactionScope.commit();
		}
		catch (PersistenceException ex)
		{
			throw TransactionHelper.translateException(ex);
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void persistGroup(Group group) 
	{
		if (group == null) 
			throw new IllegalArgumentException("Argument 'group' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		try
		{
			this.getSession().getEntityManager().persist(group);
			
			markAsChanged(group);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteGroup(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM Group gr WHERE gr.id = :id")
				.setParameter("id", id)
				.executeUpdate();
			
			transactionScope.commit();
		}
		catch (PersistenceException ex)
		{
			throw TransactionHelper.translateException(ex);
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void acceptInvitation(Registration receiver, Invitation invitation) 
	{
		if (receiver == null) throw new IllegalArgumentException("Argument 'receiver' must not be null.");
		if (invitation == null) throw new IllegalArgumentException("Argument 'invitation' must not be null.");
		if (invitation.getGroup() == null) throw new IllegalArgumentException("Argument 'invitation.group' must not be null.");
				
		//When invitation is accepted, the receiver is added to the group and the strategy is called
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			Group previousGroup = receiver.getGroup();

			Group group = invitation.getGroup();
			
			if (group == previousGroup) return;
			
			entityManager.refresh(group);
			entityManager.refresh(previousGroup);
			
			CoOp coop = receiver.getCoop();
			
			if (group.getRegistrations().size() >= coop.getMaxGroupSize())
			{
				throw new BusinessRuleViolationException(getLocalizedString("TARGET_GROUP_IS_FULL"));
			}
			
			previousGroup.getRegistrations().remove(receiver);
			
			//Add student Registration to group
			group.getRegistrations().add(receiver);
			receiver.setGroup(group);
			
			// Remove the receiver's previous group if it becomes orphan.
			if (previousGroup.getRegistrations().size() == 0 && 
					previousGroup.getJob() == null &&
					previousGroup.getInvitations().size() == 0)
			{
				entityManager.remove(previousGroup);
			}

			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}

		//Find the invitation strategy
		
		IInvitationStrategy invitationStrategy = getInvitationStatety();
		
		invitationStrategy.onAcceptInvitation(receiver, invitation, entityManager);	
	}

	private IInvitationStrategy getInvitationStatety() 
	{
		// Find the strategy using Spring. If a strategy is defined but not found, an coop exception is thrown.
		// If no strategy is defined, the default (that does nothing) is called.
		
		BeanFactory beanFactory = FactoriesRepository.getInvitationStrategyFactory();
		
		try
		{
		
			IInvitationStrategy strategy = beanFactory.getBean("invitationStrategy", IInvitationStrategy.class);
			
			return strategy;
		}
		catch (NoSuchBeanDefinitionException ex)
		{
			return new DefaultInvitationStrategy();
		}
	}

	@Override
	public void closeGroup(Group group) 
	{
		if (group == null) 
			throw new IllegalArgumentException("Argument 'group' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			group.setPendingFormation(false);
			
			this.getSession().getEntityManager().persist(group);
			
			markAsChanged(group);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
		IGroupStrategy groupStrategy = getGroupStrategy(group);
		
		groupStrategy.onCloseGroup(group, this.getSession().getEntityManager());
	}

	@Override
	public void openGroup(Group group) 
	{
		if (group == null) 
			throw new IllegalArgumentException("Argument 'group' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			group.setPendingFormation(true);
			
			this.getSession().getEntityManager().persist(group);
			
			markAsChanged(group);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}

	}

	@Override
	public void closeGroups(CoOp coOp) 
	{
		if (coOp == null) 
			throw new IllegalArgumentException("Argument 'coOp' must not be null.");
		
		EntityManager entityManager = this.getSession().getEntityManager();

		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("UPDATE Group gr SET gr.isPendingFormation = false "
						+"WHERE gr IN " 
						+"(SELECT sgr.id FROM Group sgr INNER JOIN sgr.registrations reg INNER JOIN reg.coop co WHERE co.id = :mid)")
				.setParameter("mid", coOp.getId())
				.executeUpdate();
			
			transactionScope.commit();
		}
		catch (PersistenceException ex)
		{
			throw TransactionHelper.translateException(ex);
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	private IGroupStrategy getGroupStrategy(Group group) 
	{
		BeanFactory beanFactory = FactoriesRepository.getInvitationStrategyFactory();
		
		IGroupStrategy bean = beanFactory.getBean("groupStrategy", IGroupStrategy.class);
		
		if ( bean == null)
		{
			return new DefaultGroupStrategy();
		}
		else 
			return bean;
	}

	@Override
	public void persistRegistration(Registration registration) 
	{
		if (registration == null) 
			throw new IllegalArgumentException("Argument 'registration' must not be null.");
		
		TransactionScope scope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(registration);
			
			this.markAsChanged(registration);
			
			if (registration.getTracking() != null && 
					registration.getTracking().getCreatedBy() != registration.getStudent())
			{
				registration.getTracking().setCreatedBy(registration.getStudent());
			}

			scope.commit();
		}
		finally
		{
			scope.dispose();
		}
	}

	@Override
	public void deleteRegistration(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM Registration reg WHERE reg.id = :myid")
				.setParameter("myid", id)
				.executeUpdate();
			
			transactionScope.commit();
		}
		catch (PersistenceException ex)
		{
			throw TransactionHelper.translateException(ex);
		}
		finally
		{
			transactionScope.dispose();
		}
	}


	@Override
	public void postInvitation(Invitation invitation)
	{
		if (invitation == null) 
			throw new IllegalArgumentException("Argument 'invitation' must not be null.");
		
		Registration sender = invitation.getSender();
		
		if (sender == null) throw new IllegalArgumentException("Argument 'invitation.sender' must not be null.");
		
		Set<Registration> recepients = invitation.getRecepients();
		
		if (recepients == null) throw new IllegalArgumentException("Argument 'invitation.recepients' must not be null.");
		
		Group group = invitation.getGroup();
		
		if (group == null) throw new IllegalArgumentException("Argument 'invitation.group' must not be null.");
		
		if (invitation.getText() == null) 
			throw new IllegalArgumentException("Argument 'invitation.text' must not be null.");
		
		CoOp coop = sender.getCoop();
		
		if (!coop.isInRegistration()) 
			throw new BusinessRuleViolationException(
					getLocalizedString("COOP_NOT_IN_REGISTRATION_PHASE"));

		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			for (Registration recepient : recepients)
			{
				
				if (recepient == null)
					throw new BusinessRuleViolationException(
							getLocalizedString("NO_RECEPIENT_ACTIVE_REGISTRATION"));
				
				if (recepient.getCoop() != coop)
					throw new BusinessRuleViolationException(
							getLocalizedString("DIFFERENT_RECEPIENT_COOP"));
				
			}
			
			this.getSession().getEntityManager().persist(invitation.getText());
			this.getSession().getEntityManager().persist(invitation);
			
			markAsChanged(invitation);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
		IInvitationStrategy invitationStrategy = getInvitationStatety();
		
		invitationStrategy.onPostInvitation(invitation, this.getSession().getEntityManager());
	}

	@Override
	public Registration register(CoOp coop, Student student)
	{
		if (coop == null) 
			throw new IllegalArgumentException("Argument 'coop' must not be null.");
		
		if (student == null) 
			throw new IllegalArgumentException("Argument 'student' must not be null.");
		
		if (!coop.isInRegistration())
			throw new BusinessRuleViolationException(
					getLocalizedString("COOP_NOT_IN_REGISTRATION_PHASE"));
		
		// Check if the student is involved in an active coop.
		RegistrationsSearchCriteria criteria = new RegistrationsSearchCriteria();
		
		criteria.setStudent(student);
		criteria.setLesson(coop.getLesson());
		criteria.setPassed(true);
		
		SearchResult<Registration> existingRegistrationsForLesson = 
			this.searchRegistrations(criteria);
		
		if (existingRegistrationsForLesson.getList().size() > 0)
			throw new BusinessRuleViolationException(getLocalizedString("LESSON_ALREADY_PASSED"));
		
		Set<CoOp> suggestedCoOps = this.suggestCoopsForRegistration(student);
		
		if (!suggestedCoOps.contains(coop))
		{
			throw new BusinessRuleViolationException(
					getLocalizedString("COOP_NOT_APPLICABLE_FOR_REGISTRATION"));
		}
		
		for (Registration previousRegistration : student.getRegistrations())
		{
			if (previousRegistration != null)
			{
				if (!previousRegistration.isPassed())
				{
					CoOp previousCoop = previousRegistration.getCoop();
					
					if (previousCoop.isActive() && previousCoop == coop)
						throw new BusinessRuleViolationException(getLocalizedString("ALREADY_REGISTERED_TO_COOP"));
				}
			}
		}
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			Registration registration = Constructor.createRegistration();
			
			registration.setCoop(coop);
			
			registration.setStudent(student);
			
			registration.setRegistrationDate(new Date());
			
			this.persistRegistration(registration);
			
			Group defaultGroup = Constructor.createGroup();
			
			defaultGroup.getRegistrations().add(registration);

			registration.setGroup(defaultGroup);
			defaultGroup.getRegistrations().add(registration);
			
			defaultGroup.setCoOp(coop);
			
			this.persistGroup(defaultGroup);
			
			if (getSession().isLoaded(coop, "registrations"))
				coop.getRegistrations().add(registration);
			
			if (getSession().isLoaded(coop, "groups"))
				coop.getGroups().add(defaultGroup);
			
			transactionScope.commit();
			
			return registration;
		}
		finally
		{
			transactionScope.dispose();
		}

	}

	@Override
	public Group mergeGroups(Collection<Group> groups, String mergedGroupComments)
	{
		if (groups == null) 
			throw new IllegalArgumentException("Argument 'groups' must not be null.");
		
		if (groups.size() == 0) 
			throw new IllegalArgumentException("Argument 'groups' must not be empty.");
		
		Group destinationGroup = null;
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			for (Group group : groups)
			{
				Job job = group.getJob();
				
				if (job != null)
					throw new BusinessRuleViolationException(
							getLocalizedString("GROUP_IS_ASSIGNED_TO_JOB"));
	
				if (destinationGroup == null)
				{
					destinationGroup = group;

					if (mergedGroupComments == null)
						destinationGroup.setComments(this.concatenateGroupComments(groups));
					else
						destinationGroup.setComments(mergedGroupComments);
				}
				else
				{
					for (Registration registration : group.getRegistrations())
					{
						registration.setGroup(destinationGroup);
						destinationGroup.getRegistrations().add(registration);
					}
					
					group.getRegistrations().clear();
					
					for (Report report : group.getReports())
					{
						report.setGroup(destinationGroup);
						destinationGroup.getReports().add(report);
					}
					
					group.getReports().clear();
					
					for (Professor supervisingProfessor : group.getSupervisingProfessors())
					{
						supervisingProfessor.getSupervisedGroups().add(destinationGroup);
						destinationGroup.getSupervisingProfessors().add(supervisingProfessor);
					}
					
					group.getSupervisingProfessors().clear();
					
					this.deleteGroup(group.getId());
				}
			}
			
			// Clamp 'comments' string length.
			destinationGroup.setComments(destinationGroup.getComments().substring(0, 4094));
			
			this.persistGroup(destinationGroup);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.commit();
		}
		
		return destinationGroup;
	}

	private String concatenateGroupComments(Collection<Group> groups)
	{
		if (groups == null) 
			throw new IllegalArgumentException("Argument 'groups' must not be null.");
		
		StringBuffer stringBuffer = new StringBuffer();
		
		for (Group group : groups)
		{
			if (stringBuffer.length() == 0)
				stringBuffer.append(group.getComments());
			else
			{
				stringBuffer.append(" | ");
				stringBuffer.append(group.getComments());
			}
		}
		
		String concatenatedComments = stringBuffer.toString();
		
		return concatenatedComments;
	}

	@Override
	public void deleteStudent(Student student)
	{
		if (student == null) 
			throw new IllegalArgumentException("Argument 'student' must not be null.");
		
		String userName = student.getUserName();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(student);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
			
			AuthorizationCache authorizationCache = AuthorizationCache.getInstance();
			
			authorizationCache.flush(userName);
		}
	}

	@Override
	public void deleteGroup(Group group)
	{
		if (group == null) 
			throw new IllegalArgumentException("Argument 'group' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(group);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteRegistration(Registration registration)
	{
		if (registration == null) 
			throw new IllegalArgumentException("Argument 'registration' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(registration);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

}
