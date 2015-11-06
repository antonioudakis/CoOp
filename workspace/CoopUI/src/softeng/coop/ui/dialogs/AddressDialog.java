package softeng.coop.ui.dialogs;

import java.util.Locale;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

public class AddressDialog
	extends CoopDialog<BeanItem<Address>>
{
	private static final long serialVersionUID = 1L;
	
	private AddressComponent addressComponent;
	
	public AddressDialog()
	{
		this.addressComponent = new AddressComponent();
		this.addComponent(addressComponent);
		
		this.addressComponent.setReadOnly(this.isReadOnly());
		
		this.setWidth("600px");
		this.setHeight("460px");
	}
	
	@Override
	public void dataBind()
	{
		this.addressComponent.dataBind();
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		this.addressComponent.setWidth("100%");
		
		this.getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				if (!addressComponent.isDataValid())
				{
					event.markAsFailed();
					getContext().showInvalidDataNotification();
					return;
				}
				
				addressComponent.commitChangesToModel();
			}
		});
		
	}
	
	@Override
	public void setModel(BeanItem<Address> address)
	{
		super.setModel(address);
		
		this.addressComponent.setModel(address);
	}

	@Override
	public boolean isDataValid()
	{
		return this.addressComponent.isDataValid();
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}

	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		addressComponent.setReadOnly(readOnly);
	}
	
}
