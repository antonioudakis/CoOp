package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.business.*;
import softeng.coop.business.locations.*;
import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.IHierarchicalModalView.IHierarchyProvider;
import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

public class LocationPickerPresenter 
	extends Presenter<Location, ICoopContext, IHierarchicalModalView<Location>>
	implements IHierarchyProvider<Location>
{
	public static class LocationsHierarchicalModel
		implements HierarchyModel<Location>
	{
		private LocationsWriterManager manager;
		
		@Override
		public Location getParent(Location item)
		{
			return item.getParentLocation();
		}

		@Override
		public boolean hasChildren(Location item)
		{
			return item.getChildLocations().size() > 0;
		}

		@Override
		public List<Location> getChildren(Location item)
		{
			return new ArrayList<Location>(item.getChildLocations());
		}

		@Override
		public void addChild(Location parent, Location newChild)
		{
			if (this.manager != null)
			{
				parent.getChildLocations().add(newChild);
				newChild.setParentLocation(parent);
				
				manager.persistLocation(newChild);
			}
		}

		@Override
		public boolean removeChild(Location child)
		{
			if (child.getParentLocation()!= null && manager != null)
			{
				child.getParentLocation().getChildLocations().remove(child);
				child.setParentLocation(null);
				
				manager.cascadeDeleteLocation(child.getId());
				
				return true;
			}
			
			return false;
		}

		@Override
		public boolean moveChild(Location child, Location newParent)
		{
			if (manager == null) return false;
			
			return manager.moveLocation(child, newParent);
		}
		
	}

	public LocationPickerPresenter(IHierarchicalModalView<Location> view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
		getView().setHierarchyProvider(this);
	}

	@Override
	public HierarchicalBeanItemContainer<Location> getHierarchy()
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

	@Override
	public Location getDefault()
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
	protected void attachToView()
	{
	}

}
