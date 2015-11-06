package softeng.coop.ui.composites;

import softeng.coop.dataaccess.Student;
import softeng.coop.ui.*;
import softeng.ui.vaadin.mvp.*;

/**
 * Base class for all UI components of the Coop system.
 * @param <M> The type of the model.
 */
public abstract class CoopComponent<M> 
	extends ModelCustomComponent<M, ICoopContext>
{
	private static final long serialVersionUID = 1L;
	
	public CoopComponent()
	{
	}
	
	public CoopComponent(String caption)
	{
		this.setCaption(caption);
	}

	@Override
	public ICoopContext getContext()
	{
		//return (ICoopContext)this.getApplication();
		
		return CoopApplicationServlet.getCurrentApplication();
	}

	/**
	 * Returns true if the current user is Student.
	 */
	protected boolean isUserStudent()
	{
		return getContext().getSession().getAuthenticatedUser() instanceof Student;
	}
}
