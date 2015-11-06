package softeng.coop.ui.viewdefinitions.viewmodels;

import softeng.coop.ui.*;

/**
 * Interface contract to control a component's visibility based 
 * on the current context. Used to control visibility of tabs.
 */
public interface IComponentVisibility
{
	/**
	 * Decide if a given component should be visible.
	 * @param context The context.
	 * @return True if the component should be visible, false otherwise.
	 */
	boolean isVisible(ICoopContext context);
}
