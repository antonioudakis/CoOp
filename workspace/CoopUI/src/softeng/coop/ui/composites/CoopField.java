package softeng.coop.ui.composites;

import softeng.coop.ui.*;
import softeng.ui.vaadin.mvp.*;

public abstract class CoopField<M> extends ModelField<M, ICoopContext>
{
	private static final long serialVersionUID = 1L;
	
	public CoopField()
	{
	}
	
	public CoopField(String caption)
	{
		super(caption);
	}

	@Override
	public ICoopContext getContext()
	{
		//return (ICoopContext)this.getApplication();
		
		return CoopApplicationServlet.getCurrentApplication();
	}

}
