package softeng.coop.ui.viewdefinitions;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

public interface IOkCancelView extends IView<OkCancelViewModel, ICoopContext>
{
	/**
	 * The Execute event, which is fired before the OK event. 
	 * If the vote supplied by the listeners marks failure, 
	 * or if an exception is thrown, the OK
	 * event will not be subsequently invoked.
	 */
	void addExecuteListener(IListener<CommandExecutionVote> listener);
	void removeExecuteListener(IListener<CommandExecutionVote> listener);
	
	
	/**
	 * The OK event is fired after the Execute event.
	 */
	void addOkListener(IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView> listener);
	void removeOkListener(IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView> listener);

	/**
	 * The Cancel event.
	 */
	void addCancelListener(IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView> listener);
	void removeCancelListener(IViewListener<OkCancelViewModel, ICoopContext, IOkCancelView> listener);
	
	/**
	 * If the Execute or OK event result in an exception, the OkFailed event is fired.
	 */
	void addOkFailedListener(IListener<RuntimeException> listener);
	void removeOkFailedListener(IListener<RuntimeException> listener);
	
	boolean isReadOnly();
	void setReadOnly(boolean readOnly);
	
	/**
	 * Determines if the keyboard shortcuts corresponding to the current mode are enabled.
	 * Default is true.
	 */
	boolean areShortcutsEnabled();
	/**
	 * Determines if the keyboard shortcuts corresponding to the current mode are enabled.
	 * Default is true.
	 */
	void setShortcutsEnabled(boolean shortcutsEnabled);
}
