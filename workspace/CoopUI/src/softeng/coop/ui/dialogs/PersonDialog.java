package softeng.coop.ui.dialogs;

import java.util.*;

import softeng.coop.dataaccess.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.Panel;

import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.CommandExecutionVote;
import softeng.ui.vaadin.mvp.IListener;

public class PersonDialog<M extends Person>
extends CoopDialog<BeanItem<M>>
{
	private static final long serialVersionUID = 1L;

	private PersonDataComponent personDataComponent;

	@Override
	public void dataBind()
	{
		personDataComponent.setModel(getModel());
		personDataComponent.dataBind();
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setWidth("600px");
		setHeight("500px");
		
		Panel panel = new Panel();
//		panel.setWidth("100%");
//		panel.setHeight("360px");
		panel.setSizeFull();
		addComponent(panel);
		
		personDataComponent = new PersonDataComponent();
		personDataComponent.setWidth("100%");
		panel.addComponent(personDataComponent);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onAccept(event);
			}
		});
	}

	protected void onAccept(CommandExecutionVote event)
	{
		if (personDataComponent.isDataValid())
		{
			personDataComponent.commitChangesToModel();
		}
		else
		{
			event.markAsFailed();
			getContext().showInvalidDataNotification();
		}
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}

	@Override
	public boolean isDataValid()
	{
		return personDataComponent.isDataValid();
	}

	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		personDataComponent.setReadOnly(readOnly);
	}
}
