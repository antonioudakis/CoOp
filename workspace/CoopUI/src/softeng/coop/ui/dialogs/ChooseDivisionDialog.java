package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public class ChooseDivisionDialog
extends CoopDialog<BeanItem<Division>>
{
	private Department department;
	
	private DivisionsTableComponent tableComponent;
	
	public ChooseDivisionDialog(Department department)
	{
		if (department == null) 
			throw new IllegalArgumentException("Argument 'department' must not be null.");
		
		this.department = department;
		
		tableComponent = new DivisionsTableComponent();
		
		tableComponent.setSizeFull();
		
		addComponent(tableComponent);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept();
			}
		});
	}

	@Override
	public void dataBind()
	{
		tableComponent.setParentModel(department);
		
		tableComponent.setModel(department.getDivisions());
		
		tableComponent.dataBind();
		
		if (getModel() != null)
			tableComponent.setSelectedValue(getModel().getBean());
		
	}

	protected void onAccept()
	{
		setModel(tableComponent.getSelectedItem());
	}

	
	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		tableComponent.setCaption(getLocalizedString("TABLE_CAPTION"));
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		tableComponent.setReadOnly(readOnly);
		tableComponent.setUserSelectable(!readOnly);
	}
}
