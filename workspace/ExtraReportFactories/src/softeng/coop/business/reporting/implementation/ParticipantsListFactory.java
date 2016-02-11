package softeng.coop.business.reporting.implementation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;

import softeng.coop.business.reporting.DataType;
import softeng.coop.business.reporting.IParameterType;

/**
 * Supports the list of participants report.
 */
public class ParticipantsListFactory extends ReportFactoryBase
{

	@Override
	public List<IParameterType> getParametersSpecification()
	{
		ArrayList<IParameterType> list = new ArrayList<IParameterType>();
		
		list.add(new ParameterType("FINANCIAL_SOURCE", DataType.CoopFinancialSource, true));
		
		return list;
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
