package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.dialogs.ChooseNationalityDialog;
import softeng.coop.ui.viewdefinitions.*;

import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public class NationalityPickerField
extends PickerField<Nationality>
{
	public NationalityPickerField()
	{
		super("name");
	}

	@Override
	public Class<?> getType()
	{
		return Nationality.class;
	}

	@Override
	protected IModalView<BeanItem<Nationality>> showBrowseForm(BeanItem<Nationality> item)
	{
		ChooseNationalityDialog dialog = new ChooseNationalityDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		return dialog;
	}

}
