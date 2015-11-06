package softeng.coop.business.reporting.implementation;

import java.util.*;
import softeng.coop.business.reporting.*;
import org.eclipse.birt.report.engine.api.*;

public class KatastasiApozimioseonFactory extends ReportFactoryBase
{
	@Override
	public List<IParameterType> getParametersSpecification()
	{
		ArrayList<IParameterType> list = new ArrayList<IParameterType>();
		
		list.add(new ParameterType("SOURCE", DataType.CoopFinancialSource));
		//list.add(new ParameterType("DOCUMENT_DATE", DataType.Date, false));
		
		return list;
	}

	protected RenderOption getRenderOption(){
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
