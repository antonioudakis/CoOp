package softeng.coop.ui.forms;

import softeng.coop.dataaccess.FinancialSource;
import softeng.coop.ui.data.MultilingualValidationForm;

public class FinancialSourceForm
extends MultilingualValidationForm<FinancialSource>
{
	private static final long serialVersionUID = 1L;

	public FinancialSourceForm()
	{
		super(FinancialSource.class);
	}
}
