package softeng.ui.vaadin.data;

import java.beans.*;
import com.vaadin.data.util.*;

/**
 * Variation of NestedMethodProperty which allows nulls
 * in the property path. If some property in the chain is null, 
 * getValue() returns null, setValue() does nothing, and the property is marked
 * as read-only. 
 */
public class NullableNestedProperty
extends AbstractProperty
{
	private static final long serialVersionUID = 1L;
	
	private Object instance;
	private PropertyDescriptor[] descriptors;
	
	public NullableNestedProperty(Object instance, String propertyPath)
	{
		if (instance == null) 
			throw new IllegalArgumentException("Argument 'instance' must not be null.");
		if (propertyPath == null) 
			throw new IllegalArgumentException("Argument 'propertyPath' must not be null.");
		
		this.instance = instance;
		
		String[] pathElements = propertyPath.split("\\.");
		
		if (pathElements.length == 0)
			throw new IllegalArgumentException("'propertyPath' does not specify any property");
		
		descriptors = new PropertyDescriptor[pathElements.length];
		
		Class<?> holderType = instance.getClass();

		for (int i = 0; i < pathElements.length; i++)
		{
			String pathElement = pathElements[i];

			try
			{	
				descriptors[i] = new PropertyDescriptor(pathElement, holderType);
				
				holderType = descriptors[i].getPropertyType();
			}
			catch (IntrospectionException ex)
			{
				throw new RuntimeException(
						String.format(
								"Property path element '%s' of property path '%s' cannot be accessed.", 
								pathElement, 
								propertyPath), 
						ex);
			}
		}

	}

	@Override
	public Object getValue()
	{
		Object value = instance;
		
		try
		{
			for (int i = 0; i < descriptors.length; i++)
			{
				value = descriptors[i].getReadMethod().invoke(value);
				if (value == null) return null;
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Cannot access property", ex);
		}

		return value;
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException
	{
		Object value = instance;
		
		try
		{
			for (int i = 0; i < descriptors.length - 1; i++)
			{
				value = descriptors[i].getReadMethod().invoke(value);
				if (value == null) return;
			}
			
			descriptors[descriptors.length - 1].getWriteMethod().invoke(value, newValue);
			
			fireValueChange();
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Cannot write property", ex);
		}

	}

	@Override
	public Class<?> getType()
	{
		return descriptors[descriptors.length - 1].getPropertyType();
	}

	@Override
	public boolean isReadOnly()
	{
		if (isPathChainNull()) return true;
		
		return super.isReadOnly();
	}
	
	@Override
  public void setReadOnly(boolean newStatus) 
	{
		if (isPathChainNull()) return;

		super.setReadOnly(newStatus);
	}

	private boolean isPathChainNull()
	{
		Object value = instance;
		
		try
		{
			for (int i = 0; i < descriptors.length - 1; i++)
			{
				value = descriptors[i].getReadMethod().invoke(value);
				if (value == null) return true;
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Cannot write property", ex);
		}
		
		return false;
	}
}
