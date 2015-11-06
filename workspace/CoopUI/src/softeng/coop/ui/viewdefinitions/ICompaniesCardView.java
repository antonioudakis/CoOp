package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.*;

import softeng.ui.vaadin.data.*;

public interface ICompaniesCardView
	extends IFormView<HierarchicalBeanItemContainer<Category>>
{
	IOkCancelView getOkCancelView();
	
	Company getSelectedCompany();
}
