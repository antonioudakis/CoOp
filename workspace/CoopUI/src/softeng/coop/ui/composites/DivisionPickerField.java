package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public class DivisionPickerField
extends PickerField<Division>
{
	private Department department;
	
	public DivisionPickerField(Department department)
	{
		super("name");
		
		if (department == null) 
			throw new IllegalArgumentException("Argument 'department' must not be null.");
		
		this.department = department;
	}

	@Override
	public Class<?> getType()
	{
		return Division.class;
	}

	@Override
	protected IModalView<BeanItem<Division>> showBrowseForm(BeanItem<Division> item)
	{
		ChooseDivisionDialog dialog = new ChooseDivisionDialog(department);
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();

		return dialog;
	}

}
