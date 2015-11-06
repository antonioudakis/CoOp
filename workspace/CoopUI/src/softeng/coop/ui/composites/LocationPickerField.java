package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.ui.vaadin.mvp.*;

public class LocationPickerField 
	extends HierarchicalPickerField<Location>
{
	private static final long serialVersionUID = 1L;

	public LocationPickerField()
	{
		super("name");
	}
	
	public LocationPickerField(String caption)
	{
		this();

		setCaption(caption);
	}

	@Override
	public Class<?> getType()
	{
		return Location.class;
	}

	@Override
	protected Presenter<Location, ICoopContext, IHierarchicalModalView<Location>> supplyPresenter()
	{
		return new LocationPickerPresenter(this);
	}

	@Override
	protected String getBrowseFormCaption()
	{
		return getContext().getLocalizedString("CHOOSE_LOCATION_CAPTION");
	}
}
