package softeng.coop.business;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;

import softeng.coop.business.payments.*;
import softeng.coop.dataaccess.*;

public class PaymentsManagerImpl extends ManagerBaseImpl implements
		PaymentsManager 
{

	PaymentsManagerImpl(Session session) 
	{
		super(session);
	}

	@Override
	public boolean isWriteable() 
	{
		return false;
	}

	@Override
	public Payment getPayment(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		Payment payment = entityManager.find(Payment.class, id);
		
		return payment;

	}

	@Override
	public SearchResult<Payment> searchPayments(PaymentsSearchCriteria criteria) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return QueryHelper.builderSearch(criteria, paymentSearchAssember, entityManager);
	}

	private static class PaymentAggregateSearchAssembler
		extends QueryHelper.ProjectorAssembler<PaymentAggregateImpl, PaymentAggregateSearchCriteria>
	{
		@Override
		public <Q> Selection<PaymentAggregateImpl> build(PaymentAggregateSearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<Payment> paymentRoot = paymentSearchAssember.build(criteria, query, eagerFetch, builder);
			
			List<Expression<?>> groupings = new ArrayList<Expression<?>>();
			
			Expression<Student> studentExpression = builder.nullLiteral(Student.class);
			
			int combinationSelector = 0;
			
			if (criteria.isGroupByStudent())
			{
				studentExpression = 
					paymentRoot
					.join(Payment_.registration, JoinType.INNER)
					.join(Registration_.student, JoinType.INNER);
				
				groupings.add(studentExpression);
				
				combinationSelector |= 1;
			}
			
			Expression<FinancialSource> financialSourceExpression = 
				builder.nullLiteral(FinancialSource.class);
			
			if (criteria.isGroupByFinancialSource())
			{
				financialSourceExpression =	paymentRoot.join(Payment_.source, JoinType.LEFT);
				groupings.add(financialSourceExpression);
				
				combinationSelector |= 2;
			}
			
			query.groupBy(QueryHelper.expressionsToArray(groupings));

			switch (combinationSelector)
			{
				case 0:
					return builder.construct(
							PaymentAggregateImpl.class, 
							builder.sum(
									paymentRoot.get(Payment_.amount)
							)
					);
					
				case 1:
					return builder.construct(
							PaymentAggregateImpl.class, 
							builder.sum(
									paymentRoot.get(Payment_.amount)
							), 
							studentExpression
					);
					
				case 2:
					return builder.construct(
							PaymentAggregateImpl.class, 
							builder.sum(
									paymentRoot.get(Payment_.amount)
							), 
							financialSourceExpression
					);
					
				case 3:
					return builder.construct(
							PaymentAggregateImpl.class, 
							builder.sum(
									paymentRoot.get(Payment_.amount)
							), 
							studentExpression, 
							financialSourceExpression
					);
			}

			return null;
		}
		
	}
	
	private static PaymentAggregateSearchAssembler paymentAggregateSearchAssembler =
		new PaymentAggregateSearchAssembler();
	
	private static class PaymentSearchAssembler extends QueryHelper.Assembler<Payment, PaymentsSearchCriteria>
	{

		@Override
		public <Q> Root<Payment> build(PaymentsSearchCriteria criteria,
				CriteriaQuery<Q> query, boolean eagerFetch,
				CriteriaBuilder builder) 
		{
			Root<Payment> paymentRoot = query.from(Payment.class);
			
			List<Predicate> predicates = getPaymentSearchPredicates(criteria, builder, paymentRoot);
			
			query.distinct(true);
			
			query.where(QueryHelper.listToArray(predicates));
			
			return paymentRoot;
		}
	}

	private static PaymentSearchAssembler paymentSearchAssember 
		= new PaymentSearchAssembler();


	private static List<Predicate> getPaymentSearchPredicates(
			PaymentsSearchCriteria criteria, CriteriaBuilder builder,
			Root<Payment> paymentRoot) {


		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (criteria.getCoOp() != null)
		{
			predicates.add(builder.equal(paymentRoot.get(Payment_.source).get(FinancialSource_.coOps)
					, builder.literal(criteria.getCoOp())));
		}
		
		if (criteria.getStudent() != null)
		{
			predicates.add(builder.equal(
					paymentRoot.get(Payment_.registration).get(Registration_.student), 
					builder.literal(criteria.getStudent())));
		}
		
		if (criteria.getRegistration() != null)
		{
			predicates.add(
					builder.equal(
							paymentRoot.get(Payment_.registration), 
							builder.literal(criteria.getRegistration())));
		}
		
		if (criteria.getFinancialSource() != null)
		{
			predicates.add(builder.equal(paymentRoot.get(Payment_.source)
					, builder.literal(criteria.getFinancialSource())));
		}
		
		if (criteria.getFromDate() != null)
		{
			predicates.add(builder.greaterThanOrEqualTo(paymentRoot.get(Payment_.paymentDate)
					, builder.literal(criteria.getFromDate())));
		}
		
		if (criteria.getToDate() != null)
		{
			predicates.add(builder.lessThanOrEqualTo(paymentRoot.get(Payment_.paymentDate)
					, builder.literal(criteria.getToDate())));
		}
		
		if (criteria.getPaymentType() != null)
		{
			predicates.add(builder.equal(paymentRoot.get(Payment_.type)
					, builder.literal(criteria.getPaymentType())));	
		}
		
		return predicates;
	}

	@Override
	public PaymentsWriterManager getWriterManager() 
	{
		return null;
	}

	@Override
	public SearchResult<PaymentAggregate> aggregatePayments(PaymentAggregateSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return QueryHelper.<PaymentAggregate, PaymentAggregateSearchCriteria, Payment>builderSearch (
				criteria, 
				paymentAggregateSearchAssembler, 
				entityManager,
				Payment.class);
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "PaymentsManager";
	}

	@Override
	public Set<FinancialSource> getFinancialSources()
	{
		EntityManager entityManager = getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<FinancialSource> query = builder.createQuery(FinancialSource.class);
		
		Root<FinancialSource> financialSourceRoot = query.from(FinancialSource.class);
		
		query.orderBy(builder.asc(financialSourceRoot.get(FinancialSource_.name)));
		
		return new LinkedHashSet<FinancialSource>(entityManager.createQuery(query).getResultList());
	}

}
