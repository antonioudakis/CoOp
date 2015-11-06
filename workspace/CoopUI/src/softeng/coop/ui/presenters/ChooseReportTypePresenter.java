package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.reporting.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.DataItemContainer;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class ChooseReportTypePresenter
extends Presenter<ReportTypeParameters, ICoopContext, IChooseReportTypeView>
{
	private ReportsWriterManager manager;
	
	public ChooseReportTypePresenter(IChooseReportTypeView view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getReportsManager() == null || 
				!session.getReportsManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		manager = session.getReportsManager().getWriterManager();
		
		getView().addSelectedReportTypeChangedListener(new IListener<ModelEvent<ReportType>>()
		{
			@Override
			public void onEvent(ModelEvent<ReportType> event)
			{
				onSelectedReportTypeChanged(event.getModel());
			}
		});
	}

	protected void onSelectedReportTypeChanged(ReportType reportType)
	{
		if (manager == null) return;
		
		List<IParameterType> parameterTypes = manager.getParametersSpecification(reportType);
		
		getView().setParameterTypes(parameterTypes);
	}

	@Override
	protected void setupView()
	{
		if (manager == null) return;
		
		DataItemContainer<ReportType> reportTypesContainer =
			new DataItemContainer<ReportType>(
					ReportType.class, 
					manager.getAllowedReportTypes(getView().getReportScope()), 
					manager);
		
		getView().setReportTypesContainer(reportTypesContainer);
		
		getView().dataBind();
	}

}
