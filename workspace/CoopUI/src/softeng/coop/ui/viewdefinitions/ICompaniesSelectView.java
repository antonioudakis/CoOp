package softeng.coop.ui.viewdefinitions;

import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public interface ICompaniesSelectView
extends IView<DataItemContainer<Company>, ICoopContext>
{
	IHierarchyView<Category> getCategoriesView();
	
	Field getCompanyNameSearchField();
	
	void addSearchListener(
			IViewListener<DataItemContainer<Company>, ICoopContext, ICompaniesSelectView> listener);

	void removeSearchListener(
			IViewListener<DataItemContainer<Company>, ICoopContext, ICompaniesSelectView> listener);
	
	void addAddCompanyListener(
			IListener<ModelEvent<Company>> listener);
	
	void removeAddCompanyListener(
			IListener<ModelEvent<Company>> listener);
	
	void addRemoveCompanyListener(
			IListener<ModelEvent<Company>> listener);
	
	void removeRemoveCompanyListener(
			IListener<ModelEvent<Company>> listener);
	
	void addCanRemoveCompanyListener(
			IListener<ElementExecutionVote<Company>> listener);
	
	void removeCanRemoveCompanyListener(
			IListener<ElementExecutionVote<Company>> listener);
	
	DataItemContainer<Company> getFoundCompanies();
	
	void setFoundCompanies(DataItemContainer<Company> foundCompaniesContainer);
}
