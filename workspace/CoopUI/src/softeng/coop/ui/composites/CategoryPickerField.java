package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.presenters.*;

import softeng.ui.vaadin.mvp.*;

public class CategoryPickerField
	extends HierarchicalPickerField<Category>
{
	private static final long serialVersionUID = 1L;
	
	public CategoryPickerField()
	{
		super("name");
	}
	
	public CategoryPickerField(String caption)
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
	protected Presenter<Category, ICoopContext, ? extends IView<Category, ICoopContext>> supplyPresenter()
	{
		return new CategoryPickerPresenter(this);
	}

	@Override
	protected String getBrowseFormCaption()
	{
		return getContext().getLocalizedString("CHOOSE_CATEGORY_CAPTION");
	}

}
