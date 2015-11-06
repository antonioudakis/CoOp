package softeng.coop.business.reporting.implementation;

import java.util.*;
import softeng.coop.business.reporting.*;
import org.eclipse.birt.report.engine.api.*;

public class Entypo6Factory extends ReportFactoryBase
{
	@Override
	public List<IParameterType> getParametersSpecification()
	{
		ArrayList<IParameterType> list = new ArrayList<IParameterType>();
		
		list.add(new ParameterType("SOURCE", DataType.CoopFinancialSource));
		list.add(new ParameterType("PROGRAM_TITLE", DataType.String));
		list.add(new ParameterType("ACCOUNT", DataType.String));
		list.add(new ParameterType("FOR_TRANSPORTATION", DataType.Boolean));
		list.add(new ParameterType("ATTACHED_DOCUMENTS", DataType.String, false));
		
		return list;
	}

	protected RenderOption getRenderOption()
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
