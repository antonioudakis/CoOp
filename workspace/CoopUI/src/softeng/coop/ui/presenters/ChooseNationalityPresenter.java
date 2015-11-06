package softeng.coop.ui.presenters;

import java.util.Collection;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.students.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.viewdefinitions.*;

import com.vaadin.data.util.*;

public class ChooseNationalityPresenter
extends Presenter<BeanItem<Nationality>, ICoopContext, ICollectionModalView<Nationality>>
implements ICollectionModalView.ICollectionProvider<Nationality>
{
	private StudentsManager manager;

	public ChooseNationalityPresenter(ICollectionModalView<Nationality> view)
	{
		super(view);
	}

	@Override
	public Collection<Nationality> getContainer()
	{
		if (manager == null) return null;
		
		return manager.getNationalities();
	}

	@Override
	public Nationality getDefault()
	{
		return manager.getNationality("GR");
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		manager = session.getStudentsManager();
		
		if (manager == null)
		{
			getContext().showAccessDeniedNotification();
		}
	}

	@Override
	protected void setupView()
	{
		if (manager != null)
		{
			getView().setCollectionProvider(this);
			getView().dataBind();
		}
	}

}
