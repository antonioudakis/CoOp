package softeng.coop.business.reporting.implementation;

import java.util.*;

import org.eclipse.birt.report.engine.api.*;

import softeng.coop.business.reporting.*;

public class GradesListFactory extends ReportFactoryBase
{

	@Override
	public List<IParameterType> getParametersSpecification()
	{
		List<IParameterType> parameters = new ArrayList<IParameterType>(2);
		
		parameters.add(new ParameterType("ONLY_QUALIFIED_FOR_ASSIGNMENT", DataType.Boolean));
		parameters.add(new ParameterType("ONLY_QUALIFIED_FOR_COMPLETION", DataType.Boolean));
		parameters.add(new ParameterType("NOT_ONLY_ASSIGNED_TO_JOB", DataType.Boolean));
		
		return parameters;
	}

	@Override
	protected IRenderOption getRenderOption()
	{
		EXCELRenderOption renderOption = new EXCELRenderOption();
		renderOption.setOption(IRenderOption.HTML_PAGINATION, true);
		renderOption.setOutputFormat("xls");
		
		return renderOption;
	}

	@Override
	protected String getContentType()
	{
		return "application/vnd.ms-excel";
	}

	@Override
	protected String getFilenameSuffix()
	{
		// Return XML instead of XLS because BIRT engine actually creates
		// XML spreadsheet instead of binary XLS.
		return ".xml";
	}

}
