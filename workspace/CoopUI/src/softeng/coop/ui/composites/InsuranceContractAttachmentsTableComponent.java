package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

public class InsuranceContractAttachmentsTableComponent
extends AttachmentsTableComponent<InsuranceContract>
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void addToParent(Attachment item)
	{
		item.setInsuranceContract(getParentModel());
	}

	@Override
	protected void removeFromParent(Attachment item)
	{
		item.setInsuranceContract(null);
	}
}
