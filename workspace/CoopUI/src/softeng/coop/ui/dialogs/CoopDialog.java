package softeng.coop.ui.dialogs;

import com.vaadin.ui.*;

import softeng.coop.ui.*;
import softeng.coop.ui.composites.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

import softeng.coop.dataaccess.*;

public abstract class CoopDialog<M>
	extends CoopWindow<M>
	implements IModalView<M>
{
	private static final long serialVersionUID = 1L;

	private VerticalLayout outerLayout;
	
	private ComponentContainer innerLayout;
	
	private OkCancelComponent okCancelComponent;
	
	private boolean readOnly;
	
	public CoopDialog()
	{
		this.setWidth("600px");
		this.setHeight("400px");

		//this.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
		
		this.outerLayout = new VerticalLayout();
		this.outerLayout.setWidth("100%");
		this.outerLayout.setHeight("100%");
		this.outerLayout.setMargin(true);
		this.outerLayout.setSpacing(true);
		
		super.setContent(outerLayout);
		
		VerticalLayout innerVerticalLayout = new VerticalLayout();
		innerVerticalLayout.setSizeFull();
		//innerVerticalLayout.setWidth("100%");
		innerVerticalLayout.setSpacing(true);
		this.outerLayout.addComponent(innerVerticalLayout);
		this.outerLayout.setExpandRatio(innerVerticalLayout, 1.0f);
		this.outerLayout.setComponentAlignment(innerVerticalLayout, Alignment.TOP_LEFT);
		this.innerLayout = innerVerticalLayout;
		
		this.okCancelComponent = new OkCancelComponent();
		this.outerLayout.addComponent(okCancelComponent);
		this.outerLayout.setComponentAlignment(okCancelComponent, Alignment.BOTTOM_RIGHT);
		
		this.okCancelComponent.setReadOnly(isReadOnly());

		this.okCancelComponent.addOkListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				if (isDataValid()) closeWindow();
			}
		});
		
		this.okCancelComponent.addCancelListener(new IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView>()
		{
			@Override
			public void onEvent(ViewEvent<OkCancelViewModel, ICoopContext, IOkCancelView> event)
			{
				revertModel();
				closeWindow();
			}
		});
		
		this.getOkCancelView().addOkFailedListener(new IListener<RuntimeException>()
		{
			@Override
			public void onEvent(RuntimeException event)
			{
				revertModel();
			}
		});
		
	}
	
	protected void revertModel()
	{
	}

	public ComponentContainer getOuterComponentContainer()
	{
		return this.outerLayout;
	}

	@Override
	public IOkCancelView getOkCancelView()
	{
		return this.okCancelComponent;
	}

	@Override
	protected Presenter<M, ICoopContext, ? extends IView<M, ICoopContext>> supplyPresenter()
	{
		return null;
	}
	
	@Override
	public Layout getLayout()
	{
		return (Layout)this.getContent();
	}

	@Override
	public void setLayout(Layout newLayout)
	{
		this.setContent(newLayout);
	}

	@Override
	public ComponentContainer getContent()
	{
		return this.innerLayout;
	}

	@Override
	public void setContent(ComponentContainer newContent)
	{
		if (newContent == null) return;
		
		this.outerLayout.removeComponent(this.innerLayout);
		
		this.innerLayout = newContent;
		
		this.outerLayout.addComponentAsFirst(newContent);
		this.outerLayout.setExpandRatio(newContent, 1.0f);
	}

	private void closeWindow()
	{
		if (this.getParent() == null) return;
		
		this.getParent().removeWindow(this);
	}

	@Override
	public void addComponent(Component c)
	{
		this.innerLayout.addComponent(c);
	}
	
	@Override
	public boolean isDataValid()
	{
		// Override to change behavior, 
		// for example to reflect forms validity.
		return true;
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		focus();
	}
	
	@Override
	public boolean isReadOnly()
	{
		return readOnly;
	}

	@Override
	public void setReadOnly(boolean readOnly)
	{
		/* Disable this line, because it is the same as setClosable()! */
		//super.setReadOnly(readOnly);
		
		this.readOnly = readOnly;
		
		okCancelComponent.setReadOnly(readOnly);
	}
	
	/**
	 * Returns true if the current user is Student.
	 */
	protected boolean isUserStudent()
	{
		return getContext().getSession().getAuthenticatedUser() instanceof Student;
	}
}
