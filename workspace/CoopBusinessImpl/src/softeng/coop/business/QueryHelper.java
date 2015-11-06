package softeng.coop.business;

import java.util.*;
import java.util.concurrent.atomic.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import softeng.coop.dataaccess.*;

/**
 * @author Master
 *
 */
public class QueryHelper
{
	/**
	 * Strategy class for assembling a JPA CriteriaQuery based on
	 * the SearchCriteria given of type C. Should define the sources, the filters,
	 * but not the select list.
	 * @param <E> The type of the entity as root.
	 * @param <C> The type of SearchCriteria.
	 */
	public static abstract class Assembler<E, C extends SearchCriteria>
	{
		/**
		 * @param <Q> The result type of the CriteriaQuery.
		 * @param criteria The SearchCriteria specialization.
		 * @param query The CriteriaQuery to be assembled.
		 * @param eagerFetch True if eager fetching relations, false if targeted for COUNT.
		 * @param builder The JPA helper for creating expressions.
		 * @return Returns the root of the query for type E.
		 */
		public abstract <Q> Root<E> build(
				C criteria, 
				CriteriaQuery<Q> query, 
				boolean eagerFetch,
				CriteriaBuilder builder);
	}
	
	/**
	 * Strategy class for assembling a JPA CriteriaQuery based on
	 * the SearchCriteria given of type C. Should define the sources, the filters,
	 * but not the select list.
	 * @param <E> The type of the entity as root.
	 * @param <C> The type of SearchCriteria.
	 */
	public static abstract class ProjectorAssembler<E, C extends SearchCriteria>
	{
		/**
		 * @param <Q> The result type of the CriteriaQuery.
		 * @param criteria The SearchCriteria specialization.
		 * @param query The CriteriaQuery to be assembled.
		 * @param eagerFetch True if eager fetching relations, false if targeted for COUNT.
		 * @param builder The JPA helper for creating expressions.
		 * @return Returns the root of the query for type E.
		 */
		public abstract <Q> Selection<E> build(
				C criteria, 
				CriteriaQuery<Q> query, 
				boolean eagerFetch,
				CriteriaBuilder builder);
	}
	
	private static AtomicInteger uniqueId = new AtomicInteger(); 
	
	/**
	 * Fetch the first result of a query or null.
	 * @param <T> the type of the results of the query.
	 * @param query The query.
	 * @return Returns the item found or null.
	 */
	public static <T> T getFirstOrDefault(TypedQuery<T> query)
	{
		List<T> result = 
			query
				.setMaxResults(1)
				.getResultList();
		
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}

	/**
	 * Fetch results from query, optionally paginating.
	 * @param <T> The type of the results of the search.
	 * @param entitiesQuery A query that returns the entities.
	 * @param criteria The search criteria.
	 * @return Returns the result containing the list of items found using entitiesQuery, 
	 * and optionally paginates.
	 */
	public static <T> SearchResult<T> createSearchResult(
			TypedQuery<T> entitiesQuery, 
			SearchCriteria criteria)
	{
		SearchResult<T> result = new SearchResult<T>();
		
		if (criteria.getPageSize() > 0)
		{
			entitiesQuery = entitiesQuery
				.setFirstResult(criteria.getPageIndex() * criteria.getPageSize())
				.setMaxResults(criteria.getPageSize());
		}
		
		result.setList(entitiesQuery.getResultList());
		result.setPageIndex(criteria.getPageIndex());
		result.setPageSize(criteria.getPageSize());
		
		return result;
	}
	
	/**
	 * Fetch results from query, optionally paginating and retrieving total count.
	 * @param <T> The type of the results of the search.
	 * @param entitiesQuery A query that returns the entities.
	 * @param countQuery A query that returns the count of the entities.
	 * @param criteria The search criteria.
	 * @return Returns the result containing the list of items found using entitiesQuery, 
	 * and optionally paginates and returns the count using the countQuery.
	 */
	public static <T> SearchResult<T> createSearchResult(
			TypedQuery<T> entitiesQuery, 
			TypedQuery<Long> countQuery, 
			SearchCriteria criteria)
	{
		SearchResult<T> result = createSearchResult(entitiesQuery, criteria);
		
		if (criteria.isRetrievingTotalCount())
		{
//			if (criteria.getPageSize() > 0) 
//				setTotalCount(result, countQuery);
//			else
//				result.setTotalCount(result.getList().size());
			
			setTotalCount(result, countQuery);
		}
		
		return result;
	}
	
	
	/**
	 * Append a JPQL filtering expression to a WHERE statement.
	 * @param baseFilterExpression The existing WHERE statement upon which to append, or the empty string.
	 * @param addedFilterExpression The added expression.
	 * @return Returns the combined WHERE statement.
	 */
	public static String appendFilter(String baseFilterExpression, String addedFilterExpression)
	{
		if (baseFilterExpression == null) 
			throw new IllegalArgumentException("Argument 'baseFilterExpression' must not be null.");
		
		if (addedFilterExpression == null) 
			throw new IllegalArgumentException("Argument 'addedFilterExpression' must not be null.");
		
		if (baseFilterExpression.isEmpty()) return " WHERE " + addedFilterExpression;
		return baseFilterExpression + " AND " + addedFilterExpression;
	}
	
	public static <X> Join<Multilingual, Literal> joinLiterals(
			Root<X> entityExpression, 
			String multilingualAttributeName)
	{
		return joinLiterals((From<X, X>)entityExpression, multilingualAttributeName);
	}
	
	public static <X> Fetch<Multilingual, Literal> fetchLiterals(
			Root<X> entityExpression, 
			String multilingualAttributeName)
	{
		return fetchLiterals((From<X, X>)entityExpression, multilingualAttributeName);
	}
	
	public static <X, Y> Join<Multilingual, Literal> joinLiterals(
			From<X, Y> entityExpression, 
			String multilingualAttributeName)
	{
		Join <X, Multilingual> multilingualJoin = 
			entityExpression.join(multilingualAttributeName);
		
		Join<Multilingual, Literal> literalsJoin = multilingualJoin.join("literals");
		
		return literalsJoin;
		
	}
	
	public static <X, Y> Fetch<Multilingual, Literal> fetchLiterals(
			From<X, Y> entityExpression, 
			String multilingualAttributeName)
	{
		Fetch<Y, Multilingual> multilingualFetch = 
			entityExpression.fetch(multilingualAttributeName, JoinType.LEFT);
		
		return multilingualFetch.<Multilingual, Literal>fetch("literals", JoinType.LEFT);
	}
	
	public static <X, Y> Join<Multilingual, Literal> joinLiterals(
			From<X, Y> entityExpression, 
			SingularAttribute<Y, Multilingual> multilingualAttribute)
	{
		Join <Y, Multilingual> multilingualJoin = 
			entityExpression.join(multilingualAttribute);
		
		Join<Multilingual, Literal> literalsJoin = 
			multilingualJoin.join(Multilingual_.literals);
		
		return literalsJoin;
	}
	
	public static <X, Y> Fetch<Multilingual, Literal> fetchLiterals(
			From<X, Y> entityExpression, 
			SingularAttribute<Y, Multilingual> multilingualAttribute)
	{
		Fetch<Y, Multilingual> multilingualFetch = 
			entityExpression.fetch(multilingualAttribute, JoinType.LEFT);
		
		return multilingualFetch.fetch(Multilingual_.literals, JoinType.LEFT);
	}
	
	public static <X> Join<Multilingual, Literal> joinLiterals(
			Root<X> entityExpression, 
			SingularAttribute<X, Multilingual> multilingualAttribute)
	{
		return joinLiterals((From<X, X>)entityExpression, multilingualAttribute);
	}
	
	public static <X> Fetch<Multilingual, Literal> fetchLiterals(
			Root<X> entityExpression, 
			SingularAttribute<X, Multilingual> multilingualAttribute)
	{
		return fetchLiterals((From<X, X>)entityExpression, multilingualAttribute);
	}
	
	public static Predicate[] listToArray(List<Predicate> list)
	{
		Predicate[] array = new Predicate[list.size()];
		
		list.toArray(array);
		
		return array;
	}
	
	public static Expression<?>[] expressionsToArray(List<Expression<?>> list)
	{
		Expression<?>[] array = new Expression<?>[list.size()];
		
		list.toArray(array);
		
		return array;
	}
	
	public static String getPrefixPattern(String value)
	{
		return value.replace("%", "") + "%";
	}
	
	public static String joinLiteralsForEntities(
			String entityAlias, 
			String multilingualName,
			String literalAlias)
	{
		if (entityAlias == null) 
			throw new IllegalArgumentException("Argument 'entityAlias' must not be null.");
		if (multilingualName == null) 
			throw new IllegalArgumentException("Argument 'multilingualName' must not be null.");
		if (literalAlias == null) 
			throw new IllegalArgumentException("Argument 'literalAlias' must not be null.");
		
		String multilingualAlias = 
			String.format("multilingual%d", uniqueId.getAndIncrement());
		
		return String.format(
				" JOIN FETCH %s.%s %s JOIN FETCH %s.literals %s ",
				entityAlias,
				multilingualName,
				multilingualAlias,
				multilingualAlias,
				literalAlias);
	}
	
	public static String joinLiteralsForCount(
			String entityAlias, 
			String multilingualName,
			String literalAlias)
	{
		if (entityAlias == null) 
			throw new IllegalArgumentException("Argument 'entityAlias' must not be null.");
		if (multilingualName == null) 
			throw new IllegalArgumentException("Argument 'multilingualName' must not be null.");
		if (literalAlias == null) 
			throw new IllegalArgumentException("Argument 'literalAlias' must not be null.");
		
		String multilingualAlias = 
			String.format("multilingual%d", uniqueId.getAndIncrement());

		return String.format(
//				", IN(%s.%s) %s, IN(%s.literals) %s  ",
				" JOIN %s.%s %s JOIN %s.literals %s ",
				entityAlias,
				multilingualName,
				multilingualAlias,
				multilingualAlias,
				literalAlias);
	}
	
	public static <T, C extends SearchCriteria> SearchResult<T> builderSearch(
			C criteria,
			Assembler<? extends T, C> builderFunction,
			EntityManager entityManager)
	{
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		// Setup of entities query.
		@SuppressWarnings("unchecked")
		CriteriaQuery<T> criteriaEntitiesQuery = (CriteriaQuery<T>)builder.createQuery();
		
		Root<? extends T> entitiesRoot = 
			builderFunction.build(criteria, criteriaEntitiesQuery, true, builder);
		
		criteriaEntitiesQuery.select(entitiesRoot);
		
		TypedQuery<T> entitiesQuery = entityManager.createQuery(criteriaEntitiesQuery);

		// Setup of count query.
		TypedQuery<Long> countQuery = null;
		
		if (criteria.isRetrievingTotalCount())
		{
			CriteriaQuery<Long> criteriaCountQuery = builder.createQuery(Long.class);
			
			Root<? extends T> countRoot = 
				builderFunction.build(criteria, criteriaCountQuery, false, builder);
			
			criteriaCountQuery.select(builder.countDistinct(countRoot));
			
			countQuery = entityManager.createQuery(criteriaCountQuery);
		}
		
		return QueryHelper.createSearchResult(entitiesQuery, countQuery, criteria);
	}

	private static <T> void setTotalCount(SearchResult<T> searchResult, TypedQuery<Long> countQuery)
	{
		Long totalCount = getFirstOrDefault(countQuery);
		
		searchResult.setTotalCount(totalCount != null ? totalCount.intValue() : 0);
	}
	
	public static <T, C extends SearchCriteria, R> SearchResult<T> builderSearch(
			C criteria,
			ProjectorAssembler<? extends T, C> builderFunction,
			EntityManager entityManager,
			Class<R> rootClass)
	{
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		// Setup of entities query.
		@SuppressWarnings("unchecked")
		CriteriaQuery<T> criteriaEntitiesQuery = (CriteriaQuery<T>)builder.createQuery();
		
		Selection<? extends T> entitiesRoot = 
			builderFunction.build(criteria, criteriaEntitiesQuery, true, builder);
		
		criteriaEntitiesQuery.select(entitiesRoot);
		
		TypedQuery<T> entitiesQuery = entityManager.createQuery(criteriaEntitiesQuery);

		// Setup of count query.
		TypedQuery<Long> countQuery = null;
		
		if (criteria.isRetrievingTotalCount())
		{
			CriteriaQuery<Long> criteriaCountQuery = builder.createQuery(Long.class);
			
			builderFunction.build(criteria, criteriaCountQuery, false, builder);
				
			Root<R> countRoot = criteriaCountQuery.from(rootClass);
			
			criteriaCountQuery.select(builder.countDistinct(countRoot));
			
			countQuery = entityManager.createQuery(criteriaCountQuery);
		}
		
		return QueryHelper.createSearchResult(entitiesQuery, countQuery, criteria);
	}
}
