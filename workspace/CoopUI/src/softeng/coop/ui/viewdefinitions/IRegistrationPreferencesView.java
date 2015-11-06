package softeng.coop.ui.viewdefinitions;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.ui.vaadin.mvp.*;

public interface IRegistrationPreferencesView extends IFormView<BeanItem<Registration>> 
{
	void setHorizontal(boolean horizontal);
	
	boolean isReadOnly();
	void setReadOnly(boolean readOnly);
	
	/**
	 * The selected location from the list of preferred locations, or null if no selection exists.
	 */
	public BeanItem<Location> getSelectedPreferredLocation();
	
	/**
	 * The selected category from the list of preferred categories, or null if no selection exists.
	 */
	public BeanItem<Category> getSelectedPreferredCategory();
	
	/**
	 * The selected job posting from the list of preferred job postings, or null if no selection exists.
	 */
	public BeanItem<JobPosting> getSelectedPreferredJobPosting();

	/**
	 * Add a listener to the event of preferred location selection change. 
	 */
	void addSelectedPreferredLocationChangeListener(IListener<ModelEvent<Location>> listener);
	/**
	 * Remove a listener from the event of preferred location selection change. 
	 */
	void removeSelectedPreferredLocationChangeListener(IListener<ModelEvent<Location>> listener);
	
	/**
	 * Add a listener to the event of preferred category selection change. 
	 */
	void addSelectedPreferredCategoryChangeListener(IListener<ModelEvent<Category>> listener);
	/**
	 * Remove a listener from the event of preferred category selection change. 
	 */
	void removeSelectedPreferredCategoryChangeListener(IListener<ModelEvent<Category>> listener);
	
	/**
	 * Add a listener to the event of preferred job posting selection change. 
	 */
	void addSelectedPreferredJobPostingChangeListener(IListener<ModelEvent<JobPosting>> listener);
	/**
	 * Remove a listener from the event of preferred job posting selection change. 
	 */
	void removeSelectedPreferredJobPostingChangeListener(IListener<ModelEvent<JobPosting>> listener);
	
}
