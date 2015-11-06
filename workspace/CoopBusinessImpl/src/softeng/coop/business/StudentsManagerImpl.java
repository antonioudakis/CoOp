package softeng.coop.business;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import softeng.coop.business.students.*;
import softeng.coop.dataaccess.*;

public class StudentsManagerImpl extends ManagerBaseImpl implements
		StudentsManager 		
{

	public StudentsManagerImpl(Session session) 
	{
		super(session);
	}

	@Override
	public boolean isWriteable() 
	{	
		return false;
	}

	@Override
	public Student getStudent(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		Student student = entityManager.find(Student.class, id);
		
		return student;
	}

	@Override
	public SearchResult<Student> searchStudents(
			StudentsSearchCriteria criteria) {
		EntityManager entityManager = this.getSession().getEntityManager();
		return QueryHelper.builderSearch(criteria, studentSearchAssembler, entityManager);
	}

	private static StudentSearchAssember studentSearchAssembler = 
		new StudentSearchAssember();
	
	private static class StudentSearchAssember 
		extends QueryHelper.Assembler<Student,StudentsSearchCriteria>
	{
		
		@Override
		public <Q> Root<Student> build(StudentsSearchCriteria criteria,
				CriteriaQuery<Q> query, boolean eagerFetch,
				CriteriaBuilder builder) {
			
			Root<Student> studentUserRoot = query.from(Student.class);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			SetJoin<Student, Registration> studentRegistrationRoot = studentUserRoot.join(Student_.registrations, JoinType.LEFT);
			//Join<Registration, CoOp> studentCoopRoot = studentRegistrationRoot.join(Registration_.coop, JoinType.LEFT);
			//Join<Registration, Group> studentGroupRoot = studentRegistrationRoot.join(Registration_.group, JoinType.LEFT);
			
			if (criteria.getCoop() != null)
			{
				restrictions.add(builder.equal(
						studentRegistrationRoot.get(Registration_.coop), 
						builder.literal(criteria.getCoop())));
			}
			
			if (criteria.getSurname() != null)
			{
				String surnamePattern = QueryHelper.getPrefixPattern(criteria.getSurname());
				
				restrictions.add(
						builder.like(
								studentUserRoot.get(Student_.surname),
								builder.literal(surnamePattern)
						)
				);
			}
			
			if (criteria.getName() != null)
			{
				String namePattern = QueryHelper.getPrefixPattern(criteria.getName());
				
				restrictions.add(
						builder.like(
								studentUserRoot.get(Student_.name), 
								builder.literal(namePattern)
						)
				);
			}
			
			if (criteria.getDepartment() != null)
			{	
				restrictions.add(
						builder.equal(
								studentUserRoot.get(Student_.department), 
								builder.literal(criteria.getDepartment())
						)
				);
				
			}
			
			if (criteria.isAssignedToJob() != null)
			{
				if (criteria.isAssignedToJob())
				{
					restrictions.add(
							builder.isNotNull(
									studentRegistrationRoot
									.join(Registration_.group, JoinType.LEFT)
									.join(Group_.job, JoinType.LEFT))
					);
				}
				else
				{
					restrictions.add(
							builder.isNull(
									studentRegistrationRoot
									.join(Registration_.group, JoinType.LEFT)
									.join(Group_.job, JoinType.LEFT))
					);
				}
			}
			
			if (criteria.isGraded() != null)
			{
				if (criteria.isGraded())
				{
					restrictions.add(
							builder.isNotNull(studentRegistrationRoot.get(Registration_.grade))
					);
				}
				else
				{
					restrictions.add(
							builder.isNull(studentRegistrationRoot.get(Registration_.grade))
					);
				}
			}
			
			if (criteria.getAcademicYear() != null)
			{
				restrictions.add(
						builder.equal(
								studentRegistrationRoot.get(Registration_.coop).get(CoOp_.academicYear), 
								builder.literal(criteria.getAcademicYear())));
			}
			
			if (criteria.isHasAMA() != null)
			{
				if (criteria.isHasAMA())
				{
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.ama)));
				}
				else
				{
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.ama)));
				}
			}
			
			if (criteria.isHasContract() != null)
			{
				if (criteria.isHasContract())
					restrictions.add(builder.isNotNull(studentRegistrationRoot.get(Registration_.insuranceContract)));
				else
					restrictions.add(builder.isNull(studentRegistrationRoot.get(Registration_.insuranceContract)));
			}
			
			if (criteria.isHasSocialSecurityId() != null)
			{
				if (criteria.isHasSocialSecurityId())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.socialSecurityId)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.socialSecurityId)));
			}
			
			if (criteria.isHasIBAN() != null)
			{
				if (criteria.isHasIBAN())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.iban)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.iban)));
			}
			
			if (criteria.isHasUserName() != null)
			{
				if (criteria.isHasUserName())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.userName)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.userName)));
			}
			
			//Add all location as orPredicates
			List<Predicate> orPredicates= new ArrayList<Predicate>();
			if (criteria.getPreferedLocations() != null)
			{	
				for (Location preferedLocation: criteria.getPreferedLocations())
				{
					orPredicates.add(
							builder.like(
									studentRegistrationRoot.join(Registration_.preferredLocations).get(Location_.path)
									, preferedLocation.getPath())
					);
				}	
				restrictions.add(builder.or(QueryHelper.listToArray(orPredicates)));
			}
			
			if (criteria.getCreatedBefore() != null)
			{
				restrictions.add(
						builder.lessThanOrEqualTo(
								studentUserRoot.get(Student_.tracking).get(Tracking_.created), 
								criteria.getCreatedBefore()
						)
				);
			}
			
			if (criteria.getCreatedAfter() != null)
			{
				restrictions.add(
						builder.greaterThanOrEqualTo(
								studentUserRoot.get(Student_.tracking).get(Tracking_.created), 
								criteria.getCreatedAfter()
						)
				);
			}
			
			if (criteria.getMinAddressesCount() != null)
			{
				Subquery<Long> addressesCountQuery = query.subquery(Long.class);
				
				Root<Address> addressRoot = addressesCountQuery.from(Address.class);
				
				addressesCountQuery.select(builder.count(addressRoot));
				
				addressesCountQuery.where(builder.equal(addressRoot.get(Address_.person), studentUserRoot));
				
				restrictions.add(
						builder.greaterThanOrEqualTo(
								addressesCountQuery,
								new Long(criteria.getMinAddressesCount())
						)
				);
			}
			
			if (criteria.getMinTelephonesCount() != null)
			{
				Subquery<Long> telephonesCountQuery = query.subquery(Long.class);
				
				Root<Telephone> telephoneRoot = telephonesCountQuery.from(Telephone.class);
				
				telephonesCountQuery.select(builder.count(telephoneRoot));
				
				telephonesCountQuery.where(builder.equal(telephoneRoot.get(Telephone_.person), studentUserRoot));
				
				restrictions.add(
						builder.greaterThanOrEqualTo(
								telephonesCountQuery, 
								new Long(criteria.getMinTelephonesCount())
						)
				);
			}
			
			if (criteria.isHasFatherName() != null)
			{
				if (criteria.isHasFatherName())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.fatherName)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.fatherName)));
			}
			
			if (criteria.isHasMotherName() != null)
			{
				if (criteria.isHasMotherName())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.motherName)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.motherName)));
			}
			
			if (criteria.isHasDateOfBirth() != null)
			{
				if (criteria.isHasDateOfBirth())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.dateOfBirth)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.dateOfBirth)));
			}
			
			if (criteria.isHasSerialNumber() != null)
			{
				if (criteria.isHasSerialNumber())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.serialNumber)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.serialNumber)));
			}
			
			if (criteria.isHasIdNumber() != null)
			{
				if (criteria.isHasIdNumber())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.idNumber)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.idNumber)));
			}
			
			if (criteria.isHasIdIssuer() != null)
			{
				if (criteria.isHasIdIssuer())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.idIssuer)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.idIssuer)));
			}
			
			if (criteria.isHasIdIssueDate() != null)
			{
				if (criteria.isHasIdIssueDate())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.idIssueDate)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.idIssueDate)));
			}
			
			if (criteria.isHasTaxId() != null)
			{
				if (criteria.isHasTaxId())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.taxId)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.taxId)));
			}
			
			if (criteria.isHasTaxDivision() != null)
			{
				if (criteria.isHasTaxDivision())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.taxDivision)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.taxDivision)));
			}
			
			if (criteria.isHasNationality() != null)
			{
				if (criteria.isHasNationality())
					restrictions.add(builder.isNotNull(studentUserRoot.get(Student_.nationality)));
				else
					restrictions.add(builder.isNull(studentUserRoot.get(Student_.nationality)));
			}
			
			if (criteria.isHasIdNumber() != null)
			
			query.distinct(true);
			
			query.where(builder.and(QueryHelper.listToArray(restrictions)));
			
			if (eagerFetch)
			{
				query.orderBy(
						builder.asc(studentUserRoot.get(Student_.surname)),
						builder.asc(studentUserRoot.get(Student_.name)));
			}
			
			return studentUserRoot;
		}
	}
	
	private static class RegistrationsSearchAssembler
		extends QueryHelper.Assembler<Registration, RegistrationsSearchCriteria>
	{

		@Override
		public <Q> Root<Registration> build(
				RegistrationsSearchCriteria criteria, 
				CriteriaQuery<Q> query, 
				boolean eagerFetch, 
				CriteriaBuilder builder)
		{
			Root<Registration> registrationRoot = query.from(Registration.class);
			
			Join<Registration, Group> groupJoin = 
				registrationRoot.join(Registration_.group, JoinType.LEFT);
			
			Join<Registration, Student> studentJoin =
				registrationRoot.join(Registration_.student, JoinType.INNER);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (criteria.isQualifiedForAssignment() != null)
			{
				restrictions.add(
						builder.equal(
								registrationRoot.get(Registration_.qualifiedForAssigmnent), 
								builder.literal(criteria.isQualifiedForAssignment())));
			}
			
			if (criteria.isQualifiedForCompletion() != null)
			{
				restrictions.add(
						builder.equal(
								registrationRoot.get(Registration_.qualifiedForCompletion), 
								builder.literal(criteria.isQualifiedForCompletion())));
			}
			
			if (criteria.isHasAMA() != null)
			{
				if (criteria.isHasAMA())
				{
					restrictions.add(
							builder.isNotNull(studentJoin.get(Student_.ama))
					);
				}
				else
				{
					restrictions.add(
							builder.isNull(studentJoin.get(Student_.ama))
					);
				}
			}
			
			if (criteria.isHasContract() != null)
			{
				if (criteria.isHasContract())
					restrictions.add(builder.isNotNull(registrationRoot.get(Registration_.insuranceContract)));
				else
					restrictions.add(builder.isNull(registrationRoot.get(Registration_.insuranceContract)));
			}
			

			if (criteria.isGraded() != null)
			{
				if (criteria.isGraded())
					restrictions.add(builder.isNotNull(registrationRoot.get(Registration_.grade)));
				else
					restrictions.add(builder.isNull(registrationRoot.get(Registration_.grade)));
			}
			
			if (criteria.isHasIBAN() != null)
			{
				if (criteria.isHasIBAN())
				{
					restrictions.add(
							builder.isNotNull(
									studentJoin.get(Student_.iban)
							)
					);
				}
				else
				{
					restrictions.add(
							builder.isNull(
									studentJoin.get(Student_.iban)
							)
					);
				}
			}
			
			if (criteria.isHasSocialSecurityId() != null)
			{
				if (criteria.isHasSocialSecurityId())
				{
					restrictions.add(
							builder.isNotNull(
									studentJoin.get(Student_.socialSecurityId)
							)
					);
				}
				else
				{
					restrictions.add(
							builder.isNull(
									studentJoin.get(Student_.socialSecurityId)
							)
					);
				}
			}
			
			if (criteria.isAssignedToJob() != null)
			{
				if (criteria.isAssignedToJob())
				{
					restrictions.add(
							builder.isNotNull(
									groupJoin
									.join(Group_.job, JoinType.LEFT)
							)
					);
				}
				else
				{
					restrictions.add(
							builder.isNull(
									groupJoin
									.join(Group_.job, JoinType.LEFT)
							)
					);
				}
			}
			
			if (criteria.getAcademicYear() != null)
			{
				restrictions.add(
						builder.equal(
								registrationRoot.get(Registration_.coop).get(CoOp_.academicYear), 
								builder.literal(criteria.getAcademicYear())));
			}
			
			if (criteria.isActive() != null)
			{
				if (criteria.isActive())
				{
					restrictions.add(
							builder.isTrue(
									registrationRoot.get(Registration_.coop).get(CoOp_.active)
							)
					);
				}
				else
				{
					restrictions.add(
							builder.isFalse(
									registrationRoot.get(Registration_.coop).get(CoOp_.active)
							)
					);
				}
			}
			
			if (criteria.getDepartment() != null)
			{
				restrictions.add(
						builder.equal(
								studentJoin.get(Student_.department), 
								criteria.getDepartment()
						)
				);
			}
			
			if (criteria.getName() != null)
			{
				String namePattern = QueryHelper.getPrefixPattern(criteria.getName());
				
				restrictions.add(
						builder.like(
								studentJoin.get(Student_.name), 
								namePattern
						)
				);
			}
			
			if (criteria.getSurname() != null)
			{
				String surnamePattern = QueryHelper.getPrefixPattern(criteria.getSurname());
				
				restrictions.add(
						builder.like(
								studentJoin.get(Student_.surname), 
								surnamePattern
						)
				);
			}
			
			if (criteria.isHasUserName() != null)
			{
				if (criteria.isHasUserName())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.userName)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.userName)));
			}
			
			if (criteria.isPassed() != null)
			{
				if (criteria.isPassed())
				{
					restrictions.add(
							builder.or(
								builder.isTrue(
										registrationRoot.get(Registration_.passed)
								),
								builder.isTrue(
										groupJoin.get(Group_.passed)
								)
							)
					);
				}
				else
				{
					restrictions.add(
							builder.and(
								builder.isFalse(
										registrationRoot.get(Registration_.passed)
								),
								builder.isFalse(
										groupJoin.get(Group_.passed)
								)
							)
					);
				}
			}
			
			if (criteria.getCoop() != null)
			{
				restrictions.add(
						builder.equal(
								registrationRoot.get(Registration_.coop), 
								builder.literal(criteria.getCoop())));
			}
			
			if (criteria.getStudent() != null)
			{
				restrictions.add(
						builder.equal(
								registrationRoot.get(Registration_.student), 
								builder.literal(criteria.getStudent())));
			}
			
			if (criteria.getMinGrade() != null)
			{
				restrictions.add(
						builder.or(
							builder.greaterThanOrEqualTo(
									registrationRoot.get(Registration_.grade), 
									criteria.getMinGrade()),
							builder.greaterThanOrEqualTo(
									groupJoin.get(Group_.grade), 
									criteria.getMinGrade())
						)
				);
			}
			
			if (criteria.getMaxGrade() != null)
			{
				restrictions.add(
						builder.or(
							builder.lessThanOrEqualTo(
									registrationRoot.get(Registration_.grade), 
									criteria.getMaxGrade()),
							builder.lessThanOrEqualTo(
									groupJoin.get(Group_.grade), 
									criteria.getMaxGrade())
						)
				);
			}
			
			if (criteria.getLesson() != null)
			{
				restrictions.add(
						builder.equal(
								registrationRoot.get(Registration_.coop).get(CoOp_.lesson), 
								criteria.getLesson()));
			}
			
			if (criteria.getCategory() != null)
			{
				String pathExpression = criteria.getCategory().getPath() + "%";
				
				restrictions.add(
						builder.like(
								registrationRoot.join(Registration_.preferredCategories).get(Category_.path), 
								pathExpression));
			}
			
			if (criteria.getLocation() != null)
			{
				String pathExpression = criteria.getLocation().getPath() + "%";
				
				restrictions.add(
						builder.like(
								registrationRoot.join(Registration_.preferredLocations).get(Location_.path), 
								pathExpression));
			}
			
			if (criteria.getCreatedBefore() != null)
			{
				restrictions.add(
						builder.lessThanOrEqualTo(
								registrationRoot.get(Registration_.tracking).get(Tracking_.created), 
								criteria.getCreatedBefore()
						)
				);
			}
			
			if (criteria.getCreatedAfter() != null)
			{
				restrictions.add(
						builder.greaterThanOrEqualTo(
								registrationRoot.get(Registration_.tracking).get(Tracking_.created), 
								criteria.getCreatedAfter()
						)
				);
			}
			
			if (criteria.getMinAddressesCount() != null)
			{
				Subquery<Long> addressesCountQuery = query.subquery(Long.class);
				
				Root<Address> addressRoot = addressesCountQuery.from(Address.class);
				
				addressesCountQuery.select(builder.count(addressRoot));
				
				addressesCountQuery.where(builder.equal(addressRoot.get(Address_.person), studentJoin));
				
				restrictions.add(
						builder.greaterThanOrEqualTo(
								addressesCountQuery,
								new Long(criteria.getMinAddressesCount())
						)
				);
			}
			
			if (criteria.getMinTelephonesCount() != null)
			{
				Subquery<Long> telephonesCountQuery = query.subquery(Long.class);
				
				Root<Telephone> telephoneRoot = telephonesCountQuery.from(Telephone.class);
				
				telephonesCountQuery.select(builder.count(telephoneRoot));
				
				telephonesCountQuery.where(builder.equal(telephoneRoot.get(Telephone_.person), studentJoin));
				
				restrictions.add(
						builder.greaterThanOrEqualTo(
								telephonesCountQuery, 
								new Long(criteria.getMinTelephonesCount())
						)
				);
			}
			
			if (criteria.isHasFatherName() != null)
			{
				if (criteria.isHasFatherName())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.fatherName)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.fatherName)));
			}
			
			if (criteria.isHasMotherName() != null)
			{
				if (criteria.isHasMotherName())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.motherName)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.motherName)));
			}
			
			if (criteria.isHasDateOfBirth() != null)
			{
				if (criteria.isHasDateOfBirth())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.dateOfBirth)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.dateOfBirth)));
			}
			
			if (criteria.isHasSerialNumber() != null)
			{
				if (criteria.isHasSerialNumber())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.serialNumber)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.serialNumber)));
			}
			
			if (criteria.isHasIdNumber() != null)
			{
				if (criteria.isHasIdNumber())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.idNumber)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.idNumber)));
			}
			
			if (criteria.isHasIdIssuer() != null)
			{
				if (criteria.isHasIdIssuer())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.idIssuer)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.idIssuer)));
			}
			
			if (criteria.isHasIdIssueDate() != null)
			{
				if (criteria.isHasIdIssueDate())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.idIssueDate)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.idIssueDate)));
			}
			
			if (criteria.isHasTaxId() != null)
			{
				if (criteria.isHasTaxId())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.taxId)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.taxId)));
			}
			
			if (criteria.isHasTaxDivision() != null)
			{
				if (criteria.isHasTaxDivision())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.taxDivision)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.taxDivision)));
			}
			
			if (criteria.isHasNationality() != null)
			{
				if (criteria.isHasNationality())
					restrictions.add(builder.isNotNull(studentJoin.get(Student_.nationality)));
				else
					restrictions.add(builder.isNull(studentJoin.get(Student_.nationality)));
			}
			
			query.where(QueryHelper.listToArray(restrictions)).distinct(true);
			
			if (eagerFetch)
			{
				registrationRoot.fetch(Registration_.payments, JoinType.LEFT);
				
				registrationRoot.fetch(Registration_.student, JoinType.INNER);
				
				Fetch<Group, Job> jobFetch = 
					registrationRoot
					.fetch(Registration_.group, JoinType.LEFT)
					.fetch(Group_.job, JoinType.LEFT);
				
				jobFetch
					.fetch(Job_.jobPosting, JoinType.LEFT)
					.fetch(JobPosting_.name, JoinType.LEFT)
					.fetch(Multilingual_.literals, JoinType.LEFT);
				
				jobFetch
					.fetch(Job_.jobParts, JoinType.LEFT)
					.fetch(JobPart_.specialPayables, JoinType.LEFT);
			}
			
			query.distinct(true);

			/* Using Hibernate / PosgreSQL combination, the following produce SQL error */
//			query.orderBy(
//					builder.asc(
//							registrationRoot.get(Registration_.student).get(Student_.surname)));
//			query.orderBy(builder.asc(studentJoin.get(Student_.surname)));
			
			
			return registrationRoot;
		}
		
	}
	
	private static RegistrationsSearchAssembler registrationsSearchAssembler =
		new RegistrationsSearchAssembler();
	
	private static class GroupsSearchAssembler
	extends QueryHelper.Assembler<Group, GroupSearchCriteria>
	{
		private static <Q> Subquery<Long> getGroupCountQuery(
				CriteriaQuery<Q> query, Root<Group> groupRoot, CriteriaBuilder builder)
		{
			Subquery<Long> groupCountQuery = query.subquery(Long.class);
			
			Root<Group> groupCorrelationRoot = groupCountQuery.correlate(groupRoot);
			
			SetJoin<Group, Registration> registrationsJoin = groupCorrelationRoot.join(Group_.registrations, JoinType.LEFT);
			
			groupCountQuery.select(builder.count(registrationsJoin));
			
			return groupCountQuery;
		}
		
		private static <Q> Subquery<Boolean> getQualificationQuery(
				CriteriaQuery<Q> query, Root<Group> groupRoot, CriteriaBuilder builder,
				SingularAttribute<Registration, Boolean> booleanAttribute)
		{
			Subquery<Boolean> qualificationQuery = query.subquery(Boolean.class);
			
			Root<Group> groupCorrelationRoot = qualificationQuery.correlate(groupRoot);
			
			SetJoin<Group, Registration> registrationsJoin = groupCorrelationRoot.join(Group_.registrations, JoinType.LEFT);
			
			qualificationQuery.select(registrationsJoin.get(booleanAttribute));
			
			return qualificationQuery;
		}

		@Override
		public <Q> Root<Group> build(
				GroupSearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<Group> groupRoot = query.from(Group.class);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			Subquery<Long> groupCountQuery = null;
			
			if (criteria.getCoop() != null)
			{
				restrictions.add(
						builder.equal(groupRoot.get(Group_.coOp), criteria.getCoop()));
			}
			
			if (criteria.isPassed() != null)
			{
				restrictions.add(
						builder.equal(
								groupRoot.get(Group_.passed), 
								builder.literal(criteria.isPassed())));
			}
			
			if (criteria.getMinGrade() != null)
			{
				restrictions.add(
						builder.greaterThanOrEqualTo(
								groupRoot.get(Group_.grade), 
								criteria.getMinGrade()));
			}
			
			if (criteria.getMaxGrade() != null)
			{
				restrictions.add(
						builder.lessThanOrEqualTo(
								groupRoot.get(Group_.grade), 
								criteria.getMaxGrade()));
			}
			
			if (criteria.isAssignedToJob() != null)
			{
				if (criteria.isAssignedToJob())
				{
					restrictions.add(
							builder.isNotNull(groupRoot.join(Group_.job, JoinType.LEFT)));
				}
				else
				{
					restrictions.add(
							builder.isNull(groupRoot.join(Group_.job, JoinType.LEFT)));
				}
			}
			
			if (criteria.isEmpty() != null)
			{
				if (groupCountQuery == null) 
					groupCountQuery = getGroupCountQuery(query, groupRoot, builder);
				
				if (criteria.isEmpty())
				{
					restrictions.add(
							builder.equal(
									groupCountQuery, 
									new Long(0)));
				}
				else
				{
					restrictions.add(
							builder.greaterThanOrEqualTo(
									groupCountQuery, 
									new Long(1)));
				}
			}
			
			if (criteria.isFull() != null)
			{
				if (groupCountQuery == null) 
					groupCountQuery = getGroupCountQuery(query, groupRoot, builder);
				
				if (criteria.isFull())
				{
					restrictions.add(
							builder.ge(
									groupCountQuery, 
									groupRoot.get(Group_.coOp).get(CoOp_.maxGroupSize)));
				}
				else
				{
					restrictions.add(
							builder.lt(
									groupCountQuery, 
									groupRoot.get(Group_.coOp).get(CoOp_.maxGroupSize)));
				}
			}
			
			if (criteria.getLocation() != null)
			{
				String pathExpression = criteria.getLocation().getPath() + "%";
				
				restrictions.add(
						builder.like(
							groupRoot.join(Group_.registrations).join(Registration_.preferredLocations).get(Location_.path), 
							pathExpression
						)
				);
			}
			
			if (criteria.getCategory() != null)
			{
				String pathExpression = criteria.getCategory().getPath() + "%";
				
				restrictions.add(
						builder.like(
							groupRoot.join(Group_.registrations).join(Registration_.preferredCategories).get(Category_.path), 
							pathExpression
						)
				);
			}
			
			/* WARNING: PostgreSQL requires the ANY/ALL expression to be right-hand!
			 * Don't use builder.isTrue because it makes ANY/ALL left-hand.
			 */
			if (criteria.isQualifiedForAssignment() != null)
			{
				if (criteria.isQualifiedForAssignment())
				{
					restrictions.add(
							builder.equal(
									builder.literal(true),
									builder.all(
											getQualificationQuery(query, groupRoot, builder, Registration_.qualifiedForAssigmnent))));
				}
				else
				{
					restrictions.add(
							builder.equal(
									builder.literal(false),
									builder.any(
											getQualificationQuery(query, groupRoot, builder, Registration_.qualifiedForAssigmnent))));
				}
			}
			
			if (criteria.isQualifiedForCompletion() != null)
			{
				if (criteria.isQualifiedForCompletion())
				{
					restrictions.add(
							builder.equal(
									builder.literal(true),
									builder.all(
											getQualificationQuery(query, groupRoot, builder, Registration_.qualifiedForCompletion))));
				}
				else
				{
					restrictions.add(
							builder.equal(
									builder.literal(false),
									builder.any(
											getQualificationQuery(query, groupRoot, builder, Registration_.qualifiedForCompletion))));
				}
			}
			
			if (eagerFetch) 
			{
				groupRoot.fetch(Group_.registrations, JoinType.LEFT).fetch(Registration_.student, JoinType.LEFT);
			}
			
			query.where(QueryHelper.listToArray(restrictions));
			query.distinct(true);
			
			return groupRoot;
		}
		
	}
	
	private static GroupsSearchAssembler groupSearchAssembler = 
		new GroupsSearchAssembler();
	
	@Override
	public Student getStudentByCode(String code) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TypedQuery<Student> query = entityManager
			.createQuery("SELECT st FROM Student st WHERE st.serialNumber = :stCode"
			, Student.class).setParameter("stCode", code);
		
		return QueryHelper.getFirstOrDefault(query);
	}

	@Override
	public Student getStudentByUserName(String userName) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TypedQuery<Student> query = entityManager
			.createQuery("SELECT st FROM Student st WHERE st.userName = :Username"
			, Student.class).setParameter("Username", userName);
		
		return QueryHelper.getFirstOrDefault(query);
	}

	@Override
	public  Group getGroup(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return entityManager.find(Group.class, id);	
	}

	@Override
	public Invitation getInvitation(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();

		return entityManager.find(Invitation.class, id);
	}

	@Override
	public Invitation getInvitationByCode(String code) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		CriteriaBuilder builder= this.getSession().getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Invitation> query = builder.createQuery(Invitation.class);
		
		Root<Invitation> rootInvitation = query.from(Invitation.class);
		
		query.where(
				builder.equal(
						rootInvitation.join(Invitation_.recepients).join(Registration_.student).get(Student_.serialNumber)
						, code
				)
		);
		
		query.select(rootInvitation);
		
		TypedQuery<Invitation> invitationQuery = entityManager.createQuery(query);
		
 		return QueryHelper.getFirstOrDefault(invitationQuery);
	}
	
	@Override
	public StudentsWriterManager getWriterManager() 
	{
		return null;
	}

	@Override
	public Registration getRegistration(int id) 
	{
		return this.getSession().getEntityManager().find(Registration.class, id);
	}

	@Override
	public Registration getActiveRegistration(String studentCode) 
	{
		//return getRegistration(studentCode, this.getSession().getCurrentUser().getDefaultCoOp());

		EntityManager entityManager = this.getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Registration> criteriaQuery = builder.createQuery(Registration.class);
		
		Root<Registration> registrationRoot = criteriaQuery.from(Registration.class);
		
		List<Predicate> restrictions = new ArrayList<Predicate>();
		
		restrictions.add(
				builder.equal(
						registrationRoot.join(Registration_.student).get(Student_.serialNumber),
						builder.literal(studentCode)
				)
		);
		
		return QueryHelper.getFirstOrDefault(entityManager.createQuery(criteriaQuery));
	}

	@Override
	public Registration getRegistration(String studentCode, CoOp coop) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Registration> criteriaQuery = builder.createQuery(Registration.class);
		
		Root<Registration> registrationRoot = criteriaQuery.from(Registration.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(builder.equal(registrationRoot.get(Registration_.coop)
				, coop));
		
		predicates.add(builder.equal(registrationRoot.join(Registration_.student).get(Student_.serialNumber), studentCode));
		
		criteriaQuery.where(builder.and(QueryHelper.listToArray(predicates)));
		
		TypedQuery<Registration> query = entityManager
					.createQuery(criteriaQuery);
				
		return QueryHelper.getFirstOrDefault(query);
	}

	@Override
	public SearchResult<Registration> searchRegistrations(RegistrationsSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return QueryHelper.builderSearch(criteria, registrationsSearchAssembler, entityManager);
	}

	@Override
	public Set<CoOp> suggestCoopsForRegistration(Student student)
	{
		if (student == null) 
			throw new IllegalArgumentException("Argument 'student' must not be null.");
		
		AuthenticatedUser user = this.getSession().getAuthenticatedUser();
		
		// If current user is a student, only allow to query for herself.
		
		if (user instanceof Student)
		{
			if (user.getId() != student.getId())
			{
				throw new AccessDeniedException(student);
			}
		}
		
		// Consult students policy
		
		try
		{
			StudentsPolicy studentsPolicy = 
				FactoriesRepository.getCoopBusinessFactory().getBean("studentsPolicy", StudentsPolicy.class);
			
			if (studentsPolicy.isRegistrationSuggestionEnabledOnlyWhenInNoActiveCoop())
			{
				RegistrationsSearchCriteria registrationSearchCriteria = new RegistrationsSearchCriteria();
				
				registrationSearchCriteria.setStudent(student);
				registrationSearchCriteria.setActive(true);
				
				SearchResult<Registration> result = searchRegistrations(registrationSearchCriteria);
				
				// If active registrations exist, return the empty set as the suggested coops.
				if (result.getList().size() > 0) return new HashSet<CoOp>();
			}
		}
		catch (NoSuchBeanDefinitionException ex)
		{
			// Proceed with default behavior when no policy is present.
		}
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		// Search for a ready coop in registration phase 
		// which doesn't belong to the the courses passed by the student.
		CriteriaQuery<CoOp> coopQuery = builder.createQuery(CoOp.class);
		
		Root<CoOp> coopRoot = coopQuery.from(CoOp.class);
		
		// Eager load name multilingual.
		coopRoot.fetch(CoOp_.name).fetch(Multilingual_.literals);
		
		Join<CoOp, Lesson> coopLessonJoin = coopRoot.join(CoOp_.lesson);
		
		List<Predicate> coopRestrictions = new ArrayList<Predicate>();
		
		coopRestrictions.add(builder.isTrue(coopRoot.get(CoOp_.inRegistration)));
		
		// Define the sub-query for the courses passed by the student.
		Subquery<Lesson> eliminatedLessonQuery = coopQuery.subquery(Lesson.class);
		
		Root<Lesson> ediminatedLessonsRoot = eliminatedLessonQuery.from(Lesson.class);
		
		SetJoin<Lesson, CoOp> eliminatedLessonsCoopsJoin =
			ediminatedLessonsRoot.join(Lesson_.coOps);
		
		SetJoin<CoOp, Registration> eliminatedCoopsRegistrationsJoin =
			eliminatedLessonsCoopsJoin.join(CoOp_.registrations);
		
		eliminatedLessonQuery
			.where(
					builder.and(
						builder.or(
								builder.isTrue(eliminatedCoopsRegistrationsJoin.get(Registration_.passed)),
								builder.isTrue(eliminatedCoopsRegistrationsJoin.join(Registration_.group).get(Group_.passed))
						),
						builder.equal(eliminatedCoopsRegistrationsJoin.get(Registration_.student), builder.literal(student)),
						builder.isFalse(eliminatedLessonsCoopsJoin.get(CoOp_.active))
					)
			)
			.select(ediminatedLessonsRoot);

		coopRestrictions.add(
				builder.not(builder.in(coopLessonJoin).value(eliminatedLessonQuery))
		);
		
		// Sub-query for the coops the student already registered to.
		Subquery<CoOp> takenCoopsQuery = coopQuery.subquery(CoOp.class);
		
		Root<CoOp> takenCoopsRoot = takenCoopsQuery.from(CoOp.class);
		
		takenCoopsQuery
			.where(
					builder.equal(
							takenCoopsRoot.join(CoOp_.registrations).get(Registration_.student), 
							student
					)
			)
			.select(takenCoopsRoot);

		coopRestrictions.add(
				builder.not(
						builder.in(coopRoot).value(takenCoopsQuery)
				)
		);
		
		List<CoOp> coops = 
			entityManager
			.createQuery(coopQuery.where(QueryHelper.listToArray(coopRestrictions)))
			.getResultList();
		
		return new HashSet<CoOp>(coops);
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "StudentsManager";
	}

	@Override
	public SearchResult<Group> searchGroups(GroupSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return QueryHelper.builderSearch(criteria, groupSearchAssembler, entityManager);
	}

	@Override
	public Nationality getNationality(String code)
	{
		if (code == null) 
			throw new IllegalArgumentException("Argument 'code' must not be null.");
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Nationality> query = builder.createQuery(Nationality.class);
		
		Root<Nationality> nationalityRoot = query.from(Nationality.class);
		
		nationalityRoot
			.fetch(Nationality_.name, JoinType.LEFT)
			.fetch(Multilingual_.literals);
		
		query
		.where(builder.equal(nationalityRoot.get(Nationality_.code), builder.literal(code)));
		
		query.distinct(true);
		
		return QueryHelper.getFirstOrDefault(entityManager.createQuery(query));
	}

	@Override
	public List<Nationality> getNationalities()
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Nationality> query = builder.createQuery(Nationality.class);
		
		Root<Nationality> nationalityRoot = query.from(Nationality.class);
		
		nationalityRoot
			.fetch(Nationality_.name, JoinType.LEFT)
			.fetch(Multilingual_.literals);
		
		query
			.orderBy(builder.asc(nationalityRoot.get(Nationality_.code)))
			.distinct(true);
		
		return entityManager.createQuery(query).getResultList();
		
	}
	
}
