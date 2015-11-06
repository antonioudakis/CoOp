package softeng.coop.ui.viewdefinitions;

import softeng.coop.ui.ICoopContext;
import softeng.ui.vaadin.mvp.IView;

public interface IModalView<M>
	extends IView<M, ICoopContext>
{
	IOkCancelView getOkCancelView();
	
	boolean isDataValid();
	
	boolean isReadOnly();
	void setReadOnly(boolean readOnly);
}
