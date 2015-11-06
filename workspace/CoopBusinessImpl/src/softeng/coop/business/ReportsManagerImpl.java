package softeng.coop.business;

import softeng.coop.business.reporting.*;
import softeng.coop.dataaccess.*;

import javax.persistence.*;
import javax.persistence.criteria.*;

class ReportsManagerImpl extends ManagerBaseImpl implements ReportsManager
{
	public ReportsManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public boolean isWriteable()
	{
		return false;
	}

	@Override
	public Report getReport(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
//		TypedQuery<Report> query = entityManager.createQuery(
//				"SELECT rep FROM Report rep JOIN FETCH rep.reportType WHERE rep.id = :id", 
//				Report.class)
//				.setParameter("id", id);
//		
//		Report report = QueryHelper.getFirstOrDefault(query);
//		
//		return accessCheck(report);
		
		int userId = this.getSession().getAuthorizationContext().getUserId();

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Report> query = builder.createQuery(Report.class);
		
		Root<Report> reportRoot = query.from(Report.class);
		
		query.where(
				builder.equal(
						reportRoot.get(Report_.id), 
						builder.literal(id)),
				builder.equal(
						reportRoot.join(Report_.reportType).join(ReportType_.roles).join(Role_.users).get(AuthenticatedUser_.id), 
						builder.literal(userId))
				);
		
		return QueryHelper.getFirstOrDefault(entityManager.createQuery(query));
	}

	protected boolean canAccess(ReportType reportType)
	{
		if (reportType == null) return false;
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		int userId = this.getSession().getAuthorizationContext().getUserId();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ReportType> query = builder.createQuery(ReportType.class);
		
		Root<ReportType> reportTypeRoot = 
			query.from(ReportType.class);
		
		Join <Role, AuthenticatedUser> userJoin = 
			reportTypeRoot.join(ReportType_.roles).join(Role_.users);
		
		query.where(
				builder.equal(userJoin.get(AuthenticatedUser_.id), builder.literal(userId)),
				builder.equal(reportTypeRoot, builder.literal(reportType)));
		
		return QueryHelper.getFirstOrDefault(entityManager.createQuery(query)) != null;
	}
	
//	private Report accessCheck(Report report)
//	{
//		if (report == null) return null;
//		
//		// Reject the loading if the user has not the appropriate access. 
//
//		AuthorizationContext authorizationContext = 
//			this.getSession().getAuthorizationContext();
//		
//		if (!canAccess(report.getReportType()))
//		{
//			return null;
//		}
//		
//		EntityAccess entityAccess = 
//			authorizationContext.hasEntityAccess(Report.class.getSimpleName());
//		
//		if (entityAccess == null) return null;
//		
//		if (!entityAccess.isReadable() || 
//				(entityAccess.isOwnReadable() && 
//				report.getTracking().getCreatedBy().getId() != authorizationContext.getUserId()))
//		{
//			return null;
//		}
//		
//		return report;
//	}

	@Override
	public ReportType getReportType(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return entityManager.find(ReportType.class, id);
	}

	@Override
	public ReportsWriterManager getWriterManager()
	{
		return null;
	}

	@Override
	public ReportType getReportType(String codeName)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TypedQuery<ReportType> query = 
			entityManager.createQuery(
					"SELECT rt FROM ReportType rt WHERE rt.codeName = :codeName", 
					ReportType.class)
					.setParameter("codeName", codeName);
		
		return QueryHelper.getFirstOrDefault(query);
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "ReportsManager";
	}

}
