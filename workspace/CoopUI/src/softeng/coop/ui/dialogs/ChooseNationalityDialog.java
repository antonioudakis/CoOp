package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.composites.TableComponent.ColumnSpecification;
import softeng.coop.ui.presenters.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class ChooseNationalityDialog
extends ChooseItemDialog<Nationality>
{
	private static List<ColumnSpecification> columnSpecifications = 
		createColumnSpecifications();

	public ChooseNationalityDialog()
	{
		super(Nationality.class, columnSpecifications);
	}

	private static List<ColumnSpecification> createColumnSpecifications()
	{
		ArrayList<ColumnSpecification> list = new ArrayList<ColumnSpecification>();
		
		list.add(new ColumnSpecification("name", "NAME_CAPTION"));
		list.add(new ColumnSpecification("code", "CODE_CAPTION"));
		
		return list;
	}

	@Override
	protected Presenter<BeanItem<Nationality>, ICoopContext, ? extends IView<BeanItem<Nationality>, ICoopContext>> supplyPresenter()
	{
		return new ChooseNationalityPresenter(this);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}

}
