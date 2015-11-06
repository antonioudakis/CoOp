package softeng.ui.vaadin.data;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import com.vaadin.data.Property;
import com.vaadin.data.util.VaadinPropertyDescriptor;

public class NullableNestedPropertyDescriptor<T>
implements VaadinPropertyDescriptor<T>
{
	private static final long serialVersionUID = 1L;
	
	private String propertyPath;
	
	private Class<?> propertyType;
	
	public NullableNestedPropertyDescriptor(String propertyPath, Class<T> rootType)
	{
		if (propertyPath == null) 
			throw new IllegalArgumentException("Argument 'name' must not be null.");
		
		this.propertyPath = propertyPath;
		
		String[] pathElements = propertyPath.split("\\.");
		
		if (pathElements.length == 0)
			throw new IllegalArgumentException("'propertyPath' does not specify any property");
		
		propertyType = rootType;

		for (int i = 0; i < pathElements.length; i++)
		{
			String pathElement = pathElements[i];

			try
			{	
				PropertyDescriptor descriptor = new PropertyDescriptor(pathElement, propertyType);
				propertyType = descriptor.getPropertyType();
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
	public String getName()
	{
		return propertyPath;
	}

	@Override
	public Class<?> getPropertyType()
	{
		return propertyType;
	}

	@Override
	public Property createProperty(T bean)
	{
		return new NullableNestedProperty(bean, propertyPath);
	}
}
