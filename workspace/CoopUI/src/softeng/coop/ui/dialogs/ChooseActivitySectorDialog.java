package softeng.coop.ui.dialogs;

import java.util.Locale;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.*;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public class ChooseActivitySectorDialog
extends CoopDialog<BeanItem<ActivitySector>>
implements IChooseActivitySectorView
{
	private static final long serialVersionUID = 1L;
	
	private TextField codeTextField = new TextField();
	
	private Tree tree = new Tree();
	
	private HierarchicalBeanItemContainer<ActivitySector> container;
	
	private Button searchButton = new Button();
	
	private Panel treePanel = new Panel();

	private ViewEventSubscription<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView> searchEventSubscription = 
		new ViewEventSubscription<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView>();

	@Override
	public void dataBind()
	{
		DataUtilities.expandTree(tree, container, getModel());
	}

	@SuppressWarnings("serial")
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setWidth("600px");
		setHeight("600px");
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		
		setLayout(verticalLayout);
		
		treePanel.setSizeFull();
		verticalLayout.addComponent(treePanel);
		verticalLayout.setExpandRatio(treePanel, 1.0f);
		
		tree.setSizeFull();
		tree.setImmediate(true);
		tree.setItemCaptionMode(Tree.ITEM_CAPTION_MODE_PROPERTY);
		tree.setItemCaptionPropertyId("description");
		tree.setItemDescriptionGenerator(new AbstractSelect.ItemDescriptionGenerator()
		{
			@Override
			public String generateDescription(Component source, Object itemId, Object propertyId)
			{
				if (itemId == null) return "";
				
				return ((ActivitySector)itemId).getCode();
			}
		});
		tree.addListener(new Property.ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				onSelectedChange((ActivitySector)tree.getValue());
			}
		});
		treePanel.addComponent(tree);
		
		HorizontalLayout codeSearchLayout = new HorizontalLayout();
		codeSearchLayout.setWidth("100%");
		codeSearchLayout.setSpacing(true);
		
		codeTextField.setWidth("100%");
		codeSearchLayout.addComponent(codeTextField);
		codeSearchLayout.setExpandRatio(codeTextField, 1.0f);
		
		searchButton.setWidth("120px");
		searchButton.setIcon(new ThemeResource("../images/actions/find.png"));
		searchButton.addListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				onSearch();
			}
		});
		
		codeSearchLayout.addComponent(searchButton);
		codeSearchLayout.setComponentAlignment(searchButton, Alignment.BOTTOM_RIGHT);
		
		verticalLayout.addComponent(codeSearchLayout);
		verticalLayout.setExpandRatio(codeSearchLayout, 0.0f);
		
	}

	protected void onSelectedChange(ActivitySector value)
	{
		if (value == null) return;
		
		codeTextField.setValue(value.getCode());
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		searchButton.setCaption(getLocalizedString("SEARCH_CAPTION"));
		searchButton.setDescription(getLocalizedString("SEARCH_DESCRIPTION"));
		
		codeTextField.setCaption(getLocalizedString("CODE_CAPTION"));
		codeTextField.setCaption(getLocalizedString("CODE_DESCRIPTION"));
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}

	@Override
	public String getCurrentCode()
	{
		return (String)codeTextField.getValue();
	}

	@Override
	public void addSearchListener(IViewListener<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView> listener)
	{
		searchEventSubscription.add(listener);
	}

	@Override
	public void removeSearchListener(IViewListener<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView> listener)
	{
		searchEventSubscription.remove(listener);
	}

	@Override
	public HierarchicalBeanItemContainer<ActivitySector> getHierarchicalContainer()
	{
		return container;
	}

	@Override
	public void setHierarchicalContainer(HierarchicalBeanItemContainer<ActivitySector> container)
	{
		this.container = container;
		
		this.tree.setContainerDataSource(container);
	}
	
	private void onSearch()
	{
		ViewEvent<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView> event =
			new ViewEvent<BeanItem<ActivitySector>, ICoopContext, IChooseActivitySectorView>(this);
		
		searchEventSubscription.fire(event);
	}

	@Override
	protected Presenter<BeanItem<ActivitySector>, ICoopContext, ? extends IView<BeanItem<ActivitySector>, ICoopContext>> supplyPresenter()
	{
		return new ChooseActivitySectorPresenter(this);
	}

}
