package softeng.coop.ui.viewdefinitions;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;
import softeng.ui.vaadin.mvp.*;

public interface IHeaderView 
	extends IView<HeaderViewModel, ICoopContext>
{
	LanguageInfo getSelectedLanguageInfo();
	void setSelectedLanguageInfo(LanguageInfo languageInfo);
	
	// Subscription for "language changed" event.
	void addLanguageChangedListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);
	void removeLanguageChangedListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);

	CoOp getSelectedCoop();

	// Subscription for "default co-op changed" event.
	void addSelectedCoopChangedListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);
	void removeSelectedCoopChangedListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);

	// Fire 'suggest registration' event.
	void fireSuggestRegistration();
	
	// Subscription for 'suggest registration' event.
	void addSuggestRegistrationListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);
	void removeSuggestRegistrationListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);
	
	// Subscription for 'register' event.
	void addRegisterListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);
	void removeRegisterListener(IViewListener<HeaderViewModel, ICoopContext, IHeaderView> listener);
	
	/**
	 * Re-attach all bound data to the current session.
	 */
	void reattachToSession();
	
}
