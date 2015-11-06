package softeng.coop.business.reporting.implementation;

import java.util.*;

import org.eclipse.birt.report.engine.api.*;

import softeng.coop.business.reporting.*;

public class CompaniesCorrespondenceFactory extends ReportFactoryBase
{

	@Override
	public List<IParameterType> getParametersSpecification()
	{
		List<IParameterType> parameters = new ArrayList<IParameterType>();
		
		parameters.add(new ParameterType("LIST_ALL_COMPANIES", DataType.Boolean));
		parameters.add(new ParameterType("DATE", DataType.Date));
		parameters.add(new ParameterType("DEAN_NAME", DataType.String));
		parameters.add(new ParameterType("DEAN_NAME_ENGLISH", DataType.String, false));
		parameters.add(new ParameterType("DEAN_GENDER", DataType.Gender));
		parameters.add(new ParameterType("PROTOCOL_NUMBER", DataType.String, false));
		parameters.add(new ParameterType("ROOT_CATEGORY", DataType.Category, false));
		
		return parameters;
	}

	@Override
	protected IRenderOption getRenderOption()
	{
		RenderOption renderOption = new RenderOption();
		renderOption.setOption(IRenderOption.HTML_PAGINATION, true);
		renderOption.setOutputFormat("doc");
		
		return renderOption;
	}

	@Override
	protected String getContentType()
	{
		return "application/msword";
	}

	@Override
	protected String getFilenameSuffix()
	{
		return ".doc";
	}

}
