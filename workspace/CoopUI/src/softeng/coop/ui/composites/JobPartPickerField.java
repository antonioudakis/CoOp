package softeng.coop.ui.composites;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.data.DataItem;
import softeng.coop.ui.dialogs.*;

@SuppressWarnings("serial")
public class JobPartPickerField
extends PickerField<JobPart>
{
	private Job job;
	
	public JobPartPickerField(Job job)
	{
		super("description");

		if (job == null) 
			throw new IllegalArgumentException("Argument 'job' must not be null.");
		
		this.job = job;
	}

	@Override
	public Class<?> getType()
	{
		return JobPart.class;
	}

	@Override
	protected IModalView<BeanItem<JobPart>> showBrowseForm(BeanItem<JobPart> item)
	{
		ChooseJobPartDialog dialog = new ChooseJobPartDialog(job);
		
		dialog.setModal(true);
		
		if (getModel() != null)
			dialog.setModel(new DataItem<JobPart>(getModel(), getContext().getSession().getBaseManager()));
		else
			dialog.setModel(null);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

}
