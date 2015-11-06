package softeng.coop.business;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;

import softeng.coop.business.reporting.*;
import softeng.coop.dataaccess.*;

class ReportsWriterManagerImpl extends ReportsManagerImpl implements WriterManagerBase, ReportsWriterManager
{
	private static class ParameterType
	implements IParameterType
	{
		private String name;
		private String description;
		private String key;
		private DataType dataType;
		private boolean required;
		
		public ParameterType(String name, String description, String key, DataType dataType, boolean required)
		{
			if (name == null) 
				throw new IllegalArgumentException("Argument 'name' must not be null.");
			if (description == null) 
				throw new IllegalArgumentException("Argument 'description' must not be null.");
			if (key == null) 
				throw new IllegalArgumentException("Argument 'key' must not be null.");
			if (dataType == null) 
				throw new IllegalArgumentException("Argument 'dataType' must not be null.");
			
			this.name = name;
			this.description = description;
			this.key = key;
			this.dataType = dataType;
			this.required = required;
		}

		public ParameterType(String name, String description, String key, DataType dataType)
		{
			this(name, description, key, dataType, true);
		}

		@Override
		public String getName(Locale locale)
		{
			return name;
		}

		@Override
		public String getDescription(Locale locale)
		{
			return description;
		}

		@Override
		public String getKey()
		{
			return key;
		}

		@Override
		public DataType getDataType()
		{
			return dataType;
		}

		@Override
		public boolean isRequired()
		{
			return required;
		}

		@Override
		public Object getDefaultValue(Locale locale)
		{
			return null;
		}
		
	}
	
	/**
	 * Default factory to use when report.factoryClassName is empty.
	 */
	private class DefaultReportFactory implements IReportFactory
	{

		@Override
		public Report createReport(Session session, ReportType reportType, Object parameters)
		{
			Report report = new Report();
			
			report.setReportType(reportType);
			
			report.setTitle(getLiteral(reportType.getName()));
			
			return report;
		}

		@Override
		public List<IParameterType> getParametersSpecification()
		{
			// Uncomment the following to test parameter system.
//			ArrayList<IParameterType> list = getTestProperties();
//			
//			return list;
			
			return null;
		}

		@SuppressWarnings("unused")
		private ArrayList<IParameterType> getTestProperties()
		{
			ArrayList<IParameterType> list = new ArrayList<IParameterType>();
			
			list.add(new ParameterType("Category", "Category field test", "Category", DataType.Category));
			list.add(new ParameterType("Boolean", "Boolean field test", "Boolean", DataType.Boolean));
			list.add(new ParameterType("Date", "Date field test", "Date", DataType.Date));
			list.add(new ParameterType("Int", "Int field test", "Int", DataType.Int));
			list.add(new ParameterType("Location", "Location field test", "Location", DataType.Location));
			list.add(new ParameterType("String", "String field test", "String", DataType.String));
			list.add(new ParameterType("Double", "Double field test", "String", DataType.Double));
			return list;
		}
		
	}
	
	private DefaultReportFactory defaultReportFactory;
	
	public ReportsWriterManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public Report createReport(ReportType reportType, Object parameters)
	{
		if (reportType == null) 
			throw new IllegalArgumentException("Argument 'reportType' must not be null.");
		
		// Check whether we have permissions to create this report.
		if (!canAccess(reportType)) return null;
		
		IReportFactory reportFactory = getReportFactory(reportType);
		
		if (reportFactory == null) return null;
		
		Report report = 
			reportFactory.createReport(this.getSession(), reportType, parameters);
		
		report.setReportType(reportType);
		markAsChanged(report);
		
		if (report.getAttachments() == null)
		{
			report.setAttachments(new HashSet<Attachment>());
		}
		else
		{
			for (Attachment attachment : report.getAttachments())
			{
				markAsChanged(attachment);
			}
		}
			
		return report;
	}
	
	@Override
	public void deleteReport(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		Report report = entityManager.find(Report.class, id);
		
		if (report == null) return;
		
		// Check whether we have permissions to delete this report.
		AuthorizationContext authorizationContext = 
			this.getSession().getAuthorizationContext();
		
		EntityAccess entityAccess = 
			authorizationContext.hasEntityAccess(Report.class.getSimpleName());
		
		try
		{
			if (entityAccess.isWritable() || 
					(entityAccess.isOwnWritable() && report.getTracking().getCreatedBy().getId() == authorizationContext.getUserId()))
			{
				for (Attachment attachment : report.getAttachments())
				{
					entityManager.remove(attachment);
				}
				
				entityManager.remove(report);
			}
			
			transactionScope.commit();
			
		}
		finally
		{
			transactionScope.dispose();
		}
			
	}

	@Override
	public Set<ReportType> getAllowedReportTypes()
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		int userId = this.getSession().getAuthorizationContext().getUserId();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ReportType> query = builder.createQuery(ReportType.class);
		
		Root<ReportType> reportTypeRoot = 
			query.from(ReportType.class);
		
		Join <Role, AuthenticatedUser> userJoin = 
			reportTypeRoot.join(ReportType_.roles).join(Role_.users);
		
		query.where(builder.equal(userJoin.get(AuthenticatedUser_.id), builder.literal(userId)));

		return new HashSet<ReportType>(entityManager.createQuery(query).getResultList());
	}

	@Override
	public List<IParameterType> getParametersSpecification(ReportType reportType)
	{
		if (reportType == null) 
			throw new IllegalArgumentException("Argument 'reportType' must not be null.");
		
		if (!canAccess(reportType)) return null;
		
		IReportFactory reportFactory = getReportFactory(reportType);
		
		return reportFactory.getParametersSpecification();
	}

	@Override
	public void persistReport(Report report)
	{
		if (report == null) 
			throw new IllegalArgumentException("Argument 'report' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.markAsChanged(report);
			
			this.getSession().getEntityManager().persist(report);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void persistAttachment(Attachment attachment)
	{
		if (attachment == null) 
			throw new IllegalArgumentException("Argument 'attachment' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();

		try
		{
			this.markAsChanged(attachment);
			
			this.getSession().getEntityManager().persist(attachment);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public boolean isWriteable()
	{
		return true;
	}

	@Override
	public ReportsWriterManager getWriterManager()
	{
		return this;
	}

	private IReportFactory getReportFactory(ReportType reportType)
	{
		// If not class name is specified, use the default factory.
		if (reportType.getFactoryClassName() == null || reportType.getFactoryClassName().isEmpty())
		{
			if (this.defaultReportFactory == null)
			{
				this.defaultReportFactory = new DefaultReportFactory();
			}
			
			return this.defaultReportFactory;
		}
		else
		{
			// The class corresponding to the report type is defined in 
			// Spring file coopBusinessFactory.xml under the name specified by the 
			// reportType.factoryClassName property.
//			IReportFactory reportFactory = 
//				FactoriesRepository
//					.getReportsFactory()
//					.getBean(reportType.getFactoryClassName(), IReportFactory.class);
			
			IReportFactory reportFactory;
			try
			{
				reportFactory = (IReportFactory)Class.forName(reportType.getFactoryClassName()).newInstance();
			}
			catch (Exception e)
			{
				throw new CoOpException(
						"Could not create report factory " + reportType.getFactoryClassName(), 
						e);
			}
			
			return reportFactory;
		}
	}

	@Override
	public void deleteReport(Report report)
	{
		if (report == null) 
			throw new IllegalArgumentException("Argument 'report' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(report);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public Set<ReportType> getAllowedReportTypes(ReportScope scope)
	{
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
				builder.equal(reportTypeRoot.get(ReportType_.scope), builder.literal(scope))
		);

		return new HashSet<ReportType>(entityManager.createQuery(query).getResultList());
	}

}
