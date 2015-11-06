package softeng.coop.ui.composites;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;

public class InsuranceContractPickerField
extends PickerField<InsuranceContract>
{
	private static final long serialVersionUID = 1L;
	
	private CoOp coop;
	
	public InsuranceContractPickerField()
	{
		super("name");
	}

	@Override
	public Class<?> getType()
	{
		return InsuranceContract.class;
	}

	@Override
	protected IModalView<BeanItem<InsuranceContract>> showBrowseForm(BeanItem<InsuranceContract> item)
	{
		if (coop == null)
			throw new CoopUISystemException("The 'coop' property has not been set");
		
		ChooseInsuranceContractDialog dialog = new ChooseInsuranceContractDialog(coop);
		
		dialog.setModal(true);
		
		if (getModel() != null)
			dialog.setModel(new BeanItem<InsuranceContract>(getModel()));
		else
			dialog.setModel(null);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	public void setCoop(CoOp coop)
	{
		if (coop == null) 
			throw new IllegalArgumentException("Argument 'coop' must not be null.");
		
		this.coop = coop;
	}

	public CoOp getCoop()
	{
		return coop;
	}

}
