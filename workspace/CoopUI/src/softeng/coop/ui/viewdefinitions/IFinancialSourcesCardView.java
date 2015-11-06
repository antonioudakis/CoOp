package softeng.coop.ui.viewdefinitions;

import java.util.*;

import softeng.coop.dataaccess.*;

public interface IFinancialSourcesCardView
extends IFormView<Collection<FinancialSource>>
{
	IOkCancelView getOkCancelView();
	
	FinancialSource getSelectedFinancialSource();
}
