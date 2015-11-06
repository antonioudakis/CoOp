package softeng.coop.business.reporting.implementation;

import java.util.*;

import softeng.coop.business.reporting.*;
import org.eclipse.birt.report.engine.api.*;

public class ListaFoititonELEFactory extends ReportFactoryBase
{
	@Override
	public List<IParameterType> getParametersSpecification()
	{
		ArrayList<IParameterType> list = new ArrayList<IParameterType>();
		list.add(new ParameterType("SOURCE", DataType.CoopFinancialSource));
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
		return "application/xls";
	}
	
	@Override
	protected String getFilenameSuffix()
	{
		// Return XML instead of XLS because BIRT engine actually creates
		// XML spreadsheet instead of binary XLS.
		return ".xml";
	}

}
