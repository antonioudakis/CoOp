package softeng.coop.business.reporting.implementation;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import softeng.coop.business.*;
import softeng.coop.business.reporting.*;

import softeng.coop.dataaccess.*;

import org.eclipse.birt.core.framework.*;
import org.eclipse.birt.report.engine.api.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base report factory for BIRT-generated reports including a single
 * attachment, the generated document. For the attachment generation, 
 * this class expects a single BIRT 'rptdesign'
 * document template existing alongside this class having the same name with the class.
 * If the factory requires parameters, it also expects java '.properties'
 * resources describing the parameters. The keys of the resources are the keys of
 * the parameters appended with '_NAME' and '_DESCRIPTION' for parameter names and descriptions
 * respectively.
 */
public abstract class ReportFactoryBase
implements IReportFactory
{
	protected static class TranslatedEntry
	implements Entry<String, Object>
	{
		private String key;
		private Object value;
		
		public TranslatedEntry(String key, Object value)
		{
			if (key == null) 
				throw new IllegalArgumentException("Argument 'key' must not be null.");
			
			this.key = key;
			this.value = value;
		}
		
		@Override
		public Object setValue(Object object)
		{
			this.value = object;
			return object;
		}
		
		@Override
		public Object getValue()
		{
			return value;
		}
		
		@Override
		public String getKey()
		{
			return key;
		}
	};
	
	/**
	 * This class expects that java '.properties' resources accompany the main
	 * class having the same name as the main class name.
	 * The keys of the resources are the keys of
	 * the parameters appended with '_NAME' and '_DESCRIPTION' for parameter names and descriptions
	 * respectively.
	 */
	protected class ParameterType
	implements IParameterType
	{
		private String key;
		private String nameKey, descriptionKey, defaultKey;
		private DataType dataType;
		private boolean required;
		
		/**
		 * Create a parameter.
		 * @param key The key of the parameter. In the .properties file, two keys with added suffices _NAME and _DESCRIPTION are expected.
		 * @param dataType The data type of the parameter. 
		 * @param required Set to true if the parameter is required.
		 */
		public ParameterType(String key, DataType dataType, boolean required)
		{
			if (key == null) 
				throw new IllegalArgumentException("Argument 'key' must not be null.");
			if (dataType == null) 
				throw new IllegalArgumentException("Argument 'dataType' must not be null.");
			
			this.dataType = dataType;
			this.key = key;
			this.nameKey = key + "_NAME";
			this.descriptionKey = key + "_DESCRIPTION";
			this.defaultKey = key + "_DEFAULT";
			this.required = required;
		}
		
		/**
		 * Create a parameter. The parameter is set as required. Use the other constructor overload to set as optional.
		 * @param key The key of the parameter. In the .properties file, two keys with added suffices _NAME and _DESCRIPTION are expected.
		 * @param dataType The data type of the parameter. 
		 */
		public ParameterType(String key, DataType dataType)
		{
			this(key, dataType, true);
		}
		
		@Override
		public String getName(Locale locale)
		{
			return getLocalizedString(nameKey, locale);
		}

		@Override
		public String getDescription(Locale locale)
		{
			return getLocalizedString(descriptionKey, locale);
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
			if (!containsLocalizedString(defaultKey, locale)) return null;
			
			return getLocalizedString(defaultKey, locale);
		}
		
	}
	
	/**
	 * Get a localized string by consulting the resources accompanying
	 * the class.
	 */
	protected String getLocalizedString(String resourceKey, Locale locale)
	{
		ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getCanonicalName(), locale);
		
		return bundle.getString(resourceKey);
	}
	
	/**
	 * Checks whether a localized string exists in the resources accompanying
	 * the class.
	 */
	protected boolean containsLocalizedString(String resourceKey, Locale locale)
	{
		ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getCanonicalName(), locale);
		
		return bundle.containsKey(resourceKey);
	}
	
	/**
	 * Translate from a parameter dictionary as specified by the application
	 * to a dictionary of values suitable for BIRT.
	 * By default, the method used translateParameter for each entry. 
	 * @param inputParameters The input dictionary.
	 * @return The translated dictionary.
	 */
	protected Map<String, Object> translateParameters(Map<String, Object> inputParameters)
	{
		Map<String, Object> outputParameters = new HashMap<String, Object>();
		
		for (Entry<String, Object> entry : inputParameters.entrySet())
		{
			Entry<String, Object> translatedEntry = translateParameter(entry);
			
			outputParameters.put(translatedEntry.getKey(), translatedEntry.getValue());
		}
		
		return outputParameters;
	}
	
	/**
	 * Translate a single parameter as specified by the application
	 * to a parameter suitable for BIRT.
	 * By default, entities are translated to their primary keys.
	 * @param entry the entry to translate.
	 * @return The translated entry.
	 */
	protected Entry<String, Object> translateParameter(Entry<String, Object> entry)
	{
		Object value = entry.getValue();
		
		if (value != null)
		{
			Class<?> type = value.getClass();
			
			if (value instanceof ITrackedEntity)
			{
				ITrackedEntity entity = (ITrackedEntity)value;
				
				value = entity.getId();
			}
			else if (type.isEnum())
			{
				Enum<?> enumValue = (Enum<?>)value;
				
				value = enumValue.ordinal();
			}
			else if (type.equals(java.util.Date.class))
			{
				// BIRT only works with java.sql.Date! (...) 
				java.util.Date date = (java.util.Date)value;
				
				value = new java.sql.Date(date.getTime());
			}
		}
		
		return new TranslatedEntry(entry.getKey(), value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Report createReport(Session session, ReportType reportType, Object parameters)
	{
		IReportEngine engine = null;
		EngineConfig config = null;
		
		try
		{
			config = new EngineConfig( );
			
			Platform.startup( config );
			
			IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			
			engine = factory.createReportEngine( config );
			
			
			//start
			
			String rtpDesignFilename = getReportTemplateFilename(session, reportType);
			
			InputStream designInputStream = null;
			IReportRunnable design = null;

			try
			{
				designInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(rtpDesignFilename);
				
				//Open a report design 
				design = engine.openReportDesign(designInputStream);
			}
			catch (RuntimeException ex)
			{
				if (designInputStream != null) designInputStream.close();
				
				throw ex;
			}

			IRunAndRenderTask task = engine.createRunAndRenderTask(design); 

			//Set parent class loader for engine
			task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());
			
			// Set JDBC connection.
			task.getAppContext().put("OdaJDBCDriverPassInConnection", getConnection());
			
			task.setLocale(session.getUserLocale());

			Map<String, Object> translatedParameters = 
				this.translateParameters((Map<String, Object>)parameters);
			
			//Set parameter values and validate
			
			for (Map.Entry<String, Object> entry : translatedParameters.entrySet())
			{
				task.setParameterValue(entry.getKey(), entry.getValue());
			}
			
			task.validateParameters();
							
			//Setup rendering.
			IRenderOption renderOption = this.getRenderOption();		
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			renderOption.setOutputStream(outputStream);
			
			task.setRenderOption(renderOption);

			//run and render report
			task.run();

			List<EngineException> errors = task.getErrors();
			
			task.close();
			
			engine.destroy();
			
			if (errors != null && errors.size() > 0)
				throw new ReportGenerationException(errors);
			
			Report report = new Report();
			
			report.setTitle(getReportTitle(session, reportType));
			report.setComments(getReportComments(session, reportType));
			
			report.setAttachments(new HashSet<Attachment>());

			report.setDateSubmitted(new Date());
			
			Attachment attachment = new Attachment();
			
			attachment.setName(report.getTitle() + this.getFilenameSuffix());
			attachment.setContent(outputStream.toByteArray());
			attachment.setContentType(this.getContentType());
			
			attachment.setTracking(new Tracking());
			
			report.getAttachments().add(attachment);
			attachment.setReport(report);
			
			return report;
		}
		catch (ReportGenerationException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new CoOpException("Error during report generation, reason: " + ex.getMessage(), ex);
		}
	}
	
	/**
	 * Get the report's title.
	 */
	protected String getReportTitle(Session session, ReportType reportType)
	{
		// Obtain a base manager to translate literals to the user's language.
		ManagerBase baseManager = session.getBaseManager();

		return baseManager.getLiteral(reportType.getName());
	}
	
	/**
	 * Get the report's comments.
	 */
	protected String getReportComments(Session session, ReportType reportType)
	{
		if (reportType.getComments() == null) return null;
		
		// Obtain a base manager to translate literals to the user's language.
		ManagerBase baseManager = session.getBaseManager();

		return baseManager.getLiteral(reportType.getComments());
	}
	
	/**
	 * Supply the render option for the report.
	 */
	protected abstract IRenderOption getRenderOption();
	
	/**
	 * The MIME type.
	 */
	protected abstract String getContentType();
	
	/**
	 * Return the filename suffix for the generated attachment, including the dot.
	 * Must agree of course with ContentType.
	 * The prefix of the filename is the the supplied ReportTitle.
	 */
	protected abstract String getFilenameSuffix();
	
	/**
	 * Opens a JDBC connection for the generation of the report.
	 * It is expected that pooling is offered.
	 */
	protected Connection getConnection()
	{
		try
		{
			Connection connection = ConnectionProvider.acquireConnection();
			
			if (connection.isClosed())
			{
				System.out.println("Connection is closed!");
			}
			
			return connection;
		}
		catch (SQLException e)
		{
			throw new CoOpException("Could not open JDBC connection for reporting", e);
		}
	}
	
	/**
	 * Get the BIRT report template file name for this factory.
	 * Default implementation returns a name having basic part the factory's class name plus the 
	 * university ID plus the
	 * department ID dot separated, if such a resource exists, else fall back to university ID alone,
	 * else returns the factory class name alone.
	 * The returned name always contains the suffix ".rptdesign".
	 */
	protected String getReportTemplateFilename(Session session, ReportType reportType)
	{
		if (session == null) 
			throw new IllegalArgumentException("Argument 'session' must not be null.");
		
		String classFileBase = this.getClass().getCanonicalName().replace('.', '/');

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		CoOp coop = session.getDefaultCoop();
		
		if (coop != null)
		{
			Department department = coop.getLesson().getDepartment();
			
			University university = department.getUniversity();
			
			String departmentSpecificFilename = String.format("%s.%d.%d.rptdesign", classFileBase, university.getId(), department.getId());
			
			if (classLoader.getResource(departmentSpecificFilename) != null) return departmentSpecificFilename;
			
			String universitySpecificFilename = String.format("%s.%d.rptdesign", classFileBase, university.getId());

			if (classLoader.getResource(universitySpecificFilename) != null) return universitySpecificFilename;
		}
		
		return classFileBase + ".rptdesign";
	}
}
