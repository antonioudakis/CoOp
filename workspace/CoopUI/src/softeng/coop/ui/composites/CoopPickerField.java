package softeng.coop.ui.composites;

import com.vaadin.data.util.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.ui.vaadin.mvp.*;

/**
 * A form field for picking co-op.
 */
public class CoopPickerField 
	extends PickerField<CoOp>
{
	private static final long serialVersionUID = 1L;
	
	private ICoopContext context;
	
	public CoopPickerField(ICoopContext context)
	{
		super("name");
		
		this.context = context;
	}
	
	public CoopPickerField(ICoopContext context, String caption)
	{
		this(context);
		this.setCaption(caption);
	}

	@Override
	public Class<?> getType()
	{
		return CoOp.class;
	}
	
	@Override
	public ICoopContext getContext()
	{
		return this.context;
	}

	@Override
	protected IModalView<BeanItem<CoOp>> showBrowseForm(BeanItem<CoOp> item)
	{
		ChooseCoopDialog dialog = new ChooseCoopDialog();
		
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.setModel(item);
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Presenter<CoOp, ICoopContext, ? extends IView<CoOp, ICoopContext>> supplyPresenter()
	{
		return null;
	}

	@Override
	protected BeanItem<CoOp> createBeanItem(CoOp value)
	{
		if (value == null) return null;
		
		if (this.getContext() == null) return super.createBeanItem(value);
		
		Session session = this.getContext().getSession();
		
		if (session != null)
		{
			AuthenticatedUser user = session.getAuthenticatedUser();
			
			ManagerBase manager = null;
			
			if (user instanceof Student)
			{
				manager = session.getStudentsManager();
			}
			else if (user instanceof FacultyUser)
			{
				manager = session.getFacultyUsersManager();
			}
				
			if (manager != null)
				return new DataItem<CoOp>(value, manager);
		}
		
		return super.createBeanItem(value);
	}

}
