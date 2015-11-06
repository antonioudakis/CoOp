package softeng.coop.business.reporting.implementation;

import java.util.*;

import org.eclipse.birt.report.engine.api.*;

import softeng.coop.business.reporting.*;

public class CoopCompaniesFactory extends ReportFactoryBase
{

	@Override
	public List<IParameterType> getParametersSpecification()
	{
		List<IParameterType> parameters = new ArrayList<IParameterType>();
		
		parameters.add(new ParameterType("LIST_ALL_COMPANIES", DataType.Boolean));
		parameters.add(new ParameterType("ROOT_CATEGORY", DataType.Category, false));
		
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
