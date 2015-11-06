package softeng.coop.ui.dialogs;

import java.util.Locale;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.composites.ChooseCoopComponent;
import softeng.coop.ui.viewdefinitions.IOkCancelView;
import softeng.coop.ui.viewdefinitions.viewmodels.OkCancelViewModel;
import softeng.ui.vaadin.mvp.IViewListener;
import softeng.ui.vaadin.mvp.ViewEvent;

public class ChooseCoopDialog
	extends CoopDialog<BeanItem<CoOp>>
{
	private static final long serialVersionUID = 1L;
	
	private ChooseCoopComponent chooseCoopComponent;
	
	public ChooseCoopDialog()
	{
		this.setHeight("500px");
		this.setWidth("710px");

		this.getOkCancelView().addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				CoOp selectedCoop = chooseCoopComponent.getSelectedCoOp();
				
				if (selectedCoop != null)
					setModel(new BeanItem<CoOp>(selectedCoop));
				else
					setModel(null);
				
			}
		});
		
		this.chooseCoopComponent = new ChooseCoopComponent();
		this.chooseCoopComponent.setSizeFull();

		this.addComponent(chooseCoopComponent);

	}
	
	public ChooseCoopDialog(String caption)
	{
		this();
		this.setCaption(caption);
	}

	@Override
	public void dataBind()
	{
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);

		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		chooseCoopComponent.setCaption(getLocalizedString("TABLE_CAPTION"));
	}

	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		chooseCoopComponent.setReadOnly(readOnly);
	}
}
