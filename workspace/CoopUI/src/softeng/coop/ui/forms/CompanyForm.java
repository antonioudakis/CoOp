package softeng.coop.ui.forms;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.data.*;

public class CompanyForm
extends MultilingualValidationForm<Company>
{
	private static final long serialVersionUID = 1L;

	public CompanyForm()
	{
		super(Company.class);
	}
}

