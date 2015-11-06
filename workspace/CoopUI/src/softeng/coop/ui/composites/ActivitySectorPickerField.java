package softeng.coop.ui.composites;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

public class ActivitySectorPickerField
extends PickerField<ActivitySector>
{
	private static final long serialVersionUID = 1L;
	
	public ActivitySectorPickerField()
	{
		super("code");
	}
	
	public ActivitySectorPickerField(String caption)
	{
		this();
		setCaption(caption);
	}

	@Override
	public Class<?> getType()
	{
		return ActivitySector.class;
	}

	@Override
	protected IModalView<BeanItem<ActivitySector>> showBrowseForm(BeanItem<ActivitySector> item)
	{
		ChooseActivitySectorDialog dialog = new ChooseActivitySectorDialog();
		
		dialog.setModel(item);
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}
}
