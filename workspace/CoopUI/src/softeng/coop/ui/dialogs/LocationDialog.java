package softeng.coop.ui.dialogs;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.Location;
import softeng.coop.ui.composites.LocationPickerField;

public class LocationDialog 
	extends CoopDialog<BeanItem<Location>>
{
	private static final long serialVersionUID = 1L;
		
	private LocationPickerField locationPicker;
	
	public LocationDialog()
	{
		this.locationPicker = new LocationPickerField();
		this.addComponent(locationPicker);
		
		this.setWidth("600px");
		this.setHeight("450px");
	}
	
	@Override
	protected void setupUI() 
	{
		// TODO Auto-generated method stub
		super.setupUI();
	}

	@Override
	public void setModel(BeanItem<Location> model) 
	{
		super.setModel(model);
		this.locationPicker.setModel(model.getBean());
	}

	@Override
	public boolean isDataValid() 
	{
			return this.locationPicker.isValid();
	}

	@Override
	public void dataBind() 
	{
		this.locationPicker.dataBind();
		
	}

}
