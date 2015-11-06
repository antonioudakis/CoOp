package softeng.coop.ui.viewdefinitions;

import softeng.coop.business.authentication.*;
import softeng.coop.ui.*;
import softeng.ui.vaadin.mvp.*;

/**
 * Contract for enrollment confirmation view.
 */
public interface IEnrollmentView extends IView<IUser, ICoopContext>
{
	// Event subscription for "Accept".
	void addAcceptListener(IViewListener<IUser, ICoopContext, IEnrollmentView> listener);
	void removeAcceptListener(IViewListener<IUser, ICoopContext, IEnrollmentView> listener);
	
	// Subscription for "registration succeeded" event.
	void addRegistrationSucceededListener(IViewListener<IUser, ICoopContext, IEnrollmentView> listener);
	void removeRegistrationSucceededListener(IViewListener<IUser, ICoopContext, IEnrollmentView> listener);
	void fireRegistrationSucceeded();
}
