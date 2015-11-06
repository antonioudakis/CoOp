package softeng.coop.ui.viewdefinitions.viewmodels;

import com.vaadin.ui.*;

/**
 * Factory contract to create components.
 * Used in tab specifications.
 */
public interface IComponentFactory
{
	/**
	 * Create the component.
	 */
	Component createComponent();
}
