package softeng.coop.ui.forms;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.data.MultilingualValidationForm;

public class InsuranceContractForm
extends MultilingualValidationForm<InsuranceContract>
{
	private static final long serialVersionUID = 1L;

	public InsuranceContractForm()
	{
		super(InsuranceContract.class);
	}

}
