package softeng.coop.ui.viewdefinitions;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.viewmodels.TabSpecification;
import softeng.ui.vaadin.mvp.*;
import java.util.*;

public interface ITabView 
	extends IView<List<TabSpecification>, ICoopContext>
{
	void refreshTabsVisibility();
}
