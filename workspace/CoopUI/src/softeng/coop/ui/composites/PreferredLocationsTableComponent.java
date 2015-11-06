package softeng.coop.ui.composites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.util.BeanItem;

import softeng.coop.business.Session;
import softeng.coop.business.locations.LocationsManager;
import softeng.coop.dataaccess.Location;
import softeng.coop.dataaccess.Registration;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.dialogs.HierarchyDialog;
import softeng.coop.ui.presenters.LocationPickerPresenter.LocationsHierarchicalModel;
import softeng.coop.ui.viewdefinitions.IModalView;
import softeng.ui.vaadin.data.HierarchicalBeanItemContainer;
import softeng.ui.vaadin.mvp.IView;
import softeng.ui.vaadin.mvp.Presenter;

public class PreferredLocationsTableComponent 
	extends TableComponent<Registration, Location>
{
	private static final long serialVersionUID = 1L;

	public PreferredLocationsTableComponent(
			List<TableComponent.ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}
	
	public PreferredLocationsTableComponent()
	{
		super(getDefaltColumnSpecifications());
	}

	public PreferredLocationsTableComponent(String caption)
	{
		super(caption, getDefaltColumnSpecifications());
	}
	
	private static List<TableComponent.ColumnSpecification> getDefaltColumnSpecifications()
	{
		List<TableComponent.ColumnSpecification> specifications =
			new ArrayList<TableComponent.ColumnSpecification>();
		
		TableComponent.ColumnSpecification nameSpecification = 
			new ColumnSpecification("name", "NAME_CAPTION");
		
		specifications.add(nameSpecification);
		
		return specifications;
	}
	
	@Override
	public Class<?> getType() 
	{
		return Location.class;
	}

	@Override
	protected IModalView<BeanItem<Location>> showAddForm(BeanItem<Location> item) 
	{
		return showForm(item);
	}

	private IModalView<BeanItem<Location>> showForm(BeanItem<Location> item)
	{
		HierarchyDialog<Location> dialog = 
			new HierarchyDialog<Location>(
					this.getHierarchy(), 
					"name");
		
		dialog.setModal(true);
		
		dialog.setModel(createItem(getDefault()));
		
		dialog.setCaption(getContext().getLocalizedString("CHOOSE_LOCATION_CAPTION"));
		
		this.getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Location createNewElement() 
	{
		return null;
	}

	@Override
	protected Presenter<Collection<Location>, ICoopContext, 
				? extends IView<Collection<Location>, 
				ICoopContext>> supplyPresenter() 
	{
		//return new PreferedLocationTablePresenter(this);
		return null;
	}
	
	private HierarchicalBeanItemContainer<Location> getHierarchy()
	{
		Session session = this.getContext().getSession();
		
		LocationsManager manager = session.getLocationsManager();
		
		if (manager == null)
		{
			return null;
		}
		
		List<Location> rootLocations = manager.getRootLocations();
		
		HierarchicalBeanItemContainer<Location> container =
			new HierarchicalBeanItemContainer<Location>(
					Location.class, 
					rootLocations, 
					new LocationsHierarchicalModel());
		
		return container;
	}

	private Location getDefault()
	{
		Session session = this.getContext().getSession();
		
		LocationsManager manager = session.getLocationsManager();
		
		if (manager == null)
		{
			return null;
		}
		
		return manager.getLocation(32811); // Greece.
	}

	@Override
	protected IModalView<BeanItem<Location>> showEditForm(
			BeanItem<Location> item) 
	{
		return null;
	}

	
}
