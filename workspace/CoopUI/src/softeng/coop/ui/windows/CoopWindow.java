package softeng.coop.ui.windows;

import softeng.coop.ui.*;
import softeng.ui.vaadin.mvp.*;

public abstract class CoopWindow<M> extends ModelWindow<M, ICoopContext>
{
	private static final long serialVersionUID = 1L;

	@Override
	public ICoopContext getContext()
	{
		return (ICoopContext)this.getApplication();
	}

}
