package softeng.coop.ui.viewdefinitions;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.reporting.*;

import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public interface IChooseReportTypeView
extends IModalView<ReportTypeParameters>
{
	BeanItemContainer<ReportType> getReportTypesContainer();
	void setReportTypesContainer(BeanItemContainer<ReportType> reportTypesContainer);
	
	void addSelectedReportTypeChangedListener(IListener<ModelEvent<ReportType>> listener);
	void removeSelectedReportTypeChangedListener(IListener<ModelEvent<ReportType>> listener);
	
	List<IParameterType> getParameterTypes();
	
	void setParameterTypes(List<IParameterType> parameterTypes);
	
	PropertysetItem getParameters();
	
	ReportType getSelectedReportType();
	
	ReportScope getReportScope();
}
