package softeng.coop.ui.composites;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.dialogs.*;

public class FinancialSourcePickerField
extends PickerField<FinancialSource>
{
	private static final long serialVersionUID = 1L;
	
	private CoOp coop;
	
	/**
	 * Create.
	 * @param coop If not null, choose among the coop's sources, else from all sources.
	 */
	public FinancialSourcePickerField(CoOp coop)
	{
		super("name");

		this.coop = coop;
	}
	
	@Override
	public Class<?> getType()
	{
		return FinancialSource.class;
	}

	@Override
	protected IModalView<BeanItem<FinancialSource>> showBrowseForm(BeanItem<FinancialSource> item)
	{
		if (!isReadOnly())
		{
			ChooseFinancialSourceDialog dialog = new ChooseFinancialSourceDialog(coop);
			
			dialog.setModal(true);
			dialog.setModel(item);
			
			getApplication().getMainWindow().addWindow(dialog);
			
			dialog.dataBind();
			
			return dialog;
		}
		else
		{
			if (item == null) return null;
			
			FinancialSourceDialog dialog = new FinancialSourceDialog();
			
			dialog.setModal(true);
			dialog.setModel(item);
			
			getApplication().getMainWindow().addWindow(dialog);
			
			dialog.dataBind();
			
			return dialog;
		}
	}

	/**
	 * If not null, choose among the coop's sources, else from all sources.
	 */
	public CoOp getCoop()
	{
		return coop;
	}
	public void setCoop(CoOp coop)
	{
		this.coop = coop;
	}

}
