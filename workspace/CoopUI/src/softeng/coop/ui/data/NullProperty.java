package softeng.coop.ui.data;

import com.vaadin.data.Property;

public class NullProperty implements Property
{
	private static final long serialVersionUID = 1L;

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException
	{
	}
	
	@Override
	public void setReadOnly(boolean newStatus)
	{
	}
	
	@Override
	public boolean isReadOnly()
	{
		return true;
	}
	
	@Override
	public Object getValue()
	{
		return "-";
	}
	
	@Override
	public Class<?> getType()
	{
		return String.class;
	}
	
	@Override
	public String toString()
	{
		return "-";
	}
}
