package softeng.coop.ui.windows;

import java.util.Locale;

import com.vaadin.ui.*;

import softeng.coop.business.authentication.*;
import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.IEnrollmentView;
import softeng.ui.vaadin.mvp.*;

public class EnrollmentWindow extends CoopWindow<IUser>
{
	private static final long serialVersionUID = 1L;

	@Override
	protected Presenter<IUser, ICoopContext, ? extends IView<IUser, ICoopContext>> supplyPresenter()
	{
		return new Presenter<IUser, ICoopContext, IView<IUser,ICoopContext>>(this)
		{
			@Override
			protected void setupView()
			{
				this.getView().dataBind();
			}

			@Override
			protected void attachToView()
			{
			}
		};
	}

	@Override
	public void dataBind()
	{
		// This is NOP.
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		this.setCaption(getLocalizedString("TITLE"));
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		this.setClosable(false);
		this.setWidth("600px");
		this.getContent().setSizeFull();
		//this.setModal(true);
		
		EnrollmentComponent component = new EnrollmentComponent();
		
		component.setSizeFull();
		
		this.addComponent(component);
		
		component.addRegistrationSucceededListener(new IViewListener<IUser, ICoopContext, IEnrollmentView>()
		{
			@Override
			public void onEvent(ViewEvent<IUser, ICoopContext, IEnrollmentView> event)
			{
				Window parent = getParent();
				Window me = getWindow();
				parent.removeWindow(me);
			}
		});
		
	}

}
