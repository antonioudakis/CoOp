package softeng.coop.ui;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.viewdefinitions.IHeaderView;
import softeng.ui.vaadin.mvp.*;

import java.util.*;
import com.vaadin.ui.*;

/**
 * Contract for the Coop application context.
 */
/**
 * @author Master
 *
 */
/**
 * @author Master
 *
 */
public interface ICoopContext
{
	/**
	 * Get the coop business session associated with the current user, 
	 * if authenticated and registered, else return null.
	 */
	Session getSession();
	
	/**
	 * The desired locale for the current user.
	 */
	Locale getLocale();

	/**
	 * The desired locale for the current user.
	 */
	void setLocale(Locale locale);
	
	/**
	 * Get the main window of the application.
	 */
	Window getMainWindow();
	
	/**
	 * Access the header view of the application.
	 */
	IHeaderView getHeaderView();
	
	/**
	 * Get a localized string from the global strings table.
	 * This is for common reusable strings between views or presenters.
	 * For more specialized messages, please use the analogous getLocalizedString method
	 * of the IView or the Presenter contract for accessing strings local to 
	 * an IView implementation or a Presenter implementation. 
	 * @param key The key of the string
	 * @return The requested string, localized to the user's language preferences if possible. 
	 */
	String getLocalizedString(String key);
	
	/**
	 * Add subscriber to the 'login' event.
	 */
	void addLoginListener(IListener<AuthenticatedUser> listener);
	
	/**
	 * Remove subscriber from the 'login' event.
	 */
	void removeLoginListener(IListener<AuthenticatedUser> listener);
	
	CoOp getSelectedCoop();
	
	/**
	 * Add subscriber to 'selected coop changed' event.
	 * This event is fired when the user switches to a different coop 
	 * or if the selected coop properties change.
	 */
	void addSelectedCoopChangedListener(IListener<CoOp> listener);
	
	/**
	 * Remove subscriber from 'selected coop changed' event.
	 * This event is fired when the user switches to a different coop 
	 * or if the selected coop properties change.
	 */
	void removeSelectedCoopChangedListener(IListener<CoOp> listener);
	
	/**
	 * Fire the 'selected coop changed' event.
	 * This is called automatically by the system,
	 * but in special cases it could be called manually,
	 * for example from a coop properties form which modifies coop data.
	 */
	void fireSelectedCoopChanged();

	/**
	 * Show an 'access denied' notification.  
	 */
	void showAccessDeniedNotification();

	/**
	 * Show an 'invalid data' notification. This is typically displayed
	 * while attempting to persist fields in a form which are not valid. 
	 */
	void showInvalidDataNotification();
	
	/**
	 * Show a 'data saved' notification. This is typically displayed 
	 * after a successful save action or any other successful transaction. 
	 */
	void showDataSavedNotification();
	
	/**
	 * Show an 'entity in use' notification. This is typically displayed to warn that
	 * a user action was canceled because it would violate integrity constraints of the model.  
	 * @param caption The reason of the action cancellation. Typically it is the summary of the violated constraints.
	 */
	void showEntityInUseNotification(String caption);
	
	/**
	 * Show a standard Vaadin notification.
	 * @param caption The caption of the message.
	 * @param description  The description underneath the caption.
	 * @param notificationType A constant specified in com.vaadin.ui.Window.Notification.
	 */
	void showNotification(
			String caption, 
			String description, 
			int notificationType);

	/**
	 * Show a standard Vaadin notification.
	 * @param caption The caption of the message.
	 * @param description  The description underneath the caption.
	 * @param notificationType A constant specified in com.vaadin.ui.Window.Notification.
	 * @param mSecDelay Delay in milliseconds, or the special values Notification.DELAY_FOREVER and Notification.DELAY_NONE
	 */
	void showNotification(
			String caption, 
			String description, 
			int notificationType,
			int mSecDelay);

	/**
	 * If the current user is a student with valid data,
	 * suggest coops to register to, if any,
	 * else do nothing.
	 */
	void suggestRegistration();
}
