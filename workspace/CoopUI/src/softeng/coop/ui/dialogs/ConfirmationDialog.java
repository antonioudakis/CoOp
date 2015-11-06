package softeng.coop.ui.dialogs;

import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.composites.OkCancelComponent;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;
import softeng.ui.vaadin.mvp.IViewListener;
import softeng.ui.vaadin.mvp.ViewEvent;

import com.vaadin.ui.*;

public class ConfirmationDialog extends Window
{
	private static final long serialVersionUID = 1L;
	
	private OkCancelComponent okCancelComponent;

	public ConfirmationDialog(String caption, String description)
	{
		super(caption);
		
		if (description == null) 
			throw new IllegalArgumentException("Argument 'description' must not be null.");
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		
		Label descriptionLabel = new Label(description);
		
		descriptionLabel.setWidth("100%");
		layout.addComponent(descriptionLabel);
		
		this.okCancelComponent = new OkCancelComponent();
		
		layout.addComponent(this.okCancelComponent);
		layout.setComponentAlignment(okCancelComponent, Alignment.BOTTOM_RIGHT);
		
		this.okCancelComponent.addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				removeWindow();
			}
		});
		
		this.okCancelComponent.addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				removeWindow();
			}
		});
		
		this.setContent(layout);
		this.setModal(true);
		this.setSizeUndefined();
		this.setWidth("360px");
	}
	
	private void removeWindow()
	{
		Window parent = this.getParent();
		
		parent.removeWindow(this);
	}
	
	public IOkCancelView getOkCancelView()
	{
		return this.okCancelComponent;
	}

}
