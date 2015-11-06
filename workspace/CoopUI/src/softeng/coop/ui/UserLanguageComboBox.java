package softeng.coop.ui;

import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.ui.*;

/**
 * A combo box to be used to select the current language
 * from any place in the UI. Synchronizes with the global language select combo.
 * Its main purpose is to appear within multilingual modal dialogs,
 * where the global language select is not accessible.
 * Its values are of type LanguageInfo.
 */
@SuppressWarnings("serial")
public class UserLanguageComboBox
extends ComboBox
{
	private static final long serialVersionUID = 1L;
	
	private IHeaderView headerView;

	public UserLanguageComboBox()
	{
		setTextInputAllowed(false);
		setImmediate(true);
		setNullSelectionAllowed(false);
		
		CoopApplication application = CoopApplicationServlet.getCurrentApplication();
		
		if (application == null) return;
		
		headerView = application.getHeaderView();
		
		if (headerView == null) return;
		
		BeanItemContainer<LanguageInfo> container = 
			new BeanItemContainer<LanguageInfo>(
					LanguageInfo.class, 
					headerView.getModel().getLanguageInfos());
		
		setContainerDataSource(container);
		
		setItemCaptionPropertyId("languageName");
		setItemIconPropertyId("icon");
		
		setValue(headerView.getSelectedLanguageInfo());
		
		addListener(new Property.ValueChangeListener()
		{
			@Override
			public void valueChange(Property.ValueChangeEvent event)
			{
				if (headerView != null) 
					headerView.setSelectedLanguageInfo((LanguageInfo)event.getProperty().getValue());
			}
		});
	}

	public UserLanguageComboBox(String caption)
	{
		this();
		
		setCaption(caption);
	}

	@Override
	public void requestRepaint()
	{
		super.requestRepaint();
		
		if (headerView == null) return;

		setValue(headerView.getSelectedLanguageInfo());
	}

}
