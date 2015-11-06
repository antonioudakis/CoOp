package softeng.coop.ui.forms;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.data.MultilingualValidationForm;

@SuppressWarnings("serial")
public class JobForm
extends MultilingualValidationForm<Job>
{

	public JobForm()
	{
		super(Job.class);
	}

}
