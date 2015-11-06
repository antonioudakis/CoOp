package softeng.coop.ui.viewdefinitions;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public interface IFinancialCardView
extends IFormView<BeanItem<FinancialActionViewModel>>
{
	ITableView<CoOp, Registration> getAssignedRegistrationsTableView();
	
	ITableView<CoOp, Registration> getUnassignedRegistrationsTableView();
	
	IOkCancelView getOkCancelView();
	
	void setFinancialSources(CoOp coop, Collection<FinancialSource> financialSources);
	
	void addExecuteActionListener(IListener<CommandExecutionVote> listener);
	void removeExecuteActionListener(IListener<CommandExecutionVote> listener);
}
