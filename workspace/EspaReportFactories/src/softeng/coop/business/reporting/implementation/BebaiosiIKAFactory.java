package softeng.coop.business.reporting.implementation;

import java.util.*;

import softeng.coop.business.reporting.*;
import org.eclipse.birt.report.engine.api.*;

public class BebaiosiIKAFactory extends ReportFactoryBase
{
	@Override
	public List<IParameterType> getParametersSpecification()
	{
		ArrayList<IParameterType> list = new ArrayList<IParameterType>();
		
		list.add(new ParameterType("CHAIRMAN", DataType.String));
		list.add(new ParameterType("CHAIRMAN_TITLE", DataType.String));
		//list.add(new ParameterType("DOCUMENT_DATE", DataType.Date));
		
		return list;
	}

	protected RenderOption getRenderOption(){
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
