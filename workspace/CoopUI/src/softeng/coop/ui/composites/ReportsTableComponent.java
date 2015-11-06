package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public abstract class ReportsTableComponent<P>
extends TableComponent<P, Report>
implements IReportsTableView<P>
{
	private static final long serialVersionUID = 1L;
	
	protected ReportScope reportScope;
	
	private Report newElement;
	
	private IReportCreator reportCreator;

	public ReportsTableComponent(ReportScope reportScope)
	{
		super(getDefaultColumnSpecifications());
		
		if (reportScope == null) 
			throw new IllegalArgumentException("Argument 'reportScope' must not be null.");
		
		this.reportScope = reportScope;
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		ArrayList<ColumnSpecification> specifications = 
			new ArrayList<TableComponent.ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("title", "TITLE_CAPTION"));
		specifications.add(new ColumnSpecification("tracking.created", "CREATION_DATE_CAPTION"));
		specifications.add(new ColumnSpecification("tracking.createdBy.surname", "CREATOR_SURNAME_CAPTION"));
		specifications.add(new ColumnSpecification("tracking.createdBy.name", "CREATOR_NAME_CAPTION"));
		
		return specifications;
	}

	@Override
	public Class<?> getType()
	{
		return Report.class;
	}

	@Override
	protected IModalView<BeanItem<Report>> showAddForm(BeanItem<Report> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Report>> showEditForm(BeanItem<Report> item)
	{
		return showForm(item);
	}

	@Override
	protected Report createNewElement()
	{
		if (reportCreator == null) return null;
		
		// If the report type has been chosen and the factory has run,
		// supply the new report, else show the dialog to do so. 
		if (this.newElement != null)
		{
			Report element = this.newElement;
			this.newElement = null;
			return element;
		}
		else
		{
			final ChooseReportTypeDialog chooseReportTypeDialog = createChooseReportTypeDialog();
			
			chooseReportTypeDialog.setModal(true);
			
			getApplication().getMainWindow().addWindow(chooseReportTypeDialog);
			
			chooseReportTypeDialog.dataBind();
			
			// If the user selected a successful report creation, call 'add'
			// again to insert the new report.
			
			chooseReportTypeDialog.getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
			{
				@Override
				public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
				{
					if (chooseReportTypeDialog.getModel() != null)
					{
						// Call report factory and subsequently the 'add' method again.
						ReportTypeParameters reportTypeParameters = chooseReportTypeDialog.getModel();
						
						newElement = reportCreator.createReport(
								reportTypeParameters.getReportType(), 
								reportTypeParameters.getParametersMap());
						
						add();
					}
				}
			});
		}
		
		
		return null;
	}

	protected ChooseReportTypeDialog createChooseReportTypeDialog()
	{
		ChooseReportTypeDialog chooseReportTypeDialog = new ChooseReportTypeDialog(reportScope);
		
		return chooseReportTypeDialog;
	}

	@Override
	protected Presenter<Collection<Report>, ICoopContext, ? extends IView<Collection<Report>, ICoopContext>> supplyPresenter()
	{
		return new ReportsTablePresenter<P>(this);
	}

	@Override
	protected String getResourceBaseName()
	{
		return ReportsTableComponent.class.getCanonicalName();
	}

	@Override
	protected BeanItemContainer<Report> createBeanItemContainer(Collection<Report> collection)
	{
		RestrictedBeanItemContainer<Report> container = 
			new RestrictedBeanItemContainer<Report>(Report.class, collection);
		
		container.addNullableNestedContainerProperty("tracking.created");
		container.addNullableNestedContainerProperty("tracking.createdBy.surname");
		container.addNullableNestedContainerProperty("tracking.createdBy.name");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	public ReportScope getReportScope()
	{
		return reportScope;
	}

	@Override
	protected abstract void addToParent(Report item);
	
	@Override
	protected abstract void removeFromParent(Report item);
	
	protected IModalView<BeanItem<Report>> showForm(BeanItem<Report> item)
	{
		// If the item is null, it means that, if called during an addition,
		// the user canceled the selection of the required
		// report type. This means that we should stop any further process by 
		// returning null;
		
		if (item == null) return null;
		
		ReportDialog reportDialog = new ReportDialog();
		
		reportDialog.setModal(true);
		reportDialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(reportDialog);
		
		reportDialog.dataBind();

		return reportDialog;
	}

	@Override
	public softeng.coop.ui.viewdefinitions.IReportsTableView.IReportCreator getReportCreator()
	{
		return reportCreator;
	}

	@Override
	public void setReportCreator(IReportCreator reportCreator)
	{
		if (reportCreator == null) 
			throw new IllegalArgumentException("Argument 'reportCreator' must not be null.");
		
		this.reportCreator = reportCreator;
	}
	
}
