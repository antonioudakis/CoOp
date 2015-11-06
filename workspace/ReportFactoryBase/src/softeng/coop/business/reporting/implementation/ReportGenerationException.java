package softeng.coop.business.reporting.implementation;

import java.util.*;

import org.eclipse.birt.report.engine.api.*;

import softeng.coop.business.*;

/**
 * Thrown during report generation.
 */
public class ReportGenerationException
extends CoOpException
{
	private static final long serialVersionUID = 1L;
	
	private List<EngineException> errors;
	
	public ReportGenerationException(List<EngineException> errors)
	{
		super(formatMessage(errors));
		
		this.errors = errors;
	}
	
	/**
	 * The collection of individual errors which lead to this exception.
	 */
	public List<EngineException> getErrors()
	{
		return this.errors;
	}
	
	private static String formatMessage(List<EngineException> errors)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Error during report generation, reasons: ");
		
		for (int i = 0; i < errors.size(); i++)
		{
			builder.append("\n");
			builder.append(i);
			builder.append(") ");
			builder.append(errors.get(i).getMessage());
		}
		
		return builder.toString();
	}
	
}
