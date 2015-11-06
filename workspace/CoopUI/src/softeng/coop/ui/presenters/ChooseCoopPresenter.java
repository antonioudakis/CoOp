package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.data.util.BeanItemContainer;

import softeng.coop.business.*;
import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.CoopUIUserException.Severity;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.IChooseCoopView.FilterOptions;
import softeng.ui.vaadin.mvp.*;

public class ChooseCoopPresenter
	extends Presenter<BeanItemContainer<CoOp>, ICoopContext, IChooseCoopView>
{
	public ChooseCoopPresenter(IChooseCoopView view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
		refreshCoopContainer(getView().getFilterOptions());
	}

	protected void refreshCoopContainer(FilterOptions filterOptions)
	{
		Session session = this.getContext().getSession();
		
		if (session == null)
		{
			throw new CoopUISystemException("Should have access to session.");
		}
		
		AuthenticatedUser user = this.getContext().getSession().getAuthenticatedUser();
		
		if (user == null)
		{
			throw new CoopUISystemException("Must have an authenticated user.");
		}
		
		CoOpsManager manager = session.getCoOpsManager();
		if (manager == null)
		{
			throw new CoopUIUserException(
					Severity.Error, 
					getContext().getLocalizedString("ACCESS_DENIED_DESCRIPTION"));
		}
		
		CoOpSearchCriteria criteria = new CoOpSearchCriteria();
		
		if (!filterOptions.isShowOtherDepartments())
		{
			criteria.setDepartment(user.getDepartment());
		}
		else
		{
			if (!filterOptions.isShowOtherUniversities())
			{
				criteria.setUniversity(user.getDepartment().getUniversity());
			}
		}
		
		criteria.setActiveOnly(!filterOptions.isShowInactive());
		
		// List CoOps according to type of authenticated user.
		if (user instanceof Student)
		{
			criteria.setRegisteredByStudent((Student)user);
		}
		
		SearchResult<CoOp> result = manager.searchCoOps(criteria);
		
		DataItemContainer<CoOp> container = 
			new DataItemContainer<CoOp>(CoOp.class, result.getList(), manager);
		
		container.addNullableNestedContainerProperty("lesson.department.name");
		
		ArrayList<String> columnIds = new ArrayList<String>();
		
		columnIds.add("lesson.department.name");
		columnIds.add("academicYear");
		columnIds.add("semester");
		columnIds.add("name");
		columnIds.add("active");
		columnIds.add("inRegistration");
		columnIds.add("setup");
		columnIds.add("gradePolicy");
		columnIds.add("maxGroupSize");

		container.setContainerPropertyIds(columnIds);
		
		getView().setModel(container);
		
		getView().dataBind();
		
		if (session.getDefaultCoop() != null && container.containsId(session.getDefaultCoop()))
		{
			getView().setSelectedCoOp(session.getDefaultCoop());
		}
		else
		{
			getView().setSelectedCoOp(null);
		}
	}

	@Override
	protected void attachToView()
	{
		this.getView().addQueryChangeListener(new IListener<IChooseCoopView.FilterOptions>()
				{
					@Override
					public void onEvent(FilterOptions event)
					{
						refreshCoopContainer(event);
					}
				});
				
	}
}
